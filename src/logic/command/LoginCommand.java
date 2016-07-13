package logic.command;

/**
 * Created by demo on 12.06.16.
 */
public class LoginCommand extends Command {

    private String nickname;
    private String password;
    private String version;

    public LoginCommand(String nickname, String password, String version) {
        this.nickname = nickname;
        this.password = password;
        this.version = version;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginCommand{");
        sb.append("nickname='").append(nickname).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
