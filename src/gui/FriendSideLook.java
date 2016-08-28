package gui;

import logic.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FriendSideLook extends JPanel {

    private JLabel photoLabel, nickLabel;
    private Friend friend;
    private MiniFriendLook friendLook;
    private Timer t;
    private JButton deleteButton, statusButton;
    private ImageIcon deleteButIcon = new ImageIcon("images/mainform/delete.png");
//    private ImageIcon deleteButIcon = new ImageIcon("images/mainform/remove.png");
    private ImageIcon deleteButIconEntered = new ImageIcon("images/mainform/delete_entered.png");
    private ImageIcon onlineIcon = new ImageIcon("images/mainform/on.png");
    private ImageIcon offlineIcon = new ImageIcon("images/mainform/off.png");
    private JPanel leftPanel, rightPanel;

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public Friend getFriend() {
        return friend;
    }

    public FriendSideLook(Friend friend) {
        this.friend = friend;
        setLayout(null);
        setBackground(new Color(0, 0, 0, 100));
        setBorder(null);

        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(null);

        rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(null);

        statusButton = new JButton();
        statusButton.setContentAreaFilled(false);
        statusButton.setFocusPainted(false);
        statusButton.setBorder(null);
        statusButton.setFocusable(false);

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

        deleteButton = new JButton();
        deleteButton.setContentAreaFilled(false);
        deleteButton.setIcon(new ImageIcon("images/mainform/delete.png"));
        deleteButton.setBorder(null);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.setBackground(new Color(0, 0, 0, 150));
        deleteButton.setForeground(Color.RED);
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setIcon(deleteButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setIcon(deleteButIcon);
            }
        });


        leftPanel.add(statusButton);
        leftPanel.add(photoLabel);
        leftPanel.add(nickLabel);
        rightPanel.add(deleteButton);

        leftPanel.setBounds(0, 0, 240, 60);
        rightPanel.setBounds(210, 10, 64, 64);

        add(leftPanel);
        add(rightPanel);

        friendLook = new MiniFriendLook(friend);
        friendLook.setVisible(false);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (!(t == null))
                    t.stop();
                friendLook.setVisible(true);
                friendLook.setLocation((int) (e.getLocationOnScreen().getX()), (int) (e.getLocationOnScreen().getY() + 10));
                t = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        friendLook.setVisible(false);
                        friendLook.dispose();
                    }
                });
                t.start();
                if ((e.getLocationOnScreen().getX() <= getLocationOfLook().getX() + 10) || (e.getLocationOnScreen().getX() >= getLocationOfLook().getX() + 207) ||
                        (e.getLocationOnScreen().getY() <= getLocationOfLook().getY() + 11) || (e.getLocationOnScreen().getY() >= getLocationOfLook().getY() + 48)) {
                    friendLook.setVisible(false);
                    friendLook.dispose();
                }
            }
        });
    }

    public JButton getStatusButton() {
        return statusButton;
    }

    public void setOnline() { // установка онлайн иконки для друга
        statusButton.setIcon(onlineIcon);
    }

    public void setOffline() { // установка оффлайн иконки для друга
        statusButton.setIcon(offlineIcon);
    }

    private Point getLocationOfLook() {
        return this.getLocationOnScreen();
    }

    public JLabel getPhotoLabel() {
        return photoLabel;
    }

    public JLabel getNickLabel() {
        return nickLabel;
    }

    public MiniFriendLook getFriendLook() {
        return friendLook;
    }
}
