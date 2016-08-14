package gui;

import logic.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FriendSidePanel extends JPanel {

    private JScrollPane scrollPane;
    private JPanel friendsPanel;
    private int currentPos = 7;

    public JPanel getFriendsPanel() {
        return friendsPanel;
    }

    public FriendSidePanel() {
        this.setBorder(null);
        setOpaque(false);
        setLayout(null);

        friendsPanel = new JPanel();
        friendsPanel.setOpaque(false);
        friendsPanel.setLayout(null);
        friendsPanel.setBorder(null);
        scrollPane = new JScrollPane(friendsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0, 150), 3));
        scrollPane.setBounds(0, 75, 240, 375);
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBar());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    public void updateInfo(User user) {
        if (user.getFriendsList().size() > 0) {
            for (int i = 0; i < user.getFriendsList().size(); i++) {
                FriendSideLook friend = new FriendSideLook(user.getFriendsList().get(i));
                friend.setBounds(7, currentPos, 220, 60);
                friendsPanel.add(friend);
                currentPos += 65;
            }
        }

        friendsPanel.repaint();
        friendsPanel.revalidate();
        friendsPanel.updateUI();
        scrollPane.repaint();
        scrollPane.revalidate();
        scrollPane.updateUI();
    }
}
