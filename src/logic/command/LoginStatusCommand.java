package logic.command;

import logic.User;

import java.util.Set;

public class LoginStatusCommand extends Command {
    private User user;
    private String exceptionDescription;
    private Set<String> friendOnline;
    private Set<String> unreadMessagesFrom;

    public LoginStatusCommand() {
        super();
    }

    public LoginStatusCommand(User user) {
        this.user = user;
    }

    public Set<String> getUnreadMessagesFrom() {
        return unreadMessagesFrom;
    }

    public void setUnreadMessagesFrom(Set<String> unreadMessagesFrom) {
        this.unreadMessagesFrom = unreadMessagesFrom;
    }

    public Set<String> getFriendOnline() {
        return friendOnline;
    }

    public void setFriendOnline(Set<String> friendOnline) {
        this.friendOnline = friendOnline;
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
