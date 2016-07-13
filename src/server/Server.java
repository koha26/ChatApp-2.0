package server;

import logic.RegistrationModel;
import logic.User;
import logic.command.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Server {

    private ServerSocket serverSocket;
    private Thread mainThread;
    private Database database;
    private BlockingQueue<ClientThread> clients;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.database = new Database();
            clients = new LinkedBlockingDeque<ClientThread>();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
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

    /**Контроллеры для взаимодействия с объектом класса Database*/

    public User registerUser(RegistrationModel regModel) throws UnknownHostException { //для регистрации
        return this.database.registerUser(regModel);
    }

    public void goOnline(User user, Connection connection) { // для хранения онлайн клиентов
        this.database.goOnline(user, connection);
    }

    public void goOffline(User user) {
        this.database.goOffline(user);
    }

    public void deleteUser(User user) {
        this.database.deleteUser(user);
    }

    public User checkUser(String nickname, String password) { // проверка на существования такой связки "ник"-"пароль"
        return database.checkUser(nickname, password);
    }

    public boolean isExist(String nickname){ // существует ли кл-т с таким ником
        return database.isExist(nickname);
    }

    public boolean isOnline(String nickname){ // в онлайне ли кл-т с таким ником
        return database.isOnline(nickname);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void main(String[] args) {
        new Server(45000).run();
    }

    /**
     * Этот класс для выделения определнного потока для каждого
     * нового клиента.
     */

    private class ClientThread implements Runnable {
        private Command lastCommand;  // последняя полученая команда
        private Connection connection;  // соединение между сервером и клиентом
        private User user;  // запись о клиенте

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
                            user = registerUser(rCommand.getRegModel()); //рега через Database
                            if (user != null){ //если рега успешна - отправка полученого объекта User
                                rsCommand = new RegistrationStatusCommand(true, user);
                                goOnline(user,connection);
                            } else { //если не успешна - то отправляем причину
                                String exceptionDescription = "Такой никнейм уже зарегестрирован! Извините, попробуйте другой.";
                                rsCommand = new RegistrationStatusCommand(false, exceptionDescription);
                            }

                            send(rsCommand);

                        } else if (lastCommand instanceof LoginCommand) { // клиент отправляет эту команду для входа в систему

                            LoginCommand logCommand = (LoginCommand) lastCommand;
                            user = checkUser(logCommand.getNickname(), logCommand.getPassword()); // проверка корректности данных
                            LoginStatusCommand lsCommand;
                            if (logCommand.getVersion().equals(Config.VERSION_ID)) {
                                if (user != null) { // если все верно и выдало объект User
                                    goOnline(user, connection);
                                    lsCommand = new LoginStatusCommand(user);
                                } else {
                                    lsCommand = new LoginStatusCommand();
                                    lsCommand.setExceptionDescription("Неверный логин или пароль!");
                                }
                            } else {
                                lsCommand = new LoginStatusCommand();
                                lsCommand.setExceptionDescription("Устаревшая версия программы. Обновите пожалуйста!");
                            }
                            send(lsCommand);

                        } else if (lastCommand instanceof SessionRequestCommand) { // клиент запрашивает разрешение на д-г с другим кл-ом

                            SessionRequestCommand srCommand = (SessionRequestCommand) lastCommand;
                            if (isExist(srCommand.getNickname_From()) && isExist(srCommand.getNickname_To()) &&
                                isOnline(srCommand.getNickname_From()) && isOnline(srCommand.getNickname_To())){//если есть такой и он в сети

                                sendTo(srCommand.getNickname_To(),srCommand);

                            }

                        } else if (lastCommand instanceof AcceptConnectionCommand) { //получает принятие/отказ от запрашиваемого кл-та на д-г

                            AcceptConnectionCommand acCommand = (AcceptConnectionCommand) lastCommand;
                            if (isExist(acCommand.getNickname_From()) && isExist(acCommand.getNickname_To()) &&
                                    isOnline(acCommand.getNickname_From()) && isOnline(acCommand.getNickname_To())){

                                sendTo(acCommand.getNickname_To(),acCommand);
                            }

                        } else if (lastCommand instanceof MessageCommand){ // перенаправляет сообщение

                            MessageCommand mCommand = (MessageCommand) lastCommand;
                            mCommand.setDate(new Date()); //установка времени, когда пришло сообщение
                            if (isExist(mCommand.getNickname_From()) && isExist(mCommand.getNickname_To()) &&
                                    isOnline(mCommand.getNickname_From()) && isOnline(mCommand.getNickname_To())){

                                sendTo(mCommand.getNickname_To(),mCommand);
                            }

                            //TODO история сообщений

                        } else if (lastCommand instanceof DisconnectCommand){ // выход с сети

                            goOffline(user);
                            send(lastCommand);
                            close();

                        }

                    } else close();

                } catch (IOException e) {
                    goOffline(user);
                    close();
                    //TODO
                } catch (ClassNotFoundException e) {
                    //TODO
                }
            }
        }

        public synchronized void send(Command command) { // м-д для отправки комманд между сервером и к-том
            try {
                this.connection.sendCommand(command);
            } catch (IOException e) {
                System.out.println("Ошибка отправки!");
            }
        }

        private synchronized void sendTo(String nickname, Command command) { // м-д для отправки комманды сервером другому кл-ту
            for (ClientThread clientThread : clients) {
                if (clientThread.getUser().getNickname().equals(nickname)){
                    clientThread.send(command);
                    break;
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
