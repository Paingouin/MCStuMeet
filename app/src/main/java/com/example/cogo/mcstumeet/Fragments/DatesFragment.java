package com.example.cogo.mcstumeet.fragments;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.GetRequestAsyncTask;
import com.example.cogo.mcstumeet.database_delete.DeleteUserAsyncTask;
import com.example.cogo.mcstumeet.database_response.DatabaseSchemaResponse;
import com.example.cogo.mcstumeet.database_response.SaveResponseAsyncTask;
import com.example.cogo.mcstumeet.date.DateRequest;
import com.example.cogo.mcstumeet.date.DateRequestTimer;
import com.example.cogo.mcstumeet.profile.ShowProfile;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DatesFragment extends Fragment {
    private ArrayList<DatabaseSchemaDate> requestList = new ArrayList<DatabaseSchemaDate>();
    private TextView sender, where, when;
    private String username;
    private ImageButton next, accept, decline;
    private Button showProfile;
    private Toast toast;
    private int index = 0;
    private ArrayList<DatabaseSchemaDate> myDates = new ArrayList<DatabaseSchemaDate>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        this.username = this.getArguments().getString("usernameBundle");
        View view = inflater.inflate(R.layout.activity_dates, container, false);
        this.next = (ImageButton) view.findViewById(R.id.next_icon);
        this.accept = (ImageButton) view.findViewById(R.id.accept_icon);
        this.decline = (ImageButton) view.findViewById(R.id.decline_icon);

        GetRequestAsyncTask task = new GetRequestAsyncTask();
        try {
            this.requestList = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(DatabaseSchemaDate db: this.requestList){
            if(this.username.equals(db.getReceiver())){
                if(!(this.myDates.contains(db.getSender()))){
                    this.myDates.add(db);
                }
            }
        }
        this.sender = (TextView) view.findViewById(R.id.username);
        this.when = (TextView) view.findViewById(R.id.when_date);
        this.where = (TextView) view.findViewById(R.id.where_date);

        if(!(myDates.isEmpty())){
            sender.setText(myDates.get(0).getSender());
            when.setText(myDates.get(0).getTime());
            where.setText(myDates.get(0).getLocation());
        } else {
            sender.setText("No date requests");
            when.setText("-");
            where.setText("-");
        }
        this.gotDateRequest(this.username);

        this.next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for(DatabaseSchemaDate db: myDates){
                    if(sender.getText().toString().equals(db.getSender())){
                        index = myDates.indexOf(db);
                        if(index < myDates.size()) {
                            if ((index + 1) == myDates.size()) {
                                index = 0;
                            } else {
                                index++;
                            }
                        }
                    }
                }
                if(!(myDates.isEmpty())){
                    DatabaseSchemaDate date = new DatabaseSchemaDate();
                    date = myDates.get(index);
                    sender.setText(date.getSender());
                    when.setText(date.getTime());
                    where.setText(date.getLocation());
                }
            }
        });

        this.accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for(DatabaseSchemaDate db: myDates){
                    if(sender.getText().toString().equals(db.getSender())){
                        DatabaseSchemaResponse dbResponse = new DatabaseSchemaResponse();
                        dbResponse.setAccepted("true");
                        System.out.println("accepted: " + dbResponse.getAccepted());
                        dbResponse.setSender(sender.getText().toString());
                        dbResponse.setReceiver(username);
                        SaveResponseAsyncTask responseTask = new SaveResponseAsyncTask();
                        responseTask.execute(dbResponse);

                        DeleteUserAsyncTask task = new DeleteUserAsyncTask();
                        task.execute(db);
                        refresh();
                    }
                }
            }
        });
        this.decline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for(DatabaseSchemaDate db: myDates){
                    if(sender.getText().toString().equals(db.getSender())){
                        DatabaseSchemaResponse dbResponse = new DatabaseSchemaResponse();
                        dbResponse.setAccepted("declined");
                        dbResponse.setSender(sender.getText().toString());
                        dbResponse.setReceiver(username);
                        SaveResponseAsyncTask responeTask = new SaveResponseAsyncTask();
                        responeTask.execute(dbResponse);

                        DeleteUserAsyncTask task = new DeleteUserAsyncTask();
                        task.execute(db);
                        refresh();
                    }
                }
            }
        });
        return view;
    }

    private void refresh(){
        for(DatabaseSchemaDate db: myDates){
            if(sender.getText().toString().equals(db.getSender())){
                index = myDates.indexOf(db);
                if(index < myDates.size()) {
                    if ((index + 1) == myDates.size()) {
                        index = 0;
                    } else {
                        index++;
                    }
                }
            }
        }
        if(!(myDates.isEmpty())){
            DatabaseSchemaDate date = new DatabaseSchemaDate();
            date = myDates.get(index);
            sender.setText(date.getSender());
            when.setText(date.getTime());
            where.setText(date.getLocation());
        } else {
            sender.setText("no date requests");
            when.setText("-");
            where.setText("-");
        }
    }

    private void showProfile(View view){
        this.showProfile = (Button) view.findViewById(R.id.show_profile);
        this.sender = (TextView) view.findViewById(R.id.username);
        String senderUsername = this.sender.getText().toString();

        ShowProfile profile = new ShowProfile();
        Intent intent = new Intent(getActivity(), ShowProfile.class);
        intent.putExtra("usernameBundle", senderUsername);
        startActivity(intent);
    }

    public void gotDateRequest(final String username){
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
                        if(!(myDates.contains(db))){
                            myDates.add(db);
                        }
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
