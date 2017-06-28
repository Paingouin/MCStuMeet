package com.example.cogo.mcstumeet.database_date;

import android.os.AsyncTask;

import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.QueryBuilder;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetRequestAsyncTask extends AsyncTask<DatabaseSchemaDate, Void, ArrayList<DatabaseSchemaDate>> {
    private static String server_output;
    public ArrayList<DatabaseSchemaDate> request = new ArrayList<DatabaseSchemaDate>();

    @Override
    protected ArrayList<DatabaseSchemaDate> doInBackground(DatabaseSchemaDate... arg0) {
        try {
            QueryBuilderDate qb = new QueryBuilderDate();
            URL url = new URL(qb.buildContactsGetURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            server_output = br.readLine();

        } catch (Exception e) {
            e.getMessage();
        }

        String mongoarray = "{list: " + server_output + "}";
        server_output = null;
        System.gc();//force call the gardbadge collector.
        Object o = JSON.parse(mongoarray);
        DBObject dbObj = (DBObject) o;
        BasicDBList requestList = (BasicDBList) dbObj.get("list");

        for(int i=0; i<requestList.size(); i++){
            DBObject requestObj = (DBObject) requestList.get(i);
            DatabaseSchemaDate temp = new DatabaseSchemaDate();

            temp.setDoc_id(requestObj.get("_id").toString());
            temp.setSender(requestObj.get("sender").toString());
            temp.setReceiver(requestObj.get("receiver").toString());
            temp.setTime(requestObj.get("time").toString());
            temp.setLocation(requestObj.get("location").toString());

            this.request.add(temp);
        }

        return this.request;
    }
}
