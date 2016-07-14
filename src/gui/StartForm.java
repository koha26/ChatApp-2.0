package gui;

import logic.Constants;
import logic.RegistrationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartForm extends JFrame {

    private PopUpMenu popUpMenu;

    private JLabel titleLabel, nickLabel, passwordLabel;
    private JTextField nickField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton, registrationButton;

    private final ImageIcon loginButIcon = new ImageIcon("images/loginform/loginbut.png");
    private final ImageIcon loginButIconEntered = new ImageIcon("images/loginform/loginbut_entered.png");
    private final ImageIcon regButIcon = new ImageIcon("images/loginform/regbut.png");
    private final ImageIcon regButIconEntered = new ImageIcon("images/loginform/regbut_entered.png");

    private JLabel regNickLabel, regPasswordLabel, regRepeatPasswordLabel, regAgeLabel,
            regCountryLabel, regNameLabel, regSurnameLabel;
    private JTextField regNickTextField, regNameTextField, regSurnameTextField;
    private JPasswordField regPasswordField, regRepeatPasswordField;
    private JButton backButton, regRegistrationButton;

    private int mode; // 0 - logicMode; 1 - registrationMode;

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartForm().setVisible(true);
            }
        });
    }

    public StartForm() {
        super("ChatApp - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(new JLabel(Images.backgroundImage));
        //setUndecorated(true);
        setIconImage(Images.appIcon.getImage());
        setLayout(null);

        popUpMenu = new PopUpMenu();
        popUpMenu.setPopUpMenu();

        // КОМПОНЕНТЫ ДЛЯ ЛОГИНА

        titleLabel = new JLabel("Hello!");
        titleLabel.setBounds(370, 25, 300, 90);
        titleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 72));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        nickLabel = new JLabel("Enter your nick");
        nickLabel.setBounds(165, 180, 250, 30);
        nickLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        nickLabel.setForeground(Color.WHITE);
        add(nickLabel);

        nickField = new JTextField();
        nickField.setFont(Fonts.typingFont);
        nickField.setBorder(null);
        nickField.setBounds(480, 180, 240, 30);
        nickField.setHorizontalAlignment(JTextField.CENTER);
        nickField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nickField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(nickField);

        passwordLabel = new JLabel("Enter your password");
        passwordLabel.setBounds(120, 235, 340, 35);
        passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setFont(Fonts.typingFont);
        passwordField.setBounds(480, 240, 240, 30);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (passwordField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(passwordField);

        loginButton = new JButton();
        loginButton.setBounds(380, 300, 185, 50);
        loginButton.setBackground(new Color(0, 0, 0, 0));
        loginButton.setOpaque(false);
        loginButton.setIcon(loginButIcon);
        loginButton.setBorder(null);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setIcon(loginButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setIcon(loginButIcon);
            }
        });
        add(loginButton);

        registrationButton = new JButton("Don't have an account yet? Click here for registration");
        registrationButton.setBounds(270, 360, 380, 30);
        registrationButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        registrationButton.setContentAreaFilled(false);
        registrationButton.setBorder(null);
        registrationButton.setFocusPainted(false);
        registrationButton.setForeground(Color.WHITE);
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRegistrationMode();
            }
        });
        add(registrationButton);

        exitButton = new JButton("Exit ChatApp");
        exitButton.setBounds(410, 385, 120, 30);
        exitButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        exitButton.setContentAreaFilled(false);
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);

        mode = 0;

        // КОМПОНЕНТЫ ДЛЯ РЕГИСТРАЦИИ

        regNickLabel = new JLabel("Nickname");
        regNickLabel.setBounds(90, 150, 180, 30);
        regNickLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        regNickLabel.setForeground(Color.WHITE);
        add(regNickLabel);


        regNickTextField = new JTextField();
        regNickTextField.setFont(Fonts.typingFont);
        regNickTextField.setBorder(null);
        regNickTextField.setBounds(180, 150, 240, 30);
        regNickTextField.setHorizontalAlignment(JTextField.CENTER);
        regNickTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (regNickTextField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(regNickTextField);

        regNameLabel = new JLabel("Name");
        regNameLabel.setBounds(120, 200, 100, 30);
        regNameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        regNameLabel.setForeground(Color.WHITE);
        add(regNameLabel);

        regNameTextField = new JTextField();
        regNameTextField.setFont(Fonts.typingFont);
        regNameTextField.setBorder(null);
        regNameTextField.setBounds(180, 200, 240, 30);
        regNameTextField.setHorizontalAlignment(JTextField.CENTER);
        regNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (regNameTextField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(regNameTextField);

        regSurnameLabel = new JLabel("Surname");
        regSurnameLabel.setBounds(100, 250, 100, 30);
        regSurnameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        regSurnameLabel.setForeground(Color.WHITE);
        add(regSurnameLabel);

        regSurnameTextField = new JTextField();
        regSurnameTextField.setFont(Fonts.typingFont);
        regSurnameTextField.setBorder(null);
        regSurnameTextField.setBounds(180, 250, 240, 30);
        regSurnameTextField.setHorizontalAlignment(JTextField.CENTER);
        regSurnameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (regSurnameTextField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(regSurnameTextField);

        regPasswordLabel = new JLabel("Password");
        regPasswordLabel.setBounds(500, 150, 180, 30);
        regPasswordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        regPasswordLabel.setForeground(Color.WHITE);
        add(regPasswordLabel);

        regPasswordField = new JPasswordField();
        regPasswordField.setBorder(null);
        regPasswordField.setFont(Fonts.typingFont);
        regPasswordField.setBounds(585, 150, 240, 30);
        regPasswordField.setHorizontalAlignment(JTextField.CENTER);
        regPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (regPasswordField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(regPasswordField);

        regRepeatPasswordLabel = new JLabel("Confirm password");
        regRepeatPasswordLabel.setBounds(435, 200, 180, 30);
        regRepeatPasswordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        regRepeatPasswordLabel.setForeground(Color.WHITE);
        add(regRepeatPasswordLabel);

        regRepeatPasswordField = new JPasswordField();
        regRepeatPasswordField.setBorder(null);
        regRepeatPasswordField.setFont(Fonts.typingFont);
        regRepeatPasswordField.setBounds(585, 200, 240, 30);
        regRepeatPasswordField.setHorizontalAlignment(JTextField.CENTER);
        regRepeatPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (regRepeatPasswordField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(regRepeatPasswordField);

        regRegistrationButton = new JButton();
        regRegistrationButton.setBounds(380, 300, 185, 50);
        regRegistrationButton.setBackground(new Color(0, 0, 0, 0));
        regRegistrationButton.setOpaque(false);
        regRegistrationButton.setIcon(regButIcon);
        regRegistrationButton.setBorder(null);
        regRegistrationButton.setFocusPainted(false);
        regRegistrationButton.setContentAreaFilled(false);
        regRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLoginMode();
            }
        });
        regRegistrationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                regRegistrationButton.setIcon(regButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                regRegistrationButton.setIcon(regButIcon);
            }
        });
        add(regRegistrationButton);

        backButton = new JButton("Back to login form");
        backButton.setBounds(277, 360, 380, 30);
        backButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        backButton.setContentAreaFilled(false);
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLoginMode();
            }
        });
        add(backButton);
        backButton.setVisible(false);

        mode = 1;

        setLoginMode();
    }

    void setLoginMode() {
        nickLabel.setVisible(true);
        nickField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        loginButton.setVisible(true);
        regNickLabel.setVisible(false);
        regNickTextField.setVisible(false);
        regPasswordLabel.setVisible(false);
        regPasswordField.setVisible(false);
        regRepeatPasswordField.setVisible(false);
        regRepeatPasswordLabel.setVisible(false);
        regNameLabel.setVisible(false);
        regNameTextField.setVisible(false);
        regSurnameLabel.setVisible(false);
        regSurnameTextField.setVisible(false);
        regRegistrationButton.setVisible(false);
        regNickTextField.setText(null);
        regSurnameTextField.setText(null);
        regNameTextField.setText(null);
        regPasswordField.setText(null);
        regRepeatPasswordField.setText(null);
        backButton.setVisible(false);
        registrationButton.setVisible(true);
        setTitle("ChatApp - Login");
        mode = 0;
    }

    void setRegistrationMode() {
        nickLabel.setVisible(false);
        nickField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        loginButton.setVisible(false);
        regNickLabel.setVisible(true);
        regNickTextField.setVisible(true);
        regPasswordLabel.setVisible(true);
        regPasswordField.setVisible(true);
        regRepeatPasswordField.setVisible(true);
        regRepeatPasswordLabel.setVisible(true);
        regNameLabel.setVisible(true);
        regNameTextField.setVisible(true);
        regSurnameLabel.setVisible(true);
        regSurnameTextField.setVisible(true);
        regRegistrationButton.setVisible(true);
        registrationButton.setVisible(false);
        setTitle("ChatApp - Registration");
        backButton.setVisible(true);
        mode = 1;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegRegistrationButton() {
        return regRegistrationButton;
    }

    public boolean isFieldFilled() {
        if (mode == 0) {
            if (!nickField.getText().equals("") && nickField.getText().length() >= 6 &&
                    passwordField.getPassword().length >= 6) {
                return true;
            } else
                return false;
        } else {
            if (!regNickTextField.getText().equals("") && regNickTextField.getText().length() >=6 &&
                    regPasswordField.getPassword().length>=6 && regRepeatPasswordField.getPassword().length>=6 &&
                        isPasswordsEquals()){
                return true;
            } else
                return false;
        }
    }

    private boolean isPasswordsEquals(){
        if (regRepeatPasswordField.getPassword().length == regPasswordField.getPassword().length){
            for (int i = 0; i < regPasswordField.getPassword().length; i++) {
                if (regPasswordField.getPassword()[i] != regRepeatPasswordField.getPassword()[i]){
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public RegistrationModel getRegistrationModel(){
        RegistrationModel regModel;
        if (mode == 1 && isFieldFilled() && isPasswordsEquals()){
            regModel = new RegistrationModel(regNickTextField.getText(),String.valueOf(regPasswordField.getPassword()));
            regModel.setName(regNameTextField.getName());
            regModel.setSurname(regSurnameTextField.getText());
            return regModel;
        } else if (mode == 0 && isFieldFilled()) {
            regModel = new RegistrationModel(nickField.getText(), String.valueOf(passwordField.getPassword()));
            return regModel;
        } else
            return null;
    }

    public void showInfoMessage(String text){
        JOptionPane.showMessageDialog(this,text);
    }


}
