package logic;

import gui.ImageSerializable;

import java.io.Serializable;

public class RegistrationModel implements Serializable{

    private String nick;
    private String password;
    private String country;
    private String city;
    private String dateOfBirth;
    private String name;
    private String surname;
    private Sex sex;
    private ImageSerializable avatar;

    public RegistrationModel(String nick, String password) {
        this.nick = nick;
        this.password = password;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ImageSerializable getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageSerializable avatar) {
        this.avatar = avatar;
    }
}
