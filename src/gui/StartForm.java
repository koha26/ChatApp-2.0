package gui;

import logic.Constants;
import logic.RegistrationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StartForm extends JFrame {

    private PopUpMenu popUpMenu;

    /*
            Поля с приставкой log - для логин мода
            Поля с приставкой reg - для регистрации
     */

    private JLabel helloLabel;
    private JLabel log_nickLabel, log_passwordLabel;
    private JLabel reg_nickLabel, reg_passwordLabel, reg_confirmPasswordLabel, reg_ageLabel,
            reg_countryLabel, reg_nameLabel, reg_surnameLabel;
    private JTextField log_nickField;
    private JTextField reg_nickField, reg_nameField, reg_surnameField;
    private JPasswordField log_passwordField;
    private JPasswordField reg_passwordField, reg_confirmPasswordField;
    private JButton loginButton, exitButton, registrationButton; // общие кнопки
    private JButton reg_backButton, reg_registrationButton;
    private JComboBox reg_yearComboBox, reg_monthComboBox, reg_dayComboBox;

    private final ImageIcon loginButIcon = new ImageIcon("images/loginform/loginbut.png");
    private final ImageIcon loginButIconEntered = new ImageIcon("images/loginform/loginbut_entered.png");
    private final ImageIcon regButIcon = new ImageIcon("images/loginform/regbut.png");
    private final ImageIcon regButIconEntered = new ImageIcon("images/loginform/regbut_entered.png");

    private Mode mode; // LOGIN_ON - loginMode; REGISTRATION_ON - registrationMode;

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
        setSize(1000, 500); // размер, можно потом поменять мб
        setLocationRelativeTo(null); // окно при запуске появляется по центру монитора
        setResizable(false);
        setContentPane(new JLabel(Images.backgroundImage)); // установка фона
        //setUndecorated(true); // метод, который убирает виндосоуские кнопки (свернуть, выход и т.д.)
        setIconImage(Images.appIcon.getImage()); // иконка фрейма
        setLayout(null);

        popUpMenu = new PopUpMenu();
        popUpMenu.setPopUpMenu();

        /*
                Компоненты для логин мода
         */

        helloLabel = new JLabel("Hello!");
        helloLabel.setBounds(370, 25, 300, 90);
        helloLabel.setFont(new Font("Century Gothic", Font.PLAIN, 72));
        helloLabel.setForeground(Color.WHITE);
        add(helloLabel);

        log_nickLabel = new JLabel("Enter your nick");
        log_nickLabel.setBounds(165, 180, 250, 30);
        log_nickLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        log_nickLabel.setForeground(Color.WHITE);
        add(log_nickLabel);

        log_nickField = new JTextField();
        log_nickField.setFont(Fonts.typingFont);
        log_nickField.setBorder(null);
        log_nickField.setBounds(480, 180, 240, 30);
        log_nickField.setHorizontalAlignment(JTextField.CENTER);
        log_nickField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (log_nickField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(log_nickField);

        log_passwordLabel = new JLabel("Enter your password");
        log_passwordLabel.setBounds(120, 235, 340, 35);
        log_passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 32));
        log_passwordLabel.setForeground(Color.WHITE);
        add(log_passwordLabel);

        log_passwordField = new JPasswordField();
        log_passwordField.setBorder(null);
        log_passwordField.setFont(Fonts.typingFont);
        log_passwordField.setBounds(480, 240, 240, 30);
        log_passwordField.setHorizontalAlignment(JTextField.CENTER);
        log_passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (log_passwordField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(log_passwordField);

        loginButton = new JButton();
        loginButton.setBounds(380, 300, 185, 50);
        loginButton.setBackground(new Color(0, 0, 0, 0));
        loginButton.setOpaque(false);
        loginButton.setIcon(loginButIcon);
        loginButton.setBorder(null);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
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

        mode = Mode.LOGIN_ON;

        /*
                Компоненты для регистрационного мода
         */

        reg_nickLabel = new JLabel("Nickname");
        reg_nickLabel.setBounds(90, 150, 180, 30);
        reg_nickLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        reg_nickLabel.setForeground(Color.WHITE);
        add(reg_nickLabel);


        reg_nickField = new JTextField();
        reg_nickField.setFont(Fonts.typingFont);
        reg_nickField.setBorder(null);
        reg_nickField.setBounds(180, 150, 240, 30);
        reg_nickField.setHorizontalAlignment(JTextField.CENTER);
        reg_nickField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_nickField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_nickField);

        reg_nameLabel = new JLabel("Name");
        reg_nameLabel.setBounds(120, 200, 100, 30);
        reg_nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        reg_nameLabel.setForeground(Color.WHITE);
        add(reg_nameLabel);

        reg_nameField = new JTextField();
        reg_nameField.setFont(Fonts.typingFont);
        reg_nameField.setBorder(null);
        reg_nameField.setBounds(180, 200, 240, 30);
        reg_nameField.setHorizontalAlignment(JTextField.CENTER);
        reg_nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_nameField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_nameField);

        reg_surnameLabel = new JLabel("Surname");
        reg_surnameLabel.setBounds(100, 250, 100, 30);
        reg_surnameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        reg_surnameLabel.setForeground(Color.WHITE);
        add(reg_surnameLabel);

        reg_surnameField = new JTextField();
        reg_surnameField.setFont(Fonts.typingFont);
        reg_surnameField.setBorder(null);
        reg_surnameField.setBounds(180, 250, 240, 30);
        reg_surnameField.setHorizontalAlignment(JTextField.CENTER);
        reg_surnameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_surnameField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_surnameField);

        reg_passwordLabel = new JLabel("Password");
        reg_passwordLabel.setBounds(500, 150, 180, 30);
        reg_passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        reg_passwordLabel.setForeground(Color.WHITE);
        add(reg_passwordLabel);

        reg_passwordField = new JPasswordField();
        reg_passwordField.setBorder(null);
        reg_passwordField.setFont(Fonts.typingFont);
        reg_passwordField.setBounds(585, 150, 240, 30);
        reg_passwordField.setHorizontalAlignment(JTextField.CENTER);
        reg_passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_passwordField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_passwordField);

        reg_confirmPasswordLabel = new JLabel("Confirm password");
        reg_confirmPasswordLabel.setBounds(435, 200, 180, 30);
        reg_confirmPasswordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        reg_confirmPasswordLabel.setForeground(Color.WHITE);
        add(reg_confirmPasswordLabel);

        reg_confirmPasswordField = new JPasswordField();
        reg_confirmPasswordField.setBorder(null);
        reg_confirmPasswordField.setFont(Fonts.typingFont);
        reg_confirmPasswordField.setBounds(585, 200, 240, 30);
        reg_confirmPasswordField.setHorizontalAlignment(JTextField.CENTER);
        reg_confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_confirmPasswordField.getText().length() == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_confirmPasswordField);

        reg_registrationButton = new JButton();
        reg_registrationButton.setBounds(380, 300, 185, 50);
        reg_registrationButton.setBackground(new Color(0, 0, 0, 0));
        reg_registrationButton.setOpaque(false);
        reg_registrationButton.setIcon(regButIcon);
        reg_registrationButton.setBorder(null);
        reg_registrationButton.setFocusPainted(false);
        reg_registrationButton.setContentAreaFilled(false);
        reg_registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDateCorrect(reg_monthComboBox.getSelectedItem(), reg_dayComboBox.getSelectedItem());
            }
        });
        reg_registrationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                reg_registrationButton.setIcon(regButIconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                reg_registrationButton.setIcon(regButIcon);
            }
        });
        add(reg_registrationButton);

        reg_backButton = new JButton("Back to login form");
        reg_backButton.setBounds(277, 360, 380, 30);
        reg_backButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        reg_backButton.setContentAreaFilled(false);
        reg_backButton.setBorder(null);
        reg_backButton.setFocusPainted(false);
        reg_backButton.setForeground(Color.WHITE);
        reg_backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLoginMode();
            }
        });
        add(reg_backButton);

        reg_ageLabel = new JLabel("Date of birth");
        reg_ageLabel.setBounds(475, 250, 150, 30);
        reg_ageLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        reg_ageLabel.setForeground(Color.WHITE);
        add(reg_ageLabel);

        ArrayList<Integer> yearsList = new ArrayList<Integer>(); // модель для комбобокса годов
        for (int year = 1950; year < 2017; year++)
            yearsList.add(year);

        String[] monthsList = { "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December" }; // модель для комбобоса месяцев

        ArrayList<Integer> daysList = new ArrayList<Integer>(); // модель для комбобокса дней
        for (int day = 1; day < 32; day++)
            daysList.add(day);

        reg_yearComboBox = new JComboBox(new DefaultComboBoxModel(yearsList.toArray()));
        reg_yearComboBox.setBounds(585, 250, 70, 30);
        reg_yearComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        add(reg_yearComboBox);

        reg_monthComboBox = new JComboBox(monthsList);
        reg_monthComboBox.setBounds(670, 250, 90, 30);
        reg_monthComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        add(reg_monthComboBox);

        reg_dayComboBox = new JComboBox(daysList.toArray());
        reg_dayComboBox.setBounds(775, 250, 50, 30);
        reg_dayComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        add(reg_dayComboBox);

        setMode(Mode.LOGIN_ON);
    }

    void setLoginMode() {
        log_nickLabel.setVisible(true);
        log_nickField.setVisible(true);
        log_passwordLabel.setVisible(true);
        log_passwordField.setVisible(true);
        loginButton.setVisible(true);
        reg_nickLabel.setVisible(false);
        reg_nickField.setVisible(false);
        reg_passwordLabel.setVisible(false);
        reg_passwordField.setVisible(false);
        reg_confirmPasswordField.setVisible(false);
        reg_confirmPasswordLabel.setVisible(false);
        reg_nameLabel.setVisible(false);
        reg_nameField.setVisible(false);
        reg_surnameLabel.setVisible(false);
        reg_surnameField.setVisible(false);
        reg_registrationButton.setVisible(false);
        reg_nickField.setText(null);
        reg_surnameField.setText(null);
        reg_nameField.setText(null);
        reg_passwordField.setText(null);
        reg_confirmPasswordField.setText(null);
        reg_backButton.setVisible(false);
        registrationButton.setVisible(true);
        reg_dayComboBox.setVisible(false);
        reg_monthComboBox.setVisible(false);
        reg_yearComboBox.setVisible(false);
        reg_ageLabel.setVisible(false);
        setTitle("ChatApp - Login");
        mode = Mode.LOGIN_ON;
    }

    void setRegistrationMode() {
        log_nickLabel.setVisible(false);
        log_nickField.setVisible(false);
        log_passwordLabel.setVisible(false);
        log_passwordField.setVisible(false);
        loginButton.setVisible(false);
        reg_nickLabel.setVisible(true);
        reg_nickField.setVisible(true);
        reg_passwordLabel.setVisible(true);
        reg_passwordField.setVisible(true);
        reg_confirmPasswordField.setVisible(true);
        reg_confirmPasswordLabel.setVisible(true);
        reg_nameLabel.setVisible(true);
        reg_nameField.setVisible(true);
        reg_surnameLabel.setVisible(true);
        reg_surnameField.setVisible(true);
        reg_registrationButton.setVisible(true);
        registrationButton.setVisible(false);
        setTitle("ChatApp - Registration");
        reg_backButton.setVisible(true);
        reg_dayComboBox.setVisible(true);
        reg_monthComboBox.setVisible(true);
        reg_yearComboBox.setVisible(true);
        reg_ageLabel.setVisible(true);
        mode = Mode.REGISTRATION_ON;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getReg_registrationButton() {
        return reg_registrationButton;
    }

    public boolean isFieldFilled() {
        if (mode == Mode.LOGIN_ON) {
            if (!log_nickField.getText().equals("") && log_nickField.getText().length() >= 6 &&
                    log_passwordField.getPassword().length >= 6) {
                return true;
            } else
                return false;
        } else {
            if (!reg_nickField.getText().equals("") && reg_nickField.getText().length() >=6 &&
                    reg_passwordField.getPassword().length>=6 && reg_confirmPasswordField.getPassword().length>=6 &&
                        isPasswordsEquals()){
                return true;
            } else
                return false;
        }
    }

    private boolean isPasswordsEquals(){
        if (reg_confirmPasswordField.getPassword().length == reg_passwordField.getPassword().length){
            for (int i = 0; i < reg_passwordField.getPassword().length; i++) {
                if (reg_passwordField.getPassword()[i] != reg_confirmPasswordField.getPassword()[i]){
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public RegistrationModel getRegistrationModel(){
        RegistrationModel regModel;
        if (mode == Mode.REGISTRATION_ON && isFieldFilled() && isPasswordsEquals()){
            regModel = new RegistrationModel(reg_nickField.getText(),String.valueOf(reg_passwordField.getPassword()));
            regModel.setName(reg_nameField.getName());
            regModel.setSurname(reg_surnameField.getText());
            if (isDateCorrect(reg_monthComboBox.getSelectedItem(), reg_dayComboBox.getSelectedItem()));
                regModel.setAge((String) reg_yearComboBox.getSelectedItem() + " " + reg_monthComboBox.getSelectedItem()
                    + " " + reg_dayComboBox.getSelectedItem());
            return regModel;
        } else if (mode == Mode.LOGIN_ON && isFieldFilled()) {
            regModel = new RegistrationModel(log_nickField.getText(), String.valueOf(log_passwordField.getPassword()));
            return regModel;
        } else
            return null;
    }

    public void setMode(Mode mode){
        if (mode == Mode.LOGIN_ON){
            setLoginMode();
        } else if (mode == Mode.REGISTRATION_ON){
            setRegistrationMode();
        }
    }

    public void showInfoMessage(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    public void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isDateCorrect(Object month, Object day) {
        boolean isCorrect = true;
        if (((month.equals("April") | month.equals("June") | month.equals("September") | month.equals("November")
                && (Integer) day > 30) | (month.equals("February") && (Integer) day > 28))) {
            isCorrect = false;
            showErrorMessage("Введите дату корректно");
        }
        return isCorrect;
    }
}
