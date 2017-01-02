package com.example.cogo.mcstumeet.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.security.Encryption;

public class RegistrationInput extends AppCompatActivity {
    private Toast toast;
    protected Encryption encryption = new Encryption();
    protected String encryptedPwd = "";
    protected String universityEmail = "student.reutlingen-university.de";
    protected String splittedEmail = "";

    /* we have to check, whether the email is an university email account
    & check, whether the birthday is a valid date*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_input);
    }

    public void passData(View view){
        EditText name = (EditText) findViewById(R.id.registration_username);
        EditText mail = (EditText) findViewById(R.id.registration_email);
        EditText password = (EditText) findViewById(R.id.registration_password);
        EditText passwordValid = (EditText) findViewById(R.id.registration_passwordValidation);
        EditText bday = (EditText) findViewById(R.id.registration_birthday);

        String username = name.getText().toString();
        String pwd = password.getText().toString();
        String pwdValid = passwordValid.getText().toString();
        String email = mail.getText().toString().toLowerCase();
        String birthday = bday.getText().toString();

        // Checks whether all fields filled out or not -- not working
        if(!(pwd.isEmpty() || pwdValid.isEmpty() || email.isEmpty() || username.isEmpty() || birthday.isEmpty())){
            // Checks whether email matches with the uni email address
            if(email.matches("(.*)@(.*)")){
                String[] forSplitEmail = email.split("@");
                for (int i=0; i<forSplitEmail.length; i++) {
                    System.out.println("splitted emails: " + forSplitEmail[i]);
                    splittedEmail = forSplitEmail[1];
                }
                // Checks whether pwd and "reenter" pwd matches with each other or not
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
                        this.toast.makeText(this, "Email is unvalid. Please use your university email address!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    this.toast.makeText(this, "No match between your passwords!", Toast.LENGTH_SHORT).show();
                }
            } else {
                this.toast.makeText(this, "Unvalid email address!", Toast.LENGTH_SHORT).show();
            }
        } else {
            this.toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        }
    }
}
