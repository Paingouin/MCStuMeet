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

public class GetUserAsynTask extends AsyncTask<DatabaseSchema, Void, ArrayList<DatabaseSchema>> {
    static BasicDBObject user = null;
    static String OriginalObject = "";
    static String server_output = null;
    static String temp_output = null;

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

            // create a basic db list
            String mongoarray = "{ artificial_basicdb_list: " + server_output + "}";
            Object o = com.mongodb.util.JSON.parse(mongoarray);

            DBObject dbObj = (DBObject) o;
            BasicDBList contacts = (BasicDBList) dbObj.get("artificial_basicdb_list");

            for (Object obj : contacts) {
                DBObject userObj = (DBObject) obj;

                DatabaseSchema temp = new DatabaseSchema();
                temp.setUsername(userObj.get("username").toString());
                user.add(temp);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return user;
    }
}
