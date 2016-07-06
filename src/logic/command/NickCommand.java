package logic.command;

/**
 * Created by demo on 12.06.16.
 */
public class NickCommand extends Command {

    private int uniqueID;
    private String version;
    private boolean isBusy;

    public NickCommand(int uniqueID, String version, boolean isBusy) {
        this.uniqueID = uniqueID;
        this.version = version;
        this.isBusy = isBusy;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NickCommand{");
        sb.append("uniqueID=").append(uniqueID);
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
