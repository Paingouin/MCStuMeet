package com.example.cogo.mcstumeet.date;

import android.os.Handler;
import android.widget.Toast;

import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.GetRequestAsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 19.01.2017.
 */

public class DateRequestTimer {
    private ArrayList<DatabaseSchemaDate> requestList = new ArrayList<DatabaseSchemaDate>();
    private ArrayList<DatabaseSchemaDate> myDates = new ArrayList<DatabaseSchemaDate>();
    private boolean gotRequest;

    public boolean gotDateRequest(final String username){
        this.gotRequest = false;
        final Handler handler = new Handler();
        final int delay = 30000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
                            myDates.add(db);
                            gotRequest = true;
                            System.out.println("hallo");
                        }
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
        this.gotRequest = true;
        return this.gotRequest;
    }
}
