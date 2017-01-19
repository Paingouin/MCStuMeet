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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.GetRequestAsyncTask;
import com.example.cogo.mcstumeet.date.DateRequestTimer;
import com.example.cogo.mcstumeet.profile.ShowProfile;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 22.12.2016.
 */

public class DatesFragment extends Fragment {
    private ArrayList<DatabaseSchemaDate> requestList = new ArrayList<DatabaseSchemaDate>();
    private TextView sender, where, when;
    private String username;
    private ImageButton next;
    private Button showProfile;
    private Toast toast;
    private int index;
    private ArrayList<DatabaseSchemaDate> myDates = new ArrayList<DatabaseSchemaDate>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        this.username = this.getArguments().getString("usernameBundle");
        View view = inflater.inflate(R.layout.activity_dates, container, false);

        DateRequestTimer timer = new DateRequestTimer();
        boolean gotRequest = timer.gotDateRequest(this.username);
        if(gotRequest){
            toast.makeText(getActivity(), "You got a date request!", Toast.LENGTH_LONG).show();
        }

        this.sender = (TextView) view.findViewById(R.id.username);
        this.when = (TextView) view.findViewById(R.id.when_date);
        this.where = (TextView) view.findViewById(R.id.where_date);

        if(this.sender.getText().equals("Username")){
        this.sender.setText(this.myDates.get(0).getSender());
        } else {
            for(DatabaseSchemaDate db: this.myDates){
                if(this.sender.equals(db.getSender())){
                    this.index = this.myDates.indexOf(this.sender);
                    if(this.index < this.myDates.size()){
                        this.index++;
                    } else if(this.index == this.myDates.size()){
                        this.index = 0;
                    }

                }
            }
            DatabaseSchemaDate date = new DatabaseSchemaDate();
            date = this.myDates.get(this.index);
            this.sender.setText(date.getSender());
            this.when.setText(date.getTime());
            this.where.setText(date.getLocation());
        }
        return view;
    }

    private void nextDate(View view){
        this.next = (ImageButton) view.findViewById(R.id.next_icon);
        this.sender = (TextView) view.findViewById(R.id.username);

        for(DatabaseSchemaDate db: this.myDates){
            if(this.sender.equals(db.getSender())){
                this.index = this.myDates.indexOf(this.sender);
                if(this.index < this.myDates.size()){
                    this.index++;
                } else if(this.index == this.myDates.size()){
                    this.index = 0;
                }
            }
        }
        DatabaseSchemaDate date = new DatabaseSchemaDate();
        date = this.myDates.get(this.index);
        this.sender.setText(date.getSender());
        this.when.setText(date.getTime());
        this.where.setText(date.getLocation());
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

}
