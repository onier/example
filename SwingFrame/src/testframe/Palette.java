/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Palette.java
 *
 * Created on Jan 5, 2011, 5:43:02 PM
 */
package testframe;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import layout.ButtonHeaderRenderer;
import layout.DownComponentPanel;
import layout.JDownComponentPanelContainer;
import layout.ShapeComponentListPanelRenderer;
import node.BlockBeanNodeElement;
import node.CaseBeanNodeElement;
import node.DoWhileBeanNodeElement;
import node.EndBeanNodeElement;
import node.ForBeanNodeElement;
import node.GeneralBeanNodeElement;
import node.CheckPointBeanNodeElement;
import node.PrintBeanNodeElement;
import node.ScriptBeanNodeElement;
import node.StartBeanNodeElement;
import node.SwitchBeanNodeElement;
import node.WhileBeanNodeElement;
import shape.BeanNodeElement;
import shape.NodeElement;

/**
 *
 * @author Administrator
 */
public class Palette extends javax.swing.JPanel {

    /**
     * @return the palette
     */
    public static Palette getPalette() {
        return palette;
    }

    public void addNode(NodeElement... objs) {
        list.getModel().addNode(objs);
    }
    private ShapeComponentListPanelRenderer list = new ShapeComponentListPanelRenderer();
    private static final Palette palette = new Palette();

    /** Creates new form Palette */
    private Palette() {
        initComponents();

        final JDownComponentPanelContainer comtainer1 = new JDownComponentPanelContainer();
        AbstractAction action = new AbstractAction("Sample Action", IconUtils.getDownIcon()) {

            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
            }
        };
        BeanNodeElement[] elements = new BeanNodeElement[]{
            new BlockBeanNodeElement(),
            new CaseBeanNodeElement(),
            new DoWhileBeanNodeElement(),
            new EndBeanNodeElement(),
            new StartBeanNodeElement(),
            new SwitchBeanNodeElement(),
            new WhileBeanNodeElement(),
            new ForBeanNodeElement(),
            new ScriptBeanNodeElement(),
            new PrintBeanNodeElement(),
            new GeneralBeanNodeElement(),
            new CheckPointBeanNodeElement()};
        comtainer1.addPopupPanel(new DownComponentPanel(new ButtonHeaderRenderer("open"), new ShapeComponentListPanelRenderer(elements)));
        comtainer1.addPopupPanel(new DownComponentPanel(new ButtonHeaderRenderer("自定义"), list));
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(new JScrollPane(comtainer1), BorderLayout.CENTER);
        this.addComponentListener(new ComponentListener() {

            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        comtainer1.doLayout();
                    }
                });

            }

            public void componentMoved(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        comtainer1.doLayout();
                    }
                });
            }

            public void componentShown(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        comtainer1.doLayout();
                    }
                });
            }

            public void componentHidden(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        comtainer1.doLayout();
                    }
                });
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        compoundPainter1 = new org.jdesktop.swingx.painter.CompoundPainter();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.painter.CompoundPainter compoundPainter1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
