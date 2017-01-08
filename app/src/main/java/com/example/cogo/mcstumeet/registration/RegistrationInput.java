package com.example.cogo.mcstumeet.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.SaveAsyncTask;
import com.example.cogo.mcstumeet.security.Encryption;

import java.net.UnknownHostException;

public class RegistrationInput extends AppCompatActivity {
    private Toast toast;
    private Encryption encryption = new Encryption();
    private String data_gender, data_interests, encryptedPwd, splittedEmail;
    private String universityEmail = "student.reutlingen-university.de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_input);

        Bundle extras = getIntent().getExtras();
        data_gender = extras.getString("gender");
        data_interests = extras.getString("interests");
    }

    public void passData(View view) throws Exception {
        Button nextButton = (Button) findViewById(R.id.next_button);
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

        Intent intent = new Intent(this, RegistrationProfile.class);

        if((!(pwd.isEmpty() || pwdValid.isEmpty() || email.isEmpty() || username.isEmpty() || birthday.isEmpty()))
                ||(!(pwd.isEmpty() && pwdValid.isEmpty() && email.isEmpty() && username.isEmpty() && birthday.isEmpty()))){
            if(username.length() > 1) {
                if (password.length() > 3) {
                    if (email.matches("(.*)@(.*)")) {
                        String[] forSplitEmail = email.split("@");
                        for (int i = 0; i < forSplitEmail.length; i++) {
                            splittedEmail = forSplitEmail[1];
                        }
                        if (pwd.equals(pwdValid)) {
                            if (universityEmail.equals(splittedEmail)) {
                                    this.encryptedPwd = this.encryption.encrypt(pwd);
                                    intent.putExtra("gender", this.data_gender);
                                    intent.putExtra("interests", this.data_interests);
                                    intent.putExtra("username", username);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", this.encryptedPwd);
                                    intent.putExtra("birthday", birthday);

                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
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
            } else {
                this.toast.makeText(this, "Your username must contain more than 1 characters!", Toast.LENGTH_LONG).show();
            }
        } else {
            this.toast.makeText(this, "Your password must contain more than 3 characters!", Toast.LENGTH_LONG).show();
        }
    }
}
