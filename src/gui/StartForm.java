package gui;

import com.sun.xml.internal.bind.v2.TODO;
import logic.Constants;
import logic.RegistrationModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StartForm extends JFrame {
    /**
     * Поля с приставкой log - для логин мода
     * Поля с приставкой reg - для регистрации
     */

    private PopUpMenu popUpMenu;
    private JLabel helloLabel;
    private JLabel log_nickLabel, log_passwordLabel;
    private JLabel reg_nickLabel, reg_passwordLabel, reg_confirmPasswordLabel, reg_ageLabel, reg_nameLabel, reg_surnameLabel;
    private JTextField log_nickField;
    private JTextField reg_nickField, reg_nameField, reg_surnameField;
    private JPasswordField log_passwordField;
    private JPasswordField reg_passwordField, reg_confirmPasswordField;
    private JButton loginButton, exitButton, registrationButton; // общие кнопки
    private JButton reg_backButton, reg_registrationButton;
    private JComboBox reg_yearComboBox, reg_monthComboBox, reg_dayComboBox;

    private static JTextField errorTextField; // лейбл, который будет отображать ошибку в случае некорректного ввода данных

    private final ImageIcon loginButIcon = new ImageIcon("images/loginform/loginbut.png");
    private final ImageIcon loginButIconEntered = new ImageIcon("images/loginform/loginbut_entered.png");
    private final ImageIcon regButIcon = new ImageIcon("images/loginform/regbut.png");
    private final ImageIcon regButIconEntered = new ImageIcon("images/loginform/regbut_entered.png");

    private Mode mode; // LOGIN_ON - loginMode; REGISTRATION_ON - registrationMode;
    private Point mouseDownCompCoords;

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
        setSize(1000, 500); // размер, можно потом поменять мб
        setLocationRelativeTo(null); // окно при запуске появляется по центру монитора
        GUIStandartOperations.FrameStartOperations(this);
        setLayout(null);
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

        popUpMenu = new PopUpMenu();
        popUpMenu.setPopUpMenu();

        /*
                Компоненты для логин мода
         */

        helloLabel = GUIStandartOperations.LabelStartOperation("Hello!", new Rectangle(390, 15, 300, 90), new Font("Century Gothic", Font.PLAIN, 72), Color.WHITE);
        log_nickLabel = GUIStandartOperations.LabelStartOperation("Enter your nick", new Rectangle(165, 180, 250, 30), new Font("Century Gothic", Font.PLAIN, 32), Color.WHITE);
        log_passwordLabel = GUIStandartOperations.LabelStartOperation("Enter your password", new Rectangle(120, 235, 340, 35), new Font("Century Gothic", Font.PLAIN, 32), Color.WHITE);
        add(helloLabel);
        add(log_nickLabel);
        add(log_passwordLabel);

        log_nickField = GUIStandartOperations.TextFieldStartOperation(Fonts.typingFont, new Rectangle(480, 180, 240, 30));
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

        log_passwordField = new JPasswordField();
        log_passwordField.setBorder(null);
        log_passwordField.setFont(Fonts.typingFont);
        log_passwordField.setBounds(480, 240, 240, 30);
        log_passwordField.setHorizontalAlignment(JTextField.CENTER);
        log_passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (log_passwordField.getPassword().length == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(log_passwordField);

        loginButton = GUIStandartOperations.ButtonStartOperations(loginButIcon, loginButIconEntered, true);
        loginButton.setBounds(380, 300, 185, 50);
        add(loginButton);

        registrationButton = GUIStandartOperations.ButtonStartOperations(null, null, true);
        registrationButton.setText("Don't have an account yet? Click here for registration");
        registrationButton.setBounds(270, 360, 380, 30);
        registrationButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        registrationButton.setForeground(Color.WHITE);
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorTextField.setVisible(false);
                reg_passwordField.setBorder(null);
                reg_confirmPasswordField.setBorder(null);
                setRegistrationMode();
            }
        });
        add(registrationButton);

        exitButton = GUIStandartOperations.ButtonStartOperations(null, null, false);
        exitButton.setText("Exit ChatApp");
        exitButton.setBounds(410, 385, 120, 30);
        exitButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
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


        //errorLabel = GUIStandartOperations.LabelStartOperation("", new Rectangle(340, 75, 300, 90), new Font("Century Gothic", Font.PLAIN, 16), Color.RED);
        // TODO: 21.07.2016 ПРОЗРАЧНЫЙ ФОН, ПОКА ЧТО РАЗБИРАЮСЬ С БАГОМ, СЕЙЧАС СТОИТ СТАРЫЙ ВАРИАНТ С РАМКОЙ
        errorTextField = new JTextField();
        errorTextField.setBounds(0, 100, 1000, 35);
        errorTextField.setFont(Fonts.typingFont);
        //errorTextField.setBorder(new LineBorder(Color.YELLOW, 2));
        errorTextField.setHorizontalAlignment(JTextField.CENTER);
        //errorTextField.setOpaque(false);
        errorTextField.setBackground(new Color(0, 0, 0, 150));
        errorTextField.setEditable(false);
        errorTextField.setFocusable(false);
        errorTextField.setForeground(Color.RED);
        errorTextField.setBorder(null);

        reg_nickLabel = GUIStandartOperations.LabelStartOperation("Nickname", new Rectangle(90, 150, 180, 30), new Font("Century Gothic", Font.PLAIN, 16), Color.WHITE);
        reg_nameLabel = GUIStandartOperations.LabelStartOperation("Name", new Rectangle(120, 200, 100, 30), new Font("Century Gothic", Font.PLAIN, 16), Color.WHITE);
        reg_surnameLabel = GUIStandartOperations.LabelStartOperation("Surname", new Rectangle(100, 250, 100, 30), new Font("Century Gothic", Font.PLAIN, 16), Color.WHITE);
        reg_passwordLabel = GUIStandartOperations.LabelStartOperation("Password", new Rectangle(500, 150, 180, 30), new Font("Century Gothic", Font.PLAIN, 16), Color.WHITE);
        reg_confirmPasswordLabel = GUIStandartOperations.LabelStartOperation("Confirm password", new Rectangle(435, 200, 180, 30), new Font("Century Gothic", Font.PLAIN, 16), Color.WHITE);
        reg_ageLabel = GUIStandartOperations.LabelStartOperation("Date of birth", new Rectangle(475, 250, 150, 30), new Font("Century Gothic", Font.PLAIN, 16), Color.WHITE);
        errorTextField.setVisible(false);
        add(errorTextField);
        add(reg_nickLabel);
        add(reg_nameLabel);
        add(reg_surnameLabel);
        add(reg_passwordLabel);
        add(reg_confirmPasswordLabel);
        add(reg_ageLabel);

        reg_nickField = GUIStandartOperations.TextFieldStartOperation(Fonts.typingFont, new Rectangle(180, 150, 240, 30));
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

        reg_nameField = GUIStandartOperations.TextFieldStartOperation(Fonts.typingFont, new Rectangle(180, 200, 240, 30));
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

        reg_surnameField = GUIStandartOperations.TextFieldStartOperation(Fonts.typingFont, new Rectangle(180, 250, 240, 30));
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

        reg_passwordField = new JPasswordField();
        reg_passwordField.setBorder(null);
        reg_passwordField.setFont(Fonts.typingFont);
        reg_passwordField.setBounds(585, 150, 240, 30);
        reg_passwordField.setHorizontalAlignment(JTextField.CENTER);
        reg_passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_passwordField.getPassword().length == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_passwordField);


        reg_confirmPasswordField = new JPasswordField();
        reg_confirmPasswordField.setBorder(null);
        reg_confirmPasswordField.setFont(Fonts.typingFont);
        reg_confirmPasswordField.setBounds(585, 200, 240, 30);
        reg_confirmPasswordField.setHorizontalAlignment(JTextField.CENTER);
        reg_confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (reg_confirmPasswordField.getPassword().length == Constants.MAX_NICK_LENGTH) {
                    e.consume();
                    getToolkit().beep();
                }
            }
        });
        add(reg_confirmPasswordField);

        reg_registrationButton = GUIStandartOperations.ButtonStartOperations(regButIcon, regButIconEntered, true);
        reg_registrationButton.setBounds(380, 300, 185, 50);
        add(reg_registrationButton);

        reg_backButton = GUIStandartOperations.ButtonStartOperations(null, null, false);
        reg_backButton.setText("Back to login form");
        reg_backButton.setBounds(277, 360, 380, 30);
        reg_backButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        reg_backButton.setForeground(Color.WHITE);
        reg_backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg_passwordField.setBorder(null);
                reg_confirmPasswordField.setBorder(null);
                errorTextField.setVisible(false);
                setLoginMode("");
            }
        });
        add(reg_backButton);

        ArrayList<Integer> yearsList = new ArrayList<>(); // модель для комбобокса годов
        for (int year = 1950; year < 2017; year++)
            yearsList.add(year);

        String[] monthsList = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"}; // модель для комбобоса месяцев

        ArrayList<Integer> daysList = new ArrayList<>(); // модель для комбобокса дней
        for (int day = 1; day < 32; day++)
            daysList.add(day);

        reg_yearComboBox = new JComboBox(new DefaultComboBoxModel(yearsList.toArray()));
        reg_yearComboBox.setBackground(Color.WHITE);
        reg_yearComboBox.setBounds(585, 250, 70, 30);
        reg_yearComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        add(reg_yearComboBox);

        reg_monthComboBox = new JComboBox(monthsList);
        reg_monthComboBox.setBackground(Color.WHITE);
        reg_monthComboBox.setBounds(670, 250, 90, 30);
        reg_monthComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        add(reg_monthComboBox);

        reg_dayComboBox = new JComboBox(daysList.toArray());
        reg_dayComboBox.setBackground(Color.WHITE);
        reg_dayComboBox.setBounds(775, 250, 50, 30);
        reg_dayComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        add(reg_dayComboBox);

        setMode(Mode.LOGIN_ON, "");
    }

    void setLoginMode(String nickname) {
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
        log_nickField.setText(nickname);
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

    public JPasswordField getReg_passwordField() {
        return reg_passwordField;
    }

    public JPasswordField getReg_confirmPasswordField() {
        return reg_confirmPasswordField;
    }

    public static JTextField getErrorTextField() {
        return errorTextField;
    }

    public boolean isFieldFilled() {
        if (mode == Mode.LOGIN_ON) {
            String login = log_nickField.getText().replaceAll(" ", ""); //TODO: оповещение пользователя о пробелах в нике, переделать с помощью split() поиск пробелов
            String password = new String(log_passwordField.getPassword());
            if (login.length() >= 6 && password.length() >= 6) {
                return true;
            } else
                return false;
        } else {
            String login = reg_nickField.getText().replaceAll(" ", ""); //TODO: оповещение пользователя о пробелах в нике, переделать с помощью split() поиск пробелов
            String password = new String(reg_passwordField.getPassword());
            String confirmPassword = new String(reg_confirmPasswordField.getPassword());
            if (login.length() >= 6 && password.length() >= 6 && confirmPassword.length() >= 6) {
                return true;
            } else
                return false;
        }
    }

    public boolean isPasswordsEquals() {
        if (reg_confirmPasswordField.getPassword().length == reg_passwordField.getPassword().length) {
            for (int i = 0; i < reg_passwordField.getPassword().length; i++) {
                if (reg_passwordField.getPassword()[i] != reg_confirmPasswordField.getPassword()[i]) {
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public RegistrationModel getRegistrationModel() {
        RegistrationModel regModel;
        if (mode == Mode.REGISTRATION_ON) {
            regModel = new RegistrationModel(reg_nickField.getText(), String.valueOf(reg_passwordField.getPassword()));
            regModel.setName(reg_nameField.getName());
            regModel.setSurname(reg_surnameField.getText());
            if (!isDateCorrect(reg_monthComboBox.getSelectedItem(), reg_dayComboBox.getSelectedItem())) {
                return null;
            }
            regModel.setAge(reg_yearComboBox.getSelectedItem() + " " + reg_monthComboBox.getSelectedItem()
                    + " " + reg_dayComboBox.getSelectedItem());
            return regModel;
        } else if (mode == Mode.LOGIN_ON) {
            regModel = new RegistrationModel(log_nickField.getText(), String.valueOf(log_passwordField.getPassword()));
            return regModel;
        } else
            return null;
    }

    public void setMode(Mode mode, String nickname) {
        if (mode == Mode.LOGIN_ON) {
            setLoginMode(nickname);
        } else if (mode == Mode.REGISTRATION_ON) {
            setRegistrationMode();
        }
    }

    public void showInfoMessage(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    public void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorLabel(String errorText) {
        errorTextField.setText(errorText);
        errorTextField.setVisible(true);
        errorTextField.revalidate();
    }

    public boolean isDateCorrect(Object month, Object day) {
        if (((month.equals("April") | month.equals("June") | month.equals("September") | month.equals("November")
                && (Integer) day > 30) || (month.equals("February") & (Integer) day > 28))) {
            showErrorMessage("Enter the correct date");
            return false;
        }
        return true;
    }
}