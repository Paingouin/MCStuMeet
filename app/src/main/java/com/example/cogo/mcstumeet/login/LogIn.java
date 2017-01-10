package com.example.cogo.mcstumeet.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;
import com.example.cogo.mcstumeet.profile.Profile;
import com.example.cogo.mcstumeet.security.Encryption;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LogIn extends AppCompatActivity {
    private Toast toast;
    private String passwordDB, gender, hobbies, education, interested_in;
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void passData(View view){
        GetUserAsyncTask task = new GetUserAsyncTask();
        Encryption decryption = new Encryption();
        String username, password;

        Button login = (Button) findViewById(R.id.login_button);
        EditText name = (EditText) findViewById(R.id.login_username);
        EditText pwd = (EditText) findViewById(R.id.login_password);

        username = name.getText().toString();
        password = pwd.getText().toString();

        try {
            this.returnValues = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(DatabaseSchema db: returnValues) {
            if (username.equals(db.getUsername())) {
                try {
                    this.passwordDB = decryption.decrypt(db.getPassword());
                    this.gender = db.getGender();
                    this.education = db.getEducation();
                    this.hobbies = db.getHobbies();
                    this.interested_in = db.getInterests();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (password.equals(this.passwordDB)) {
                    Intent intent = new Intent(this, Profile.class);
                    intent.putExtra("username", username);
                    intent.putExtra("gender", this.gender);
                    intent.putExtra("education", this.education);
                    intent.putExtra("hobbies", this.hobbies);
                    intent.putExtra("interested_in", this.interested_in);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    this.toast.makeText(this, "Password or username is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // ?
    public void forgotPwd(View view){
        Button forgotPwd = (Button)findViewById(R.id.forgot_pwd_button);
    }
}
