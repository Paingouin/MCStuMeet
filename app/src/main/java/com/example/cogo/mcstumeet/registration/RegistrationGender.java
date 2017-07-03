package com.example.cogo.mcstumeet.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.login.LogIn;

import java.util.Locale;
import java.util.ResourceBundle;


/*
* This file is the first file called to see the main screen of the application.
* when we added the "translation part, that part have to be in this file.
* this was the simpliest way to do that.
*
*
* */
public class RegistrationGender extends AppCompatActivity {
ImageView imageView_fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_gender);
    }

    //translation FR
    public void FRonClick(View view) {

        Configuration config = new Configuration();
        config.locale = Locale.FRENCH;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, RegistrationGender.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //translation DE
    public void DEonClick(View view) {

        Configuration config = new Configuration();
        config.locale = Locale.GERMAN;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, RegistrationGender.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //translation EN
    public void ENonClick(View view) {

        Configuration config = new Configuration();
        config.locale = Locale.ENGLISH;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, RegistrationGender.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    // this function is about the first screen, when you can chose your sex if you are not already a user
    // after pushed a button (male or female) you screen go to the next step (RegistrationInterests)
    public void passData(View view) {
        Button female = (Button) findViewById(R.id.female_button);
        Intent intent = new Intent(this, RegistrationInterests.class);

        if (view == female) {
            intent.putExtra("gender", "female");
        } else {
            intent.putExtra("gender", "male");
        }

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    // or you can sign-in if you already have an account.
    // this function redirect you to the login-screen
    public void singIn(View view) {
        Button female = (Button) findViewById(R.id.signinStuMeet_button);
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
