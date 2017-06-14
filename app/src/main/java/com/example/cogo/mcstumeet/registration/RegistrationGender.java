package com.example.cogo.mcstumeet.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        Button male = (Button) findViewById(R.id.male_button);
        Intent intent = new Intent(this, RegistrationInterests.class);
        Toast toast=null;

        if(view == female){
            intent.putExtra("gender", "F");
        } else if (view == male){
            intent.putExtra("gender", "M");
        } else {
            //if the code is good you should never see this toast...
            toast.makeText(this, "Please indicate your gender... ", Toast.LENGTH_SHORT).show();
        }

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void signIn(View view){
        Button signin = (Button) findViewById(R.id.signinStuMeet_button);
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
