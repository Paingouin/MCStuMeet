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
    private char gender;
    private String passwordDB, hobbies, education, interested_in, language;
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

        boolean usernameCorrect = false, passwordCorrect = false;
        Intent intent = new Intent(this, Profile.class);

        for(DatabaseSchema db: returnValues) {
            if (username.equals(db.getUsername())) {
                usernameCorrect = true;
                try {
                    this.passwordDB = decryption.decrypt(db.getPassword());
                    this.gender = db.getGender();
                    this.education = db.getEducation();
                    this.hobbies = db.getHobbies();
                    this.interested_in = db.getInterests();
                    this.language = db.getLanguages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (password.equals(this.passwordDB)) {
                    passwordCorrect = true;
                    intent.putExtra("username", username);
                    intent.putExtra("gender", this.gender);
                    intent.putExtra("education", this.education);
                    intent.putExtra("hobbies", this.hobbies);
                    intent.putExtra("interested_in", this.interested_in);
                    intent.putExtra("languages", this.language);
                }
            }
        }
        if(usernameCorrect && passwordCorrect){
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            this.toast.makeText(this, "Credentials incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotPwd(View view){

        /*TODO (Lex):
        * when you clic on this button,
        * you have a new screen where you can see a label "enter your E-mail"
        * (IF exist), when the E-mail is verified,
        * the application send you an E-mail with the login and password associated with the E-mail,
        * and toast the application with an hint "you can see your password in your E-mail",
        *
        * (IF not), when the E-mail don't exist,
        * toast on appli "this E-mail doesn't exist"
        *
        * (IF the splitEmail is wrong), when you don't enter the suffixe "students.reutlingen-university.de",
        * toast on appli "use your university E-mail"
        *
        * */
        Button forgotPwd = (Button)findViewById(R.id.forgot_pwd_button);
        this.toast.makeText(this, "We are sorry, there is no function yet!", Toast.LENGTH_SHORT).show();


    }
}
