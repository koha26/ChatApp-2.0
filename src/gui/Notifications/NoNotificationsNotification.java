package gui.Notifications;

import javax.swing.*;
import java.awt.*;

public class NoNotificationsNotification extends JPanel implements Notification {
    private JLabel label = new JLabel("You have no notifications right now!");

    public NoNotificationsNotification() {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.setBackground(new Color(0,0,0,0));

        label.setBackground(new Color(0,0,0,0));
        label.setForeground(Color.GREEN);
        label.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));

        this.add(label, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    }
}
