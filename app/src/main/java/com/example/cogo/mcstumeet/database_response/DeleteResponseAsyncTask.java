package com.example.cogo.mcstumeet.database_response;


import android.os.AsyncTask;

import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.QueryBuilderDate;

import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteResponseAsyncTask extends AsyncTask<Object, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Object... params) {
        DatabaseSchemaResponse db = (DatabaseSchemaResponse) params[0];
        try {
            QueryBuilderResponse qb = new QueryBuilderResponse();

            URL url = new URL(qb.buildContactsUpdateURL(db.getDoc_id()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            if(connection.getResponseCode() <205)
            { return true; }
            else { return false; }
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
}
