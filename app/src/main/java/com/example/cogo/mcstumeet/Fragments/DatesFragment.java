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
    private ArrayList<DatabaseSchemaDate> myDatesTemp = new ArrayList<DatabaseSchemaDate>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        this.username = this.getArguments().getString("usernameBundle");
        View view = inflater.inflate(R.layout.activity_dates, container, false);

        this.gotDateRequest(this.username);

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
                        if(!(myDates.contains(db.getSender()))){
                            myDates.add(db);
                            System.out.println("sender " + db.getSender());
                        }
                    }
                }
                sender = (TextView) getView().findViewById(R.id.username);
                when = (TextView) getView().findViewById(R.id.when_date);
                where = (TextView) getView().findViewById(R.id.where_date);

                if(!(myDates.isEmpty())){
                    System.out.println("db " + myDates.get(0).getSender());
                    sender.setText(myDates.get(0).getSender());
                    when.setText(myDates.get(0).getTime());
                    where.setText(myDates.get(0).getLocation());
                } else {
                    System.out.println("hallllloo");
                    //Ist Leer keine requests
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
