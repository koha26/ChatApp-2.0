package logic.command;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageCommand extends Command {
    private String messageText;
    private Date date;
    private String nickname_To;
    private String nickname_From;

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

    public String getNickname_To() {
        return nickname_To;
    }

    public void setNickname_To(String nickname_To) {
        this.nickname_To = nickname_To;
    }

    public String getNickname_From() {
        return nickname_From;
    }

    public void setNickname_From(String nickname_From) {
        this.nickname_From = nickname_From;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MessageCommand{" +
                "messageText='" + messageText + '\'' +
                '}';
    }

    public static void main(String[] args) {
        MessageCommand mCommand = new MessageCommand("Hidsfsdfsdfsdfsdfsdfs");
        mCommand.setDate(new Date());


        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("ex.txt"));
            os.writeObject(mCommand);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
