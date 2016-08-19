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
import java.util.Observable;

public class Client extends Observable {

    private Command lastCommand;
    private Connection connection;
    private String host;
    private int port;
    private Thread receiverThread;

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

        receiverThread = new Thread(new Receiver());
        receiverThread.start();
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

    public void sendChangingUserInfoCommand(User changedUser, boolean isInfoChanged, boolean isAvatarChanged) {
        ChangingUserInfoCommand cuiCommand = new ChangingUserInfoCommand(changedUser, isInfoChanged, isAvatarChanged);
        send(cuiCommand);
    }

    public void sendSearchCommand(String nicknamePattern) {
        SearchCommand sCommand = new SearchCommand(nicknamePattern);
        send(sCommand);
    }

    public void sendHistoryPacketCommand(String nicknameHost, String nicknameCompanion) {
        HistoryPacketCommand hpCommand = new HistoryPacketCommand();
        hpCommand.setNickname_host(nicknameHost);
        hpCommand.setNickname_companion(nicknameCompanion);
        send(hpCommand);
    }

    public void sendFriendshipEndCommand(String nicknameHost, String nicknameCompanion) {
        FriendshipEndCommand feCommand = new FriendshipEndCommand(nicknameHost, nicknameCompanion);
        send(feCommand);
    }

    public void sendDisconnectCommand() {
        send(new DisconnectCommand());
    }

    private void send(Command command) { // м-д для отправки комманд между сервером и к-том
        try {
            this.connection.sendCommand(command);
        } catch (IOException e) {
            System.out.println("Ошибка отправки!");

            receiverThread.interrupt();

            setChanged();
            notifyObservers(new DisconnectCommand());
        }
    }

    private class Receiver implements Runnable {

        public void run() {
            while (connection.isOpen()) {

                try {
                    lastCommand = connection.receiveCommand();
                } catch (IOException e) {

                    setChanged();
                    notifyObservers(new DisconnectCommand());
                    close();
                    break;
                } catch (ClassNotFoundException e) {

                    setChanged();
                    notifyObservers(new DisconnectCommand());
                    close();
                    break;
                }
                if (lastCommand != null) {

                    if (lastCommand instanceof DisconnectCommand) {
                        close();
                    } else {
                        setChanged();
                        notifyObservers(lastCommand);
                    }
                }
            }
        }

        public synchronized void close() {
            try {
                connection.close();
                receiverThread.interrupt();
            } catch (IOException e) {
                //TODO
            }
        }
    }
}
