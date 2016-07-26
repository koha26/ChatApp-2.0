package logic.command;

import java.io.Serializable;

public abstract class Command implements Serializable {
    public static void main(String[] args) {
        FriendshipRequestCommand src = new FriendshipRequestCommand();
        System.out.println(src instanceof Command);
        System.out.println(src instanceof FriendshipRequestCommand);

        Command ob = src;

        System.out.println(ob instanceof FriendshipRequestCommand);
        System.out.println(ob instanceof Command);


    }
}
