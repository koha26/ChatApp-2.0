package logic;

import java.io.Serializable;
import java.net.InetAddress;

import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private String nickname;
    private String password;
    private InetAddress ipAddress;
    private int uniqueID;
    private Set<String> friends;
    private String country;
    private String city;
    private String dateOfBirth;
    private String name;
    private String surname;

    public User() {
        this.nickname = "";
        this.password = "";
        this.uniqueID = -1;
        this.dateOfBirth = "";
        this.city = "";
        this.country = "";
    }

    public User(String nickname, String password, InetAddress ipAddress, int uniqueID) {
        this.nickname = nickname;
        this.password = password;
        this.ipAddress = ipAddress;
        this.uniqueID = uniqueID;
        this.dateOfBirth = "";
        this.city = "";
        this.country = "";
        this.name = "";
        this.surname = "";
    }

    public User(RegistrationModel regModel, InetAddress ipAddress, int uniqueID) {
        this.nickname = regModel.getNick();
        this.password = regModel.getPassword();
        this.ipAddress = ipAddress;
        this.uniqueID = uniqueID;
        this.dateOfBirth = regModel.getDateOfBirth();
        this.city = regModel.getCity();
        this.country = regModel.getCountry();
        this.name = regModel.getName();
        this.surname = regModel.getSurname();
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(Set<String> friends) {
        this.friends = friends;
    }

    public void addFriend(String nickname_friend) {
        if (friends != null && friends.size() > 0) {
            friends.add(nickname_friend);
        } else {
            friends = new HashSet<>();
            friends.add(nickname_friend);
        }
    }

    public boolean deleteFriend(String nickname_friend) {
        return friends.remove(nickname_friend);
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
