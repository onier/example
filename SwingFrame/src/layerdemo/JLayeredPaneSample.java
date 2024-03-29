/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layerdemo;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class JLayeredPaneSample {

    public static void main(String args[]) {
        JFrame f = new JFrame("JDesktopPane Sample");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        JLayeredPane desktop = new JDesktopPane();
        desktop.setOpaque(false);
        desktop.add(createLayer("Open 1"), JLayeredPane.PALETTE_LAYER);
        desktop.add(createLayer("Iconified"), JLayeredPane.LAYER_PROPERTY);
        desktop.add(createLayer("Open 2"), JLayeredPane.PALETTE_LAYER);
        content.add(desktop, BorderLayout.CENTER);
        f.setSize(300, 200);
        f.setVisible(true);
    }

    public static JInternalFrame createLayer(String label) {
        return new SelfInternalFrame(label);
    }

    static class SelfInternalFrame extends JInternalFrame {

        public SelfInternalFrame(String s) {
            getContentPane().add(new JLabel(s), BorderLayout.CENTER);
            setBounds(50, 50, 100, 100);
            setResizable(true);
            setClosable(true);
            setMaximizable(true);
            setIconifiable(true);
            setTitle(s);
            setVisible(true);
        }
    }
}

