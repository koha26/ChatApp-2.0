package logic;

public class PotentialFriend extends Friend {

    public PotentialFriend(String nickname, String name, String surname, String country, String city, String dateOfBirth, Sex sex, ImageSerializable avatar) {
        super(nickname, name, surname, country, city, dateOfBirth, sex, avatar);
    }

    public PotentialFriend(User user) {
        super(user);
    }

    public PotentialFriend(String nickname, ImageSerializable avatar) {
        super(nickname, avatar);
    }
}
