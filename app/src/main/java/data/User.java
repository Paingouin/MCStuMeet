package data;

import android.content.ContentValues;

public class User {
    private int id;
    private String name;
    private String birthday;
    private String email;
    private Gender gender;
    private Gender genderInterested;
    private String password;

    public User(int id, String name, String birthday, String email, Gender gender, Gender genderInterested,
                String password) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
        this.genderInterested = genderInterested;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGenderInterested() {
        return genderInterested;
    }

    public void setGenderInterested(Gender genderInterested) {
        this.genderInterested = genderInterested;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.KEY_ID, id);
        values.put(UserContract.UserEntry.KEY_NAME, name);
        values.put(UserContract.UserEntry.KEY_BIRTHDAY, birthday);
        values.put(UserContract.UserEntry.KEY_EMAIL, email);
        values.put(UserContract.UserEntry.KEY_GENDER, gender.toString());
        values.put(UserContract.UserEntry.KEY_GENDERINTERESTEDIN, genderInterested.toString());
        return values;
    }
}