package com.example.cogo.mcstumeet.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.login.LogIn;

public class RegistrationGender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_gender);
    }

    public void passData(View view){
        Button female = (Button) findViewById(R.id.female_button);
        Intent intent = new Intent(this, RegistrationInterests.class);

        if(view == female){
            intent.putExtra("gender", "female");
        } else {
            intent.putExtra("gender", "male");
        }

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void singIn(View view){
        Button female = (Button) findViewById(R.id.signinStuMeet_button);
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
