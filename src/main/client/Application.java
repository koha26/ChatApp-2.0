package main.client;

import gui.*;
import gui.Notifications.FriendshipRequestNotification;
import logic.*;
import logic.command.*;
import server.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
    private MainForm mainForm;
    private Mode mode;
    private Client client;
    private User user;

    public Application() throws IOException {
        init();
    }

    public void init() throws IOException {
        this.startForm = new StartForm();
        mainForm = new MainForm(); //создаем новую МейнФорму, удаляя старые данные с нее
        mainForm.getPlusButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = JOptionPane.showInputDialog(null, "Enter nickname: ");
                client.sendFriendshipRequestCommand(nick, user.getNickname());
            }
        });
        mainForm.getHomePanel().getBottomPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                final FriendLook friendLook = (FriendLook) e.getComponent().getComponentAt(e.getPoint());
                mainForm.changeModeToDialog();
                if (mainForm.startNewDialog(user.getAvatarAsBufImage(), friendLook.getFriend().getAvatarAsBufImage(), friendLook.getFriend().getNickname())) {
                    mainForm.getCurrentDialogPanel().getMessageArea().addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                                Message myMesseage = new Message();
                                myMesseage.setMessageText(mainForm.getCurrentDialogPanel().getMessageArea().getText());
                                myMesseage.setNickname_From(user.getNickname());
                                myMesseage.setNickname_To(friendLook.getFriend().getNickname());
                                client.sendMessageCommand(myMesseage);
                                mainForm.getCurrentDialogPanel().showOutcomingMessage(myMesseage);
                                mainForm.getCurrentDialogPanel().getMessageArea().setText("");
                            }
                        }
                    });
                }

                Application.this.mode = Mode.DIALOG;
            }
        });

        mainForm.getHomeButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainForm.changeModeToHomePanel();
            }
        });

        mainForm.getFriendSidePanel().getFriendsPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    final FriendSideLook friendSideLook = (FriendSideLook) e.getComponent().getComponentAt(e.getPoint());
                    mainForm.changeModeToDialog();
                    if (mainForm.startNewDialog(user.getAvatarAsBufImage(), friendSideLook.getFriend().getAvatarAsBufImage(), friendSideLook.getFriend().getNickname())) {
                        mainForm.getCurrentDialogPanel().getMessageArea().addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                                    Message myMesseage = new Message();
                                    myMesseage.setMessageText(mainForm.getCurrentDialogPanel().getMessageArea().getText());
                                    myMesseage.setNickname_From(user.getNickname());
                                    myMesseage.setNickname_To(friendSideLook.getFriend().getNickname());
                                    client.sendMessageCommand(myMesseage);
                                    mainForm.getCurrentDialogPanel().showOutcomingMessage(myMesseage);
                                    mainForm.getCurrentDialogPanel().getMessageArea().setText("");
                                }
                            }
                        });
                    }

                    Application.this.mode = Mode.DIALOG;
                }
            }
        });

        mainForm.getFriendSidePanel().getSearchTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (mainForm.getFriendSidePanel().getSearchTextField().getText().equals("")) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        mainForm.getFriendSidePanel().resetPanel();
                        mainForm.getFriendSidePanel().updateFriendSearch(mainForm.getFriendSidePanel().getSearchTextField().getText(), user);
                    }
                }
            }
        });

        mainForm.getFriendSidePanel().getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainForm.getFriendSidePanel().resetPanel();
                mainForm.getFriendSidePanel().updateInfo(user);
                mainForm.getFriendSidePanel().getSearchTextField().setText("Search...");
            }
        });

        mainForm.getFriendSidePanel().getGlobalSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: 12.08.2016
                mainForm.getFriendSidePanel().resetPanel();
                mainForm.getFriendSidePanel().getGlobalSearchButton().setBounds(20, 10, 180, 50);
//                ArrayList<Friend> friends = new ArrayList<Friend>();
//                friends.add(new Friend("dasdsad"));
//                friends.add(new Friend("dasdsad1"));
//                friends.add(new Friend("vlad22"));
//                friends.add(new Friend("max"));
//                friends.add(new Friend("max_morcos"));
//                friends.add(new Friend("dante"));
                String searchRequest = mainForm.getFriendSidePanel().getSearchTextField().getText();
                // В этот метод приходит список Friend от сервера
//                mainForm.getFriendSidePanel().updateGlobalSearch(friends, searchRequest);
            }
        });

