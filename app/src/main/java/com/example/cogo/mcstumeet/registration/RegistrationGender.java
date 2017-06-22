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

public class RegistrationGender extends AppCompatActivity {
ImageView imageView_fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_gender);
    }



    public void FRonClick(View view) {


                Configuration config = new Configuration();
            config.locale = Locale.FRENCH;

            Log.e("langue", "changée");

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, RegistrationGender.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

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

    public void singIn(View view) {
        Button female = (Button) findViewById(R.id.signinStuMeet_button);
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
