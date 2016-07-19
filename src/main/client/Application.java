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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    public Application() {
        init();
    }

    public void init() {
        this.startForm = new StartForm();
        this.mainForm = new MainFormVisualisation();
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
                            showInfoMessage(startForm, "Сервер недоступен!");
                            return;
                        } catch (IOException e1) {
                            showInfoMessage(startForm, "Невозможно подключится к серверу!");
                            return;
                        }
                    }
                    RegistrationModel regModel = startForm.getRegistrationModel(); // получение данных с формы автоматически
                    if (regModel != null) {
                        client.sendLoginCommand(regModel.getNick(), regModel.getPassword()); // получение с нее лог и пас и отправка
                    }
                } else {
                    showInfoMessage(startForm, "Заполните главные поля!");
                }
            }
        });

        this.startForm.getReg_registrationButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startForm.isFieldFilled()) {
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
                            showInfoMessage(startForm, "Сервер недоступен!");
                            return;
                        } catch (IOException e1) {
                            showInfoMessage(startForm, "Невозможно подключится к серверу!");
                            return;
                        }
                    }
                    RegistrationModel regModel = startForm.getRegistrationModel(); //получение данных с формы автоматически
                    if (regModel != null) {
                        client.sendRegistrationCommand(regModel); // отправка рег комманды
                    }
                } else {
                    showInfoMessage(startForm, "Заполните главные поля!");
                }
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
                        mainForm = new MainFormVisualisation(); //создаем новую МейнФорму, удаляя старые данные с нее
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
                showInfoMessage(startForm, lsCommand.getExceptionDescription());
            }
        } else if (arg instanceof RegistrationStatusCommand) {
            RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) arg;
            if (rsCommand.isRegistered() && rsCommand.getUser() != null) {
                user = rsCommand.getUser();
                showInfoMessage(startForm, "Your account is registered! Your ID " + user.getUniqueID());
                startForm.setMode(Mode.LOGIN_ON, user.getNickname()); // если удачно зарегестрировались, то должны войти снова
                showInfoMessage(startForm, "Please, log in using your nickname and password!");
            } else {
                showInfoMessage(startForm, rsCommand.getExceptionDescription());
            }
        } else if (arg instanceof MessageCommand) {
            MessageCommand mCommand = (MessageCommand) arg;
            this.mainForm.getMessage(mCommand.getMessageText());

            //TODO: команды ниже не имеют смысла, учитывая задуманное ТЗ
        } else if (arg instanceof SessionRequestCommand) {//TODELETE
            SessionRequestCommand srCommand = (SessionRequestCommand) arg;
            if (user.getNickname().equals("AAAAAAA"))
                client.sendAcceptConnectionCommand(srCommand.getNickname_From(), srCommand.getNickname_To(), true);
        } else if (arg instanceof AcceptConnectionCommand) { //TODELETE
            AcceptConnectionCommand acCommand = (AcceptConnectionCommand) arg;
            if (user.getNickname().equals("MAXMAXMAX") && acCommand.isAccept()) {
                showInfoMessage(mainForm, "Соеседник принял ваше предложение. Начинайте общение!");
            }
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }
}