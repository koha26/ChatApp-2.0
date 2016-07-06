package logic.command;

import logic.User;

/**
 * Created by demo on 06.07.16.
 */
public class RegistrationCommand extends Command {
    private User user;
    public RegistrationCommand(){
        super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
