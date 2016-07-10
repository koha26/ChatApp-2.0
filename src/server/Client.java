package server;

import logic.User;
import logic.command.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by demo on 05.07.16.
 */
public class Client {
    final BufferedReader userInput;
    private static int num = 0;
    private ArrayList<User> users = new ArrayList<User>();
    private Command lastCommand;
    private User user;
    private Connection connection;

    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        this.connection = new Connection(socket,os,is);

        userInput = new BufferedReader(new InputStreamReader(System.in));
        new Thread(new Receiver()).start();

    }

    public void run() {
        try {
            num++;
            User user = new User("koha26", "1111", InetAddress.getLocalHost(), num);
            users.add(user);
            RegistrationCommand rCommand = new RegistrationCommand("Kostya", "asdas");
            //rCommand.setUser(user);
            connection.sendCommand(rCommand); //для теста

            /*NickCommand nCommand = new NickCommand(user.getUniqueID(), Constants.VERSION_ID);
            objectOutputStream.writeObject(nCommand);
            objectOutputStream.flush();*/

            /*if (num > 2) {
                SessionRequestCommand srCommand = new SessionRequestCommand();
                srCommand.setUniqueID_From(user.getUniqueID());
                srCommand.setUniqueID_To(num - 2);
                objectOutputStream.writeObject(srCommand);
                objectOutputStream.flush();
            }
            while (true) {
                Object receiveObject = objectInputStream.readObject();

                if (receiveObject instanceof Command) {
                    lastCommand = (Command) receiveObject;
                } else close();

                if (lastCommand instanceof AcceptConnectionCommand) {
                    AcceptConnectionCommand acCommand = (AcceptConnectionCommand) lastCommand;
                    if (acCommand.isAccept() && acCommand.getUniqueID_To() == user.getUniqueID()) {
                        break;
                    }
                } else if (lastCommand instanceof SessionRequestCommand) {
                    SessionRequestCommand crCommand = (SessionRequestCommand) lastCommand;
                    AcceptConnectionCommand acCommand = new AcceptConnectionCommand();
                    acCommand.setUniqueID_To(crCommand.getUniqueID_From());
                    acCommand.setUniqueID_From(crCommand.getUniqueID_To());

                    objectOutputStream.writeObject(acCommand);
                    objectOutputStream.flush();
                }
            }


            System.out.println("Type phrase(s):");
            while (true) {
                String userString = null;
                try {
                    userString = userInput.readLine();
                } catch (IOException ignored) {
                }
                if (userString == null || userString.length() == 0 || socket.isClosed()) {
                    close();
                    break;
                } else {
                    try {
                        MessageCommand mCommand = new MessageCommand();
                        mCommand.setUniqueID_From(user.getUniqueID());
                        mCommand.setUniqueID_To(num-2);
                        mCommand.setMessageText(userString);
                        objectOutputStream.writeObject(mCommand);
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        close();
                    }
                }
            }*/
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        if (connection.isOpen()) {
            try {
                connection.close();
                System.exit(0);
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Client("localhost", 45000).run();
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

                    if (lastCommand instanceof RegistrationStatusCommand) {
                        RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) lastCommand;
                        if (rsCommand.isRegistered()) {
                            user = rsCommand.getUser();
                            System.out.println(user);
                        }
                    }
                    if (lastCommand instanceof MessageCommand) {
                        MessageCommand mCommand = (MessageCommand) lastCommand;
                        System.out.println(mCommand.getMessageText());
                    }
                }
            }
        }
    }
}
