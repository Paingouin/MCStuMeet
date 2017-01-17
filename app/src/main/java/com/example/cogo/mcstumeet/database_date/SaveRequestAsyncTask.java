package com.example.cogo.mcstumeet.database_date;

import android.os.AsyncTask;

import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.QueryBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Gamze on 17.01.2017.
 */

public class SaveRequestAsyncTask extends AsyncTask<DatabaseSchemaDate, Void, Boolean> {
    @Override
    protected Boolean doInBackground(DatabaseSchemaDate... arg0) {
        try
        {
            DatabaseSchemaDate requestDate = arg0[0];
            QueryBuilderDate qb = new QueryBuilderDate();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(qb.buildContactsSaveURL());

            StringEntity params = new StringEntity(qb.createRequest(requestDate));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            if(response.getStatusLine().getStatusCode()<205)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
            String val = e.getMessage();
            String val2 = val;
            return false;
        }
    }
}
