package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFormVisualisation extends JFrame{

    private JPanel mainPanel;
    private JButton settingsButton = new JButton("SETTINGS");
    private JButton contactsButton = new JButton("CONTACTS");
    private JButton exitButton = new JButton("EXIT");
    private JLabel friendsNick = new JLabel("BOB_MORCOS");
    private JTextArea messageArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageArea);
    private MessageView messageView = new MessageView();

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

        friendsNick.setHorizontalAlignment(JLabel.RIGHT);
        messageArea.setFont(Fonts.typingFont);
        messageArea.setLineWrap(true);
        messageArea.setBackground(Color.LIGHT_GRAY);
        messageArea.setWrapStyleWord(true);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setBounds(screenWidth / 4, screenHeight / 4, screenWidth / 2, screenHeight / 2);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBackground(Color.BLACK);

        setLayout(new GridBagLayout());

        mainPanel.add(settingsButton, new GridBagConstraints(0, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 75, 15, 2), 0, 0));
        mainPanel.add(contactsButton, new GridBagConstraints(1, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 2), 0, 0));
        mainPanel.add(exitButton, new GridBagConstraints(2, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 2), 0, 0));
        mainPanel.add(friendsNick, new GridBagConstraints(3, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 85), 0, 0));
        JPanel lastPanel = new JPanel();
        lastPanel.setBackground(new Color(0, 0, 0, 0));
        lastPanel.setLayout(new GridBagLayout());

        messageArea.setBackground(new Color(0, 0, 0, 150));
        messageArea.setForeground(Color.WHITE);
        messageScrollPane.setOpaque(false);
        messageScrollPane.getViewport().setOpaque(false);
        lastPanel.add(messageScrollPane, new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 175, 5, 175), 0, 0));

        mainPanel.add(messageView, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 75, 2, 75), 0, 0));
        mainPanel.add(lastPanel, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 0.2, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        add(mainPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        //setVisible(true);

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
                    messageView.getMessage("Боб Моркос охуенный");
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
