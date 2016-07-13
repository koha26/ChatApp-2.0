package logic.command;

/**
 * Created by demo on 05.07.16.
 */
public class SessionRequestCommand extends Command {
    private String nickname_To;
    private String nickname_From;

    public SessionRequestCommand(){
        super();
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
}
