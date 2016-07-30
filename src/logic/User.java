package logic;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private String nickname;
    private String password;
    private InetAddress ipAddress;
    private int uniqueID;
    private ArrayList<String> friends;
    private String country;
    private String city;
    private String dateOfBirth;
    private String name;
    private String surname;
    private Sex sex;
    private transient BufferedImage avatar;

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
        this.sex = Sex.MALE;
        this.friends = new ArrayList<>(0);
        this.avatar = null;
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
        this.sex = regModel.getSex();
        this.friends = new ArrayList<>(0);
        this.avatar = regModel.getAvatar();
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public BufferedImage getAvatar() {
        return avatar;
    }

    public void setAvatar(BufferedImage avatar) {
        this.avatar = avatar;
    }

    public Set<String> getFriendsSet() {
        Set<String> set = new HashSet<>();
        if (friends!=null)
            set.addAll(friends);
        return set;
    }

    public List<String> getFriendsList() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void addFriend(String nicknameFriend) {
        if (!friends.contains(nicknameFriend)){
            friends.add(nicknameFriend);
        }
    }

    public boolean deleteFriend(String nicknameFriend) {
        return friends.remove(nicknameFriend);
    }

    public boolean hasFriend(String nicknameFriend) {
        return friends.contains(nicknameFriend);
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
