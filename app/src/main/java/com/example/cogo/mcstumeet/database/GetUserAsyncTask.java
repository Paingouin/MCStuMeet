package com.example.cogo.mcstumeet.database;

import android.os.AsyncTask;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetUserAsyncTask extends AsyncTask<DatabaseSchema, Void, ArrayList<DatabaseSchema>> {
    private static String server_output = null;
    private static String temp_output = null;
    public ArrayList<DatabaseSchema> user = new ArrayList<DatabaseSchema>();

    @Override
    protected ArrayList<DatabaseSchema> doInBackground(DatabaseSchema... arg0) {
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
        } catch (Exception e) {
            e.getMessage();
        }
            String mongoarray = "{list: " + server_output + "}";
            Object o = JSON.parse(mongoarray);
            DBObject dbObj = (DBObject) o;
            BasicDBList userList = (BasicDBList) dbObj.get("list");

            for(int i=0; i<userList.size(); i++){
                DBObject userObj = (DBObject) userList.get(i);
                DatabaseSchema temp = new DatabaseSchema();

                temp.setDoc_id(userObj.get("id").toString());
                temp.setUsername(userObj.get("username").toString());
                temp.setBirthday(userObj.get("birthday").toString());
                temp.setEmail(userObj.get("email").toString());
                temp.setDescription(userObj.get("description").toString());
                temp.setEducation(userObj.get("education").toString());
                temp.setGender(userObj.get("gender").toString());
                temp.setHobbies(userObj.get("hobbies").toString());
                temp.setImage(userObj.get("image").toString());
                temp.setInterests(userObj.get("interests").toString());
                temp.setLanguages(userObj.get("languages").toString());
                temp.setPassword(userObj.get("password").toString());

                this.user.add(temp);
            }
        return this.user;
    }
}
