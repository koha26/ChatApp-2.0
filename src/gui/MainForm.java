package gui;

import gui.Notifications.FriendshipRequestNotification;
import gui.Notifications.Notification;
import logic.*;
import logic.command.FriendshipRequestCommand;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
    private NotificationPanel notificationPanel;
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
    private final ImageIcon friendSideOpenIcon = new ImageIcon("images/mainform/sidepnl_open.png");
    private final ImageIcon friendSideCloseIcon = new ImageIcon("images/mainform/sidepnl_close.png");
    private final ImageIcon friendSideOpenIconEntered = new ImageIcon("images/mainform/sidepnl_open_entr.png");
    private final ImageIcon friendSideCloseIconEntered = new ImageIcon("images/mainform/sidepnl_close_entr.png");
    private JButton settingsButton, contactsButton, exitButton, plusButton, homeButton, friendPanelButton;
    private FriendSidePanel friendSidePanel;
    private boolean isFriendPanelOpened;

    public JButton getPlusButton() {
        return plusButton;
    }

    public DialogPanel getDialogPanel() {
        return dialogPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public FriendSidePanel getFriendSidePanel() {
        return friendSidePanel;
    }

    public JButton getFriendPanelButton() {
        return friendPanelButton;
    }

    public void friendPanelMode() {
        if (isFriendPanelOpened == true) {
            friendPanelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    friendPanelButton.setIcon(friendSideCloseIconEntered);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    friendPanelButton.setIcon(friendSideCloseIcon);
                }
            });
            friendPanelButton.setIcon(friendSideCloseIcon);
            setSize(1250, 610);
        } else {
            setSize(960, 610);
            friendPanelButton.setIcon(friendSideOpenIcon);
            friendPanelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    friendPanelButton.setIcon(friendSideOpenIconEntered);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    friendPanelButton.setIcon(friendSideOpenIcon);
                }
            });
        }
        bigPanel.revalidate();
        bigPanel.repaint();
        bigPanel.updateUI();
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

        JPanel topPanel = new JPanel(null);
        topPanel.setBackground(new Color(0, 0, 0, 150));
        topPanel.setBounds(0, 20, 960, 65);
        topPanel.setBorder(new LineBorder(Color.WHITE));
        bigPanel.add(topPanel);

        notificationPanel = new NotificationPanel();
        notificationPanel.setBounds(0, 0, 500, 65);
        notificationPanel.setBorder(new LineBorder(Color.WHITE));
        topPanel.add(notificationPanel);

        RepaintPanel repaintPanel = new RepaintPanel(notificationPanel);
        Thread thread = new Thread(repaintPanel);
        thread.start();

        RepaintPanel repaintPanel2 = new RepaintPanel(bigPanel);
        Thread thread2 = new Thread(repaintPanel2);
        thread2.start();

        settingsButton = GUIStandartOperations.ButtonStartOperations(settingsButIcon, settingsButIconEntered, true);
        contactsButton = GUIStandartOperations.ButtonStartOperations(contactsButIcon, contactsButIconEntered, true);
        exitButton = GUIStandartOperations.ButtonStartOperations(exitButIcon, exitButIconEntered, true);
        plusButton = GUIStandartOperations.ButtonStartOperations(plusButIcon, plusButIconEntered, true);
        homeButton = GUIStandartOperations.ButtonStartOperations(homeButIcon, homeButIconEntered, true);

        friendPanelButton = new JButton();
        friendPanelButton.setOpaque(false);
        friendPanelButton.setBorder(null);
        friendPanelButton.setFocusPainted(false);
        friendPanelButton.setContentAreaFilled(false);
        friendPanelButton.setIcon(friendSideOpenIcon);
        friendPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                friendPanelButton.setIcon(friendSideOpenIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                friendPanelButton.setIcon(friendSideOpenIcon);
            }
        });
        friendPanelButton.setVisible(false);

        friendPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFriendPanelOpened = !isFriendPanelOpened;
                friendPanelMode();
            }
        });

        friendSidePanel = new FriendSidePanel();
        friendSidePanel.setBorder(null);
        friendSidePanel.setBounds(970, 75, 240, 490);
        this.add(friendSidePanel);

        settingsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendshipRequestNotification notification1 = new FriendshipRequestNotification();
                FriendshipRequestCommand command = new FriendshipRequestCommand();
                command.setNickname_From("test" + Math.random());
                notification1.updateInfo(command);
                notificationPanel.addNotification(notification1);
            }
        });

        homeButton.setBounds(538, 8, 64, 64);
        topPanel.add(homeButton);
        plusButton.setBounds(612, 0, 64, 64);
        topPanel.add(plusButton);
        settingsButton.setBounds(686, 0, 64, 64);
        topPanel.add(settingsButton);
        contactsButton.setBounds(760, 0, 64, 64);
        topPanel.add(contactsButton);
        exitButton.setBounds(834, 0, 64, 64);
        topPanel.add(exitButton);
        friendPanelButton.setBounds(900, 10, 40, 45);
        topPanel.add(friendPanelButton);

        homePanel = new HomePanel();
        homePanel.setBounds(0, 84, 960, 1000);

        bigPanel.add(homePanel);

        dialogPanel = new DialogPanel();

        this.add(bigPanel);

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

    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }

    public void addNotificationPanel(Notification notification) {
        notificationPanel.addNotification(notification);
    }

    public void closeNotification() {
        notificationPanel.closeNotification();
    }

    public void changeModeToHomePanel() {
        friendPanelButton.setVisible(false);
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
        friendPanelButton.setVisible(true);
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
