package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DialogTab extends JPanel {
    private OutlineButton nickButton;
    private JLabel newMessageLabel = new JLabel(new ImageIcon("images/mainform/message.png"));
    private JButton closeButton;
    private final ImageIcon closeButtonIcon = new ImageIcon("images/mainform/close.png");

    public DialogTab(String nick) {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.WHITE));

        nickButton = new OutlineButton(nick);
        nickButton.setHorizontalAlignment(JLabel.CENTER);
        nickButton.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 16));
        nickButton.setForeground(Color.BLACK);
        nickButton.setBackground(new Color(0, 0, 0, 0));
        nickButton.setOpaque(false);
        nickButton.setBorder(null);
        nickButton.setFocusPainted(false);
        nickButton.setContentAreaFilled(false);

        newMessageLabel.setBackground(new Color(0, 0, 0, 0));
        newMessageLabel.setOpaque(false);
        newMessageLabel.setBorder(null);
        newMessageLabel.setVisible(false);

        closeButton = GUIStandartOperations.ButtonStartOperations(closeButtonIcon, closeButtonIcon, true);

        nickButton.setBounds(5, 5, 75, 30);
        newMessageLabel.setBounds(90, 4, 32, 32);
        closeButton.setBounds(130, 10, 16, 16);

        this.add(nickButton);
        this.add(newMessageLabel);
        this.add(closeButton);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JLabel getNewMessageLabel() {
        return newMessageLabel;
    }

    public OutlineButton getNickButton() {
        return nickButton;
    }
}
