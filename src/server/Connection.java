package server;

import logic.command.*;

import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public Connection(Socket socket, OutputStream outputStream, InputStream inputStream) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(outputStream);
        this.inputStream = new ObjectInputStream(inputStream);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void close() throws IOException {
        if (!socket.isClosed()){
            socket.close();
        }
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

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
        outputStream.flush();
    }

    public Command receiveCommand() throws IOException, ClassNotFoundException {
        Object receiveObject = inputStream.readObject();
        if (receiveObject instanceof Command){
            return (Command) receiveObject;
        } else
            return null;
    }
}
