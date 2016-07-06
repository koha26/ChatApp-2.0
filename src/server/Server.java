package server;

import logic.User;
import logic.command.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by demo on 05.07.16.
 */
public class Server {
    private Map<User,Socket> userSocketMap;
    private int connectionsNumber;
    private ServerSocket serverSocket;
    private ArrayList<ClientThread> clientThreads;
    private Thread mainThread;

    private final static int SERVER_PORT = 35770;
    private static InetAddress INET_ADDRESS;

    public Server(int port){
        try {
            this.userSocketMap = new TreeMap<User, Socket>(new SortedByUniqueID());
            this.connectionsNumber = 0;
            this.serverSocket = new ServerSocket(port);
            INET_ADDRESS = serverSocket.getInetAddress();
            clientThreads = new ArrayList<ClientThread>();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        mainThread = Thread.currentThread();
        while (true){
            try {
                Socket newSocket = serverSocket.accept();
                if (mainThread.isInterrupted()){
                    break;
                }
                else if (newSocket != null){
                    ClientThread clThread = new ClientThread(newSocket);
                    Thread thread = new Thread(clThread);

                    thread.setDaemon(true);
                    thread.setPriority(Thread.NORM_PRIORITY);
                    thread.start();

                    clientThreads.add(clThread);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUser(User newUser, Socket newSocket){
        userSocketMap.put(newUser,newSocket);
        incConnectionNumber();
    }

    public void deleteUser(int uniqueID){
        User comparatedUser = new User();
        comparatedUser.setUniqueID(uniqueID);

        if (userSocketMap.containsKey(comparatedUser)){
            userSocketMap.remove(comparatedUser);
            decConnectionNumber();
        }
    }

    private void incConnectionNumber(){
        this.connectionsNumber++;
    }

    private void decConnectionNumber(){
        this.connectionsNumber--;
    }

    public int getConnectionsNumber() {
        return connectionsNumber;
    }

    public Map<User, Socket> getUserSocketMap() {
        return userSocketMap;
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
Этот класс для выделения определнного потока для каждого
нового клиента.
*/
    private class ClientThread implements Runnable{
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;
        private Command lastCommand;
        private int uniqueID_client;

        public ClientThread(Socket socket){
            try {
                this.socket = socket;
                this.inputStream = new ObjectInputStream(socket.getInputStream());
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() { //очень сырой
            while (!socket.isClosed()){
                try {
                    Object receiveObject = inputStream.readObject();

                    if (receiveObject instanceof Command){
                        lastCommand = (Command) receiveObject;
                    } else close();

                    if (lastCommand instanceof NickCommand){

                        NickCommand nCommand = (NickCommand)lastCommand;
                        uniqueID_client = nCommand.getUniqueID();

                        User u = new User();
                        u.setUniqueID(nCommand.getUniqueID());
                        if (!userSocketMap.containsKey(u)){
                           close();
                        }

                    } else if (lastCommand instanceof SessionRequestCommand){
                        SessionRequestCommand crCommand = (SessionRequestCommand) lastCommand;

                        User u = new User();
                        u.setUniqueID(crCommand.getUniqueID_To());

                        if (userSocketMap.containsKey(u)){
                            send(crCommand.getUniqueID_To(), crCommand);
                        }

                    } else if (lastCommand instanceof MessageCommand){

                        MessageCommand mCommand = (MessageCommand) lastCommand;
                        send(mCommand.getUniqueID_To(), mCommand);

                    } else if (lastCommand instanceof AcceptConnectionCommand){

                        AcceptConnectionCommand acCommand = (AcceptConnectionCommand) lastCommand;
                        send(acCommand.getUniqueID_To(),acCommand);

                    } else if (lastCommand instanceof RegistrationCommand){

                        RegistrationCommand rCommand = (RegistrationCommand) lastCommand;
                        addUser(rCommand.getUser(),socket);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void send(int uniqueID_client, Command сommand){
            for (ClientThread clientThread: clientThreads){
                if (clientThread.uniqueID_client == uniqueID_client){
                    try {
                        clientThread.outputStream.writeObject(сommand);
                        clientThread.outputStream.flush();
                    } catch (IOException e) {
                        close();
                    }
                }
            }
        }

        public synchronized void close(){
            clientThreads.remove(this);
            if (!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
