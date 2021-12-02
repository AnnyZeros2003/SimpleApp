package com.example.simpleandroidapp.model;

public class User {
    private String ID;
    private String Password;
    private String Birthday;
    private String Email;

    public User() {
    }

    public User(String ID, String password, String birthday, String email) {
        this.ID = ID;
        Password = password;
        Birthday = birthday;
        Email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", Password='" + Password + '\'' +
                ", Birthday=" + Birthday +
                ", Email='" + Email + '\'' +
                '}';
    }
}
