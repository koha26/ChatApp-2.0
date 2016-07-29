package logic.command;

public class FriendOfflineCommand extends Command {
    private String nicknameFriend;
    public FriendOfflineCommand(String nicknameFriend){
        this.nicknameFriend = nicknameFriend;
    }

    public String getNicknameFriend() {
        return nicknameFriend;
    }

    public void setNicknameFriend(String nicknameFriend) {
        this.nicknameFriend = nicknameFriend;
    }
}
