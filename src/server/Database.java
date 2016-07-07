package server;

import logic.RegistrationModel;
import logic.User;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Database {

    private Map<Integer, User> userMap;

    public Database() {
        userMap = new HashMap<Integer, User>();
    }

    public void addUser(RegistrationModel regModel) throws UnknownHostException {
        if (isNickAvailable(regModel.getNick())) { // FIXME: 07.07.2016
            int uniqueID = getLastUserId() + 1;
            User newUser = new User(regModel.getNick(), regModel.getPassword(), InetAddress.getLocalHost(), uniqueID);
            userMap.put(uniqueID, newUser);
            System.out.println("User registered: " + newUser.toString());
        }
    }

    public boolean isNickAvailable(String nick) {
        boolean b = true;
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            if (entry.getValue().getNickname().equals(nick))
                b = false;
        }
        return b;
    }

    public int getLastUserId() {
        if (userMap.isEmpty()) {
            return 0;
        } else {
            int lastID = userMap.size();
            return lastID;
        }
    }

    public User getUserById(int uniqueID) {
        return userMap.get(uniqueID);
    }

    public static void main(String[] args) throws UnknownHostException {
        RegistrationModel rm1 = new RegistrationModel("Bob", "morcos");
        RegistrationModel rm2 = new RegistrationModel("koha", "iloveNIGGERS");
        Database db = new Database();
        db.addUser(rm1);
        db.addUser(rm2);
    }
}
