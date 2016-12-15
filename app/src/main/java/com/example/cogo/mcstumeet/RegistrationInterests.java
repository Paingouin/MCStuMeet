package com.example.cogo.mcstumeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class RegistrationInterests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_interests);
    }

    public void passData(View view){
        Button female = (Button) findViewById(R.id.interests_female);
        Intent intent = new Intent(this, RegistrationInput.class);
        if(view == female){
            intent.putExtra("interests", "female");
        } else {
            intent.putExtra("interests", "male");
        }
        startActivity(intent);
    }
}
