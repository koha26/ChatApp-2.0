package server;

import logic.Message;
import logic.PotentialFriend;
import logic.RegistrationModel;
import logic.User;
import logic.command.*;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Server implements Runnable{

    private ServerSocket serverSocket;
    private Thread mainThread;
    private volatile Database database;
    private BlockingQueue<ClientThread> clients;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.database = new Database();
            clients = new LinkedBlockingDeque<ClientThread>();
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        mainThread = Thread.currentThread();
        while (true) {
            try {
                Socket newSocket = getNewConnection(); // получаем соединение с кем-то
                if (mainThread.isInterrupted()) {
                    break;
                } else if (newSocket != null) {
                    final ClientThread clThread = new ClientThread(newSocket);
                    final Thread thread = new Thread(clThread); // поток обработки комманд

                    thread.setDaemon(true);
                    thread.setPriority(Thread.NORM_PRIORITY);
                    thread.start();

                    clients.add(clThread); //добавляем в очередь
                }
            } catch (IOException e) {
                //stop();
            }
        }
    }

    private Socket getNewConnection() {
        Socket s = null;
        try {
            s = serverSocket.accept();
        } catch (IOException e) {
            // если ошибка в момент приема - "гасим" сервер
        }
        return s;
    }

    public void stop() throws IOException {
        if (!mainThread.isInterrupted() && mainThread.isAlive()){
            mainThread.interrupt();
            serverSocket.close();
        }
    }

    /**
     * Контроллеры для взаимодействия с объектом класса Database
     */

    private User registerUser(RegistrationModel regModel, Connection connection) throws UnknownHostException { //для регистрации
        return this.database.registerUser(regModel, connection);
    }

    private void goOnline(String nickname, Connection connection) { // для хранения онлайн клиентов
        this.database.goOnline(nickname, connection);
    }

    private void goOffline(String nickname) {
        this.database.goOffline(nickname);
    }

    private void deleteUser(String nickname) {
        this.database.deleteUser(nickname);
    }

    private User checkUser(String nickname, String password) { // проверка на существования такой связки "ник"-"пароль"
        return database.checkUser(nickname, password);
    }

    private User updateUser(String nickname) {
        return database.updateUser(nickname);
    }

    private boolean isExist(String nickname) { // существует ли кл-т с таким ником
        return database.isExist(nickname);
    }

    private User modificationUser(User changedUser, boolean isInfoChanged, boolean isAvatarChanged) {
        return database.modificationUser(changedUser, isInfoChanged, isAvatarChanged);
    }

    private List<PotentialFriend> getResultByGlobalSearch(User host, String nicknamePattern) {
        return database.getResultByGlobalSearch(host, nicknamePattern);
    }

    private boolean isOnline(String nickname) { // в онлайне ли кл-т с таким ником
        return database.isOnline(nickname);
    }

    private void addFriend(String nickname_host, String nickname_friend) {
        database.addFriend(nickname_host, nickname_friend);
    }

    private boolean deleteFriend(String nickname_host, String nickname_friend) {
        return database.deleteFriend(nickname_host, nickname_friend);
    }

    private Set<String> retainAllFriendOnline(Set<String> comparatedSet) {
        return database.retainAllFriendOnline(comparatedSet);
    }

    private Set<String> getSetOfFriendUnreadMes(String nickname_host) {
        return database.getSetOfFriendUnreadMes(nickname_host);
    }

    private boolean deleteAndCreateUnreadMesFile(String nickname_host) {
        return database.deleteAndCreateUnreadMesFile(nickname_host);
    }

    private void saveMessageInSenderHistory(Message message, String nickname_sender, String nickname_receiver) {
        try {
            database.saveMessageInSenderHistory(message, nickname_sender, nickname_receiver);
        } catch (IOException e) {
            //
        }
    }

    private void saveMessageInReceiverHistory(Message message, String nickname_sender, String nickname_receiver, boolean isRead) {
        try {
            database.saveMessageInReceiverHistory(message, nickname_sender, nickname_receiver, isRead);
        } catch (IOException e) {
            //
        }
    }

    private Stack<HistoryPacketCommand> loadHistory(String nickname_asker, String nickname_companion) {
        try {
            return database.loadHistory(nickname_asker, nickname_companion);
        } catch (IOException e) {
            //
            return null;
        }
    }

    private boolean deleteFileWithUnreadMessage(String nickname_asker, String nickname_companion) {
        return database.deleteFileWithUnreadMessage(nickname_asker, nickname_companion);
    }

    private LinkedList<Command> loadAwaitingCommands(String nickname) {
        return database.loadAwaitingCommands(nickname);
    }

    private void addAwaitingCommand(String nickname, Command command) {
        database.addAwaitingCommand(nickname, command);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void main(String[] args) {
        new Server(8621).run();
    }

    /**
     * Этот класс для выделения определнного потока для каждого
     * нового клиента.
     */

    private class ClientThread implements Runnable {
        private Command lastCommand;  // последняя полученая команда
        private Connection connection;  // соединение между сервером и клиентом
        private User user;  // запись о клиенте
        private List<Stack<HistoryPacketCommand>> histories;

        public ClientThread(Socket socket) throws IOException {
            this.connection = new Connection(socket);
        }

        public Connection getConnection() {
            return connection;
        }

        public User getUser() {
            return user;
        }

        @Override
        public void run() {
            while (connection.isOpen()) { //если наше соединение активно, то обрабыватываем получаемые команды
                try {
                    lastCommand = connection.receiveCommand();

                    if (lastCommand != null) {// receiveCommand() может выдать null в случае,
                        // если полученый объект не наследник от Command
                        if (lastCommand instanceof RegistrationCommand) { // клиент отправляет эту команду для регистрации

                            RegistrationCommand rCommand = (RegistrationCommand) lastCommand;
                            RegistrationStatusCommand rsCommand;
                            user = registerUser(rCommand.getRegModel(), connection); //рега через Database

                            if (rCommand.getRegModel().getAvatarAsBufImage() != null) {
                                modificationUser(user, false, true);
                            }

                            if (user != null) { //если рега успешна - отправка полученого объекта User
                                rsCommand = new RegistrationStatusCommand(true, user);
                            } else { //если не успешна - то отправляем причину
                                String exceptionDescription = "This nickname is already registered! Sorry, try another.";
                                rsCommand = new RegistrationStatusCommand(false, exceptionDescription);
                            }

                            send(rsCommand);

                        } else if (lastCommand instanceof LoginCommand) { // клиент отправляет эту команду для входа в систему

                            LoginCommand logCommand = (LoginCommand) lastCommand;
                            user = checkUser(logCommand.getNickname(), logCommand.getPassword()); // проверка корректности данных
                            LoginStatusCommand lsCommand;
                            if (logCommand.getVersion().equals(Config.VERSION_ID)) {
                                if (user != null) { // если все верно и выдало объект User
                                    goOnline(user.getNickname(), connection);
                                    lsCommand = new LoginStatusCommand(user);
                                    lsCommand.setFriendOnline(retainAllFriendOnline(user.getFriendsSet()));

                                    Set<String> unreadMessagesFrom = getSetOfFriendUnreadMes(user.getNickname());//позьзователи с которыми есть непрочитанные смс

                                    if (unreadMessagesFrom != null) {
                                        deleteAndCreateUnreadMesFile(user.getNickname()); // удалить файл с никами выше

                                        lsCommand.setUnreadMessagesFrom(unreadMessagesFrom);

                                        histories = Collections.synchronizedList(new ArrayList<Stack<HistoryPacketCommand>>());
                                        for (String nicknameFriend : unreadMessagesFrom) {
                                            histories.add(loadHistory(user.getNickname(), nicknameFriend)); //загружаем историю с каждым собеседником в наборе
                                        }
                                    }
                                } else {
                                    lsCommand = new LoginStatusCommand();
                                    lsCommand.setExceptionDescription("Wrong login or password!");
                                }

                                send(lsCommand);// отправка логин статуса

                                if (histories != null) {
                                    for (Stack<HistoryPacketCommand> historyPacketCommands : histories) { // проход истори с каждым собеседником

                                        Stack<HistoryPacketCommand> tmp = new Stack<>();//для последующей записи в файл

                                        while (!historyPacketCommands.empty() && historyPacketCommands.peek().isUnreadHas()) {//если содержит пакет с непрочитанными
                                            HistoryPacketCommand hpCommand = historyPacketCommands.pop();
                                            send(hpCommand);

                                            tmp.push(hpCommand); // для записи в файл

                                        }

                                        final Stack<HistoryPacketCommand> toWritting = tmp;
                                        /*EventQueue.invokeLater(new Runnable() {//запись непрочитанных в прочитанные
                                            @Override
                                            public void run() {*/
                                                if (!toWritting.empty()) {
                                                    String nickname_asker = toWritting.peek().getNickname_host();
                                                    String nickname_friend = toWritting.peek().getNickname_companion();

                                                    if (deleteFileWithUnreadMessage(nickname_asker, nickname_friend)) {
                                                        deleteFileWithUnreadMessage(nickname_asker, nickname_friend);
                                                    }

                                                }

                                                while (!toWritting.empty()) {
                                                    HistoryPacketCommand packetCommand = toWritting.pop();

                                                    for (Message message : packetCommand.getHistoryPart()) {
                                                        saveMessageInReceiverHistory(message, packetCommand.getNickname_companion(), packetCommand.getNickname_host(), true);
                                                    }
                                                }

                                            //}
                                        //});
                                    }
                                }

                                /*EventQueue.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {*/
                                        broadcastOnlineFriend(user.getNickname());

                                        LinkedList<Command> missingCommands = loadAwaitingCommands(user.getNickname());
                                        for (Command command : missingCommands) {
                                            send(command);
                                        }
                                    //}
                                //});

                            } else {
                                lsCommand = new LoginStatusCommand();
                                lsCommand.setExceptionDescription("An outdated version of the program. Update please!");
                                send(lsCommand);
                            }


                        } else if (lastCommand instanceof FriendshipRequestCommand) { // клиент запрашивает разрешение на д-г с другим кл-ом

                            FriendshipRequestCommand srCommand = (FriendshipRequestCommand) lastCommand;
                            if (isExist(srCommand.getNickname_From()) && isExist(srCommand.getNickname_To()) &&
                                    isOnline(srCommand.getNickname_From())) {//если есть такой и он в сети

                                if (isOnline(srCommand.getNickname_To())) {
                                    sendTo(srCommand.getNickname_To(), srCommand);
                                } else {
                                    addAwaitingCommand(srCommand.getNickname_To(), srCommand);
                                }

                            } else {
                                AcceptFriendshipCommand acCommand = new AcceptFriendshipCommand();
                                acCommand.setAccept(false);

                                send(acCommand);
                            }

                        } else if (lastCommand instanceof AcceptFriendshipCommand) { //получает принятие/отказ от запрашиваемого кл-та на д-г

                            AcceptFriendshipCommand acCommand = (AcceptFriendshipCommand) lastCommand;
                            if (isExist(acCommand.getNickname_From()) && isExist(acCommand.getNickname_To()) &&
                                    isOnline(acCommand.getNickname_From())) {

                                if (acCommand.isAccept())
                                    addFriend(acCommand.getNickname_To(), acCommand.getNickname_From());//наоборот ОТ и КОМУ, потому что на клиенте меняется сторонами это

                                if (isOnline(acCommand.getNickname_To())) { // отправка ответа тому, кто-то изначально запросил дружбу
                                    sendTo(acCommand.getNickname_To(), acCommand);
                                } else {
                                    addAwaitingCommand(acCommand.getNickname_To(), acCommand);
                                }

                                acCommand.setNickname_From(acCommand.getNickname_To());
                                acCommand.setNickname_To(user.getNickname());

                                if (isOnline(acCommand.getNickname_To())) { // отправка ответа тому, кто только что ее подтвердил
                                    send(acCommand);
                                } else {
                                    addAwaitingCommand(acCommand.getNickname_To(), acCommand);
                                }

                                User u1 = updateUser(acCommand.getNickname_To());
                                User userForTo = new User(u1.getName(), u1.getSurname(), u1.getCountry(), u1.getCity(), u1.getDateOfBirth(), u1.getSex(),
                                        u1.getNickname(), u1.getPassword(), u1.getIpAddress(), u1.getUniqueID(),
                                        (new ArrayList<>(u1.getFriendsList())), u1.getAvatar());

                                User u2 = updateUser(acCommand.getNickname_From());
                                User userForFrom = new User(u2.getName(), u2.getSurname(), u2.getCountry(), u2.getCity(), u2.getDateOfBirth(), u2.getSex(),
                                        u2.getNickname(), u2.getPassword(), u2.getIpAddress(), u2.getUniqueID(),
                                        (new ArrayList<>(u2.getFriendsList())), u2.getAvatar());

                                ChangingUserInfoStatusCommand cuisCommand = new ChangingUserInfoStatusCommand(userForTo);

                                if (isOnline(acCommand.getNickname_To())) {
                                    sendTo(acCommand.getNickname_To(), cuisCommand);
                                } else {
                                    addAwaitingCommand(acCommand.getNickname_To(), cuisCommand);
                                }

                                cuisCommand = new ChangingUserInfoStatusCommand(userForFrom);
                                if (isOnline(acCommand.getNickname_From())) {
                                    sendTo(acCommand.getNickname_From(), cuisCommand);
                                } else {
                                    addAwaitingCommand(acCommand.getNickname_From(), cuisCommand);
                                }
                                /*sendTo(acCommand.getNickname_To(), new ChangingUserInfoStatusCommand(sendedUser_To));
                                sendTo(acCommand.getNickname_From(), new ChangingUserInfoStatusCommand(sendedUser_From));*/
                            }

                        } else if (lastCommand instanceof MessageCommand) { // перенаправляет сообщение

                            MessageCommand mCommand = (MessageCommand) lastCommand;
                            Message message = mCommand.getMessage();
                            message.setDate(new Date());//установка времени, когда пришло сообщение
                            if (isExist(message.getNickname_From()) && isExist(message.getNickname_To()) &&
                                    isOnline(message.getNickname_From())) {

                                saveMessageInSenderHistory(message, message.getNickname_From(), message.getNickname_To());

                                if (isOnline(message.getNickname_To())) {
                                    sendTo(message.getNickname_To(), mCommand);
                                    saveMessageInReceiverHistory(message, message.getNickname_From(), message.getNickname_To(), true);
                                } else {
                                    saveMessageInReceiverHistory(message, message.getNickname_From(), message.getNickname_To(), false);
                                }
                            } // если такого ника не существует

                        } else if (lastCommand instanceof DisconnectCommand) { // выход с сети

                            goOffline(user.getNickname());
                            send(lastCommand);
                            close();

                        } else if (lastCommand instanceof HistoryPacketCommand) {

                            HistoryPacketCommand hpCommand = (HistoryPacketCommand) lastCommand;

                            for (Stack<HistoryPacketCommand> historyPacketCommands : histories) {
                                if (!historyPacketCommands.empty() &&
                                        historyPacketCommands.peek().getNickname_host().equals(user.getNickname()) &&
                                        historyPacketCommands.peek().getNickname_companion().equals(hpCommand.getNickname_companion())) {
                                    int count = 0;
                                    while (!historyPacketCommands.empty()) {
                                        send(historyPacketCommands.pop());
                                        count++;
                                        if (count == 2) {
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }

                        } else if (lastCommand instanceof ChangingUserInfoCommand) {

                            ChangingUserInfoCommand cuiCommand = (ChangingUserInfoCommand) lastCommand;
                            User u = modificationUser(cuiCommand.getChangedUser(), cuiCommand.isInfoChanged(), cuiCommand.isAvatarChanged());

                            User changedUser = new User(u.getName(), u.getSurname(), u.getCountry(), u.getCity(), u.getDateOfBirth(), u.getSex(),
                                    u.getNickname(), u.getPassword(), u.getIpAddress(), u.getUniqueID(),
                                    (new ArrayList<>(u.getFriendsList())), u.getAvatar());

                            ChangingUserInfoStatusCommand cuisCommand = new ChangingUserInfoStatusCommand(changedUser);
                            send(cuisCommand);

                        } else if (lastCommand instanceof SearchCommand) {

                            SearchCommand sCommand = (SearchCommand) lastCommand;
                            List<PotentialFriend> resultList = getResultByGlobalSearch(user, sCommand.getNicknamePattern());

                            SearchStatusCommand ssCommand = new SearchStatusCommand(resultList);
                            send(ssCommand);

                        } else if (lastCommand instanceof FriendshipEndCommand) {

                            FriendshipEndCommand feCommand = (FriendshipEndCommand) lastCommand;

                            boolean deleted = deleteFriend(feCommand.getNicknameHost(), feCommand.getNicknameCompanion());

                            User u1 = updateUser(feCommand.getNicknameHost());
                            User userForHost = new User(u1.getName(), u1.getSurname(), u1.getCountry(), u1.getCity(), u1.getDateOfBirth(), u1.getSex(),
                                    u1.getNickname(), u1.getPassword(), u1.getIpAddress(), u1.getUniqueID(),
                                    (new ArrayList<>(u1.getFriendsList())), u1.getAvatar());

                            User u2 = updateUser(feCommand.getNicknameCompanion());
                            User userForCompanion = new User(u2.getName(), u2.getSurname(), u2.getCountry(), u2.getCity(), u2.getDateOfBirth(), u2.getSex(),
                                    u2.getNickname(), u2.getPassword(), u2.getIpAddress(), u2.getUniqueID(),
                                    (new ArrayList<>(u2.getFriendsList())), u2.getAvatar());

                            String descriptionHost;
                            String descriptionCompanion;

                            if (deleted) {
                                descriptionHost = "You have deleted " + feCommand.getNicknameCompanion() + " from your friend list";
                                descriptionCompanion = "You had been deleted from friend list by " + feCommand.getNicknameHost();
                            } else {
                                descriptionHost = "There was error while removing. Sorry.";
                                descriptionCompanion = "There was error while removing. Sorry.";
                            }

                            FriendshipEndStatusCommand fesCommand = new FriendshipEndStatusCommand(userForHost, descriptionHost);
                            if (isOnline(feCommand.getNicknameHost())) {
                                send(fesCommand);
                            } else {
                                addAwaitingCommand(feCommand.getNicknameHost(), fesCommand);
                            }

                            fesCommand = new FriendshipEndStatusCommand(userForCompanion, descriptionCompanion);
                            if (isOnline(feCommand.getNicknameCompanion())) {
                                sendTo(feCommand.getNicknameCompanion(), fesCommand);
                            } else {
                                addAwaitingCommand(feCommand.getNicknameCompanion(), fesCommand);
                            }
                        }

                    } else close();

                } catch (IOException e) {
                    goOffline(user.getNickname());
                    broadcastOfflineFriend(user.getNickname());
                    close();
                    //TODO
                } catch (ClassNotFoundException e) {
                    //TODO
                }
            }
        }

        private synchronized void send(Command command) { // м-д для отправки комманд между сервером и к-том
            try {
                if (connection.isOpen())
                    this.connection.sendCommand(command);
            } catch (IOException e) {
                System.err.println("Ошибка отправки!");
            }
        }

        private synchronized void sendTo(String nickname, Command command) { // м-д для отправки комманды сервером другому кл-ту
            for (ClientThread clientThread : clients) {
                if (clientThread.getUser().getNickname().equals(nickname)) {
                    clientThread.send(command);
                    break;
                }
            }
        }

        private void broadcastOfflineFriend(String nicknameFriend) {
            for (ClientThread clientThread : clients) {
                if (isOnline(clientThread.user.getNickname()) && clientThread.user != null && clientThread != this) {
                    if (clientThread.user.hasFriend(nicknameFriend)) {
                        clientThread.send(new FriendOfflineCommand(nicknameFriend));
                    }
                }
            }
        }

        private void broadcastOnlineFriend(String nicknameFriend) {
            for (ClientThread clientThread : clients) {
                if (isOnline(clientThread.user.getNickname()) && clientThread.user != null && clientThread != this) {
                    if (clientThread.user.hasFriend(nicknameFriend)) {
                        clientThread.send(new FriendOnlineCommand(nicknameFriend));
                    }
                }
            }
        }

        public synchronized void close() {
            clients.remove(this);
            try {
                connection.close();
            } catch (IOException e) {
                //TODO
            }
        }
    }
}
