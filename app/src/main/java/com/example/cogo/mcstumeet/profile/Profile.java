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
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        bottomBar = BottomBar.attach(this, savedInstanceState);
        UsersProfileFragment profile = new UsersProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, profile).commit();

        bottomBar.setItemsFromMenu(R.menu.bottom_bar, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                if(itemId == R.id.profile_bottombar){
                    UsersProfileFragment profile = new UsersProfileFragment();
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
        // Set the color for the active tab
        bottomBar.setActiveTabColor("#1E90FF");
    }
}
