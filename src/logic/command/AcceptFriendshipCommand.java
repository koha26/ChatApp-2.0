package logic.command;

public class AcceptFriendshipCommand extends Command {

    private String nickname_To;
    private String nickname_From;
    private boolean isAccept;
    public AcceptFriendshipCommand(){
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

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }
}
