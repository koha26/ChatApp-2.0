package logic.command;

import java.io.Serializable;

public abstract class Command implements Serializable {
    public static void main(String[] args) {
        SessionRequestCommand src = new SessionRequestCommand();
        System.out.println(src instanceof Command);
        System.out.println(src instanceof SessionRequestCommand);

        Command ob = src;

        System.out.println(ob instanceof SessionRequestCommand);
        System.out.println(ob instanceof Command);


    }
}
