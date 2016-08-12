package gui;

import logic.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FriendSideLook extends JPanel {

    private JLabel photoLabel, nickLabel;
    private Friend friend;

    public Friend getFriend() {
        return friend;
    }

    public FriendSideLook(Friend friend) {
        this.friend = friend;
        setLayout(new FlowLayout(FlowLayout.LEFT));
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

        add(photoLabel);
        add(nickLabel);
    }
}
