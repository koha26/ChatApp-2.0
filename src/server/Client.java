package server;

import logic.Constants;
import logic.User;
import logic.command.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by demo on 05.07.16.
 */
public class Client {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private BufferedReader userInput;
    private static int num = 0;
    private ArrayList<User> users = new ArrayList<User>();
    private Command lastCommand;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            userInput = new BufferedReader(new InputStreamReader(System.in));
            new Thread(new Receiver()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            num++;
            User user = new User("koha26", "1111", InetAddress.getLocalHost(), num);
            users.add(user);
            RegistrationCommand rCommand = new RegistrationCommand();
            rCommand.setUser(user);
            objectOutputStream.writeObject(rCommand);
            objectOutputStream.flush();
            NickCommand nCommand = new NickCommand(user.getUniqueID(), Constants.VERSION_ID);
            objectOutputStream.writeObject(nCommand);
            objectOutputStream.flush();

            if (num > 2) {
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
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                System.exit(0);
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client("localhost", 45000).run();
    }

    private class Receiver implements Runnable {

        public void run() {
            while (!socket.isClosed()) {
                Object receiveObject = null;
                try {
                    receiveObject = objectInputStream.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (receiveObject instanceof Command) {
                    lastCommand = (Command) receiveObject;
                } else close();

                if (lastCommand instanceof MessageCommand) {
                    MessageCommand mCommand = (MessageCommand) lastCommand;
                    System.out.println(mCommand.getMessageText());
                }
            }
        }
    }
}
