package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainFormVisualisation extends JFrame {
    private JPanel mainPanel;
    private JButton settingsButton, contactsButton, exitButton;
    private JButton friendsNick = new OutlineButton("MaxTEAMLEAD");
    private JTextArea messageArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageArea);
    private MessageView messageView = new MessageView();
    private Point mouseDownCompCoords = new Point();
    private final ImageIcon settingsButIcon = new ImageIcon("images/mainform/settings_button.png");
    private final ImageIcon settingsButIconEntered = new ImageIcon("images/mainform/settings_button_entered.png");
    private final ImageIcon contactsButIcon = new ImageIcon("images/mainform/contacts_button.png");
    private final ImageIcon contactsButIconEntered = new ImageIcon("images/mainform/contacts_button_entered.png");
    private final ImageIcon exitButIcon = new ImageIcon("images/mainform/exit_button.png");
    private final ImageIcon exitButIconEntered = new ImageIcon("images/mainform/exit_button_entered.png");

    public MainFormVisualisation() throws IOException {
        GUIStandartOperations.FrameStartOperations(this);
        setSize(960,540);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

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

        friendsNick.setHorizontalAlignment(JLabel.LEFT);
        friendsNick.setFont(Fonts.nickFont);
        friendsNick.setForeground(Color.WHITE);
        friendsNick.setBackground(new Color(0, 0, 0, 0));
        friendsNick.setOpaque(false);
        friendsNick.setBorder(null);
        friendsNick.setFocusPainted(false);
        friendsNick.setContentAreaFilled(false);

        messageArea.setFont(Fonts.typingFont);
        messageArea.setLineWrap(true);
        messageArea.setBackground(Color.LIGHT_GRAY);
        messageArea.setWrapStyleWord(true);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        settingsButton = GUIStandartOperations.ButtonStartOperations(settingsButIcon, settingsButIconEntered, true);
        contactsButton = GUIStandartOperations.ButtonStartOperations(contactsButIcon, contactsButIconEntered, true);
        exitButton = GUIStandartOperations.ButtonStartOperations(exitButIcon, exitButIconEntered, true);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBackground(Color.BLACK);

        JPanel lastPanel = new JPanel();
        lastPanel.setBackground(new Color(0, 0, 0, 0));
        lastPanel.setLayout(new GridBagLayout());

        messageArea.setBackground(new Color(0, 0, 0, 150));
        messageArea.setForeground(Color.WHITE);
        messageScrollPane.setOpaque(false);
        messageScrollPane.getViewport().setOpaque(false);
        messageScrollPane.setBorder(null);
        lastPanel.add(messageScrollPane, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 10), 0, 0));
        lastPanel.add(CircleLabelClass.CircleLabel("max.jpg"), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 100, 5, 0), 0, 0));
        lastPanel.add(CircleLabelClass.CircleLabel("pavlo.jpg"), new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 100), 0, 0));

        mainPanel.add(friendsNick, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 100, 15, 300), 0, 0));
        mainPanel.add(settingsButton, new GridBagConstraints(1, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 2), 0, 0));
        mainPanel.add(contactsButton, new GridBagConstraints(2, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 2), 0, 0));
        mainPanel.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 2, 15, 75), 0, 0));
        mainPanel.add(messageView, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 75, 2, 75), 0, 0));
        mainPanel.add(lastPanel, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

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
                    messageArea.setText("");
                    messageView.getMessage("Здравствуйте, Максим Вадимович!");
                    repaintAndRevalidate();
                }
            }
        });
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public void sendMessage() {
        messageView.sendMessage(messageArea.getText());
    }

    public void getMessage(String text) {
        messageView.getMessage(text);
    }

    public void repaintAndRevalidate() {
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
