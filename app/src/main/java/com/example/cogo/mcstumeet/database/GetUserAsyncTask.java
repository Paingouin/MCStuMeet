package com.example.cogo.mcstumeet.database;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by Gamze on 03.01.2017.
 */

public class GetUserAsyncTask extends AsyncTask<DatabaseSchema, Void, ArrayList<DatabaseSchema>> {
    private static String server_output = null;
    private static String temp_output = null;

    @Override
    protected ArrayList<DatabaseSchema> doInBackground(DatabaseSchema... arg0) {
        ArrayList<DatabaseSchema> user = new ArrayList<DatabaseSchema>();

        try {
            QueryBuilder qb = new QueryBuilder();
            URL url = new URL(qb.buildContactsGetURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            while ((temp_output = br.readLine()) != null) {
                server_output = temp_output;
            }

            String mongoarray = "{ db_list: " + server_output + "}";
            Object o = com.mongodb.util.JSON.parse(mongoarray);
            DBObject dbObj = (DBObject) o;
            BasicDBList userList = (BasicDBList) dbObj.get("db_list");
            System.out.println("userlist: " + userList);

            for (Object obj : userList) {
                DBObject userObj = (DBObject) obj;
                DatabaseSchema temp = new DatabaseSchema();

                temp.setUsername(userObj.get("username").toString());
                temp.setBirthday(userObj.get("birthday").toString());
                temp.setDates(userObj.get("dates").toString());
                temp.setEmail(userObj.get("email").toString());
                temp.setDescription(userObj.get("description").toString());
                temp.setEducation(userObj.get("education").toString());
                temp.setGender(userObj.get("gender").toString());
                temp.setHobbies(userObj.get("hobbies").toString());
                temp.setImage(userObj.get("image").toString());
                temp.setInterests(userObj.get("interests").toString());
                temp.setLanguages(userObj.get("languages").toString());
                temp.setNumberOfDates(userObj.get("email").toString());
                temp.setPassword(userObj.get("password").toString());
                temp.setUploadedImages(userObj.get("uploadedImages").toString());
                user.add(temp);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return user;
    }
}
