package logic;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by demo on 12.06.16.
 */
public class User {
    private String  nickname;
    private String password;
    private InetAddress ipAddress;
    private int uniqueID;

    public User()  {
        this.nickname = "";
        this.password = "";
        try {
            this.ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.uniqueID = -1;
    }

    public User(String nickname, String password, InetAddress ipAddress, int uniqueID) {
        this.nickname = nickname;
        this.password = password;
        this.ipAddress = ipAddress;
        this.uniqueID = uniqueID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

}
