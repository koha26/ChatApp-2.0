package gui;

import logic.Friend;
import logic.PotentialFriend;
import logic.User;
import server.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.awt.event.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;

public class FriendSidePanel extends JPanel {

    private JScrollPane scrollPane;
    private JPanel friendsPanel, searchPanel;
    private JLabel searchIconLabel;
    private int currentPos = 7;
    private JTextField searchTextField;
    private JButton cancelButton;
    private JButton globalSearchButton;

    private final ImageIcon searchIcon = new ImageIcon("images/mainform/search.png");
    private final ImageIcon cancelSearchIcon = new ImageIcon("images/mainform/cancel_search.png");
    private ImageIcon onlineIcon = new ImageIcon("images/mainform/on.png");
    private ImageIcon offlineIcon = new ImageIcon("images/mainform/off.png");

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
        setOpaque(false);
        setLayout(null);

        searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new BorderLayout());

        searchIconLabel = new JLabel(searchIcon);
        searchPanel.add(searchIconLabel, BorderLayout.WEST);

        searchTextField = new JTextField(11);
        searchTextField.setFont(Fonts.typingFont);
        searchTextField.setText("Search...");
        searchTextField.setForeground(Color.WHITE);
        searchTextField.setBorder(null);
        searchTextField.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        searchTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (searchTextField.getText().equals("Search..."))
                    searchTextField.setText(null);
            }
        });
        searchPanel.add(searchTextField, BorderLayout.CENTER);

        cancelButton = new JButton();
        cancelButton.setIcon(cancelSearchIcon);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorder(null);
        cancelButton.setFocusPainted(false);
        searchPanel.add(cancelButton, BorderLayout.EAST);

        searchPanel.setBounds(0, 6, 280, 30);
        add(searchPanel);

        friendsPanel = new JPanel(null);
        friendsPanel.setOpaque(false);
        friendsPanel.setBorder(null);
        scrollPane = new JScrollPane(friendsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0, 150), 3));
        scrollPane.setBounds(0, 50, 280, 475);
        scrollPane.getVerticalScrollBar().setUI(new ChatAppVerticalScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);

        globalSearchButton = new JButton("<html> Search in ChatApp </html>");
        globalSearchButton.setHorizontalAlignment(SwingConstants.CENTER);
        globalSearchButton.setForeground(Color.WHITE);
        globalSearchButton.setFocusPainted(false);
        globalSearchButton.setContentAreaFilled(false);
        globalSearchButton.setOpaque(false);
        globalSearchButton.setFont(Fonts.smallFont);
        globalSearchButton.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
        //globalSearchButton.setBackground(new Color(0, 0, 0, 150));

        RepaintPanel repaintPanel = new RepaintPanel(this);
        Thread thread = new Thread(repaintPanel);
        thread.start();
    }

    public void updateInfo(final User user, final Client client) {
        resetPanel();

        if (user.getFriendsList().size() > 0) {
            for (int i = 0; i < user.getFriendsList().size(); i++) {
                final FriendSideLook friend = new FriendSideLook(user.getFriendsList().get(i));
                friend.getDeleteButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete " + friend.getNickLabel().getText() + "?", "Choose", JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {
                            client.sendFriendshipEndCommand(user.getNickname(), friend.getNickLabel().getText());
                            updateInfo(user, client);
                        }
                    }
                });
                friend.setBounds(6, currentPos, 255, 60);
                if (i % 2 == 0) friend.setOffline(); // установка оффлайн иконки для друга
                else friend.setOnline(); // установка онлайн иконки для друга
                friendsPanel.add(friend);
                currentPos += 65;
            }
        }

        friendsPanel.setPreferredSize(new Dimension(240, user.getFriendsList().size() * 65 + 7));

        repaint();
        revalidate();
        updateUI();
    }

    public void updateFriendSearch(String searchRequest, User user) {
        resetPanel();

        globalSearchButton.setBounds(40, currentPos + 3, 180, 50);
        friendsPanel.add(globalSearchButton);
        currentPos += 75;

        if (user.getFriendsList().size() > 0) {
            for (int i = 0; i < user.getFriendsList().size(); i++) {
                if (user.getFriendsList().get(i).getNickname().contains(searchRequest)) {
                    FriendSideLook friend = new FriendSideLook(user.getFriendsList().get(i));
                    friend.setBounds(7, currentPos, 255, 60);
                    friend.setOnline(); // установка онлайн иконки для друга
                    friendsPanel.add(friend);
                    currentPos += 65;
                }
            }
        }

        friendsPanel.setPreferredSize(new Dimension(240, user.getFriendsList().size() * 65 + 7));

        repaint();
        revalidate();
        updateUI();
    }

    public Point getLocationOfLook() {
        return this.getLocationOnScreen();
    }

    public void updateGlobalSearch(final ArrayList<PotentialFriend> userList, final User user, final Client client) {
        resetPanel();

        globalSearchButton.setBounds(40, currentPos + 3, 180, 50);
        friendsPanel.add(globalSearchButton);
        globalSearchButton.setText("<html> Search in ChatApp </html>");
        currentPos += 75;

        for (int i = 0; i < userList.size(); i++) {
            final GlobalSearchLook foundUser = new GlobalSearchLook(userList.get(i));
            foundUser.getAddButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.sendFriendshipRequestCommand(foundUser.getNickLabel().getText(), user.getNickname());
                    foundUser.getAddButton().setEnabled(false);
                }
            });

            final int finalI = i;
            foundUser.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    MiniFriendLook miniFriendLook = new MiniFriendLook(userList.get(finalI));
                    miniFriendLook.setVisible(true);
                }
            });

            foundUser.setBounds(7, currentPos, 255, 60);
            friendsPanel.add(foundUser);
            currentPos += 65;
        }

        friendsPanel.setPreferredSize(new Dimension(240, user.getFriendsList().size() * 65 + 7));

        repaint();
        revalidate();
        updateUI();
    }

    public void userIsNotFound() {
        globalSearchButton.setText("<html> User is not found. Try again </html>");
        friendsPanel.add(globalSearchButton);
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