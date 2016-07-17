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

public class Application implements Observer{

    private StartForm startForm;
    private MainFormVisualisation mainForm;
    private Mode mode; // STARTFORM_ON - старт форма / MAINFORM_ON - мейн форма

    //Хранение объекта клиента и пользователя будет находиться здесь. Хотя, вероятно, лучше будет юзера передать в менй форм.
    private Client client;
    private User user;

    public Application(){
        init();
    }

    public void init(){
        this.startForm =  new StartForm();
        this.mainForm = new MainFormVisualisation();

        this.mode = Mode.STARTFROM_ON;

        // УСТАНАВЛИВАЮ СЛУШАТЕЛИ НА СТАРТ ФОРМУ: НА КНОПКУ ЛОГИНА И НА КНОПКУ РЕГИСТРАЦИИ
        this.startForm.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (startForm.isFieldFilled()) {

                    if (client == null){  // если еще не создано соединение через клиента, то создаем его
                        /**НА ЭТОМ МОМЕНТЕ НУЖНО СДЕЛАТЬ ИЛИ ЗАГРУЗКУ СЕРВЕРА С НОРМЛАЬНОЙ РАБОТОЙ ФОРМЫ
                         * ИЛИ ЗАРАНЕЕ ПОДКЛЮЧАТЬСЯ С СЕРВЕРУ И ПРИ ОТПРАВЛЕНИИ УЖЕ КАК_ТО РЕАГИРОВАТЬ ПРИ ОТСУТСВИИ ИНТЕРНЕТА.
                         * ТОЕСТЬ ПОСТОЯННАЯ ПРОВЕРКА РАБОТЫ ПОДКЛЮЧЕНИЯ.*/
                        client = new Client(Constants.HOST, Constants.PORT);
                        client.addObserver(Application.this);
                        try {
                            client.start();
                        } catch (IOException e1) {
                            if (e1 instanceof UnknownHostException){
                                showInfoMessage(startForm, "Сервер недоступен!");
                            } else {
                                showInfoMessage(startForm, "Невозможно подключится к серверу!");
                            }
                            return; // если ошибки - то выход с слушателя
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

                    if (client == null){ // если еще не создано соединение через клиента, то создаем его

                        /**НА ЭТОМ МОМЕНТЕ НУЖНО СДЕЛАТЬ ИЛИ ЗАГРУЗКУ СЕРВЕРА С НОРМЛАЬНОЙ РАБОТОЙ ФОРМЫ
                         * ИЛИ ЗАРАНЕЕ ПОДКЛЮЧАТЬСЯ С СЕРВЕРУ И ПРИ ОТПРАВЛЕНИИ УЖЕ КАК_ТО РЕАГИРОВАТЬ ПРИ ОТСУТСВИИ ИНТЕРНЕТА.
                         * ТОЕСТЬ ПОСТОЯННАЯ ПРОВЕРКА РАБОТЫ ПОДКЛЮЧЕНИЯ.*/
                        client = new Client(Constants.HOST, Constants.PORT);
                        client.addObserver(Application.this);
                        try {
                            client.start();
                        } catch (IOException e1) {
                            if (e1 instanceof UnknownHostException){
                                showInfoMessage(startForm, "Сервер недоступен!");
                            } else {
                                showInfoMessage(startForm, "Невозможно подключится к серверу!");
                            }
                            return; // если ошибки - то выход с слушателя
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

        this.mainForm.getMessageArea().addKeyListener(new KeyAdapter() { //TODELETE
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    String text = mainForm.getMessageArea().getText();

                    mainForm.sendMessage();
                    mainForm.getMessageArea().setText("");
                    mainForm.repaintAndRevalidate();

                    if (user.getNickname().equals("AAAAAAA")){
                        client.sendMessageCommand("MAXMAXMAX","AAAAAAA",text);
                    } else if (user.getNickname().equals("MAXMAXMAX")){
                        client.sendMessageCommand("AAAAAAA","MAXMAXMAX",text);
                    }
                }
            }
        });
    }


    public void start(){ //явный запуск приложения
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (mode == Mode.STARTFROM_ON){
                        startForm.setVisible(true);
                    } else if (mode == Mode.MAINFROM_ON){
                        mainForm.setVisible(true);
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        }
    }

    public void setMode(final Mode mode){ // смена режима работы
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (mode == Mode.STARTFROM_ON && Application.this.mode!=Mode.STARTFROM_ON){
                        Application.this.mode = mode;
                        startForm.setVisible(true); //становится видна логин форма
                        mainForm =  new MainFormVisualisation(); //создаем новую МейнФорму, удаляя старые данные с нее
                        mainForm.setVisible(false); //оставляем ее невидимой
                    } else if (mode == Mode.MAINFROM_ON && Application.this.mode!=Mode.MAINFROM_ON){
                        Application.this.mode = mode;
                        mainForm.setVisible(true); //становится видна старт форма
                        startForm.dispose();
                        //startForm = new StartForm(); // создаем новую старт форму, удалялл данный с нее
                        //startForm.setVisible(false); // оставляеем ее невидимой
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
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
                showInfoMessage(startForm, "Вы выполнили вход! Ваш ид " + user.getUniqueID());

                setMode(Mode.MAINFROM_ON); // есди вошли удачно - то меняем режим работы на мейн форм

                //kostyl
                if (user.getNickname().equals("MAXMAXMAX"))
                    client.sendSessionRequestCommand("AAAAAAA","MAXMAXMAX"); //to delete

            } else {
                showInfoMessage(startForm, lsCommand.getExceptionDescription());
            }

        } else if (arg instanceof RegistrationStatusCommand) {

            RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) arg;
            if (rsCommand.isRegistered() && rsCommand.getUser() != null) {
                user = rsCommand.getUser();
                showInfoMessage(startForm, "Вы зарегестрированы! Ваш ид " + user.getUniqueID());

                startForm.setMode(Mode.LOGIN_ON); // если удачно зарегестрировались, то должны войти снова

                showInfoMessage(startForm, "Пожалуйста, войдите под своим логинов и паролем.");
            } else {
                showInfoMessage(startForm, rsCommand.getExceptionDescription());
            }
        } else if (arg instanceof MessageCommand) {

            MessageCommand mCommand = (MessageCommand) arg;
            this.mainForm.getMessage(mCommand.getMessageText());

        } else if (arg instanceof SessionRequestCommand){//TODELETE

            SessionRequestCommand srCommand = (SessionRequestCommand) arg;

            if (user.getNickname().equals("AAAAAAA"))
                client.sendAcceptConnectionCommand(srCommand.getNickname_From(), srCommand.getNickname_To(), true);

        } else if (arg instanceof AcceptConnectionCommand){ //TODELETE

            AcceptConnectionCommand acCommand = (AcceptConnectionCommand) arg;
            if (user.getNickname().equals("MAXMAXMAX") && acCommand.isAccept()){
                showInfoMessage(mainForm, "Соеседник принял ваше предложение. Начинайте общение!");
            }
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }
}
