package com.example.cogo.mcstumeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void passData(View view){
        Button forgotPwd = (Button)findViewById(R.id.login_button);
        // look in db to check it is a valid username and password
    }

    public void forgotPwd(View view){
        Button forgotPwd = (Button)findViewById(R.id.forgot_pwd_button);
    }
}
