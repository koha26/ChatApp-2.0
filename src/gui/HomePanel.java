package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomePanel extends JPanel {
    private JLabel yourPhoto;
    private JLabel nickLabel;
    private JLabel personaInfo;
    private JPanel leftPanel, rightPanel, bottomPanel, topPanel;
    private JButton settingsButton, contactsButton, exitButton;
    private final ImageIcon settingsButIcon = new ImageIcon("images/mainform/settings_button.png");
    private final ImageIcon settingsButIconEntered = new ImageIcon("images/mainform/settings_button_entered.png");
    private final ImageIcon contactsButIcon = new ImageIcon("images/mainform/contacts_button.png");
    private final ImageIcon contactsButIconEntered = new ImageIcon("images/mainform/contacts_button_entered.png");
    private final ImageIcon exitButIcon = new ImageIcon("images/mainform/exit_button.png");
    private final ImageIcon exitButIconEntered = new ImageIcon("images/mainform/exit_button_entered.png");

    public HomePanel() {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));

        yourPhoto = new JLabel(new ImageIcon("images/avatarBIG.png"));
        nickLabel = new JLabel("MaxTEAMLEAD");
        nickLabel.setFont(Fonts.nickFont);
        nickLabel.setForeground(Color.WHITE);
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(0, 0, 0, 150));

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(0, 0, 0, 150));
        personaInfo = new JLabel("Personal Info");
        personaInfo.setFont(Fonts.nickFont);
        personaInfo.setForeground(Color.WHITE);
        personaInfo.setHorizontalAlignment(SwingConstants.CENTER);
        personaInfo.setBorder(new LineBorder(Color.WHITE, 5));

        JLabel namePanel = new JLabel("Name: ");
        JLabel surnamePanel = new JLabel("Surname: ");
        JLabel dateOfBirthPanel = new JLabel("Date of birth: ");
        JLabel sexPanel = new JLabel("Sex: ");
        namePanel.setForeground(Color.WHITE);
        surnamePanel.setForeground(Color.WHITE);
        dateOfBirthPanel.setForeground(Color.WHITE);
        sexPanel.setForeground(Color.WHITE);

        JLabel namePanelInfo = new JLabel("Max");
        JLabel surnamePanelInfo = new JLabel("Tkachenko");
        JLabel dateOfBirthPanelInfo = new JLabel("6 September 1997");
        JLabel sexPanelInfo = new JLabel("Male");
        namePanelInfo.setForeground(Color.RED);
        surnamePanelInfo.setForeground(Color.RED);
        dateOfBirthPanelInfo.setForeground(Color.RED);
        sexPanelInfo.setForeground(Color.RED);

        namePanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        surnamePanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        dateOfBirthPanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        sexPanelInfo.setHorizontalAlignment(SwingConstants.CENTER);

        namePanel.setFont(Fonts.nickFont);
        surnamePanel.setFont(Fonts.nickFont);
        dateOfBirthPanel.setFont(Fonts.nickFont);
        sexPanel.setFont(Fonts.nickFont);
        namePanelInfo.setFont(Fonts.nickFont);
        surnamePanelInfo.setFont(Fonts.nickFont);
        dateOfBirthPanelInfo.setFont(Fonts.nickFont);
        sexPanelInfo.setFont(Fonts.nickFont);

        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(new Color(0, 0, 0, 150));

        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(0, 0, 0, 0));

        settingsButton = GUIStandartOperations.ButtonStartOperations(settingsButIcon, settingsButIconEntered, true);
        contactsButton = GUIStandartOperations.ButtonStartOperations(contactsButIcon, contactsButIconEntered, true);
        exitButton = GUIStandartOperations.ButtonStartOperations(exitButIcon, exitButIconEntered, true);

        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });

        topPanel.add(settingsButton, new GridBagConstraints(1, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 700, 2, 2), 0, 0));
        topPanel.add(contactsButton, new GridBagConstraints(2, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        topPanel.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 75), 0, 0));

        bottomPanel.add(new FriendLook("images/avatar.png", "Kostya Dyachenko", "koha26"), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(new FriendLook("images/avatar.png", "Kate Kurshakova", "marphinia"), new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(new FriendLook("images/avatar.png", "Igor Pekhov", "addep7y"), new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(new FriendLook("images/avatar.png", "Jesse Lingard", "DABBERMAN"), new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(new FriendLook("images/avatar.png", "Sasha Shatalov", "Sanchez"), new GridBagConstraints(4, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        leftPanel.add(yourPhoto, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        leftPanel.add(nickLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        rightPanel.add(personaInfo, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        rightPanel.add(namePanel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 10, 2, 2), 0, 0));
        rightPanel.add(namePanelInfo, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        rightPanel.add(surnamePanel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 10, 2, 2), 0, 0));
        rightPanel.add(surnamePanelInfo, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        rightPanel.add(dateOfBirthPanel, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 10, 2, 2), 0, 0));
        rightPanel.add(dateOfBirthPanelInfo, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        rightPanel.add(sexPanel, new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 10, 2, 2), 0, 0));
        rightPanel.add(sexPanelInfo, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        this.add(topPanel, new GridBagConstraints(0, 0, 2, 1, 0.2, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(leftPanel, new GridBagConstraints(0, 1, 1, 1, 0.2, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 15, 20), 0, 0));
        this.add(rightPanel, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 20, 15, 20), 0, 0));
        this.add(bottomPanel, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 100, 2, 100), 0, 0));

        RepaintPanel repaintPanel = new RepaintPanel(this);
        Thread thread = new Thread(repaintPanel);
        thread.start();
    }
}
