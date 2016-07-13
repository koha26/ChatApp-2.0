package logic.command;

import logic.User;

/**
 * Created by demo on 10.07.16.
 */
public class RegistrationStatusCommand extends Command {
    private boolean isRegistered;
    private User user;
    private String exceptionDescription;

    public RegistrationStatusCommand(boolean isRegistered, User user){
        this.isRegistered = isRegistered;
        this.user = user;
    }

    public RegistrationStatusCommand(boolean isRegistered, String mesException){
        this.isRegistered = isRegistered;
        if (!isRegistered){
            this.user = null;
        } else {
            this.isRegistered = false;
            this.user = null;
        }
        this.exceptionDescription = mesException;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public User getUser() {
        return user;
    }
}
