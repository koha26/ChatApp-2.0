package logic.command;

import logic.User;

public class ChangingUserInfoStatusCommand extends Command {
    private User changedUser;

    public ChangingUserInfoStatusCommand(User user){
        this.changedUser = user;
    }

    public User getChangedUser() {
        return changedUser;
    }

    public void setChangedUser(User changedUser) {
        this.changedUser = changedUser;
    }
}
