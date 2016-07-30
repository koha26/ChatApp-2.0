package logic.command;

public class FriendOnlineCommand extends Command {
    private String nicknameFriend;

    public FriendOnlineCommand(String nicknameFriend) {
        this.nicknameFriend = nicknameFriend;
    }

    public String getNicknameFriend() {
        return nicknameFriend;
    }

    public void setNicknameFriend(String nicknameFriend) {
        this.nicknameFriend = nicknameFriend;
    }
}
