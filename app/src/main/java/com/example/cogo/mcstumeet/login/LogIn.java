package com.example.cogo.mcstumeet.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cogo.mcstumeet.R;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void passData(View view){
        Button login = (Button) findViewById(R.id.login_button);
        EditText username = (EditText) findViewById(R.id.login_username);
        EditText pwd = (EditText) findViewById(R.id.login_password);
        // look in db to check it is a valid username and password
    }

    // ?
    public void forgotPwd(View view){
        Button forgotPwd = (Button)findViewById(R.id.forgot_pwd_button);
    }
}
