package gui;

import logic.Constants;
import logic.Sex;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RegPanel extends JPanel {
    private JLabel nickLabel, passwordLabel, confirmPasswordLabel, dateOfBirthLabel, nameLabel, surnameLabel, sexLabel, mainLabel, additionalLabel;
    private JTextField nicknameField, nameField, surnameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox dayBox, monthBox, yearBox, sexBox;
    private static JLabel avatar;
    private JCheckBox additionalCheckBox;
    private static JButton regButton;
    private JButton backButton;
    private static JButton openButton;
    private Font font = new Font("Century Gothic", Font.PLAIN, 16);
    private final ImageIcon regButIcon = new ImageIcon("images/loginform/regbut.png");
    private final ImageIcon regButIconEntered = new ImageIcon("images/loginform/regbut_entered.png");
    private boolean isEntered;
    private static BufferedImage bufImage;

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    public void setEntered(boolean isEntered) {
        this.isEntered = isEntered;
    }

    public static BufferedImage getBufImage() {
        return bufImage;
    }

    public static void changeEnableOfButtons() {
        regButton.setEnabled(true);
        openButton.setEnabled(true);
    }

    public static void setBufImage(BufferedImage bufImage2) {
        bufImage = bufImage2;
        avatar.setIcon(new ImageIcon(bufImage));
    }

    public void changeView() {
        isEntered = !isEntered;
        avatar.setVisible(isEntered);
        openButton.setVisible(isEntered);
        nameField.setVisible(isEntered);
        nameLabel.setVisible(isEntered);
        surnameField.setVisible(isEntered);
        surnameLabel.setVisible(isEntered);
        sexBox.setVisible(isEntered);
        sexLabel.setVisible(isEntered);
        dateOfBirthLabel.setVisible(isEntered);
        yearBox.setVisible(isEntered);
        monthBox.setVisible(isEntered);
        dayBox.setVisible(isEntered);
        if (isEntered) {
            regButton.setBounds(390, 570, 180, 50);
            backButton.setBounds(285, 620, 380, 30);
        } else {
            regButton.setBounds(390, 260, 180, 50);
            backButton.setBounds(285, 310, 380, 30);
        }
    }

    public JCheckBox getAdditionalCheckBox() {
        return additionalCheckBox;
    }

    public JTextField getNicknameField() {
        return nicknameField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getSurnameField() {
        return surnameField;
    }

    public JComboBox getDayBox() {
        return dayBox;
    }

    public JComboBox getMonthBox() {
        return monthBox;
    }

    public JComboBox getYearBox() {
        return yearBox;
    }

    public JComboBox getSexBox() {
        return sexBox;
    }

    public JLabel getAvatar() {
        return avatar;
    }

    public JPasswordField getConfirmPasswordField() {

        return confirmPasswordField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getRegButton() {
        return regButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public RegPanel() {
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 150));
        this.setOpaque(false);

        try {
            bufImage = ImageIO.read(new File("images/avatarBIG.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        avatar = new JLabel(new ImageIcon(bufImage));

        mainLabel = new JLabel("Main Information");
        mainLabel.setForeground(Color.RED);
        mainLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));

        nickLabel = new JLabel("Nickname");
        passwordLabel = new JLabel("Password");
        confirmPasswordLabel = new JLabel("Confirm password");
        nickLabel.setFont(font);
        nickLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(font);
        passwordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setFont(font);
        confirmPasswordLabel.setForeground(Color.WHITE);
        nickLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        nicknameField = new JTextField();
        nicknameField.setBorder(null);
        nicknameField.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameField.setFont(Fonts.typingFont);
        nicknameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nicknameField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });

        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setFont(Fonts.typingFont);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (passwordField.getPassword().length == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(null);
        confirmPasswordField.setFont(Fonts.typingFont);
        confirmPasswordField.setHorizontalAlignment(JTextField.CENTER);
        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (confirmPasswordField.getPassword().length == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });

        additionalLabel = new JLabel("Additional Information");
        additionalLabel.setForeground(Color.RED);
        additionalLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));

        additionalCheckBox = new JCheckBox();
        additionalCheckBox.setBackground(new Color(0, 0, 0, 0));
        additionalCheckBox.setOpaque(false);

        openButton = new JButton("Open");
        openButton.setForeground(Color.WHITE);
        openButton.setBorder(new LineBorder(Color.WHITE, 1));
        openButton.setBackground(new Color(0, 0, 0, 0));
        openButton.setFocusPainted(false);
        openButton.setContentAreaFilled(false);
        openButton.setOpaque(false);

        final JFileChooser fileopen = new JFileChooser();

        openButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileopen.setCurrentDirectory(new File("/"));
                fileopen.showOpenDialog(null);
                fileopen.setMultiSelectionEnabled(false);
                if (!(fileopen.getSelectedFile() == null)) {
                    final File file = fileopen.getSelectedFile();
                    if (getFileExtension(file).toLowerCase().equals("jpg")) {
                        ImageIcon img = new ImageIcon(file.getAbsolutePath());
                        if (img.getIconHeight() >= 250 || img.getIconWidth() >= 250) {
                            regButton.setEnabled(false);
                            openButton.setEnabled(false);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        new AvatarEditor(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(null, "Very small picture. Choose 250x250px or bigger!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose JPEG File please!");
                    }
                }
            }
        });

        nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);
        surnameLabel = new JLabel("Surname");
        surnameLabel.setFont(font);
        surnameLabel.setForeground(Color.WHITE);
        sexLabel = new JLabel("Sex");
        sexLabel.setFont(font);
        sexLabel.setForeground(Color.WHITE);
        dateOfBirthLabel = new JLabel("Date of birth");
        dateOfBirthLabel.setFont(font);
        dateOfBirthLabel.setForeground(Color.WHITE);

        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        surnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateOfBirthLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        nameField = new JTextField();
        nameField.setBorder(null);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setFont(Fonts.typingFont);
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nicknameField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });

        surnameField = new JTextField();
        surnameField.setBorder(null);
        surnameField.setHorizontalAlignment(SwingConstants.CENTER);
        surnameField.setFont(Fonts.typingFont);
        surnameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nicknameField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });

        ArrayList<Integer> yearsList = new ArrayList<>(); // модель для комбобокса годов
        for (int year = 1950; year < 2017; year++)
            yearsList.add(year);

        String[] monthsList = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"}; // модель для комбобоса месяцев

        ArrayList<Integer> daysList = new ArrayList<>(); // модель для комбобокса дней
        for (int day = 1; day < 32; day++)
            daysList.add(day);

        yearBox = new JComboBox(new DefaultComboBoxModel(yearsList.toArray()));
        yearBox.setBackground(Color.WHITE);
        yearBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        monthBox = new JComboBox(monthsList);
        monthBox.setBackground(Color.WHITE);
        monthBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        dayBox = new JComboBox(daysList.toArray());
        dayBox.setBackground(Color.WHITE);
        dayBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        sexBox = new JComboBox(Sex.values());
        sexBox.setBackground(Color.WHITE);
        sexBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        avatar.setBorder(new LineBorder(Color.WHITE, 1));

        regButton = GUIStandartOperations.ButtonStartOperations(regButIcon, regButIconEntered, true);

        backButton = GUIStandartOperations.ButtonStartOperations(null, null, false);
        backButton.setText("Back to login form");
        backButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        backButton.setForeground(Color.WHITE);

        avatar.setVisible(isEntered);
        openButton.setVisible(isEntered);
        nameField.setVisible(isEntered);
        nameLabel.setVisible(isEntered);
        surnameField.setVisible(isEntered);
        surnameLabel.setVisible(isEntered);
        sexBox.setVisible(isEntered);
        sexLabel.setVisible(isEntered);
        dateOfBirthLabel.setVisible(isEntered);
        yearBox.setVisible(isEntered);
        monthBox.setVisible(isEntered);
        dayBox.setVisible(isEntered);

        mainLabel.setBounds(130, 70, 280, 30);
        nickLabel.setBounds(130, 115, 80, 30);
        nicknameField.setBounds(220, 115, 220, 25);
        passwordLabel.setBounds(510, 115, 80, 30);
        passwordField.setBounds(600, 115, 220, 25);
        confirmPasswordLabel.setBounds(450, 155, 140, 30);
        confirmPasswordField.setBounds(600, 155, 220, 25);

        additionalLabel.setBounds(130, 220, 350, 30);
        additionalCheckBox.setBounds(495, 215, 50, 50);
        avatar.setBounds(130, 270, 260, 260);
        openButton.setBounds(225, 535, 80, 20);
        nameLabel.setBounds(450, 270, 140, 30);
        nameField.setBounds(600, 270, 220, 25);
        surnameLabel.setBounds(450, 310, 140, 30);
        surnameField.setBounds(600, 310, 220, 25);
        sexLabel.setBounds(450, 350, 140, 30);
        sexBox.setBounds(600, 350, 100, 25);
        dateOfBirthLabel.setBounds(450, 390, 140, 30);
        yearBox.setBounds(600, 390, 70, 25);
        monthBox.setBounds(675, 390, 100, 25);
        dayBox.setBounds(780, 390, 40, 25);

        regButton.setBounds(390, 260, 180, 50);
        backButton.setBounds(285, 310, 380, 30);

        this.add(mainLabel);
        this.add(nickLabel);
        this.add(nicknameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(confirmPasswordLabel);
        this.add(confirmPasswordField);
        this.add(additionalLabel);
        this.add(additionalCheckBox);
        this.add(avatar);
        this.add(nameLabel);
        this.add(nameField);
        this.add(surnameLabel);
        this.add(surnameField);
        this.add(openButton);
        this.add(sexLabel);
        this.add(sexBox);
        this.add(dateOfBirthLabel);
        this.add(yearBox);
        this.add(monthBox);
        this.add(dayBox);
        this.add(regButton);
        this.add(backButton);
    }
}
