package com.example.cogo.mcstumeet.profile;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.GetRequestAsyncTask;
import com.example.cogo.mcstumeet.database_response.DatabaseSchemaResponse;
import com.example.cogo.mcstumeet.database_response.DeleteResponseAsyncTask;
import com.example.cogo.mcstumeet.database_response.GetResponseAsyncTask;
import com.example.cogo.mcstumeet.fragments.DatesFragment;
import com.example.cogo.mcstumeet.fragments.SearchUserFragment;
import com.example.cogo.mcstumeet.fragments.UsersProfileFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Profile extends AppCompatActivity {
    public BottomBar bottomBar;
    public UsersProfileFragment profile;
    private Toast toast;
    private ArrayList<DatabaseSchemaDate> requestList = new ArrayList<DatabaseSchemaDate>();
    private ArrayList<DatabaseSchemaResponse> requestListResponse = new ArrayList<DatabaseSchemaResponse>();
    private ArrayList<String> myDates = new ArrayList<String>();
    private int gotRequest = 0;
    private Bundle newBundleUserInformation = new Bundle();
    private Handler handler = new Handler();
    private final int delay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        final String username = extras.getString("username");
        final String gender = extras.getString("gender");
        final String education = extras.getString("education");
        final String hobbies = extras.getString("hobbies");
        final String languages = extras.getString("languages");
        final String interested_in = extras.getString("interested_in");

        newBundleUserInformation.putString("usernameBundle", username);
        newBundleUserInformation.putString("genderBundle", gender);
        newBundleUserInformation.putString("educationBundle", education);
        newBundleUserInformation.putString("hobbiesBundle", hobbies);
        newBundleUserInformation.putString("interestedInBundle", interested_in);
        newBundleUserInformation.putString("languagesBundle", languages);

        GetRequestAsyncTask task = new GetRequestAsyncTask();
        try {
            requestList = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(DatabaseSchemaDate db: this.requestList){
            if(username.equals(db.getReceiver())){
                if(!(this.myDates.contains(db.getSender()))){
                    this.myDates.add(db.getSender());
                    this.gotRequest++;
                }
            }
        }
        if(this.gotRequest > 0){
            this.toast.makeText(this, "You got " + this.gotRequest + " date requests!", Toast.LENGTH_SHORT).show();
        }

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
                            myDates.add(db.getSender());
                            toast.makeText(getApplicationContext(), "You got a new date request!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetResponseAsyncTask taskResponse = new GetResponseAsyncTask();
                try {
                    requestListResponse = taskResponse.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                for(DatabaseSchemaResponse db: requestListResponse){
                    if(username.equals(db.getSender())){
                        if(db.getAccepted() == "true"){
                            myDates.remove(db);
                            toast.makeText(getApplicationContext(), db.getReceiver() + " accepted your date request!", Toast.LENGTH_SHORT).show();
                        } else if(db.getAccepted() == "false"){
                            myDates.remove(db);
                            toast.makeText(getApplicationContext(), db.getReceiver() + " declined your date request!", Toast.LENGTH_SHORT).show();
                        }

                        DeleteResponseAsyncTask deleteResponse = new DeleteResponseAsyncTask();
                        deleteResponse.execute(db);
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);

        this.profile = new UsersProfileFragment();
        this.profile.setArguments(newBundleUserInformation);

        this.bottomBar = BottomBar.attach(this, savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, this.profile).commit();

        this.bottomBar.setItemsFromMenu(R.menu.bottom_bar, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                if(itemId == R.id.profile_bottombar){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, profile).commit();
                } else if(itemId == R.id.search_bottombar){
                    SearchUserFragment search = new SearchUserFragment();
                    search.setArguments(newBundleUserInformation);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, search).commit();
                } else if(itemId == R.id.dates_bottombar){
                    DatesFragment dates = new DatesFragment();
                    dates.setArguments(newBundleUserInformation);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, dates).commit();
                }
            }
        });
        bottomBar.setActiveTabColor("#1E90FF");
    }
}
