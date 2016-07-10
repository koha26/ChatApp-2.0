package logic.command;

import logic.User;

/**
 * Created by demo on 10.07.16.
 */
public class RegistrationStatusCommand extends Command {
    private boolean isRegistered;
    private User user;

    public RegistrationStatusCommand(boolean isRegistered, User user){
        this.isRegistered = isRegistered;
        this.user = user;
    }

    public RegistrationStatusCommand(boolean isRegistered){
        this.isRegistered = isRegistered;
        if (!isRegistered){
            this.user = null;
        } else {
            this.isRegistered = false;
            this.user = null;
        }
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public User getUser() {
        return user;
    }
}
