package com.example.cogo.mcstumeet.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.fragments.DatesFragment;
import com.example.cogo.mcstumeet.fragments.SearchUserFragment;
import com.example.cogo.mcstumeet.fragments.UsersProfileFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

// bottom bar is created here
public class Profile extends AppCompatActivity {
    public BottomBar bottomBar;
    public UsersProfileFragment profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        final String username = extras.getString("username");

        Bundle newBundleUsername = new Bundle();
        newBundleUsername.putString("usernameBundle", username);

        this.profile = new UsersProfileFragment();
        this.profile.setArguments(newBundleUsername);

        this.bottomBar = BottomBar.attach(this, savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, this.profile).commit();

        this.bottomBar.setItemsFromMenu(R.menu.bottom_bar, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                if(itemId == R.id.profile_bottombar){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, profile).commit();
                } else if(itemId == R.id.search_bottombar){
                    SearchUserFragment search = new SearchUserFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, search).commit();
                } else if(itemId == R.id.dates_bottombar){
                    DatesFragment dates = new DatesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, dates).commit();
                }
            }
        });
        bottomBar.setActiveTabColor("#1E90FF");
    }
}
