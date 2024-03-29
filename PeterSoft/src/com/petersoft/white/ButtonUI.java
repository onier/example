package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import com.petersoft.advancedswing.jdropdownbutton.JDropDownButton;

public class ButtonUI extends BasicButtonUI {

    private final static ButtonUI buttonUI = new ButtonUI();
    // Icon disableIcon;
    // boolean showText = true;
    // ////////////////////////////////////////////////////////////////
    Image normalUpperLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_upperLeft.png")).getImage();
    Image normalMiddleLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_middleLeft.png")).getImage();
    Image normalLowerLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_lowerLeft.png")).getImage();
    Image normalMiddleUpper = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_middleUpper.png")).getImage();
    Image normalMiddleLower = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_middleLower.png")).getImage();
    Image normalUpperRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_upperRight.png")).getImage();
    Image normalMiddleRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_middleRight.png")).getImage();
    Image normalLowerRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/normal/PButton_normal_lowerRight.png")).getImage();
    // ////////////////////////////////////////////////////////////////
    Image mouseOverUpperLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_upperLeft.png")).getImage();
    Image mouseOverMiddleLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_middleLeft.png")).getImage();
    Image mouseOverLowerLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_lowerLeft.png")).getImage();
    Image mouseOverMiddleUpper = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_middleUpper.png")).getImage();
    Image mouseOverMiddleLower = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_middleLower.png")).getImage();
    Image mouseOverUpperRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_upperRight.png")).getImage();
    Image mouseOverMiddleRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_middleRight.png")).getImage();
    Image mouseOverLowerRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/mouseOver/PButton_mouseOver_lowerRight.png")).getImage();
    // ////////////////////////////////////////////////////////////////
    Image disableUpperLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_upperLeft.png")).getImage();
    Image disableMiddleLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_middleLeft.png")).getImage();
    Image disableLowerLeft = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_lowerLeft.png")).getImage();
    Image disableMiddleUpper = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_middleUpper.png")).getImage();
    Image disableMiddleLower = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_middleLower.png")).getImage();
    Image disableUpperRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_upperRight.png")).getImage();
    Image disableMiddleRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_middleRight.png")).getImage();
    Image disableLowerRight = new ImageIcon(ButtonUI.class.getResource("images/PButton/disable/PButton_disable_lowerRight.png")).getImage();
    Color internalBGColor = new Color(248, 248, 248);
    Color internalPressingBGColor = new Color(225, 237, 255);
    Color disabledInternalBGColor = new Color(230, 230, 230);
    Color buttonRollOverColor = new Color(145, 145, 145);

    public ButtonUI() {
        super();
    }

    public static ComponentUI createUI(JComponent c) {
        return buttonUI;
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBorder(new EmptyBorder(5, 5, 5, 5));

        c.setOpaque(false);
        // c.addKeyListener(this);
    }

    public void installDefaults(AbstractButton button) {
        super.installDefaults(button);
        button.setRolloverEnabled(true);
    }

    public void uninstallDefaults(AbstractButton button) {
        super.uninstallDefaults(button);
        button.setRolloverEnabled(false);
    }

    protected BasicButtonListener createButtonListener(AbstractButton button) {
        return new ButtonListener(button);
    }

    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        if (c.getParent() instanceof BasicInternalFrameTitlePane) {
        } else if (c.getParent() instanceof JToolBar || c.getParent() instanceof JDropDownButton) {
            if (button.getModel().isRollover()) {
                g.setColor(buttonRollOverColor);
                g.drawRect(1, 1, button.getWidth() - 3, button.getHeight() - 3);
            }
        } else {
            if (button.getModel().isRollover()) {
                g.drawImage(mouseOverUpperLeft, 0, 0, 5, 5, null, null);
                g.drawImage(mouseOverMiddleLeft, 0, 5, 4, button.getHeight() - 10, null, null);
                g.drawImage(mouseOverLowerLeft, 0, button.getHeight() - 5, 5, 5, null, null);

                g.drawImage(mouseOverMiddleUpper, 5, 0, button.getWidth() - 11, 4, null, null);
                g.drawImage(mouseOverMiddleLower, 5, button.getHeight() - 4, button.getWidth() - 11, 4, null, null);

                g.drawImage(mouseOverUpperRight, button.getWidth() - 6, 0, 5, 5, null, null);
                g.drawImage(mouseOverMiddleRight, button.getWidth() - 5, 5, 4, button.getHeight() - 10, null, null);
                g.drawImage(mouseOverLowerRight, button.getWidth() - 6, button.getHeight() - 5, 5, 5, null, null);
            } else {
                g.drawImage(normalUpperLeft, 0, 0, 5, 5, null, null);
                g.drawImage(normalMiddleLeft, 0, 5, 4, button.getHeight() - 10, null, null);
                g.drawImage(normalLowerLeft, 0, button.getHeight() - 5, 5, 5, null, null);

                g.drawImage(normalMiddleUpper, 5, 0, button.getWidth() - 11, 4, null, null);
                g.drawImage(normalMiddleLower, 5, button.getHeight() - 4, button.getWidth() - 11, 4, null, null);

                g.drawImage(normalUpperRight, button.getWidth() - 6, 0, 5, 5, null, null);
                g.drawImage(normalMiddleRight, button.getWidth() - 5, 5, 4, button.getHeight() - 10, null, null);
                g.drawImage(normalLowerRight, button.getWidth() - 6, button.getHeight() - 5, 5, 5, null, null);
            }
        }

        super.paint(g, c);
    }

    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        g.setColor(new Color(140, 140, 140));
        g.drawLine(5, b.getHeight() - 6, b.getWidth() - 7, b.getHeight() - 6);
    }

    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        super.paintText(g, c, textRect, text);
    }

    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        g.setColor(internalPressingBGColor);
        g.fillRect(4, 4, b.getWidth() - 8, b.getHeight() - 8);
    }

    public void update(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        if ((c.getBackground() instanceof UIResource) && button.isContentAreaFilled() && c.isEnabled()) {
            ButtonModel model = button.getModel();
            if (!(c.getParent() instanceof JToolBar)) {
                if (!model.isArmed() && !model.isPressed()) {
                    paint(g, c);
                    return;
                }
            } else if (model.isRollover()) {
                paint(g, c);
                return;
            }
        }
        super.update(g, c);
    }
}
