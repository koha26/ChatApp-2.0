package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFormVisualisation extends JFrame{

    private JPanel mainPanel;
    private JButton settingsButton;
    private JButton contactsButton;
    private JButton exitButton;
    private JLabel friendsNick = new JLabel("BOB_MORCOS");
    private JTextArea messageArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageArea);
    private MessageView messageView = new MessageView();
    private Point mouseDownCompCoords;
    private final ImageIcon settingsButIcon = new ImageIcon("images/mainform/settings_button.png");
    private final ImageIcon settingsButIconEntered = new ImageIcon("images/mainform/settings_button_entered.png");
    private final ImageIcon contactsButIcon = new ImageIcon("images/mainform/contacts_button.png");
    private final ImageIcon contactsButIconEntered = new ImageIcon("images/mainform/contacts_button_entered.png");
    private final ImageIcon exitButIcon = new ImageIcon("images/mainform/exit_button.png");
    private final ImageIcon exitButIconEntered = new ImageIcon("images/mainform/exit_button_entered.png");

    public MainFormVisualisation() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp - Main window");
        setContentPane(new JLabel(Images.backgroundImage));
        setIconImage(Images.appIcon.getImage());
        setUndecorated(true);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        friendsNick.setHorizontalAlignment(JLabel.LEFT);
        friendsNick.setFont(Fonts.nickFont);
        friendsNick.setForeground(Color.WHITE);
        messageArea.setFont(Fonts.typingFont);
        messageArea.setLineWrap(true);
        messageArea.setBackground(Color.LIGHT_GRAY);
        messageArea.setWrapStyleWord(true);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        settingsButton = new JButton();
        settingsButton.setBackground(new Color(0, 0, 0, 0));
        settingsButton.setOpaque(false);
        settingsButton.setIcon(settingsButIcon);
        settingsButton.setBorder(null);
        settingsButton.setFocusPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                settingsButton.setIcon(settingsButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settingsButton.setIcon(settingsButIcon);
            }
        });

        contactsButton = new JButton();
        contactsButton.setBackground(new Color(0, 0, 0, 0));
        contactsButton.setOpaque(false);
        contactsButton.setIcon(contactsButIcon);
        contactsButton.setBorder(null);
        contactsButton.setFocusPainted(false);
        contactsButton.setContentAreaFilled(false);
        contactsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                contactsButton.setIcon(contactsButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                contactsButton.setIcon(contactsButIcon);
            }
        });

        exitButton = new JButton();
        exitButton.setBackground(new Color(0, 0, 0, 0));
        exitButton.setOpaque(false);
        exitButton.setIcon(exitButIcon);
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(exitButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitButIcon);
            }
        });

        setBounds(screenWidth / 4, screenHeight / 4, screenWidth / 2, screenHeight / 2);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBackground(Color.BLACK);

        setLayout(new GridBagLayout());

        mainPanel.add(settingsButton, new GridBagConstraints(1, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 2), 0, 0));
        mainPanel.add(contactsButton, new GridBagConstraints(2, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 2), 0, 0));
        mainPanel.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 75), 0, 0));
        mainPanel.add(friendsNick, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 100, 15, 20), 0, 0));

        JPanel lastPanel = new JPanel();
        lastPanel.setBackground(new Color(0, 0, 0, 0));
        lastPanel.setLayout(new GridBagLayout());

        messageArea.setBackground(new Color(0, 0, 0, 150));
        messageArea.setForeground(Color.WHITE);
        messageScrollPane.setOpaque(false);
        messageScrollPane.getViewport().setOpaque(false);
        messageScrollPane.setBorder(null);
        lastPanel.add(messageScrollPane, new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 175, 5, 175), 0, 0));

        mainPanel.add(messageView, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 75, 2, 75), 0, 0));
        mainPanel.add(lastPanel, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 0.2, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        add(mainPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        RepaintPanel repaintPanel = new RepaintPanel(mainPanel);
        Thread repaintThread = new Thread(repaintPanel);
        repaintThread.start();

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        messageArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    messageView.sendMessage(messageArea.getText());
                    messageView.getMessage("Привет!");
                    messageArea.setText("");
                    mainPanel.repaint();
                    mainPanel.revalidate();
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFormVisualisation mainFrame = new MainFormVisualisation();
                    mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
