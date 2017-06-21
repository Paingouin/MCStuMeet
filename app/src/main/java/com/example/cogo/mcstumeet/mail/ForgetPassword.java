package com.example.cogo.mcstumeet.mail;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;
import com.example.cogo.mcstumeet.profile.Profile;
import com.example.cogo.mcstumeet.security.Encryption;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class ForgetPassword extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
    }

    public void sendMail(View view){
        Log.e("test","yeededazd");
        AsyncMail sd = new AsyncMail();
        sd.execute();

    }

}

