package logic;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
    private String messageText;
    private Date date;
    private String nickname_To;
    private String nickname_From;
    private boolean isReaded;

    public Message(){
        messageText = "";
        nickname_From = "";
        nickname_To = "";
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean isReaded) {
        this.isReaded = isReaded;
    }
}
