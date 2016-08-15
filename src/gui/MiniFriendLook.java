package gui;

import logic.Friend;
import logic.Sex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MiniFriendLook extends JFrame {

    private Point mouseDownCompCoords = new Point();
    private JLabel photo;
    private JLabel nick;
    private JLabel sex;
    private JLabel name;
    private JLabel surname;
    private JLabel dateOfBirth;
    private JLabel countOfMutualFriend;
    private JLabel namePanel, surnamePanel, dateOfBirthPanel, mutualFriendsPanel;

    public MiniFriendLook(Friend friend) {
        GUIStandartOperations.FrameStartOperations(this);
        this.setSize(200, 260);
        this.setLocationRelativeTo(null);

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }

            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        BufferedImage scaled = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(friend.getAvatarAsBufImage(), 0, 0, 100, 100, null);
        g.dispose();

        try {
            photo = CircleLabelClass.CircleLabel(scaled);
        } catch (IOException e) {
            e.printStackTrace();
        }

        photo.setBounds(50, 15, 100, 100);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(0, 0, 0, 150));
        panel.setBounds(0, 130, 200, 30);

        nick = new JLabel(friend.getNickname());
        nick.setFont(Fonts.smallFont);
        nick.setBackground(new Color(0, 0, 0, 0));
        nick.setForeground(Color.RED);
        nick.setHorizontalAlignment(SwingConstants.CENTER);
        nick.setBounds(0, 130, 200, 30);

        sex = new JLabel();
        if (friend.getSex() == Sex.MALE) {
            sex.setIcon(new ImageIcon("images/male.png"));
        } else {
            sex.setIcon(new ImageIcon("images/female.png"));
        }

        sex.setBounds(145, 130, 30, 30);

        namePanel = new JLabel("Name: ");
        surnamePanel = new JLabel("Surname: ");
        dateOfBirthPanel = new JLabel("Date of birth: ");
        mutualFriendsPanel = new JLabel("<html>Amount of<br> mutual friends:</html>");
        namePanel.setHorizontalAlignment(SwingConstants.LEFT);
        surnamePanel.setHorizontalAlignment(SwingConstants.LEFT);
        dateOfBirthPanel.setHorizontalAlignment(SwingConstants.LEFT);
        mutualFriendsPanel.setHorizontalAlignment(SwingConstants.LEFT);
        namePanel.setForeground(Color.WHITE);
        surnamePanel.setForeground(Color.WHITE);
        dateOfBirthPanel.setForeground(Color.WHITE);
        mutualFriendsPanel.setForeground(Color.WHITE);
        namePanel.setFont(Fonts.smallFont);
        mutualFriendsPanel.setFont(Fonts.smallFont);
        surnamePanel.setFont(Fonts.smallFont);
        dateOfBirthPanel.setFont(Fonts.smallFont);

        name = new JLabel(friend.getName());
        surname = new JLabel(friend.getSurname());
        dateOfBirth = new JLabel(friend.getDateOfBirth());
        countOfMutualFriend = new JLabel("0");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        surname.setHorizontalAlignment(SwingConstants.CENTER);
        dateOfBirth.setHorizontalAlignment(SwingConstants.CENTER);
        countOfMutualFriend.setHorizontalAlignment(SwingConstants.CENTER);
        name.setForeground(Color.CYAN);
        surname.setForeground(Color.CYAN);
        dateOfBirth.setForeground(Color.CYAN);
        countOfMutualFriend.setForeground(Color.CYAN);
        name.setFont(Fonts.smallFont);
        countOfMutualFriend.setFont(Fonts.smallFont);
        surname.setFont(Fonts.smallFont);
        dateOfBirth.setFont(Fonts.smallFont);

        namePanel.setBounds(5, 170, 100, 20);
        surnamePanel.setBounds(5, 200, 100, 20);
        dateOfBirthPanel.setBounds(5, 230, 100, 20);
        mutualFriendsPanel.setBounds(5, 260, 100, 40);

        name.setBounds(105, 170, 95, 20);
        surname.setBounds(105, 200, 95, 20);
        dateOfBirth.setBounds(105, 230, 95, 20);
        countOfMutualFriend.setBounds(105, 260, 95, 40);

        this.add(nick);
        this.add(sex);
        this.add(panel);
        this.add(photo);
        this.add(namePanel);
        this.add(surnamePanel);
        this.add(dateOfBirthPanel);
        this.add(mutualFriendsPanel);
        this.add(name);
        this.add(surname);
        this.add(dateOfBirth);
        this.add(countOfMutualFriend);
    }
}
