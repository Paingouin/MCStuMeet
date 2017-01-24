package com.example.cogo.mcstumeet.date;

import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.GetRequestAsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DateRequestTimer {
    private ArrayList<DatabaseSchemaDate> requestList = new ArrayList<DatabaseSchemaDate>();
    private ArrayList<String> myDates = new ArrayList<String>();
    private int gotRequest = 0;

    public int allRequests(String username){
        GetRequestAsyncTask task = new GetRequestAsyncTask();
        try {
            requestList = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(DatabaseSchemaDate db: requestList){
            if(username.equals(db.getReceiver())){
                if(!(myDates.contains(db.getSender()))){
                    myDates.add(db.getSender());
                    gotRequest++;
                }
            }
        }
        return gotRequest;
    }
}
