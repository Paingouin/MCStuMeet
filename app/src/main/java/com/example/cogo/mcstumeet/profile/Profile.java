package com.example.cogo.mcstumeet.profile;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.date.DateRequestTimer;
import com.example.cogo.mcstumeet.fragments.DatesFragment;
import com.example.cogo.mcstumeet.fragments.SearchUserFragment;
import com.example.cogo.mcstumeet.fragments.UsersProfileFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

// bottom bar is created here
public class Profile extends AppCompatActivity {
    public BottomBar bottomBar;
    public UsersProfileFragment profile;
    private Toast toast;

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

        final Bundle newBundleUserInformation = new Bundle();
        newBundleUserInformation.putString("usernameBundle", username);
        newBundleUserInformation.putString("genderBundle", gender);
        newBundleUserInformation.putString("educationBundle", education);
        newBundleUserInformation.putString("hobbiesBundle", hobbies);
        newBundleUserInformation.putString("interestedInBundle", interested_in);
        newBundleUserInformation.putString("languagesBundle", languages);

        DateRequestTimer timer = new DateRequestTimer();
        boolean gotRequest = timer.gotDateRequest(username);
        if(gotRequest){
            toast.makeText(this, "You got a date request!", Toast.LENGTH_LONG).show();
        }

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
