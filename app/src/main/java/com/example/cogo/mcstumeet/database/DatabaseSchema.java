package com.example.cogo.mcstumeet.database;

/**
 * Created by Gamze on 02.01.2017.
 */

public class DatabaseSchema {
    public String username;
    public String email;
    public String password;
    public String gender;
    public String interests;
    public String birthday;
    public String description;
    public String image;
    public String hobbies;
    public String education;
    public String languages;
    public String uploadedImages;
    public int numberOfDates;
    public String dates;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
