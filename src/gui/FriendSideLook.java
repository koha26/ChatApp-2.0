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
    private JButton deleteButton;
    private ImageIcon deleteButIcon = new ImageIcon("images/mainform/delete.png");
    private ImageIcon deleteButIconEntered = new ImageIcon("images/mainform/delete_entered.png");

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public Friend getFriend() {
        return friend;
    }

    public FriendSideLook(Friend friend) {
        this.friend = friend;
        setLayout(new BorderLayout(15, 0));
        setBackground(new Color(0, 0, 0, 100));
        setBorder(null);

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

        add(photoLabel);
        deleteButton = new JButton();
        deleteButton.setContentAreaFilled(false);
        deleteButton.setIcon(new ImageIcon("delete.png"));
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

        add(photoLabel, BorderLayout.WEST);
        add(nickLabel, BorderLayout.CENTER);
        add(deleteButton, BorderLayout.EAST);

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
