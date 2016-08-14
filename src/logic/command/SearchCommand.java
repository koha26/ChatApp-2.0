package logic.command;

public class SearchCommand extends Command{
    private String nicknamePattern;
    //TODO filter

    public SearchCommand(String nicknamePattern){
        this.nicknamePattern = nicknamePattern;
    }

    public String getNicknamePattern() {
        return nicknamePattern;
    }

    public void setNicknamePattern(String nicknamePattern) {
        this.nicknamePattern = nicknamePattern;
    }
}
