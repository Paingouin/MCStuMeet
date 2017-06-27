package com.example.cogo.mcstumeet.database;

public class DatabaseSchema {

    public String _id;
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

    public String getDoc_id() {
        return _id;
    }
    public void setDoc_id(String doc_id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setInterests(String interests){
        this.interests = interests;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setHobbies(String hobbies){
        this.hobbies = hobbies;
    }

    public void setEducation(String education){
        this.education = education;
    }

    public void setLanguages(String languages){
        this.languages = languages;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getInterests() {
        return interests;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getEducation() {
        return education;
    }

    public String getLanguages() {
        return languages;
    }
}
