package com.example.cogo.mcstumeet;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationInput extends AppCompatActivity {
    Toast toast;
    /*We have to check, whether the username is still available
    & check, whether the email is an university email account
    & encrypt pwd
    & check, whether the birthday is a valid date
    & check, whether the inputs are filled or not -- mit isEmpty()*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_input);
    }

    public void passData(View view){
        EditText username = (EditText) findViewById(R.id.registration_username);
        EditText mail = (EditText) findViewById(R.id.registration_email);
        EditText password = (EditText) findViewById(R.id.registration_password);
        EditText passwordValid = (EditText) findViewById(R.id.registration_passwordValidation);

        //Look after username in db whether the username is taken or not

        String pwd = password.getText().toString();
        String pwdValid = passwordValid.getText().toString();
        String email = mail.getText().toString().toLowerCase();
        System.out.println(email);

        String universityEmail = "student.reutlingen-university.de";
        String splittedEmail = "";

        if(email.matches("(.*)@(.*)")){
            String[] forSplitEmail = email.split("@");
            for (int i=0; i<forSplitEmail.length; i++) {
                System.out.println("splited emails: " + forSplitEmail[i]);
                splittedEmail = forSplitEmail[1];
            }
            if(pwd.equals(pwdValid)){
                if(universityEmail.equals(splittedEmail)){
                    //Insert username and pwd in db
                }
                else {
                    toast.makeText(this, "Email is unvalid! Please use your university email", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                toast.makeText(this, "No match between your passwords!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();
            }

        } else {
            toast.makeText(this, "Unvalid email adress!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
}
