package com.petersoft;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicDirectoryModel;
import javax.swing.plaf.basic.BasicFileChooserUI;

import sun.awt.shell.ShellFolder;

public class FilterBasicDirectoryModel extends AbstractListModel implements PropertyChangeListener {

	private JFileChooser filechooser = null;
	// PENDING(jeff) pick the size more sensibly
	private Vector fileCache = new Vector(50);
	private LoadFilesThread loadThread = null;
	private Vector files = null;
	private Vector directories = null;
	private int fetchID = 0;
	private String filterText = null;

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
		fileCache.clear();
		validateFileCache();
		fireContentsChanged();
	}

	private PropertyChangeSupport changeSupport;

	private boolean busy = false;

	public FilterBasicDirectoryModel(JFileChooser filechooser) {
		this.filechooser = filechooser;
		validateFileCache();
	}

	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		if (prop == JFileChooser.DIRECTORY_CHANGED_PROPERTY || prop == JFileChooser.FILE_VIEW_CHANGED_PROPERTY || prop == JFileChooser.FILE_FILTER_CHANGED_PROPERTY
				|| prop == JFileChooser.FILE_HIDING_CHANGED_PROPERTY || prop == JFileChooser.FILE_SELECTION_MODE_CHANGED_PROPERTY) {
			validateFileCache();
		} else if ("UI".equals(prop)) {
			Object old = e.getOldValue();
			if (old instanceof BasicFileChooserUI) {
				BasicFileChooserUI ui = (BasicFileChooserUI) old;
				BasicDirectoryModel model = ui.getModel();
				if (model != null) {
					model.invalidateFileCache();
				}
			}
		} else if ("JFileChooserDialogIsClosingProperty".equals(prop)) {
			invalidateFileCache();
		}
	}

	/**
	 * This method is used to interrupt file loading thread.
	 */
	public void invalidateFileCache() {
		if (loadThread != null) {
			loadThread.interrupt();
			loadThread.cancelRunnables();
			loadThread = null;
		}
	}

	public Vector<File> getDirectories() {
		synchronized (fileCache) {
			if (directories != null) {
				return directories;
			}
			Vector fls = getFiles();
			return directories;
		}
	}

	public Vector<File> getFiles() {
		synchronized (fileCache) {
			if (files != null) {
				return files;
			}
			files = new Vector();
			directories = new Vector();
			directories.addElement(filechooser.getFileSystemView().createFileObject(filechooser.getCurrentDirectory(), ".."));

			for (int i = 0; i < getSize(); i++) {
				File f = (File) fileCache.get(i);
				if (filechooser.isTraversable(f)) {
					directories.add(f);
				} else {
					files.add(f);
				}
			}
			return files;
		}
	}

	public void validateFileCache() {
		File currentDirectory = filechooser.getCurrentDirectory();
		if (currentDirectory == null) {
			return;
		}
		if (loadThread != null) {
			loadThread.interrupt();
			loadThread.cancelRunnables();
		}

		setBusy(true, ++fetchID);

		loadThread = new LoadFilesThread(currentDirectory, fetchID);
		loadThread.start();
	}

	/**
	 * Renames a file in the underlying file system.
	 * 
	 * @param oldFile
	 *            a <code>File</code> object representing the existing file
	 * @param newFile
	 *            a <code>File</code> object representing the desired new file
	 *            name
	 * @return <code>true</code> if rename succeeded, otherwise
	 *         <code>false</code>
	 * @since 1.4
	 */
	public boolean renameFile(File oldFile, File newFile) {
		synchronized (fileCache) {
			if (oldFile.renameTo(newFile)) {
				validateFileCache();
				return true;
			}
			return false;
		}
	}

	public void fireContentsChanged() {
		fireContentsChanged(this, 0, getSize() - 1);
	}

	public int getSize() {
		return fileCache.size();
	}

	public boolean contains(Object o) {
		return fileCache.contains(o);
	}

	public int indexOf(Object o) {
		return fileCache.indexOf(o);
	}

	public Object getElementAt(int index) {
		return fileCache.get(index);
	}

	/**
	 * Obsolete - not used.
	 */
	public void intervalAdded(ListDataEvent e) {
	}

	/**
	 * Obsolete - not used.
	 */
	public void intervalRemoved(ListDataEvent e) {
	}

	protected void sort(Vector<? extends File> v) {
		ShellFolder.sortFiles(v);
	}

	// Obsolete - not used
	protected boolean lt(File a, File b) {
		// First ignore case when comparing
		int diff = a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
		if (diff != 0) {
			return diff < 0;
		} else {
			// May differ in case (e.g. "mail" vs. "Mail")
			return a.getName().compareTo(b.getName()) < 0;
		}
	}

	class LoadFilesThread extends Thread {
		File currentDirectory = null;
		int fid;
		Vector runnables = new Vector(10);

		public LoadFilesThread(File currentDirectory, int fid) {
			super("Basic L&F File Loading Thread");
			this.currentDirectory = currentDirectory;
			this.fid = fid;
		}

		public void run() {
			run0();
			setBusy(false, fid);
		}

		public void run0() {
			try {
				FileSystemView fileSystem = filechooser.getFileSystemView();
				File[] list = fileSystem.getFiles(currentDirectory, filechooser.isFileHidingEnabled());

				if (isInterrupted()) {
					return;
				}

				final Vector newFileCache = new Vector();
				Vector newFiles = new Vector();

				// run through the file list, add directories and selectable
				// files
				// to fileCache
				// Note that this block must be OUTSIDE of Invoker thread
				// because of
				// deadlock possibility with custom synchronized FileSystemView

				for (File file : list) {
					if (file != null && filechooser.accept(file)) {
						boolean isTraversable = filechooser.isTraversable(file);

						boolean shouldAdd = false;
						try {
							if (filterText == null) {
								shouldAdd = true;
							} else if (filterText.trim().length() == 0) {
								shouldAdd = true;
							} else {
								Pattern pattern = Pattern.compile(".*" + filterText + ".*");
								Matcher matcher = pattern.matcher(file.getName().toLowerCase());
								if (matcher.lookingAt()) {
									shouldAdd = true;
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							shouldAdd = true;
						}

						if (shouldAdd) {
							if (isTraversable) {
								newFileCache.addElement(file);
							} else if (filechooser.isFileSelectionEnabled()) {
								newFiles.addElement(file);
							}
						}

						if (isInterrupted()) {
							return;
						}
					}
				}

				// First sort alphabetically by filename
				sort(newFileCache);
				sort(newFiles);

				newFileCache.addAll(newFiles);

				// To avoid loads of synchronizations with Invoker and improve
				// performance we
				// execute the whole block on the COM thread

				DoChangeContents doChangeContents = ShellFolder.getInvoker().invoke(new Callable<DoChangeContents>() {
					public DoChangeContents call() {
						int newSize = newFileCache.size();
						int oldSize = fileCache.size();

						if (newSize > oldSize) {
							// see if interval is added
							int start = oldSize;
							int end = newSize;
							for (int i = 0; i < oldSize; i++) {
								if (!newFileCache.get(i).equals(fileCache.get(i))) {
									start = i;
									for (int j = i; j < newSize; j++) {
										if (newFileCache.get(j).equals(fileCache.get(i))) {
											end = j;
											break;
										}
									}
									break;
								}
							}
							if (start >= 0 && end > start && newFileCache.subList(end, newSize).equals(fileCache.subList(start, oldSize))) {
								if (isInterrupted()) {
									return null;
								}
								return new DoChangeContents(newFileCache.subList(start, end), start, null, 0, fid);
							}
						} else if (newSize < oldSize) {
							// see if interval is removed
							int start = -1;
							int end = -1;
							for (int i = 0; i < newSize; i++) {
								if (!newFileCache.get(i).equals(fileCache.get(i))) {
									start = i;
									end = i + oldSize - newSize;
									break;
								}
							}
							if (start >= 0 && end > start && fileCache.subList(end, oldSize).equals(newFileCache.subList(start, newSize))) {
								if (isInterrupted()) {
									return null;
								}
								return new DoChangeContents(null, 0, new Vector(fileCache.subList(start, end)), start, fid);
							}
						}
						if (!fileCache.equals(newFileCache)) {
							if (isInterrupted()) {
								cancelRunnables(runnables);
							}
							return new DoChangeContents(newFileCache, 0, fileCache, 0, fid);
						}

						return null;
					}
				});

				if (doChangeContents != null) {
					runnables.addElement(doChangeContents);
					SwingUtilities.invokeLater(doChangeContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void cancelRunnables(Vector runnables) {
			for (int i = 0; i < runnables.size(); i++) {
				((DoChangeContents) runnables.elementAt(i)).cancel();
			}
		}

		public void cancelRunnables() {
			cancelRunnables(runnables);
		}
	}

	/**
	 * Adds a PropertyChangeListener to the listener list. The listener is
	 * registered for all bound properties of this class.
	 * <p>
	 * If <code>listener</code> is <code>null</code>, no exception is thrown and
	 * no action is performed.
	 * 
	 * @param listener
	 *            the property change listener to be added
	 * 
	 * @see #removePropertyChangeListener
	 * @see #getPropertyChangeListeners
	 * 
	 * @since 1.6
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a PropertyChangeListener from the listener list.
	 * <p>
	 * If listener is null, no exception is thrown and no action is performed.
	 * 
	 * @param listener
	 *            the PropertyChangeListener to be removed
	 * 
	 * @see #addPropertyChangeListener
	 * @see #getPropertyChangeListeners
	 * 
	 * @since 1.6
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (changeSupport != null) {
			changeSupport.removePropertyChangeListener(listener);
		}
	}

	/**
	 * Returns an array of all the property change listeners registered on this
	 * component.
	 * 
	 * @return all of this component's <code>PropertyChangeListener</code>s or
	 *         an empty array if no property change listeners are currently
	 *         registered
	 * 
	 * @see #addPropertyChangeListener
	 * @see #removePropertyChangeListener
	 * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners
	 * 
	 * @since 1.6
	 */
	public PropertyChangeListener[] getPropertyChangeListeners() {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners();
	}

	/**
	 * Support for reporting bound property changes for boolean properties. This
	 * method can be called when a bound property has changed and it will send
	 * the appropriate PropertyChangeEvent to any registered
	 * PropertyChangeListeners.
	 * 
	 * @param propertyName
	 *            the property whose value has changed
	 * @param oldValue
	 *            the property's previous value
	 * @param newValue
	 *            the property's new value
	 * 
	 * @since 1.6
	 */
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (changeSupport != null) {
			changeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	/**
	 * Set the busy state for the model. The model is considered busy when it is
	 * running a separate (interruptable) thread in order to load the contents
	 * of a directory.
	 */
	private synchronized void setBusy(final boolean busy, int fid) {
		if (fid == fetchID) {
			boolean oldValue = this.busy;
			this.busy = busy;

			if (changeSupport != null && busy != oldValue) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						firePropertyChange("busy", !busy, busy);
					}
				});
			}
		}
	}

	class DoChangeContents implements Runnable {
		private List addFiles;
		private List remFiles;
		private boolean doFire = true;
		private int fid;
		private int addStart = 0;
		private int remStart = 0;
		private int change;

		public DoChangeContents(List addFiles, int addStart, List remFiles, int remStart, int fid) {
			this.addFiles = addFiles;
			this.addStart = addStart;
			this.remFiles = remFiles;
			this.remStart = remStart;
			this.fid = fid;
		}

		synchronized void cancel() {
			doFire = false;
		}

		public synchronized void run() {
			if (fetchID == fid && doFire) {
				int remSize = (remFiles == null) ? 0 : remFiles.size();
				int addSize = (addFiles == null) ? 0 : addFiles.size();
				synchronized (fileCache) {
					if (remSize > 0) {
						fileCache.removeAll(remFiles);
					}
					if (addSize > 0) {
						fileCache.addAll(addStart, addFiles);
					}

					files = null;
					directories = null;
				}
				if (remSize > 0 && addSize == 0) {
					fireIntervalRemoved(FilterBasicDirectoryModel.this, remStart, remStart + remSize - 1);
				} else if (addSize > 0 && remSize == 0 && fileCache.size() > addSize) {
					fireIntervalAdded(FilterBasicDirectoryModel.this, addStart, addStart + addSize - 1);
				} else {
					fireContentsChanged();
				}
			}
		}
	}
}
