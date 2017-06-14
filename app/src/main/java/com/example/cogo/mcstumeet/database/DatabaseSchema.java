package com.example.cogo.mcstumeet.database;

import java.util.Date;

public class DatabaseSchema {
    //TABLE USER
    public String username;
    public String name;
    public String email;
    public String password;
    public String gender;
    public Date birthday;
    public String description;
    public String image;
    public Date date_registration;
    //TABLE REQUETE
    public int status;
    public Date date_rdv;
    public String location;
    //TABLE HOOBBIES
    public String name_hobbies;
    //TABLE INTEREST
    public String name_interest;
    //TABLE LANGAGES
    public String name_langages;
    //TABLE EDUCATION
    public String location_education;


    // Partie SET => TABLE USER
    public void setUsername(String username) {this.username = username;}
    public void setEmail(String email){
        this.email = email;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setImage(String image){
        this.image = image;
    }
    public void setDate_registration(Date date_registration){this.date_registration = date_registration;}
    // Partie SET => TABLE REQUETE
    public void setStatus (int status){
        this.status = status;
    }
    public void setDate_rdv (Date date_rdv){
        this.date_rdv = date_rdv;
    }
    public void setLocation (String location){
        this.location = location;
    }

    //Partie SET => TABLE HOBBIES
    public void setName_hobbies (String name_hobbies){
        this.name_hobbies = name_hobbies;
    }

    //Partie SET => TABLE INTEREST
    public void setName_interest (String name_interest){
        this.name_interest = name_interest;
    }

    //Partie SET => TABLE LANGAGES
    public void setName_langages (String name_langages){
        this.name_langages = name_langages;
    }

    //Partie SET => TABLE EDUCATION
    public void setLocation_education (String location_education){
        this.location_education = location_education;}



    // Partie GET => TABLE USER
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getGender() {
        return gender;
    }
    public Date getBirthday() {
        return birthday;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public Date getDate_registration() {
        return date_registration;
    }

    // Partie GET => TABLE USER
    public int getStatus() { return status;}
    public Date getDate_rdv() {
        return date_rdv;
    }
    public String getLocation() {
        return location;
    }

    //Partie GET => TABLE HOBBIES
    public String getName_hobbies() {
        return name_hobbies;
    }
    //Partie GET => TABLE INTEREST
    public String getName_interest() {
        return name_interest;
    }
    //Partie GET => TABLE LANGAGES
    public String getName_langages() {
        return name_langages;
    }
    //Partie GET => TABLE EDUCATION
    public String getLocation_education() {
        return location_education;
    }
}
