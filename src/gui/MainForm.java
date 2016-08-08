package gui;

import logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private HomePanel homePanel;
    private DialogPanel dialogPanel;
    private JPanel bigPanel;
    private Point mouseDownCompCoords = new Point();
    private final ImageIcon settingsButIcon = new ImageIcon("images/mainform/settings_button.png");
    private final ImageIcon settingsButIconEntered = new ImageIcon("images/mainform/settings_button_entered.png");
    private final ImageIcon contactsButIcon = new ImageIcon("images/mainform/contacts_button.png");
    private final ImageIcon contactsButIconEntered = new ImageIcon("images/mainform/contacts_button_entered.png");
    private final ImageIcon exitButIcon = new ImageIcon("images/mainform/exit_button.png");
    private final ImageIcon exitButIconEntered = new ImageIcon("images/mainform/exit_button_entered.png");
    private final ImageIcon plusButIcon = new ImageIcon("images/mainform/plus.png");
    private final ImageIcon plusButIconEntered = new ImageIcon("images/mainform/plus_entered.png");
    private final ImageIcon homeButIcon = new ImageIcon("images/mainform/home.png");
    private final ImageIcon homeButIconEntered = new ImageIcon("images/mainform/home_entered.png");
    private JButton settingsButton, contactsButton, exitButton, plusButton, homeButton;

    public JButton getPlusButton() {
        return plusButton;
    }

    public DialogPanel getDialogPanel() {
        return dialogPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public MainForm() {
        GUIStandartOperations.FrameStartOperations(this);
        setSize(960, 610);
        setLocationRelativeTo(null);
        setLayout(null);

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

        bigPanel = new JPanel(null);
        bigPanel.setSize(960, 600);
        bigPanel.setOpaque(false);
        bigPanel.setBackground(new Color(0, 0, 0, 0));

        settingsButton = GUIStandartOperations.ButtonStartOperations(settingsButIcon, settingsButIconEntered, true);
        contactsButton = GUIStandartOperations.ButtonStartOperations(contactsButIcon, contactsButIconEntered, true);
        exitButton = GUIStandartOperations.ButtonStartOperations(exitButIcon, exitButIconEntered, true);
        plusButton = GUIStandartOperations.ButtonStartOperations(plusButIcon, plusButIconEntered, true);
        homeButton = GUIStandartOperations.ButtonStartOperations(homeButIcon, homeButIconEntered, true);

        homeButton.setBounds(538, 18, 64, 64);
        bigPanel.add(homeButton);
        plusButton.setBounds(612, 10, 64, 64);
        bigPanel.add(plusButton);
        settingsButton.setBounds(686, 10, 64, 64);
        bigPanel.add(settingsButton);
        contactsButton.setBounds(760, 10, 64, 64);
        bigPanel.add(contactsButton);
        exitButton.setBounds(834, 10, 64, 64);
        bigPanel.add(exitButton);
        homePanel = new HomePanel();
        homePanel.setBounds(0, 84, 960, 1000);

        bigPanel.add(homePanel);

        dialogPanel = new DialogPanel();

        this.add(bigPanel);

        try {
            dialogPanel = new DialogPanel(null, null, "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public void changeModeToHomePanel() {
        dialogPanel.setVisible(false);
        homePanel.setVisible(true);
    }

    public void changeModeToDialog(BufferedImage myPhoto, BufferedImage friendPhoto, String friendNickname) {
        try {
            dialogPanel.updateInfo(myPhoto, friendPhoto, friendNickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogPanel.setBounds(0, 84, 960, 1000);
        bigPanel.add(dialogPanel);
        dialogPanel.setVisible(true);
        homePanel.setVisible(false);
        repaint();
    }


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistrationModel regModel = new RegistrationModel("MaxTEAMLEAD", "2331239max");
                regModel.setName("Max");
                regModel.setSurname("Tkachenko");
                regModel.setSex(Sex.MALE);
                regModel.setDateOfBirth("06 September 1997");
                try {
                    regModel.setAvatar(new ImageSerializable(ImageIO.read(new File("2.jpg"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                User user = null;
                try {
                    user = new User(regModel, InetAddress.getLocalHost(), 1);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                ArrayList<Friend> friends = new ArrayList<>();

                try {
                    for (int i = 0; i < 8; i++) {
                        Friend friend1 = new Friend("koha26", "Kostya", "Dyachenko", "", "", "", Sex.MALE, new ImageSerializable(ImageIO.read(new File("photos/kostik.jpg"))));
                        friends.add(friend1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                user.setFriends(friends);

                MainForm mainForm = new MainForm();

                mainForm.setVisible(true);
            }
        });
    }
}
