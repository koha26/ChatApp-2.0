package logic.command;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Created by demo on 12.06.16.
 */
public class MessageCommand extends Command {
    private String messageText;
    private Date date;
    private int uniqueID_To;
    private int uniqueID_From;

    public MessageCommand(){
        super();
        this.messageText = "";
        this.date = new Date();
    }
    public MessageCommand(String messageText){
        super();
        this.messageText = messageText;
        this.date = new Date();
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getUniqueID_To() {
        return uniqueID_To;
    }

    public void setUniqueID_To(int uniqueID_To) {
        this.uniqueID_To = uniqueID_To;
    }

    public int getUniqueID_From() {
        return uniqueID_From;
    }

    public void setUniqueID_From(int uniqueID_From) {
        this.uniqueID_From = uniqueID_From;
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
        mCommand.setUniqueID_From(123);
        mCommand.setUniqueID_To(12);

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("ex.txt"));
            os.writeObject(mCommand);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
