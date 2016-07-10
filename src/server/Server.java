package server;

import logic.User;
import logic.command.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by demo on 05.07.16.
 */
public class Server {

    private int connectionsNumber;
    private ServerSocket serverSocket;
    private ArrayList<ClientThread> clientThreads;
    private Thread mainThread;
    private Database database;
    private BlockingQueue<ClientThread> clients;

    private final static int SERVER_PORT = 35770;
    private static InetAddress INET_ADDRESS;

    public Server(int port) {
        try {
            this.connectionsNumber = 0;
            this.serverSocket = new ServerSocket(port);
            INET_ADDRESS = serverSocket.getInetAddress();
            this.clientThreads = new ArrayList<ClientThread>();
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
                Socket newSocket = getNewConn();
                if (mainThread.isInterrupted()) {
                    break;
                } else if (newSocket != null) {
                    final ClientThread clThread = new ClientThread(newSocket);
                    final Thread thread = new Thread(clThread);

                    thread.setDaemon(true);
                    thread.setPriority(Thread.NORM_PRIORITY);
                    thread.start();

                    //clientThreads.add(clThread);
                    clients.add(clThread);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Socket getNewConn() {
        Socket s = null;
        try {
            s = serverSocket.accept();
        } catch (IOException e) {
            // если ошибка в момент приема - "гасим" сервер
        }
        return s;
    }

    public void addUser(User newUser, Socket newSocket) {
        database.getUserSocketMap().put(newUser, newSocket);
        incConnectionNumber();
    }

    /*public void deleteUser(int uniqueID) {
        User comparatedUser = new User();
        comparatedUser.setUniqueID(uniqueID);

        if (userSocketMap.containsKey(comparatedUser)) {
            userSocketMap.remove(comparatedUser);
            decConnectionNumber();
        }
    }*/

    private void incConnectionNumber() {
        this.connectionsNumber++;
    }

    private void decConnectionNumber() {
        this.connectionsNumber--;
    }

    public int getConnectionsNumber() {
        return connectionsNumber;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void main(String[] args) {
        /*User u1 = new User();
        u1.setUniqueID(1);
        User u2 = new User();
        u2.setUniqueID(2);

        Server server = new Server();
        server.addUser(u1, new Socket());
        server.addUser(u2, new Socket());

        server.deleteUser(0);*/

        new Server(45000).run();
    }

    /**
     * Этот класс для выделения определнного потока для каждого
     * нового клиента.
     */
    private class ClientThread implements Runnable {
        private Command lastCommand;
        private int uniqueID_client;
        private Connection connection;

        public ClientThread(Socket socket) throws IOException {
            this.connection = new Connection(socket);
        }

        public Connection getConnection() {
            return connection;
        }

        public int getUniqueID_client() {
            return uniqueID_client;
        }

        @Override
        public void run() { //очень сырой
            while (connection.isOpen()) {
                try {
                    lastCommand = connection.receiveCommand();

                    if (lastCommand != null) {
                        if (lastCommand instanceof RegistrationCommand){

                            RegistrationCommand rCommand = (RegistrationCommand) lastCommand;
                            RegistrationStatusCommand rsCommand;
                            if (database.isNickAvailable(rCommand.getRegModel().getNick())){
                                User registeredUser = database.registerUser(rCommand.getRegModel());
                                 rsCommand = new RegistrationStatusCommand(true, registeredUser);

                            } else
                                rsCommand = new RegistrationStatusCommand(false);
                                connection.sendCommand(rsCommand);

                        } else if (lastCommand instanceof NickCommand) {

                            NickCommand nCommand = (NickCommand) lastCommand;
                            uniqueID_client = nCommand.getUniqueID();

                            User u = new User();
                            u.setUniqueID(nCommand.getUniqueID());
                            /*if (!userSocketMap.containsKey(u)) {
                                close();
                            }*/

                        } else if (lastCommand instanceof SessionRequestCommand) {
                            SessionRequestCommand crCommand = (SessionRequestCommand) lastCommand;

                            User u = new User();
                            u.setUniqueID(crCommand.getUniqueID_To());

                            /*if (userSocketMap.containsKey(u)) {
                                send(crCommand.getUniqueID_To(), crCommand);
                            }*/

                        } else if (lastCommand instanceof MessageCommand) {

                            MessageCommand mCommand = (MessageCommand) lastCommand;
                            send(mCommand.getUniqueID_To(), mCommand);

                        } else if (lastCommand instanceof AcceptConnectionCommand) {

                            AcceptConnectionCommand acCommand = (AcceptConnectionCommand) lastCommand;
                            send(acCommand.getUniqueID_To(), acCommand);

                        }
                    } else close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void send(int uniqueID_client, Command command) {
            for (ClientThread clientThread : clientThreads) {
                if (clientThread.getUniqueID_client() == uniqueID_client) {
                    try {
                        clientThread.getConnection().sendCommand(command);
                    } catch (IOException e) {
                        close();
                    }
                }
            }
        }

        public synchronized void close() {
            clientThreads.remove(this);
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
