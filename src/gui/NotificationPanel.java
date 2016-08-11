package gui;

import gui.Notifications.NoNotificationsNotification;
import gui.Notifications.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class NotificationPanel extends JPanel {
    private LinkedList<Notification> bufer;
    private JButton closeButton;
    private JPanel currentNotification;
    private final ImageIcon closeButtonIcon = new ImageIcon("images/mainform/close.png");

    public NotificationPanel() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 150));
        this.setLayout(new GridBagLayout());

        bufer = new LinkedList<>();

        currentNotification = new NoNotificationsNotification();
        bufer.addFirst((Notification) currentNotification);

        closeButton = GUIStandartOperations.ButtonStartOperations(closeButtonIcon, closeButtonIcon, true);
        closeButton.setVisible(false);

        this.add(currentNotification, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(closeButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 40, 2), 0, 0));

        closeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeNotification();
            }
        });
    }

    public void addNotification(Notification notification) {
        bufer.addFirst(notification);
        currentNotification.setVisible(false);
        currentNotification = (JPanel) notification;
        this.add(currentNotification, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        closeButton.setVisible(true);
        repaint();
        revalidate();
        updateUI();
    }

    public void closeNotification() {
        bufer.removeFirst();
        currentNotification.setVisible(false);
        if (bufer.size() == 1) {
            closeButton.setVisible(false);
        }
        Notification notification = bufer.getFirst();
        currentNotification = (JPanel) notification;
        this.add(currentNotification, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        repaint();
        revalidate();
        updateUI();
        currentNotification.setVisible(true);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JPanel getCurrentNotification() {
        return currentNotification;
    }
}
