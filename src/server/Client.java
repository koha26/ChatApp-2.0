package server;

import logic.Constants;
import logic.Message;
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
    public void sendAcceptFriendshipCommand(String nickname_To, String nickname_From, boolean isAccept) {
        AcceptFriendshipCommand acCommand = new AcceptFriendshipCommand();
        acCommand.setNickname_From(nickname_From);
        acCommand.setNickname_To(nickname_To);
        acCommand.setAccept(isAccept);
        send(acCommand);
    }

    public void sendMessageCommand(Message message) {
        MessageCommand mCommand = new MessageCommand();
        message.setReaded(true);
        mCommand.setMessage(message);
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

    public void sendFriendshipRequestCommand(String nickname_To, String nickname_From) {
        FriendshipRequestCommand srCommand = new FriendshipRequestCommand();
        srCommand.setNickname_From(nickname_From);
        srCommand.setNickname_To(nickname_To);
        send(srCommand);
    }

    public void sendChangingUserInfoCommand(User changedUser, boolean isInfoChanged, boolean isAvatarChanged){
        ChangingUserInfoCommand cuiCommand = new ChangingUserInfoCommand(changedUser,isInfoChanged,isAvatarChanged);
        send(cuiCommand);
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
            //e.printStackTrace();
            System.out.println("Oops!");
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
                        } else {
                            setChanged();
                            notifyObservers(rsCommand);
                        }
                    } else if (lastCommand instanceof MessageCommand) {

                        MessageCommand mCommand = (MessageCommand) lastCommand;

                        setChanged();
                        notifyObservers(mCommand);
                    } else if (lastCommand instanceof AcceptFriendshipCommand) {

                        AcceptFriendshipCommand afCommand = (AcceptFriendshipCommand) lastCommand;

                        setChanged();
                        notifyObservers(afCommand);
                    } else if (lastCommand instanceof FriendshipRequestCommand) {

                        FriendshipRequestCommand frCommand = (FriendshipRequestCommand) lastCommand;

                        setChanged();
                        notifyObservers(frCommand);
                    } else if (lastCommand instanceof DisconnectCommand) {
                        close();
                    } else if (lastCommand instanceof HistoryPacketCommand) {

                        HistoryPacketCommand hpCommand = (HistoryPacketCommand) lastCommand;

                        setChanged();
                        notifyObservers(hpCommand);
                    } else if (lastCommand instanceof FriendOfflineCommand){

                        FriendOfflineCommand foCommand = (FriendOfflineCommand) lastCommand;

                        setChanged();
                        notifyObservers(foCommand);
                    } else if (lastCommand instanceof ChangingUserInfoStatusCommand){

                        ChangingUserInfoStatusCommand cuisCommand = (ChangingUserInfoStatusCommand) lastCommand;

                        setChanged();
                        notifyObservers(cuisCommand);
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
