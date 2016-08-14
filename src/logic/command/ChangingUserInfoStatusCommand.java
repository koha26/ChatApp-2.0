package logic.command;

import logic.User;

public class ChangingUserInfoStatusCommand extends Command {
    private User changedUser;

    public ChangingUserInfoStatusCommand(User u){
        this.changedUser = u;
    }

    public User getChangedUser(){
        //return new User(name,surname,country,city,dateOfBirth,sex,nickname,password,ipAddress,uniqueID,friends,avatar);
        return changedUser;
    }
}
