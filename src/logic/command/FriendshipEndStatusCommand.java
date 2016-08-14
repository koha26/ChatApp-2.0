package logic.command;

import logic.User;

public class FriendshipEndStatusCommand extends Command{
    private User changedUser;
    private String description;

    public FriendshipEndStatusCommand(User changedUser, String description){
        this.changedUser = changedUser;
        this.description = description;
    }

    public User getChangedUser() {
        return changedUser;
    }

    public void setChangedUser(User changedUser) {
        this.changedUser = changedUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
