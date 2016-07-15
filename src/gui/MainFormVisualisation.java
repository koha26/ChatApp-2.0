package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFormVisualisation {
    public static MainFormVisualisation window;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton settingsButton = new JButton("SETTINGS");
    private JButton contactsButton = new JButton("CONTACTS");
    private JButton exitButton = new JButton("EXIT");
    private JLabel friendsNick = new JLabel("NICK");
    private JTextArea messageArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageArea);
    private MessageView messageView = new MessageView();

    public MainFormVisualisation() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setTitle("ChatApp - Main window");
        mainFrame.setContentPane(new JLabel(Images.backgroundImage));
        mainFrame.setIconImage(Images.appIcon.getImage());
        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        friendsNick.setHorizontalAlignment(JLabel.RIGHT);
        messageArea.setFont(Fonts.typingFont);
        messageArea.setLineWrap(true);
        messageArea.setBackground(Color.LIGHT_GRAY);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainFrame.setBounds(screenWidth / 4, screenHeight / 4, screenWidth / 2, screenHeight / 2);

       /* mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);/*
        mainPanel.setBackground(Color.BLACK);*/
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.add(settingsButton, new GridBagConstraints(0, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainFrame.add(contactsButton, new GridBagConstraints(1, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainFrame.add(exitButton, new GridBagConstraints(2, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainFrame.add(friendsNick, new GridBagConstraints(3, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 100), 0, 0));
        JPanel lastPanel = new JPanel();
        lastPanel.setBackground(new Color(0, 0, 0, 150));
        lastPanel.setLayout(new GridBagLayout());
        lastPanel.add(messageArea, new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 100, 5, 100), 0, 0));

        mainFrame.add(messageView, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainFrame.add(lastPanel, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 0.2, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

       // mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

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
                    messageArea.setText("");
                }
            }

        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new MainFormVisualisation();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
