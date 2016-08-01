package logic;

import java.io.Serializable;

public abstract class Human implements Serializable{
    private String country;
    private String city;
    private String dateOfBirth;
    private String name;
    private String surname;
    private Sex sex;

    public Human(){
        this.country = "";
        this.city = "";
        this.dateOfBirth = "";
        this.name = "";
        this.surname = "";
        this.sex = Sex.MALE;
    }

    public Human(String name, String surname, String country, String city, String dateOfBirth, Sex sex) {
        this.country = country;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
