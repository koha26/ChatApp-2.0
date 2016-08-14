package gui;

import logic.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GlobalSearchLook extends JPanel {

    private JLabel photoLabel, nickLabel;
    private JButton addButton;
    private Friend friend;

    public JButton getAddButton() {
        return addButton;
    }

    public Friend getFriend() {
        return friend;
    }

    public JLabel getNickLabel() {
        return nickLabel;
    }

    public GlobalSearchLook() {}

    public GlobalSearchLook(Friend friend) {
        this.friend = friend;

        setLayout(new BorderLayout(10, 0));
        setBackground(new Color(0, 0, 0, 100));
        setBorder(null);

        BufferedImage scaled = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(friend.getAvatarAsBufImage(), 0, 0, 50, 50, null);
        g.dispose();
        try {
            photoLabel = CircleLabelClass.CircleLabel(scaled);
       } catch (IOException e) {
            e.printStackTrace();
        }

        nickLabel = new JLabel(friend.getNickname());
        nickLabel.setForeground(Color.WHITE);
        nickLabel.setFont(Fonts.typingFont);

        addButton = new JButton("    ");
        addButton.setContentAreaFilled(false);
        addButton.setForeground(Color.GREEN);
        addButton.setIcon(new ImageIcon("images/mainform/add.png"));
        addButton.setDisabledIcon(new ImageIcon("images/mainform/add_disabled.png"));
        addButton.setFocusPainted(false);
        addButton.setBorder(null);
        addButton.setFont(Fonts.typingFont);

        add(photoLabel, BorderLayout.WEST);
        add(nickLabel, BorderLayout.CENTER);
        add(addButton, BorderLayout.EAST);
    }
}