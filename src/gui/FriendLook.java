package gui;

import logic.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FriendLook extends JPanel {
    private JLabel photoLabel;
    private JLabel infoLabel;
    private JLabel nickLabel;
    private Friend friend;

    public Friend getFriend() {
        return friend;
    }

    public FriendLook(Friend friend) {
        this.setLayout(new GridBagLayout());

        this.friend = friend;

        BufferedImage scaled = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = scaled.createGraphics();
        g.drawImage(friend.getAvatarAsBufImage(), 0, 0, 100, 100, null);
        g.dispose();

        try {
            photoLabel = CircleLabelClass.CircleLabel(scaled);
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoLabel = new JLabel(friend.getName() + " " + friend.getSurname());
        nickLabel = new JLabel("(" + friend.getNickname() + ")");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        nickLabel.setForeground(Color.RED);

        this.setBackground(new Color(0, 0, 0, 0));

        this.add(photoLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(infoLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(nickLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    }
}
