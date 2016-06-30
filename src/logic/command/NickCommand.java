package logic.command;

/**
 * Created by demo on 12.06.16.
 */
public class NickCommand extends Command {
    private String nickname;
    private String version;
    private boolean isBusy;

    public NickCommand(String nickname, String version, boolean isBusy) {
        this.nickname = nickname;
        this.version = version;
        this.isBusy = isBusy;
    }

    public NickCommand(){
        this.nickname = "";
        this.version = "";
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NickCommand{");
        sb.append("nickname='").append(nickname).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", isBusy=").append(isBusy);
        sb.append('}');
        return sb.toString();
    }
}
