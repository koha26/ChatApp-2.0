package logic.command;

import logic.User;

public class LoginStatusCommand extends Command {
    private User user;
    private String exceptionDescription;

    public LoginStatusCommand(){
        super();
    }

    public LoginStatusCommand(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }
}
