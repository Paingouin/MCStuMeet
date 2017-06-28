package com.example.cogo.mcstumeet.database;

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jordan on 28/06/2017.
 */

public class UpdateUser extends AsyncTask<Object, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Object... params) {
        DatabaseSchema contact = (DatabaseSchema) params[0];

        try {

            QueryBuilder qb = new QueryBuilder();
            URL url = new URL(qb.buildContactsUpdateURL(contact.getDoc_id()));
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter osw = new OutputStreamWriter(
                    connection.getOutputStream());

            osw.write(qb.setUserData(contact));
            osw.flush();
            osw.close();
            if(connection.getResponseCode() <205)
            {

                return true;
            }
            else
            {
                return false;

            }

        } catch (Exception e) {
            e.getMessage();
            return false;

        }

    }

}
