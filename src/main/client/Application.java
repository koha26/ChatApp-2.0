package main.client;

import gui.MainFormVisualisation;
import gui.Mode;
import gui.StartForm;
import logic.Constants;
import logic.RegistrationModel;
import logic.User;
import logic.command.*;
import server.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class Application implements Observer {
    /**
     * STARTFORM_ON - старт форма
     * MAINFORM_ON - мейн форма
     * LOGIN_ON - отображение панели входа на старт форме
     * REGISTRATION_ON - отображение панели регистрации на старт форме
     * <p/>
     * Хранение объекта клиента и пользователя будет находиться здесь. Хотя, вероятно, лучше будет юзера передать в менй форм
     */
    private StartForm startForm;
    private MainFormVisualisation mainForm;
    private Mode mode;
    private Client client;
    private User user;

    public Application() throws IOException {
        init();
    }

    public void init() throws IOException {
        this.startForm = new StartForm();
        try {
            this.mainForm = new MainFormVisualisation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mode = Mode.STARTFROM_ON;
        // УСТАНАВЛИВАЮ СЛУШАТЕЛИ НА СТАРТ ФОРМУ: НА КНОПКУ ЛОГИНА И НА КНОПКУ РЕГИСТРАЦИИ
        this.startForm.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startForm.isFieldFilled()) {
                    if (client == null) {  // если еще не создано соединение через клиента, то создаем его
                        /**НА ЭТОМ МОМЕНТЕ НУЖНО СДЕЛАТЬ ИЛИ ЗАГРУЗКУ СЕРВЕРА С НОРМАЛЬНОЙ РАБОТОЙ ФОРМЫ
                         * ИЛИ ЗАРАНЕЕ ПОДКЛЮЧАТЬСЯ К СЕРВЕРУ И ПРИ ОТПРАВЛЕНИИ УЖЕ КАК-ТО РЕАГИРОВАТЬ ПРИ ОТСУТСВИИ ИНТЕРНЕТА.
                         * ТО ЕСТЬ ПОСТОЯННАЯ ПРОВЕРКА РАБОТЫ ПОДКЛЮЧЕНИЯ.
                         * */
                        client = new Client(Constants.HOST, Constants.PORT);
                        client.addObserver(Application.this);
                        try {
                            client.start();
                        } catch (UnknownHostException e1) {
                            StartForm.showErrorLabel("Сервер не доступен");
                            return;
                        } catch (IOException e1) {
                            StartForm.showErrorLabel("Невозможно подключиться к серверу");
                            return;
                        }
                    }
                    RegistrationModel regModel = startForm.getRegistrationModel(); // получение данных с формы автоматически
                    if (regModel != null) {
                        client.sendLoginCommand(regModel.getNick(), regModel.getPassword()); // получение с нее лог и пас и отправка
                    }
                } else {
                    StartForm.showErrorLabel("Заполните поля входа");
                }
            }
        });

        this.startForm.getReg_registrationButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startForm.isFieldFilled() & startForm.isPasswordsEquals()) {
                    if (client == null) { // если еще не создано соединение через клиента, то создаем его
                        /**НА ЭТОМ МОМЕНТЕ НУЖНО СДЕЛАТЬ ИЛИ ЗАГРУЗКУ СЕРВЕРА С НОРМАЛЬНОЙ РАБОТОЙ ФОРМЫ
                         * ИЛИ ЗАРАНЕЕ ПОДКЛЮЧАТЬСЯ К СЕРВЕРУ И ПРИ ОТПРАВЛЕНИИ УЖЕ КАК-ТО РЕАГИРОВАТЬ ПРИ ОТСУТСВИИ ИНТЕРНЕТА.
                         * ТО ЕСТЬ ПОСТОЯННАЯ ПРОВЕРКА РАБОТЫ ПОДКЛЮЧЕНИЯ.
                         * */
                        client = new Client(Constants.HOST, Constants.PORT);
                        client.addObserver(Application.this);
                        try {
                            client.start();
                        } catch (UnknownHostException e1) {
                            StartForm.showErrorLabel("Сервер не доступен");
                            return;
                        } catch (IOException e1) {
                            StartForm.showErrorLabel("Невозможно подключиться к серверу");
                            return;
                        }
                    }
                    RegistrationModel regModel = startForm.getRegistrationModel(); //получение данных с формы автоматически
                    if (regModel != null) {
                        client.sendRegistrationCommand(regModel); // отправка рег комманды
                    }
                } else if (!startForm.isFieldFilled()) {
                    StartForm.showErrorLabel("Заполните главные поля!");
                } else if (startForm.isFieldFilled() && !startForm.isPasswordsEquals()) {
                    StartForm.showErrorLabel("Пароли не совпадают!");
                    startForm.getRegPanel().getPasswordField().setBorder(new LineBorder(Color.RED, 2));
                    startForm.getRegPanel().getConfirmPasswordField().setBorder(new LineBorder(Color.RED, 2));
                }
            }
        });

        this.mainForm.getSettingsButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendFriendshipRequestCommand("koha26", "testtest");
            }
        });
    }


    public void start() { //явный запуск приложения
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (mode == Mode.STARTFROM_ON) {
                        startForm.setVisible(true);
                    } else if (mode == Mode.MAINFROM_ON) {
                        mainForm.setVisible(true);
                    }
                }
            });
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        }
    }

    public void setMode(final Mode mode) { // смена режима работы
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (mode == Mode.STARTFROM_ON && Application.this.mode != Mode.STARTFROM_ON) {
                        Application.this.mode = mode;
                        startForm.setVisible(true); //становится видна логин форма
                        try {
                            mainForm = new MainFormVisualisation(); //создаем новую МейнФорму, удаляя старые данные с нее
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mainForm.setVisible(false); //оставляем ее невидимой
                    } else if (mode == Mode.MAINFROM_ON && Application.this.mode != Mode.MAINFROM_ON) {
                        Application.this.mode = mode;
                        mainForm.setVisible(true); //становится видна старт форма
                        startForm.dispose();
                    }
                }
            });
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        }
    }

    public void showInfoMessage(Component comp, String text) {
        JOptionPane.showMessageDialog(comp, text);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LoginStatusCommand) {
            LoginStatusCommand lsCommand = (LoginStatusCommand) arg;
            if (lsCommand.getUser() != null) {
                user = lsCommand.getUser();
                showInfoMessage(startForm, "You entered as " + user.getNickname() + "! Your ID: " + user.getUniqueID());
                setMode(Mode.MAINFROM_ON); // есди вошли удачно - то меняем режим работы на мейн форм
            } else {
                startForm.showErrorLabel(lsCommand.getExceptionDescription());
            }
        } else if (arg instanceof RegistrationStatusCommand) {
            RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) arg;
            if (rsCommand.isRegistered() && rsCommand.getUser() != null) {
                user = rsCommand.getUser();
                showInfoMessage(startForm, "Your account is registered! Your ID " + user.getUniqueID());
                startForm.setMode(Mode.LOGIN_ON, user.getNickname()); // если удачно зарегестрировались, то должны войти снова
                showInfoMessage(startForm, "Please, log in using your nickname and password!");
            } else {
                startForm.showErrorLabel(rsCommand.getExceptionDescription());
            }
        } else if (arg instanceof MessageCommand) {
            MessageCommand mCommand = (MessageCommand) arg;
            this.mainForm.getMessage(mCommand.getMessage().getMessageText());
        } else if (arg instanceof FriendshipRequestCommand) {

            FriendshipRequestCommand srCommand = (FriendshipRequestCommand) arg;

            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(null,
                    "Accept friendship request from " + srCommand.getNickname_From() + "?", "Friendship request",
                    JOptionPane.YES_NO_CANCEL_OPTION,

                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (n == 0)
                client.sendAcceptFriendshipCommand(srCommand.getNickname_From(), srCommand.getNickname_To(), true);

        } else if (arg instanceof AcceptFriendshipCommand) {

            AcceptFriendshipCommand acCommand = (AcceptFriendshipCommand) arg;
            if (user.getNickname().equals("MAXMAXMAX") && acCommand.isAccept()) {
                showInfoMessage(mainForm, "Соеседник принял ваше предложение. Начинайте общение!");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.start();
    }
}
