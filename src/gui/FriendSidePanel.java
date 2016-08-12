package gui;

import logic.Friend;
import logic.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
    private JButton globalSearchButton;
    private GlobalSearchLook foundUser = new GlobalSearchLook();

    private final ImageIcon searchIcon = new ImageIcon("images/mainform/search.png");
    private final ImageIcon cancelSearchIcon = new ImageIcon("images/mainform/cancel_search.png");

    public GlobalSearchLook getFoundUser() {
        return foundUser;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JPanel getFriendsPanel() {
        return friendsPanel;
    }

    public JButton getGlobalSearchButton() {
        return globalSearchButton;
    }

    public FriendSidePanel() {
        setBorder(null);
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

        globalSearchButton = new JButton("<html> Search in ChatApp </html>");
        globalSearchButton.setHorizontalAlignment(SwingConstants.CENTER);
        globalSearchButton.setForeground(Color.WHITE);
        globalSearchButton.setFocusPainted(false);
        globalSearchButton.setContentAreaFilled(false);
        globalSearchButton.setOpaque(false);
        globalSearchButton.setFont(Fonts.smallFont);
        globalSearchButton.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
        //globalSearchButton.setBackground(new Color(0, 0, 0, 150));
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
        globalSearchButton.setBounds(20, currentPos + 3, 180, 50);
        friendsPanel.add(globalSearchButton);
        currentPos += 75;

        if (user.getFriendsList().size() > 0) {
           for (int i = 0; i < user.getFriendsList().size(); i++) {
               if (user.getFriendsList().get(i).getNickname().contains(searchRequest)) {
                   FriendSideLook friend = new FriendSideLook(user.getFriendsList().get(i));
                   friend.setBounds(7, currentPos, 220, 60);
                   friendsPanel.add(friend);
               }
           }
        }

        repaint();
        revalidate();
        updateUI();
    }

    public void updateGlobalSearch(ArrayList<Friend> userList, String searchRequest) {
        /*
                Метод, который принимает в себя список пользователей от сервера
         */
        globalSearchButton.setBounds(20, currentPos + 3, 180, 50);
        friendsPanel.add(globalSearchButton);
        currentPos += 75;

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getNickname().contains(searchRequest)) {
                foundUser = new GlobalSearchLook(userList.get(i));
                foundUser.setBounds(7, currentPos, 220, 60);
                friendsPanel.add(foundUser);
                currentPos += 65;
            }
        }

        repaint();
        revalidate();
        updateUI();
    }

    public void resetPanel() {
        friendsPanel.removeAll();
        repaintPanel(friendsPanel);
        this.currentPos = 7;
    }


    public void repaintPanel(JPanel panel) {
        panel.repaint();
        panel.revalidate();
        panel.updateUI();
    }
}
