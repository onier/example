/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LabelTable.java
 *
 * Created on 2010-12-28, 14:07:00
 */
package layout;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JScrollPane;

/**
 *
 * @author Administrator
 */
public class LabelTable extends javax.swing.JFrame {

    /** Creates new form LabelTable */
    public LabelTable() {
        initComponents();
        JDownComponentPanelContainer container = new JDownComponentPanelContainer();
        container.addPopupPanel(new DownComponentPanel(new LabelHeaderRenderer("属性"), new TableComponentPanelRenderer()));
        container.addPopupPanel(new DownComponentPanel(new LabelHeaderRenderer("属性"), new TableComponentPanelRenderer()));
        container.addPopupPanel(new DownComponentPanel(new LabelHeaderRenderer("属性"), new TableComponentPanelRenderer()));
        container.addPopupPanel(new DownComponentPanel(new ButtonHeaderRenderer("属性"), new TableComponentPanelRenderer()));
        container.addPopupPanel(new DownComponentPanel(new ButtonHeaderRenderer("属性"), new TableComponentPanelRenderer()));
        container.addPopupPanel(new DownComponentPanel(new ButtonHeaderRenderer("属性"), new TableComponentPanelRenderer()));
        this.jPanel2.setLayout(new BorderLayout());
        jPanel2.add(new JScrollPane(container));
        AbstractAction action = new AbstractAction("Sample Action", ButtonHeaderRenderer.ICON_DOWN) {

            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
            }
        };
        container = new JDownComponentPanelContainer();
        container.addPopupPanel(new DownComponentPanel(new LabelHeaderRenderer("属性1"), new DownComponentPanel(new LabelHeaderRenderer("属性1.1"), new DownComponentPanel(new LabelHeaderRenderer("属性1.1.1"), new DownComponentMenu(new Action[]{action, action, action, action, action, action, action, action})))));
//        container.addPopupPanel(new DownComponentPanel(new LabelHeaderRenderer("属性"), new DownComponentPanel(new LabelHeaderRenderer("属性"), new TableComponentPanelRenderer())));
//        container.addPopupPanel(new DownComponentPanel(new LabelHeaderRenderer("属性"), new DownComponentPanel(new LabelHeaderRenderer("属性"), new TableComponentPanelRenderer())));
        this.jPanel1.setLayout(new BorderLayout());
        jPanel1.add(new JScrollPane(container));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LabelTable().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
