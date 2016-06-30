package logic.network;

import logic.Constants;
import logic.command.*;

import java.io.*;
import java.net.Socket;

/**
 * Created by demo on 12.06.16.
 */
public class Connection {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void close() throws IOException {
        socket.close();
    }

    public boolean isOpen() {
        if (!socket.isConnected()) {
            return false;
        } else {
            if (socket.isClosed()) {
                return false;
            }
        }
        return true;
    }

    public void sendNickCommand(String nick) throws IOException {
        OutputStream os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        NickCommand nickCommand = new NickCommand(nick, Constants.VERSION_ID, false);
        outputStream.writeObject(nickCommand);
        outputStream.flush();
        outputStream.close();
    }

    public void sendNickCommandBusy(String nick) throws IOException {
        OutputStream os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        NickCommand nickCommand = new NickCommand(nick, Constants.VERSION_ID, true);
        outputStream.writeObject(nickCommand);
        outputStream.flush();
        outputStream.close();
    }

    public void sendMessage(String messageText) throws IOException {
        OutputStream os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        MessageCommand messageCommand = new MessageCommand(messageText);
        outputStream.writeObject(messageCommand);
        outputStream.flush();
        outputStream.close();
    }

    public void sendAccept() throws IOException {
        OutputStream os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(new AcceptConectionCommand());
        outputStream.flush();
        outputStream.close();
    }
    public void sendReject() throws IOException {
        OutputStream os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(new RejectCommand());
        outputStream.flush();
        outputStream.close();
    }

    public void sendDisconnect() throws IOException {
        OutputStream os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(new DisconnectCommand());
        outputStream.flush();
        outputStream.close();
    }

    public Command receiveCommand() throws IOException, ClassNotFoundException {
        InputStream is = socket.getInputStream();
        inputStream = new ObjectInputStream(is);
        Object receiveObject = inputStream.readObject();
        if (receiveObject instanceof Command){
            return (Command) receiveObject;
        } else
            return null;
    }
}
