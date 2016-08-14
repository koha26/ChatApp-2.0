package logic.command;

public class FriendshipEndCommand extends FriendshipCommand {

    private String nicknameHost;
    private String nicknameCompanion;

    public FriendshipEndCommand(String nicknameHost, String nicknameCompanion){
        this.nicknameHost = nicknameHost;
        this.nicknameCompanion = nicknameCompanion;
    }

    public String getNicknameHost() {
        return nicknameHost;
    }

    public void setNicknameHost(String nicknameHost) {
        this.nicknameHost = nicknameHost;
    }

    public String getNicknameCompanion() {
        return nicknameCompanion;
    }

    public void setNicknameCompanion(String nicknameCompanion) {
        this.nicknameCompanion = nicknameCompanion;
    }
}
