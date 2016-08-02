package gui;

import logic.ImageSerializable;
import logic.RegistrationModel;
import logic.Sex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static logic.Constants.SCRDIM_KX;
import static logic.Constants.SCRDIM_KY;

public class StartForm extends JFrame {
    private RegPanel regPanel;
    private LoginPanel loginPanel;
    private JLabel helloLabel;
    private JButton exitButton;
    private Point mouseDownCompCoords = new Point();
    private PopUpMenu popUpMenu;
    private static JTextField errorTextField; // лейбл, который будет отображать ошибку в случае некорректного ввода данных
    private Mode mode; // LOGIN_ON - loginMode; REGISTRATION_ON - registrationMode;
    private boolean isChanged;

    public void changeSize() {
        if (!isChanged) {
            this.setSize((int) (960 * SCRDIM_KX), (int) (860 * SCRDIM_KY));
            exitButton.setBounds((int)(430 * SCRDIM_KX), (int) (760 * SCRDIM_KY), (int)(100 * SCRDIM_KX), (int) (50 * SCRDIM_KY));
            this.setLocationRelativeTo(null);
        } else {
            setSize((int) (960 * SCRDIM_KX), (int) (550 * SCRDIM_KY));
            exitButton.setBounds((int)(430 * SCRDIM_KX), (int) (450 * SCRDIM_KY), (int)(100 * SCRDIM_KX), (int) (50 * SCRDIM_KY));
            this.setLocationRelativeTo(null);
        }
    }

