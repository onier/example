/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VolatileImagePane.java
 *
 * Created on 2009-9-7, 13:06:32
 */
package MaskImage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

/**
 *
 * @author Administrator
 */
public class VolatileImagePane extends javax.swing.JPanel {

    GraphicsEnvironment ge;
    GraphicsConfiguration gc;
    VolatileImage vimage;
    BufferedImage bimage;

    /** Creates new form VolatileImagePane */
    public VolatileImagePane() {
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        vimage = gc.createCompatibleVolatileImage(300, 300);
        initComponents();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = null;

        do {

            int valid = vimage.validate(gc);

            if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
                vimage = createVolatileImage(300, 300);
            }

            try {
                g2D = vimage.createGraphics();

                draw(g2D); // This is assumed to be created somewhere else, and is only used as an example.
            } finally {
                // It's always best to dispose of your Graphics objects.
                g2D.dispose();
            }
        } while (vimage.contentsLost());
        g.drawImage(vimage, 0, 0, this);
//        bimage = new BufferedImage(300,300,BufferedImage.TYPE_INT_ARGB);
//        this.draw(bimage.createGraphics());
//        g.drawImage(bimage, 0, 0, this);
    }

    void draw(Graphics2D g2D) {
        for (int i = 0; i < 10000; i++) {
            g2D.fill(new Rectangle(300, 300));
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
