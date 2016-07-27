package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FriendLook extends JPanel {
    private JLabel photoLabel;
    private JLabel infoLabel;
    private JLabel nickLabel;

    public FriendLook(String path, String info, String nick) {
        this.setLayout(new GridBagLayout());
        try {
            photoLabel = CircleLabelClass.CircleLabel(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoLabel = new JLabel(info);
        nickLabel = new JLabel("(" + nick + ")");
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
