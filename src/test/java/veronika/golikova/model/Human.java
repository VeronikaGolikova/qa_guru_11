package veronika.golikova.model;

import java.util.ArrayList;

public class Human {
    private String name;
    private int age;
    private ArrayList<String> hobby;
    private String birthdayPlace;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getHobby() {
        return hobby;
    }

    public String getBirthdayPlace() {
        return birthdayPlace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHobby(ArrayList<String> hobby) {
        this.hobby = hobby;
    }

    public void setBirthdayPlace(String birthdayPlace) {
        this.birthdayPlace = birthdayPlace;
    }
}
