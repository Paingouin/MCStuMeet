package com.example.cogo.mcstumeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationInput extends AppCompatActivity {
    Toast toast;
    Encryption encryption = new Encryption();
    String encryptedPwd = "";

    /* we have to check, whether the email is an university email account
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
                    try {
                        this.encryptedPwd = this.encryption.encrypt(pwd);
                        //Insert username and encrypted pwd into db
                        System.out.println("Encrypted pwd: " + this.encryptedPwd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    this.toast.makeText(this, "Email is unvalid! Please use your university email", Toast.LENGTH_SHORT);
                    this.toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    this.toast.show();
                }
            } else {
                this.toast.makeText(this, "No match between your passwords!", Toast.LENGTH_SHORT);
                this.toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                this.toast.show();
            }

        } else {
            this.toast.makeText(this, "Unvalid email adress!", Toast.LENGTH_SHORT);
            this.toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            this.toast.show();
        }

    }
}
