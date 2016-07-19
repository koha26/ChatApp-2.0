package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIStandartOperations {
    public static JFrame FrameStartOperations(JFrame frame) {
        frame.setResizable(false);
        frame.setContentPane(new JLabel(Images.backgroundImage)); // установка фона
        frame.setUndecorated(true); // метод, который убирает виндосоуские кнопки (свернуть, выход и т.д.)
        frame.setIconImage(Images.appIcon.getImage()); // иконка фрейма
        return frame;
    }

    public static JButton ButtonStartOperations(final Icon icon, final Icon iconEntered, boolean hasIcon) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        if (hasIcon) {
            final JButton finalButton = button;
            button.setIcon(icon);
            button.setBackground(new Color(0, 0, 0, 0));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    finalButton.setIcon(iconEntered);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    finalButton.setIcon(icon);
                }
            });
        }
        return button;
    }

    public static JLabel LabelStartOperation(String text, Rectangle r, Font font, Color foreground) {
        JLabel label = new JLabel(text);
        label.setBounds(r);
        label.setFont(font);
        label.setForeground(foreground);
        return label;
    }

    public static JTextField TextFieldStartOperation(Font font, Rectangle r) {
        JTextField component = new JTextField();
        component.setBorder(null);
        component.setFont(font);
        component.setBounds(r);
        component.setHorizontalAlignment(JTextField.CENTER);
        return component;
    }
}
