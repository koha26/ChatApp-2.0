package logic;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class User implements Serializable {
    private String nickname;
    private String password;
    private InetAddress ipAddress;
    private int uniqueID;
    private Date dateOfReg;

    public User() {
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

    @Override
    public String toString() {
        String st = "";
        st += "nick = " + nickname + " password = " + password + " IP = " + ipAddress.toString() + " ID = " + uniqueID;
        return st;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (uniqueID != user.uniqueID) return false;
        return true;
    }
}
