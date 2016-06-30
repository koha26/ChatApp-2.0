package logic.command;

/**
 * Created by demo on 12.06.16.
 */
public class MessageCommand extends Command {
    private String messageText;
    public MessageCommand(){
        super();
        this.messageText = "";
    }
    public MessageCommand(String messageText){
        super();
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return "MessageCommand{" +
                "messageText='" + messageText + '\'' +
                '}';
    }
}
