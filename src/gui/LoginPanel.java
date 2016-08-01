package gui;

import logic.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {
    private JLabel nickLabel, passwordLabel;
    private JTextField nickField;
    private JPasswordField passwordField;
    private JButton loginButton, regButton;
    private final ImageIcon loginButIcon = new ImageIcon("images/loginform/loginbut.png");
    private final ImageIcon loginButIconEntered = new ImageIcon("images/loginform/loginbut_entered.png");

    public JTextField getNickField() {
        return nickField;
    }

    public JButton getRegButton() {
        return regButton;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {

        return loginButton;
    }

    public LoginPanel() {
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 150));
        this.setOpaque(false);

        nickLabel = new JLabel("Enter your nick");
        nickLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        nickLabel.setBackground(new Color(0, 0, 0, 0));
        nickLabel.setForeground(Color.WHITE);
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);

        passwordLabel = new JLabel("Enter your password");
        passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        passwordLabel.setBackground(new Color(0, 0, 0, 0));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

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

        nickField = new JTextField();
        nickField.setBorder(null);
        nickField.setFont(Fonts.typingFont);
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

        loginButton = GUIStandartOperations.ButtonStartOperations(loginButIcon, loginButIconEntered, true);

        regButton = GUIStandartOperations.ButtonStartOperations(null, null, true);
        regButton.setText("Don't have an account yet? Click here for registration");
        regButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        regButton.setForeground(Color.WHITE);

        nickLabel.setBounds(165, 75, 250, 30);
        passwordLabel.setBounds(120, 130, 340, 35);
        nickField.setBounds(480, 75, 240, 30);
        passwordField.setBounds(480, 130, 240, 30);
        loginButton.setBounds(380, 180, 185, 50);
        regButton.setBounds(270, 240, 380, 30);

        this.add(nickLabel);
        this.add(passwordLabel);
        this.add(nickField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(regButton);
    }
}
