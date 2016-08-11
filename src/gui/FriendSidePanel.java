package gui;

import logic.Friend;
import logic.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FriendSidePanel extends JPanel {

    private JScrollPane scrollPane;
    private JPanel friendsPanel, searchPanel;
    private JLabel searchIconLabel;
    private int currentPos = 7;
    private JTextField searchTextField;
    private JButton cancelButton;

    private final ImageIcon searchIcon = new ImageIcon("images/mainform/search.png");
    private final ImageIcon cancelSearchIcon = new ImageIcon("images/mainform/cancel_search.png");

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JPanel getFriendsPanel() {
        return friendsPanel;
    }

    public FriendSidePanel() {
        this.setBorder(null);
        setOpaque(false);
        setLayout(null);

        searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        searchIconLabel = new JLabel(searchIcon);
        searchPanel.add(searchIconLabel);

        searchTextField = new JTextField(11);
        searchTextField.setFont(Fonts.typingFont);
        searchTextField.setText("Search...");
        searchTextField.setForeground(Color.WHITE);
        searchTextField.setBorder(null);
        searchTextField.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 150)));
        searchTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (searchTextField.getText().equals("Search..."))
                    searchTextField.setText(null);
            }
        });
        searchPanel.add(searchTextField);

        cancelButton = new JButton();
        cancelButton.setIcon(cancelSearchIcon);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorder(null);
        cancelButton.setFocusPainted(false);
        searchPanel.add(cancelButton);

        searchPanel.setBounds(0, 34, 240, 30);
        add(searchPanel);

        friendsPanel = new JPanel();
        friendsPanel.setOpaque(false);
        friendsPanel.setLayout(null);
        friendsPanel.setBorder(null);
        scrollPane = new JScrollPane(friendsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0, 150), 3));
        scrollPane.setBounds(0, 75, 240, 420);
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

        repaint();
        revalidate();
        updateUI();
    }

    public void updateFriendSearch(String searchRequest, User user) {
        if (user.getFriendsList().size() > 0) {
           for (int i = 0; i < user.getFriendsList().size(); i++) {
               if (user.getFriendsList().get(i).getNickname().contains(searchRequest)) {
                   FriendSideLook friend = new FriendSideLook(user.getFriendsList().get(i));
                   friend.setBounds(7, currentPos, 220, 60);
                   friendsPanel.add(friend);
                   currentPos += 65;
               }
           }
        }
        friendsPanel.repaint();
        friendsPanel.revalidate();
        friendsPanel.updateUI();
        scrollPane.repaint();
        scrollPane.revalidate();
        scrollPane.updateUI();
    }

    public void resetPanel() {
        friendsPanel.removeAll();
        friendsPanel.repaint();
        friendsPanel.revalidate();
        this.currentPos = 7;
    }
}
