package gui;

import logic.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

    private JLabel helloLabel, nickLabel, passwordLabel;
    private JTextField nickField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;

    private final ImageIcon loginButIcon = new ImageIcon("images/loginform/loginbut.png");
    private final ImageIcon loginButIconEntered = new ImageIcon("images/loginform/loginbut_entered.png");

    public LoginPanel() {
        setBounds(25, 25, 940, 480);
        setOpaque(false);
        setLayout(null);

        helloLabel = new JLabel("Hello!");
        helloLabel.setBounds(370, 25, 300, 90);
        helloLabel.setFont(new Font("Century Gothic", Font.PLAIN, 72));
        helloLabel.setForeground(Color.WHITE);
        add(helloLabel);

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
        passwordLabel.setBounds(120, 240, 340, 35);
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
        loginButton.setBounds(380, 290, 185, 50);
        loginButton.setBackground(new Color(0, 0, 0, 0));
        loginButton.setOpaque(false);
        loginButton.setIcon(loginButIcon);
        loginButton.setBorder(null);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
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
    }
}