    public RegPanel getRegPanel() {
        return regPanel;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public StartForm() {
        GUIStandartOperations.FrameStartOperations(this);
        setSize((int) (960 * SCRDIM_KX), (int) (550 * SCRDIM_KY));
        setLocationRelativeTo(null);
        setLayout(null);

        popUpMenu = new PopUpMenu();
        popUpMenu.setPopUpMenu();

        regPanel = new RegPanel();
        loginPanel = new LoginPanel();

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

        regPanel.getAdditionalCheckBox().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isChanged = !isChanged;
                regPanel.changeView();
                changeSize();
            }
        });

        loginPanel.getRegButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorTextField.setVisible(false);
                regPanel.getPasswordField().setBorder(null);
                regPanel.getConfirmPasswordField().setBorder(null);
                setRegistrationMode();
                regPanel.setEntered(false);
                isChanged = true;
                changeSize();
            }
        });

        regPanel.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regPanel.getPasswordField().setBorder(null);
                regPanel.getConfirmPasswordField().setBorder(null);
                errorTextField.setVisible(false);
                isChanged = true;
                changeSize();
                regPanel.setEntered(true);
                regPanel.getAdditionalCheckBox().setSelected(false);
                regPanel.changeView();
                setLoginMode("");
            }
        });

        helloLabel = new JLabel("Hello!");
        helloLabel.setFont(new Font("Century Gothic", Font.PLAIN, (int) (72 * SCRDIM_KX)));
        helloLabel.setForeground(Color.WHITE);
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        exitButton = new JButton("Exit ChatApp");
        exitButton.setFont(new Font("Century Gothic", Font.PLAIN, (int) (12 * SCRDIM_KX)));
        exitButton.setForeground(Color.WHITE);
        exitButton.setOpaque(false);
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        errorTextField = new JTextField();
        errorTextField.setBounds(0, (int) (120 * SCRDIM_KY), (int)(960 * SCRDIM_KX), (int) (35 * SCRDIM_KY));
        errorTextField.setFont(Fonts.typingFont);
        errorTextField.setHorizontalAlignment(JTextField.CENTER);
        errorTextField.setBackground(new Color(0, 0, 0, 150));
        errorTextField.setEditable(false);
        errorTextField.setFocusable(false);
        errorTextField.setForeground(Color.RED);
        errorTextField.setBorder(null);
        errorTextField.setVisible(false);

        helloLabel.setBounds((int)(330 * SCRDIM_KX), (int) (15 * SCRDIM_KY), (int)(300 * SCRDIM_KX), (int) (90 * SCRDIM_KY));
        exitButton.setBounds((int)(430 * SCRDIM_KX), (int) (450 * SCRDIM_KY), (int)(100 * SCRDIM_KX), (int) (50 * SCRDIM_KY));
        loginPanel.setBounds(0, (int) (120 * SCRDIM_KY), (int)(1000 * SCRDIM_KX), (int) (1000 * SCRDIM_KY));
        regPanel.setBounds(0, (int) (120 * SCRDIM_KY), (int)(1000 * SCRDIM_KX), (int) (1000 * SCRDIM_KY));

        add(helloLabel);
        add(loginPanel);
        add(exitButton);
        add(regPanel);
        add(errorTextField);

        RepaintFrame repaintFrame = new RepaintFrame(this);
        Thread repaintThread = new Thread(repaintFrame);
        repaintThread.start();

        setMode(Mode.LOGIN_ON, "");
    }

    void setLoginMode(String nickname) {
        loginPanel.setVisible(true);
        regPanel.setVisible(false);
        this.setSize((int)(960 * SCRDIM_KX), (int) (550 * SCRDIM_KY));
        exitButton.setBounds((int)(430 * SCRDIM_KX), (int) (450 * SCRDIM_KY), (int)(100 * SCRDIM_KX), (int) (50 * SCRDIM_KY));
        this.setLocationRelativeTo(null);
        loginPanel.getNickField().setText(nickname);
        mode = Mode.LOGIN_ON;
    }

    void setRegistrationMode() {
        loginPanel.setVisible(false);
        regPanel.setVisible(true);
        mode = Mode.REGISTRATION_ON;
    }

    public JButton getLoginButton() {
        return loginPanel.getLoginButton();
    }

    public JButton getReg_registrationButton() {
        return regPanel.getRegButton();
    }

    public JPasswordField getReg_passwordField() {
        return regPanel.getPasswordField();
    }

    public JPasswordField getReg_confirmPasswordField() {
        return regPanel.getConfirmPasswordField();
    }

    public static JTextField getErrorTextField() {
        return errorTextField;
    }

    public boolean isFieldFilled() {
        if (mode == Mode.LOGIN_ON) {
            String login = loginPanel.getNickField().getText().replaceAll(" ", ""); //TODO: оповещение пользователя о пробелах в нике, переделать с помощью split() поиск пробелов
            String password = new String(loginPanel.getPasswordField().getPassword());
            if (login.length() >= 6 && password.length() >= 6) {
                return true;
            } else
                return false;
        } else {
            String login = regPanel.getNicknameField().getText().replaceAll(" ", ""); //TODO: оповещение пользователя о пробелах в нике, переделать с помощью split() поиск пробелов
            String password = new String(regPanel.getPasswordField().getPassword());
            String confirmPassword = new String(regPanel.getConfirmPasswordField().getPassword());
            if (regPanel.getAdditionalCheckBox().isSelected()) {
                if (regPanel.getNameField().getText().replaceAll(" ", "").length() < 6 && regPanel.getSurnameField().getText().replaceAll(" ", "").length() < 6) {
                    return false;
                }
            }
            if (login.length() >= 6 && password.length() >= 6 && confirmPassword.length() >= 6) {
                return true;
            } else
                return false;
        }
    }

    public boolean isPasswordsEquals() {
        if (regPanel.getConfirmPasswordField().getPassword().length == regPanel.getPasswordField().getPassword().length) {
            for (int i = 0; i < regPanel.getPasswordField().getPassword().length; i++) {
                if (regPanel.getPasswordField().getPassword()[i] != regPanel.getConfirmPasswordField().getPassword()[i]) {
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
            regModel = new RegistrationModel(regPanel.getNicknameField().getText(), String.valueOf(regPanel.getPasswordField().getPassword()));
            regModel.setName(regPanel.getNameField().getText());
            regModel.setSurname(regPanel.getSurnameField().getText());
            regModel.setSex((Sex) regPanel.getSexBox().getSelectedItem());
            ImageSerializable imageSerializable = new ImageSerializable(regPanel.getBufImage());
            regModel.setAvatar(imageSerializable);
            if (!isDateCorrect(regPanel.getMonthBox().getSelectedItem(), regPanel.getDayBox().getSelectedItem())) {
                return null;
            }
            regModel.setDateOfBirth(regPanel.getYearBox().getSelectedItem() + " " + regPanel.getMonthBox().getSelectedItem()
                    + " " + regPanel.getDayBox().getSelectedItem());
            return regModel;
        } else if (mode == Mode.LOGIN_ON) {
            regModel = new RegistrationModel(loginPanel.getNickField().getText(), String.valueOf(loginPanel.getPasswordField().getPassword()));
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

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                StartForm startForm = new StartForm();
                startForm.setVisible(true);
            }
        });
    }
}
