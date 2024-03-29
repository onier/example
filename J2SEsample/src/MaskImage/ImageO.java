/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.font.TextLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import java.net.URL;

public class ImageO extends JApplet {

    private BufferedImage bi[];
    public static final float[] SHARPEN3x3_3 = {
        0.f, -1.f, 0.f,
        -1.f, 5.f, -1.f,
        0.f, -1.f, 0.f};

    public void init() {
        setBackground(Color.white);

        bi = new BufferedImage[4];
        String s[] = {"bld.jpg", "bld.jpg", "boat.gif", "boat.gif"};
        for (int i = 0; i < bi.length; i++) {
            Image img = getImage(getURL("images/" + s[i]));
            try {
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(img, 0);
                tracker.waitForID(0);
            } catch (Exception e) {
            }
            int iw = img.getWidth(this);
            int ih = img.getHeight(this);
            bi[i] = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi[i].createGraphics();
            big.drawImage(img, 0, 0, this);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        int w = getSize().width;
        int h = getSize().height;

        g2.setColor(Color.black);
        float[][] data = {{0.1f, 0.1f, 0.1f, // low-pass filter
                0.1f, 0.2f, 0.1f,
                0.1f, 0.1f, 0.1f},
            SHARPEN3x3_3};

        String theDesc[] = {"Convolve LowPass", "Convolve Sharpen",
            "LookupOp", "RescaleOp"};
        for (int i = 0; i < bi.length; i++) {
            int iw = bi[i].getWidth(this);
            int ih = bi[i].getHeight(this);
            int x = 0, y = 0;

            AffineTransform at = new AffineTransform();
            at.scale((w - 14) / 2.0 / iw, (h - 34) / 2.0 / ih);

            BufferedImageOp biop = null;
            BufferedImage bimg = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);

            switch (i) {
                case 0:
                case 1:
                    x = i == 0 ? 5 : w / 2 + 3;
                    y = 15;
                    Kernel kernel = new Kernel(3, 3, data[i]);
                    ConvolveOp cop = new ConvolveOp(kernel,
                            ConvolveOp.EDGE_NO_OP,
                            null);
                    cop.filter(bi[i], bimg);
                    biop = new AffineTransformOp(at,
                            AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    break;
                case 2:
                    x = 5;
                    y = h / 2 + 15;
                    byte chlut[] = new byte[256];
                    for (int j = 0; j < 200; j++) {
                        chlut[j] = (byte) (256 - j);
                    }
                    ByteLookupTable blut = new ByteLookupTable(0, chlut);
                    LookupOp lop = new LookupOp(blut, null);
                    lop.filter(bi[i], bimg);
                    biop = new AffineTransformOp(at,
                            AffineTransformOp.TYPE_BILINEAR);
                    break;
                case 3:
                    x = w / 2 + 3;
                    y = h / 2 + 15;
                    RescaleOp rop = new RescaleOp(1.1f, 20.0f, null);
                    rop.filter(bi[i], bimg);
                    biop = new AffineTransformOp(at,
                            AffineTransformOp.TYPE_BILINEAR);
            }
            g2.drawImage(bimg, biop, x, y);
            TextLayout tl = new TextLayout(theDesc[i], g2.getFont(), g2.getFontRenderContext());
            tl.draw(g2, (float) x, (float) y - 4);
        }
    }

    protected URL getURL(String filename) {
        URL codeBase = this.getCodeBase();
        URL url = null;

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Couldn't create image: " + "badly specified URL");
            return null;
        }

        return url;
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("ImageOps");
        f.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JApplet applet = new ImageO();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(550, 550));
        f.show();
    }
}
