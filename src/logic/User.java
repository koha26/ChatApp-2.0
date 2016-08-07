package logic;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.*;

public class User extends Human {
    private String nickname;
    private String password;
    private InetAddress ipAddress;
    private int uniqueID;
    private ArrayList<Friend> friends;
    private ImageSerializable avatar;

    public User() {
        super();
        this.password = "";
        this.uniqueID = -1;
    }

    public User(String nickname, String password, InetAddress ipAddress, int uniqueID) {
        //String nickname, String country, String city, String dateOfBirth, String name, String surname, Sex sex
        super();
        this.nickname = nickname;
        this.password = password;
        this.ipAddress = ipAddress;
        this.uniqueID = uniqueID;
        this.friends = new ArrayList<>(0);
        this.avatar = null;
    }

    public User(RegistrationModel regModel, InetAddress ipAddress, int uniqueID) {
        super();
        this.nickname = regModel.getNick();
        this.password = regModel.getPassword();
        this.ipAddress = ipAddress;
        this.uniqueID = uniqueID;
        this.friends = new ArrayList<>(0);
        this.avatar = regModel.getAvatar();
        super.setName(regModel.getName());
        super.setSurname(regModel.getSurname());
        super.setDateOfBirth(regModel.getDateOfBirth());
        super.setCity(regModel.getCity());
        super.setCountry(regModel.getCountry());
        super.setSex(regModel.getSex());
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

    public ImageSerializable getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageSerializable avatar) {
        this.avatar = avatar;
    }

    public BufferedImage getAvatarAsBufImage() {
        return avatar.getBufferedImage();
    }

    public void setAvatar(BufferedImage bufImage) {
        avatar = new ImageSerializable(bufImage);
    }

    public Set<String> getFriendsSet() {
        Set<String> resultSet = new HashSet<>();
        for (Friend friend :friends) {
            resultSet.add(friend.getNickname());
        }
        return resultSet;
    }

    public List<Friend> getFriendsList() {
        return friends;
    }

    public List<Friend> getFriendsList(Collection<String> friendNickOnline) {
        List<Friend> resultList = new ArrayList<>();
        for (String friendNickname : friendNickOnline) {
            resultList.add(getFriend(friendNickname));
        }
        return resultList;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public void addFriend(Friend friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public boolean deleteFriend(String nicknameFriend) {
        for (Friend friend : friends) {
            if (friend.getNickname().equals(nicknameFriend)) {
                return friends.remove(friend);
            }
        }
        return false;
    }

    public boolean hasFriend(String nicknameFriend) {
        for (Friend friend : friends) {
            if (friend.getNickname().equals(nicknameFriend)) {
                return true;
            }
        }
        return false;
    }

    public Friend getFriend(String nicknameFriend) {
        for (Friend friend : friends) {
            if (friend.getNickname().equals(nicknameFriend)) {
                return friend;
            }
        }
        return null;
    }

    public Friend toFriendObject() {
        return new Friend(this);
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
