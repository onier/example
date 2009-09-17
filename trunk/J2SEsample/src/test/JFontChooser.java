/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFontChooser.java
 *
 * Created on 2009-9-14, 10:38:17
 */
package test;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Vector;
import javax.swing.DefaultListModel;

/**
 *
 * @author Administrator
 */
public class JFontChooser extends javax.swing.JDialog {

    Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    String formats[] = new String[]{"无格式", "粗体", "斜体", "粗斜体"};
    boolean initFlag = true;

    /** Creates new form JFontChooser */
    public JFontChooser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.jFormattedTextField1.setValue(fonts[0].getSize());
        fontNameList.setSelectedIndex(0);
        this.jList1.setSelectedIndex(0);
    }

    private void reSetPreviewValue() {
        Font fontTemp = fonts[fontNameList.getSelectedIndex()];
        Font font = new Font(fontTemp.getFontName(), this.jList1.getSelectedIndex(), 20);
        this.previewLabel.setText(this.fontNameLabel.getText() + " " + this.formatLabel.getText());
        previewLabel.setFont(font);
//    this.previewLabel.setText("abc");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        fontTitleLabel = new javax.swing.JLabel();
        fontformatTitleLabel = new javax.swing.JLabel();
        sizeTitleLabel = new javax.swing.JLabel();
        fontNameLabel = new javax.swing.JLabel();
        formatLabel = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        fontNameList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList(formats);
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        previewLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FontChooser");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fontTitleLabel.setText("字体");
        getContentPane().add(fontTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        fontformatTitleLabel.setText("字体样式");
        getContentPane().add(fontformatTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, -1));

        sizeTitleLabel.setText("大小");
        getContentPane().add(sizeTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, -1, 20));
        getContentPane().add(fontNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, 20));

        formatLabel.setText("     ");
        getContentPane().add(formatLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 130, -1));

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 40, 20));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < fonts.length; i++) {
            model.addElement(fonts[i].getFontName());
        }
        fontNameList.setModel(model);
        fontNameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                fontNameListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(fontNameList);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 160, 140));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(24, 24));

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 130, 140));

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList2);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("预览"));

        previewLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        previewLabel.setText("abcdef");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(previewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(previewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 370, -1));

        jButton1.setText("确定");
        jPanel2.add(jButton1);

        jButton2.setText("取消");
        jPanel2.add(jButton2);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, -1, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fontNameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fontNameListValueChanged
        int index = this.fontNameList.getSelectedIndex();
        if (index >= 0) {
            this.fontNameLabel.setText(fontNameList.getSelectedValue().toString());
            reSetPreviewValue();
        }
}//GEN-LAST:event_fontNameListValueChanged

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        int index = jList1.getSelectedIndex();
        if (index >= 0) {
            this.formatLabel.setText(jList1.getSelectedValue().toString());
            reSetPreviewValue();
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFontChooser dialog = new JFontChooser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fontNameLabel;
    private javax.swing.JList fontNameList;
    private javax.swing.JLabel fontTitleLabel;
    private javax.swing.JLabel fontformatTitleLabel;
    private javax.swing.JLabel formatLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel previewLabel;
    private javax.swing.JLabel sizeTitleLabel;
    // End of variables declaration//GEN-END:variables
}