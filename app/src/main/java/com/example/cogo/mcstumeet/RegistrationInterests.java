package com.example.cogo.mcstumeet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class RegistrationInterests extends AppCompatActivity {
    String data_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String data = extras.getString("gender");
        data_gender = data;
        setContentView(R.layout.activity_registration_interests);
    }

    public void passData(View view){
        Button girls = (Button) findViewById(R.id.interests_girl);
        Intent intent = new Intent(this, RegistrationInput.class);
        if(view == girls){
           intent.putExtra("gender", data_gender);
           intent.putExtra("interests", "girls");
        } else {
            intent.putExtra("gender", data_gender);
            intent.putExtra("interests", "guys");
        }
        startActivity(intent);
    }
}
