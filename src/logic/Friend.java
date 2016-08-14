package logic;

import java.awt.image.BufferedImage;

public class Friend extends Human {
    private String nickname;
    private ImageSerializable avatar;

    public Friend(String nickname, String name, String surname, String country, String city, String dateOfBirth, Sex sex, ImageSerializable avatar) {
        super(name, surname, country, city, dateOfBirth, sex);
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public Friend(String nickname) { // создал конструктор для теста поиска
        super();
        this.nickname = nickname;
    }

    public Friend(String nickname, ImageSerializable avatar) {
        super();
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public Friend(User user) {
        super(user.getName(), user.getSurname(), user.getCountry(), user.getCity(), user.getDateOfBirth(), user.getSex());
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
