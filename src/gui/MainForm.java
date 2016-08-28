package gui;

import gui.Notifications.Notification;
import logic.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private HomePanel homePanel;
    private DialogPanel currentDialogPanel;
    private DialogTab currentDialogTab;
    private JPanel bigPanel;
    private NotificationPanel notificationPanel;
    private JPanel dialogTabsPanel;
    private JPanel noConversationsPanel;
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
    private final ImageIcon dialogButIcon = new ImageIcon("images/mainform/msg.png");
    private final ImageIcon dialogButIconEntered = new ImageIcon("images/mainform/msg_entered.png");
    private final ImageIcon newDialogButIcon = new ImageIcon("images/mainform/new_msg.png");
    private final ImageIcon newDialogButIconEntered = new ImageIcon("images/mainform/new_msg_entered.png");
    private final ImageIcon friendSideOpenIcon = new ImageIcon("images/mainform/sidepnl_open.png");
    private final ImageIcon friendSideCloseIcon = new ImageIcon("images/mainform/sidepnl_close.png");
    private final ImageIcon friendSideOpenIconEntered = new ImageIcon("images/mainform/sidepnl_open_entr.png");
    private final ImageIcon friendSideCloseIconEntered = new ImageIcon("images/mainform/sidepnl_close_entr.png");
    private final ImageIcon changeAccButIcon = new ImageIcon("images/mainform/changeacc.png");
    private final ImageIcon changeAccButIconEntered = new ImageIcon("images/mainform/changeacc_entered.png");
    private JButton changeAccButton, settingsButton, contactsButton, exitButton, plusButton, homeButton, friendPanelButton;
    private FriendSidePanel friendSidePanel;
    private boolean isFriendPanelOpened;
    private ArrayList<DialogPanel> dialogPanelArrayList;
    private ArrayList<DialogTab> dialogTabArrayList;

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

        dialogPanelArrayList = new ArrayList<>();
        dialogTabArrayList = new ArrayList<>();

        bigPanel = new JPanel(null);
        bigPanel.setSize(1300, 600);
        bigPanel.setOpaque(false);
        bigPanel.setBackground(new Color(0, 0, 0, 0));

        dialogTabsPanel = new JPanel(null);
        dialogTabsPanel.setBackground(new Color(0, 0, 0, 150));

        JPanel topPanel = new JPanel(null);
        topPanel.setBackground(new Color(0, 0, 0, 150));
        topPanel.setBounds(30, 20, 900, 65);
        topPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
        bigPanel.add(topPanel);

        notificationPanel = new NotificationPanel();
        notificationPanel.setBounds(5, 0, 500, 65);
        notificationPanel.setBorder(null);
        topPanel.add(notificationPanel);

        RepaintPanel repaintPanel = new RepaintPanel(notificationPanel);
        Thread thread = new Thread(repaintPanel);
        thread.start();

        RepaintPanel repaintPanel2 = new RepaintPanel(bigPanel);
        Thread thread2 = new Thread(repaintPanel2);
        thread2.start();

        changeAccButton = GUIStandartOperations.ButtonStartOperations(changeAccButIcon, changeAccButIconEntered, true);
        settingsButton = GUIStandartOperations.ButtonStartOperations(settingsButIcon, settingsButIconEntered, true);
        contactsButton = GUIStandartOperations.ButtonStartOperations(contactsButIcon, contactsButIconEntered, true);
        exitButton = GUIStandartOperations.ButtonStartOperations(exitButIcon, exitButIconEntered, true);
        plusButton = GUIStandartOperations.ButtonStartOperations(plusButIcon, plusButIconEntered, true);
        homeButton = GUIStandartOperations.ButtonStartOperations(dialogButIcon, dialogButIconEntered, true);

        changeAccButton.setToolTipText("Change account");
        settingsButton.setToolTipText("Your personal settings");
        contactsButton.setToolTipText("Contacts");
        exitButton.setToolTipText("Exit ChatApp");
        homeButton.setToolTipText("Dialog page / Home");

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

        friendPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFriendPanelOpened = !isFriendPanelOpened;
                friendPanelMode();
            }
        });

        friendSidePanel = new FriendSidePanel();
        friendSidePanel.setBorder(null);
        friendSidePanel.setBounds(970, 50, 450, 650);
        bigPanel.add(friendSidePanel);

        noConversationsPanel = new JPanel(null);
        noConversationsPanel.setOpaque(false);
        noConversationsPanel.setBackground(new Color(0, 0, 0, 0));
        noConversationsPanel.setBounds(0, 0, 960, 600);
        JLabel noConversationLabel = new JLabel("You have no active conversations now...");
        noConversationLabel.setFont(Fonts.nickFont);
        noConversationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noConversationLabel.setForeground(Color.WHITE);
        noConversationLabel.setBackground(new Color(0, 0, 0, 0));
        noConversationLabel.setBounds(0, 300, 960, 50);

        JButton noConversationsButton = GUIStandartOperations.ButtonStartOperations(null, null, false);
        noConversationsButton.setText("<HTML><U>It's time to begin, isn't it?</U></HTML>");
        noConversationsButton.setFont(Fonts.nickFont);
        noConversationsButton.setBounds(0, 350, 960, 50);
        noConversationsButton.setHorizontalAlignment(SwingConstants.CENTER);
        noConversationsButton.setForeground(Color.RED);
        noConversationsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFriendPanelOpened = true;
                friendPanelMode();
            }
        });

        noConversationsPanel.add(noConversationsButton);
        noConversationsPanel.add(noConversationLabel);
        noConversationsPanel.setVisible(false);

        final int shiftRight = 90;

        changeAccButton.setBounds(420 + shiftRight, 6, 64, 64);
        topPanel.add(changeAccButton);
        homeButton.setBounds(550 + shiftRight, 6, 64, 64);
        topPanel.add(homeButton);
        settingsButton.setBounds(650 + shiftRight, 0, 64, 64);
        topPanel.add(settingsButton);
        contactsButton.setBounds(600 + shiftRight, 0, 64, 64);
        topPanel.add(contactsButton);
        exitButton.setBounds(470 + shiftRight, 0, 64, 64);
        topPanel.add(exitButton);
        friendPanelButton.setBounds(730 + shiftRight, 6, 64, 64);
        topPanel.add(friendPanelButton);

        friendPanelButton.setVisible(false);

        homePanel = new HomePanel();
        homePanel.setBounds(0, 84, 960, 1000);

        dialogTabsPanel.setBounds(30, 100, 900, 40);
        dialogTabsPanel.setVisible(false);

        bigPanel.add(dialogTabsPanel);
        bigPanel.add(homePanel);
        bigPanel.add(noConversationsPanel);

        this.add(bigPanel);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void addNotificationPanel(Notification notification) {
        notificationPanel.addNotification(notification);
    }

    public void closeNotification() {
        notificationPanel.closeNotification();
    }

    public void repaintDialogTabsPanel() {
        dialogTabsPanel.setVisible(false);
        dialogTabsPanel = new JPanel(null);
        dialogTabsPanel.setBorder(null);
        dialogTabsPanel.setBackground(new Color(0, 0, 0, 150));
        dialogTabsPanel.setBounds(100, 100, 760, 54);

        for (int i = 0; i < dialogTabArrayList.size(); i++) {
            dialogTabArrayList.get(i).setBounds(i * 151, 0, 150, 40);
            dialogTabsPanel.add(dialogTabArrayList.get(i));
        }

        bigPanel.add(dialogTabsPanel);

        repaint();
        revalidate();
    }

    public boolean closeDialog(DialogTab dialogTab) {
        for (int i = 0; i < dialogTabArrayList.size(); i++) {
            if (dialogTabArrayList.get(i).equals(dialogTab)) {
                dialogPanelArrayList.remove(i);
                dialogTabArrayList.remove(i);
                if (!(currentDialogPanel == null))
                    currentDialogPanel.setVisible(false);
                if (dialogTabArrayList.size() == 0) {
                    dialogTabsPanel.setVisible(false);
                    currentDialogTab = null;
                    currentDialogPanel = null;
                    repaint();
                    revalidate();
                    return true;
                }

                if ((dialogTab.equals(currentDialogTab))) {
                    if (i > 0) {
                        currentDialogPanel = dialogPanelArrayList.get(i - 1);
                        currentDialogTab = dialogTabArrayList.get(i - 1);
                    } else {
                        currentDialogPanel = dialogPanelArrayList.get(i);
                        currentDialogTab = dialogTabArrayList.get(i);
                    }

                }

                if (!(currentDialogPanel == null)) {
                    currentDialogTab.setBorder(new LineBorder(Color.RED));
                    currentDialogPanel.setBounds(0, 84, 960, 1000);
                    bigPanel.add(currentDialogPanel);
                    currentDialogPanel.setVisible(true);
                }

                dialogTabsPanel.setVisible(false);
                if (dialogTabArrayList.size() > 0)
                    repaintDialogTabsPanel();
                bigPanel.repaint();
                bigPanel.revalidate();
                break;
            }
        }
        return false;
    }

    public boolean startNewDialog(BufferedImage myPhoto, BufferedImage friendPhoto, String friendNick) {
        for (int i = 0; i < dialogTabArrayList.size(); i++) {
            if (dialogTabArrayList.get(i).getNickButton().getText().equals(friendNick)) {
                if (!(currentDialogPanel == null)) {
                    currentDialogPanel.setVisible(false);
                    currentDialogTab.setBorder(new LineBorder(Color.WHITE));
                }
                currentDialogPanel = dialogPanelArrayList.get(i);
                currentDialogTab = dialogTabArrayList.get(i);
                currentDialogTab.setBorder(new LineBorder(Color.RED));
                currentDialogTab.getNewMessageLabel().setVisible(false);
                currentDialogPanel.setBounds(0, 84, 960, 1000);
                bigPanel.add(currentDialogPanel);
                currentDialogPanel.setVisible(true);
                repaint();
                revalidate();
                return false;
            }
        }

        final DialogTab dialogTab = new DialogTab(friendNick);
        currentDialogTab = dialogTab;

        dialogTab.setBorder(new LineBorder(Color.RED));
        dialogTabArrayList.add(dialogTab);
        final DialogPanel dialogPanel = new DialogPanel();
        try {
            dialogPanel.updateInfo(myPhoto, friendPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogPanelArrayList.add(dialogPanel);

        for (int i = 0; i < dialogTabArrayList.size() - 1; i++) {
            dialogTabArrayList.get(i).setBorder(new LineBorder(Color.WHITE));
        }

        if (!(currentDialogPanel == null))
            currentDialogPanel.setVisible(false);
        currentDialogPanel = dialogPanel;
        currentDialogPanel.setBounds(0, 84, 960, 1000);
        bigPanel.add(currentDialogPanel);
        repaintDialogTabsPanel();
        repaint();
        revalidate();

        dialogTab.getNickButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < dialogTabArrayList.size(); i++) {
                    dialogTabArrayList.get(i).setBorder(new LineBorder(Color.WHITE));
                }

                dialogTab.setBorder(new LineBorder(Color.RED));

                dialogTab.getNewMessageLabel().setVisible(false);

                currentDialogTab = dialogTab;

                currentDialogPanel.setVisible(false);
                currentDialogPanel = dialogPanel;
                currentDialogPanel.setBounds(0, 84, 960, 1000);
                bigPanel.add(currentDialogPanel);
                currentDialogPanel.setVisible(true);
                repaintDialogTabsPanel();
                repaint();
                revalidate();
            }
        });

        noConversationsPanel.setVisible(false);

        return true;
    }

    public DialogPanel receiveIncomingMessage(Message message, BufferedImage myPhoto, BufferedImage friendPhoto, Mode mode) {
        if (mode == Mode.HOME_PANEL) {
            homeButton.setIcon(newDialogButIcon);
            PopUpMenu.displayMessage("You have new message from " + message.getNickname_From());
            homeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    homeButton.setIcon(newDialogButIconEntered);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    homeButton.setIcon(newDialogButIcon);
                }
            });
        }

        for (int i = 0; i < dialogTabArrayList.size(); i++) {
            if (message.getNickname_From().equals(dialogTabArrayList.get(i).getNickButton().getText())) {
                if (dialogTabArrayList.get(i).equals(currentDialogTab)) {
                    dialogPanelArrayList.get(i).showIncomingMessage(message);
                } else {
                    dialogPanelArrayList.get(i).showIncomingMessage(message);
                    dialogTabArrayList.get(i).getNewMessageLabel().setVisible(true);
                }
                return null;
            }
        }

        final DialogTab dialogTab = new DialogTab(message.getNickname_From());

        dialogTab.setBorder(new LineBorder(Color.WHITE));
        dialogTabArrayList.add(dialogTab);
        dialogTab.getNewMessageLabel().setVisible(true);
        final DialogPanel dialogPanel = new DialogPanel();
        try {
            dialogPanel.updateInfo(myPhoto, friendPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogPanelArrayList.add(dialogPanel);

        repaintDialogTabsPanel();
        repaint();
        revalidate();

        dialogTab.getNickButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < dialogTabArrayList.size(); i++) {
                    dialogTabArrayList.get(i).setBorder(new LineBorder(Color.WHITE));
                }

                dialogTab.setBorder(new LineBorder(Color.RED));

                dialogTab.getNewMessageLabel().setVisible(false);

                currentDialogTab = dialogTab;

                if (!(currentDialogPanel == null))
                    currentDialogPanel.setVisible(false);
                currentDialogPanel = dialogPanel;
                currentDialogPanel.setBounds(0, 84, 960, 1000);
                bigPanel.add(currentDialogPanel);
                currentDialogPanel.setVisible(true);
                repaintDialogTabsPanel();
                repaint();
                revalidate();
            }
        });
        dialogPanel.showIncomingMessage(message);

        noConversationsPanel.setVisible(false);

        return dialogPanel;
    }

    public void changeMode(Mode mode) {
        if (mode == Mode.DIALOG) {
            boolean newMessages = false;
            for (int i = 0; i < dialogTabArrayList.size(); i++) {
                if (dialogTabArrayList.get(i).getNewMessageLabel().isVisible()) {
                    newMessages = true;
                }
            }
            if (newMessages) {
                homeButton.setIcon(newDialogButIcon);
            } else {
                homeButton.setIcon(dialogButIcon);
            }
            final boolean finalNewMessages = newMessages;
            homeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (finalNewMessages) {
                        homeButton.setIcon(newDialogButIconEntered);
                    } else {
                        homeButton.setIcon(dialogButIconEntered);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (finalNewMessages) {
                        homeButton.setIcon(newDialogButIcon);
                    } else {
                        homeButton.setIcon(dialogButIcon);
                    }
                }
            });

            if (!(currentDialogTab == null))
                currentDialogPanel.setVisible(false);
            homePanel.setVisible(true);
            friendPanelButton.setVisible(false);
            dialogTabsPanel.setVisible(false);
            isFriendPanelOpened = false;
            noConversationsPanel.setVisible(false);
            friendPanelMode();
        } else if (mode == Mode.HOME_PANEL)

        {
            homeButton.setIcon(homeButIcon);
            homeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    homeButton.setIcon(homeButIconEntered);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    homeButton.setIcon(homeButIcon);
                }
            });

            friendPanelButton.setVisible(true);
            homePanel.setVisible(false);
            if (dialogTabArrayList.size() > 0) {
                dialogTabsPanel.setVisible(true);
            } else {
                noConversationsPanel.setVisible(true);
            }
            if (!(currentDialogPanel == null)) {
                currentDialogPanel.setVisible(true);
            }
            friendPanelButton.setVisible(true);
            repaint();
        }
    }

    public void friendPanelMode() {
        if (isFriendPanelOpened) {
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
            setSize(1300, 610);
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

    public DialogTab getCurrentDialogTab() {
        return currentDialogTab;
    }

    public JPanel getDialogTabsPanel() {
        return dialogTabsPanel;
    }

    public JButton getPlusButton() {
        return plusButton;
    }

    public DialogPanel getCurrentDialogPanel() {
        return currentDialogPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public FriendSidePanel getFriendSidePanel() {
        return friendSidePanel;
    }

    public ArrayList<DialogPanel> getDialogPanelArrayList() {
        return dialogPanelArrayList;
    }

    public ArrayList<DialogTab> getDialogTabArrayList() {
        return dialogTabArrayList;
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JPanel getBigPanel() {
        return bigPanel;
    }

    public JButton getChangeAccButton() {
        return changeAccButton;
    }
}
