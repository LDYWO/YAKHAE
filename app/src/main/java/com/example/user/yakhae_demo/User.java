package com.example.user.yakhae_demo;

public class User {

    private String UserID;
    private String Gender;
    private String Nickname;
    private String Age;
    private String Type;

    public User(String userID, String gender, String nickname, String age, String type) {
        UserID = userID;
        Gender = gender;
        Nickname = nickname;
        Age = age;
        Type = type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }
}

