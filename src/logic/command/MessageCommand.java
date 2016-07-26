package logic.command;

import logic.Message;

public class MessageCommand extends Command {
    private Message message;

    public MessageCommand(){
        super();
        message = new Message();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static void main(String[] args) {

    }
}
