package server;

import logic.RegistrationModel;
import logic.User;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Database {

    private Map<String, User> userMap; // все пользователи
    private Map<User, Connection> userOnline; // пользователи, которые онлайн

    public Database() {
        this.userMap = new HashMap<String , User>();
        this.userOnline = new TreeMap<User, Connection>(new SortedByUniqueID());
    }

    public void goOnline(User user, Connection connection){
        if (connection.isOpen()){
            this.userOnline.put(user,connection);
        }
    }

    public void goOffline(User user){
        if (!userOnline.get(user).isOpen()){
            this.userOnline.put(user,null);
        }
    }

    public User checkUser(String nickname, String password){
        for (User u: userMap.values()) {
            if (u!= null && u.getNickname().equals(nickname) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }

    public boolean isExist(String nickname){
        return userMap.containsKey(nickname);
    }

    public boolean isOnline(String nickname){
        if (userOnline.get(userMap.get(nickname)) != null){
            return true;
        } else
            return false;
    }

    public void deleteUser(User user){
        this.userMap.remove(user.getNickname());
        this.userOnline.remove(user);
    }

    public User registerUser(RegistrationModel regModel) throws UnknownHostException {
        if (isNickAvailable(regModel.getNick())) { // FIXME by Koha: 10.07.2016
            int uniqueID = getLastUserId() + 1;
            Config.changeID(uniqueID);
            User newUser = new User(regModel.getNick(), regModel.getPassword(), InetAddress.getLocalHost(), uniqueID);//+regModel
            userMap.put(regModel.getNick(), newUser);
            return newUser;
        } else
            return null;
    }

    public boolean isNickAvailable(String nickname) {
        return !userMap.containsKey(nickname);
    }

    public int getLastUserId() {
        /*if (userMap.isEmpty()) {
            return 0;
        } else {
            int lastID = userMap.size();
            return lastID;
        }*/
        return Config.UNIQUE_ID;
    }

    public User getUserById(int uniqueID) {
        for (Map.Entry entry :userMap.entrySet()) {
            if (((User)entry.getValue()).getUniqueID() == uniqueID){
                return (User) entry.getValue();
            }
        }
        return null;
    }

    public ArrayList<User> getUsersOnline() {
        return (ArrayList<User>) userMap.values();
    }

    public static void main(String[] args) throws UnknownHostException {
        RegistrationModel rm1 = new RegistrationModel("Bob", "morcos");
        RegistrationModel rm2 = new RegistrationModel("koha", "iloveNIGGERS");
        Database db = new Database();
        db.registerUser(rm1);
        db.registerUser(rm2);

        //db.changeID(db.getLastUserId());

    }
}
