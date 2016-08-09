package gui;

import logic.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FriendSideLook extends JPanel {

    private JLabel photoLabel, nickLabel;
    private JPanel singlePanel;
    private Friend friend;

    public Friend getFriend() {
        return friend;
    }

    public FriendSideLook(Friend friend) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(null);
        this.friend = friend;

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

        singlePanel = new JPanel();
        singlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        singlePanel.setBorder(null);
        singlePanel.setBackground(new Color(0, 0, 0, 100));
        singlePanel.add(photoLabel);
        singlePanel.add(nickLabel);
        add(singlePanel);
    }
}
