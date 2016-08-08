package logic.command;

import logic.Friend;
import logic.User;

import java.util.List;
import java.util.Set;

public class LoginStatusCommand extends Command {
    private User user;
    private String exceptionDescription;
    private Set<String> friendNickOnline; //nicknames
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

    public Set<String> getFriendNickOnline() {
        return friendNickOnline;
    }

    public void setFriendOnline(Set<String> friendNickOnline) {
        this.friendNickOnline = friendNickOnline;
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

    public List<Friend> getFriendOnlineList(){
        return user.getFriendsList(friendNickOnline);
    }

    public List<Friend> getUnreadMessageFromList(){
        return user.getFriendsList(unreadMessagesFrom);
    }
}
