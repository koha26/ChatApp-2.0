package logic.command;

import logic.User;

public class ChangingUserInfoCommand extends Command{
    private User changedUser;
    private boolean isInfoChanged;
    private boolean isAvatarChanged;

    public ChangingUserInfoCommand(User changedUser, boolean isInfoChanged, boolean isAvatarChanged) {
        this.changedUser = changedUser;
        this.isAvatarChanged = isAvatarChanged;
        this.isInfoChanged = isInfoChanged;
    }

    public User getChangedUser() {
        return changedUser;
    }

    public void setChangedUser(User changedUser) {
        this.changedUser = changedUser;
    }

    public boolean isInfoChanged() {
        return isInfoChanged;
    }

    public void setInfoChanged(boolean isInfoChanged) {
        this.isInfoChanged = isInfoChanged;
    }

    public boolean isAvatarChanged() {
        return isAvatarChanged;
    }

    public void setAvatarChanged(boolean isAvatarChanged) {
        this.isAvatarChanged = isAvatarChanged;
    }
}
