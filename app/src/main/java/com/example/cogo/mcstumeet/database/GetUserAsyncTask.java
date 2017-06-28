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
    private static String server_output ;
    public ArrayList<DatabaseSchema> user ;


    @Override
    protected void onPreExecute(){
        user = new ArrayList<DatabaseSchema>();
    }

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

            server_output= br.readLine();

        } catch (Exception e) {
            e.getMessage();
        }
            String mongoarray = "{list: " + server_output + "}";
            server_output = null;

            Object o = JSON.parse(mongoarray);
            mongoarray = null;
            DBObject dbObj = (DBObject) o;
            o = null;
            BasicDBList userList = (BasicDBList) dbObj.get("list");
            dbObj =null;

            System.gc(); // Call of  garbadge collector
            for(int i=0; i<userList.size(); i++){
                DBObject userObj = (DBObject) userList.get(i);
                DatabaseSchema temp = new DatabaseSchema();

                temp.setDoc_id(userObj.get("_id").toString());
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
