package com.example.cogo.mcstumeet.database_response;

import android.os.AsyncTask;

import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.QueryBuilderDate;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class SaveResponseAsyncTask extends AsyncTask<DatabaseSchemaResponse, Void, Boolean> {
    @Override
    protected Boolean doInBackground(DatabaseSchemaResponse... arg0) {
        try
        {
            DatabaseSchemaResponse responseDate = arg0[0];
            QueryBuilderResponse qb = new QueryBuilderResponse();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(qb.buildContactsSaveURL());

            StringEntity params = new StringEntity(qb.createResponse(responseDate));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            if(response.getStatusLine().getStatusCode() < 205)
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
