package server;

import logic.Constants;
import logic.RegistrationModel;
import logic.User;
import logic.command.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Observable;
import java.util.Scanner;

public class Client extends Observable {

    private Command lastCommand;
    private User user;
    private Connection connection;
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        //через Properties получаем HOST & PORT server'a
        //но пока так
    }

    public void start() throws IOException {
        InetAddress address = InetAddress.getByName(host);
        Socket socket = new Socket(address, port);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        this.connection = new Connection(socket, os, is);

        new Thread(new Receiver()).start();
    }

    /**
     * Методы для отправки конкретных комманд, которые разрешены клиенту.
     * Сделано для того, чтобы в пользовательском интерфейсе и обработчиках не было взаимодействия с коммандами напрямую.
     */
    public void sendAcceptConnectionCommand(String nickname_To, String nickname_From, boolean isAccept) {
        AcceptConnectionCommand acCommand = new AcceptConnectionCommand();
        acCommand.setNickname_From(nickname_From);
        acCommand.setNickname_To(nickname_To);
        acCommand.setAccept(isAccept);
        send(acCommand);
    }

    public void sendMessageCommand(String nickname_To, String nickname_From, String messageText) {
        MessageCommand mCommand = new MessageCommand();
        mCommand.setMessageText(messageText);
        mCommand.setNickname_From(nickname_From);
        mCommand.setNickname_To(nickname_To);
        send(mCommand);
    }

    public void sendLoginCommand(String nickname, String password) {
        LoginCommand lCommand = new LoginCommand(nickname, password, Constants.VERSION_ID);
        send(lCommand);
    }

    public void sendRegistrationCommand(RegistrationModel registrationModel) {
        RegistrationCommand rCommand = new RegistrationCommand(registrationModel);
        send(rCommand);
    }

    public void sendRegistrationCommand(String nickname, String password) {
        RegistrationCommand rCommand = new RegistrationCommand(nickname, password);
        send(rCommand);
    }

    public void sendSessionRequestCommand(String nickname_To, String nickname_From) {
        SessionRequestCommand srCommand = new SessionRequestCommand();
        srCommand.setNickname_From(nickname_From);
        srCommand.setNickname_To(nickname_To);
        send(srCommand);
    }

    public void sendDisconnectCommand() {
        send(new DisconnectCommand());
    }

    private void send(Command command) { // м-д для отправки комманд между сервером и к-том
        try {
            this.connection.sendCommand(command);
        } catch (IOException e) {
            System.out.println("Ошибка отправки!");
        }
    }

    public void run() { // для тестов
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("nick: ");
            String nick = sc.nextLine();
            System.out.println("pass: ");
            String pass = sc.nextLine();
            RegistrationCommand rCommand = new RegistrationCommand(nick, pass);

            connection.sendCommand(rCommand); //для теста
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            //new Client("localhost", 8621).run();
            Client client = new Client(Constants.HOST, Constants.PORT);
            client.start();
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Receiver implements Runnable {

        public void run() {
            while (connection.isOpen()) {

                try {
                    lastCommand = connection.receiveCommand();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (lastCommand != null) {

                    if (lastCommand instanceof LoginStatusCommand) {

                        LoginStatusCommand lsCommand = (LoginStatusCommand) lastCommand;

                        setChanged();
                        notifyObservers(lsCommand);

                    } else if (lastCommand instanceof RegistrationStatusCommand) {
                        RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) lastCommand;
                        if (rsCommand.isRegistered()) {
                            user = rsCommand.getUser();
                            System.out.println(user);

                            setChanged();
                            notifyObservers(rsCommand);


                            /*if (user.getNickname().equals("Kostya2")){
                                SessionRequestCommand srCommand = new SessionRequestCommand();
                                srCommand.setNickname_To("Kostya1");
                                srCommand.setNickname_From(user.getNickname());
                                try {
                                    connection.sendCommand(srCommand);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }*/
                        } else {
                            setChanged();
                            notifyObservers(rsCommand);
                        }
                    } else if (lastCommand instanceof MessageCommand) {

                        MessageCommand mCommand = (MessageCommand) lastCommand;

                        setChanged();
                        notifyObservers(mCommand);

                        /*System.out.println(mCommand.getNickname_From()+": "+mCommand.getMessageText());
                        mCommand.setNickname_To(mCommand.getNickname_From());
                        mCommand.setNickname_From(user.getNickname());
                        mCommand.setMessageText("Хай. А я "+user.getNickname());
                        try {
                            connection.sendCommand(mCommand);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                    } else if (lastCommand instanceof AcceptConnectionCommand) {

                        AcceptConnectionCommand acCommand = (AcceptConnectionCommand) lastCommand;

                        setChanged();
                        notifyObservers(acCommand);

                        /*if (acCommand.isAccept()){
                            System.out.println("мы начинает общение! =)");

                            MessageCommand mCommand = new MessageCommand();
                            mCommand.setNickname_From(user.getNickname());
                            mCommand.setNickname_To(acCommand.getNickname_From());
                            mCommand.setMessageText("Привет, меня зовут "+user.getNickname());

                            try {
                                connection.sendCommand(mCommand);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }*/

                    } else if (lastCommand instanceof SessionRequestCommand) {

                        SessionRequestCommand srCommand = (SessionRequestCommand) lastCommand;

                        setChanged();
                        notifyObservers(srCommand);

                        /*AcceptConnectionCommand acCommand = new AcceptConnectionCommand();
                        acCommand.setAccept(true);
                        acCommand.setNickname_From(srCommand.getNickname_To());
                        acCommand.setNickname_To(srCommand.getNickname_From());
                        try {
                            connection.sendCommand(acCommand);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    } else if (lastCommand instanceof DisconnectCommand) {
                        close();
                    }

                }
            }
        }

        public synchronized void close() {
            try {
                connection.close();
            } catch (IOException e) {
                //TODO
            } finally {
                user = null;
            }
        }
    }
}
