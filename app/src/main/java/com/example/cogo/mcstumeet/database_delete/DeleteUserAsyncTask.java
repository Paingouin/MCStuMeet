package com.example.cogo.mcstumeet.database_delete;

import android.os.AsyncTask;

import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.QueryBuilderDate;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteUserAsyncTask extends AsyncTask<Object, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Object... params) {
        DatabaseSchemaDate db = (DatabaseSchemaDate) params[0];
        try {
            QueryBuilderDate qb = new QueryBuilderDate();

           URL url = new URL(qb.buildContactsUpdateURL(db.getDoc_id()));
            System.out.println("id " + db.getDoc_id());
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
