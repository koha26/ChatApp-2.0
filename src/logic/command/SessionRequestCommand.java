package logic.command;

/**
 * Created by demo on 05.07.16.
 */
public class SessionRequestCommand extends Command {
    private int uniqueID_To;
    private int uniqueID_From;

    public SessionRequestCommand(){
        super();
    }

    public int getUniqueID_From() {
        return uniqueID_From;
    }

    public void setUniqueID_From(int uniqueID_From) {
        this.uniqueID_From = uniqueID_From;
    }

    public int getUniqueID_To() {
        return uniqueID_To;
    }

    public void setUniqueID_To(int uniqueID_To) {
        this.uniqueID_To = uniqueID_To;
    }
}