//        mainForm.getFriendSidePanel().getFoundUser().getAddButton().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String newFriendNick = mainForm.getFriendSidePanel().getFoundUser().getNickLabel().getText();
//                client.sendFriendshipRequestCommand(newFriendNick, user.getNickname());
//            }
//        });

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
                            StartForm.showNotificationLabel("Server is not available", Color.RED);
                            return;
                        } catch (IOException e1) {
                            StartForm.showNotificationLabel("Unable to connect to server", Color.RED);
                            return;
                        }
                    }
                    RegistrationModel regModel = startForm.getRegistrationModel(); // получение данных с формы автоматически
                    if (regModel != null) {
                        client.sendLoginCommand(regModel.getNick(), regModel.getPassword()); // получение с нее лог и пас и отправка
                    }
                } else {
                    StartForm.showNotificationLabel("Fill in the entry fields!", Color.RED);
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
                            StartForm.showNotificationLabel("Server is not available", Color.RED);
                            return;
                        } catch (IOException e1) {
                            StartForm.showNotificationLabel("Unable to connect to server", Color.RED);
                            return;
                        }
                    }
                    RegistrationModel regModel = startForm.getRegistrationModel(); //получение данных с формы автоматически
                    if (regModel != null) {
                        client.sendRegistrationCommand(regModel); // отправка рег комманды
                    }
                } else if (!startForm.isFieldFilled()) {
                    StartForm.showNotificationLabel("Fill in the main fields!", Color.RED);
                } else if (startForm.isFieldFilled() && !startForm.isPasswordsEquals()) {
                    StartForm.showNotificationLabel("Passwords do not match!", Color.RED);
                    startForm.getRegPanel().getPasswordField().setBorder(new LineBorder(Color.RED, 2));
                    startForm.getRegPanel().getConfirmPasswordField().setBorder(new LineBorder(Color.RED, 2));
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
                        mainForm.setVisible(false); //оставляем ее невидимой
                    } else if (mode == Mode.MAINFROM_ON && Application.this.mode != Mode.MAINFROM_ON) {
                        mainForm.getHomePanel().updateInfo(user);
                        mainForm.getFriendSidePanel().updateInfo(user);
                        Application.this.mode = Mode.HOME_PANEL;
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
                startForm.showNotificationLabel(lsCommand.getExceptionDescription(), Color.RED);
            }
        } else if (arg instanceof RegistrationStatusCommand) {
            RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) arg;
            if (rsCommand.isRegistered() && rsCommand.getUser() != null) {
                user = rsCommand.getUser();
                startForm.showNotificationLabel("Your account is registered! Your ID " + user.getUniqueID() + ". Please, log in using your nickname and password!", Color.GREEN);
                startForm.setMode(Mode.LOGIN_ON, user.getNickname());
            } else {
                startForm.showNotificationLabel(rsCommand.getExceptionDescription(), Color.RED);
            }
        } else if (arg instanceof MessageCommand) {
            MessageCommand mCommand = (MessageCommand) arg;
            Message message = mCommand.getMessage();
            mainForm.changeModeToDialog();
            Friend friend = user.getFriend(message.getNickname_From());
            if (message.getNickname_To().equals(user.getNickname()))
                this.mainForm.receiveIncomingMessage(message, user.getAvatarAsBufImage(), friend.getAvatarAsBufImage());
        } else if (arg instanceof FriendshipRequestCommand) {

            FriendshipRequestCommand srCommand = (FriendshipRequestCommand) arg;

            FriendshipRequestNotification notification = new FriendshipRequestNotification();
            notification.updateInfo(srCommand);

            final String friendNick = notification.getNickname().getText();

            notification.getAcceptButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.sendAcceptFriendshipCommand(friendNick, user.getNickname(), true);
                    mainForm.closeNotification();
                }
            });

            notification.getRejectButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.sendAcceptFriendshipCommand(friendNick, user.getNickname(), false);
                    mainForm.closeNotification();
                }
            });

            mainForm.addNotificationPanel(notification);
        } else if (arg instanceof AcceptFriendshipCommand) {
            AcceptFriendshipCommand acCommand = (AcceptFriendshipCommand) arg;
            if (acCommand.isAccept()) {
                JOptionPane.showMessageDialog(mainForm, acCommand.getNickname_From() + " and you are friends now! Congrats!");
                mainForm.getHomePanel().updateInfo(user);
                mainForm.getFriendSidePanel().updateInfo(user);
                mainForm.getFriendSidePanel().updateInfo(user);
            }
        } else if (arg instanceof ChangingUserInfoStatusCommand) {
            ChangingUserInfoStatusCommand cuisCommand = (ChangingUserInfoStatusCommand) arg;
            user = cuisCommand.getChangedUser();
            mainForm.getHomePanel().updateInfo(user);
            mainForm.getFriendSidePanel().updateInfo(user);
        }
    }

    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.start();
    }
}
