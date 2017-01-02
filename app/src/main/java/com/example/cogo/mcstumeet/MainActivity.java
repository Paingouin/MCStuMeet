package com.example.cogo.mcstumeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import data.UsersDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //with this command we have the database open now we can use its methods
        UsersDbHelper database = new UsersDbHelper(this);
    }
}