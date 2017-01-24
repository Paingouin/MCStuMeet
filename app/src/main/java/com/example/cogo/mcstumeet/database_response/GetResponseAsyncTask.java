package com.example.cogo.mcstumeet.database_response;

import android.os.AsyncTask;

import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.QueryBuilderDate;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetResponseAsyncTask extends AsyncTask<DatabaseSchemaResponse, Void, ArrayList<DatabaseSchemaResponse>> {
    private static String server_output = null;
    private static String temp_output = null;
    public ArrayList<DatabaseSchemaResponse> request = new ArrayList<DatabaseSchemaResponse>();

    @Override
    protected ArrayList<DatabaseSchemaResponse> doInBackground(DatabaseSchemaResponse... arg0) {
        try {
            QueryBuilderResponse qb = new QueryBuilderResponse();
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
        BasicDBList requestList = (BasicDBList) dbObj.get("list");

        for(int i=0; i<requestList.size(); i++){
            DBObject requestObj = (DBObject) requestList.get(i);
            DatabaseSchemaResponse temp = new DatabaseSchemaResponse();

            temp.setDoc_id(requestObj.get("_id").toString());
            temp.setSender(requestObj.get("sender").toString());
            temp.setReceiver(requestObj.get("receiver").toString());
            temp.setAccepted(requestObj.get("accepted").toString());

            this.request.add(temp);
        }
        return this.request;
    }
}
