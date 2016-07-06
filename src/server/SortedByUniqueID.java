package server;

import logic.User;

import java.util.Comparator;

/**
 * Created by demo on 05.07.16.
 */
public class SortedByUniqueID implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        if (u1.getUniqueID()<u2.getUniqueID())
            return -1;
        else if (u1.getUniqueID() > u2.getUniqueID())
            return 1;
        else return 0;
    }
}