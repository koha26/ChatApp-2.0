package gui;

import logic.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static logic.Constants.SCRDIM_KX;
import static logic.Constants.SCRDIM_KY;

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
        nickLabel.setFont(new Font("Century Gothic", Font.PLAIN, (int) (32 * SCRDIM_KX)));
        nickLabel.setBackground(new Color(0, 0, 0, 0));
        nickLabel.setForeground(Color.WHITE);
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);

        passwordLabel = new JLabel("Enter your password");
        passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, (int) (32 * SCRDIM_KX)));
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
        regButton.setFont(new Font("Century Gothic", Font.PLAIN, (int) (12 * SCRDIM_KX)));
        regButton.setForeground(Color.WHITE);

        nickLabel.setBounds((int) (165 * SCRDIM_KX), (int) (75 * SCRDIM_KY), (int) (250 * SCRDIM_KX), (int) (30 * SCRDIM_KY));
        passwordLabel.setBounds((int) (120 * SCRDIM_KX), (int) (130 * SCRDIM_KY), (int) (340 * SCRDIM_KX), (int) (35 * SCRDIM_KY));
        nickField.setBounds((int) (480 * SCRDIM_KX), (int) (75 * SCRDIM_KY), (int) (240 * SCRDIM_KX), (int) (30 * SCRDIM_KY));
        passwordField.setBounds((int) (480 * SCRDIM_KX), (int) (130 * SCRDIM_KY), (int) (240 * SCRDIM_KX), (int) (30 * SCRDIM_KY));
        loginButton.setBounds((int) (380 * SCRDIM_KX), (int) (180 * SCRDIM_KY), (int) (185 * SCRDIM_KX), (int) (50 * SCRDIM_KY));
        regButton.setBounds((int) (270 * SCRDIM_KX), (int) (240 * SCRDIM_KY), (int) (380 * SCRDIM_KX), (int) (30 * SCRDIM_KY));

        this.add(nickLabel);
        this.add(passwordLabel);
        this.add(nickField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(regButton);
    }
}
