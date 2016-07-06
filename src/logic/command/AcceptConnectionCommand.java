package logic.command;

/**
 * Created by demo on 12.06.16.
 */
public class AcceptConnectionCommand  extends Command{
    private int uniqueID_To;
    private int uniqueID_From;
    private boolean isAccept;
    public AcceptConnectionCommand(){
        super();
    }

    public int getUniqueID_To() {
        return uniqueID_To;
    }

    public void setUniqueID_To(int uniqueID_To) {
        this.uniqueID_To = uniqueID_To;
    }

    public int getUniqueID_From() {
        return uniqueID_From;
    }

    public void setUniqueID_From(int uniqueID_From) {
        this.uniqueID_From = uniqueID_From;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }
}
