package com.example.cogo.mcstumeet.mail;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;
import com.example.cogo.mcstumeet.database.QueryBuilder;
import com.example.cogo.mcstumeet.login.LogIn;
import com.example.cogo.mcstumeet.profile.Profile;
import com.example.cogo.mcstumeet.security.Encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;


public class ForgetPassword extends AppCompatActivity {

    private Toast toast;
    private Encryption encryption = new Encryption();
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private DatabaseSchema db = new DatabaseSchema();

    private String encryptedPwd, splittedEmail;
    private String universityEmail = "student.reutlingen-university.de";
    private boolean usernameIsInDb = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
    }

    public void sendMail(View view) throws Exception {


        EditText mail = (EditText) findViewById(R.id.forget_email_to_send);
        String email = mail.getText().toString();


        this.usernameIsInDb = false;

        if (!email.isEmpty()) {
            GetUserAsyncTask task = new GetUserAsyncTask();

            this.returnValues = task.execute().get();


            for (DatabaseSchema db : this.returnValues) {
                if ((email.equals(db.getEmail()))) {
                    this.usernameIsInDb = true;
                    this.db = db;
                }
            }
            if (usernameIsInDb == true) {
                if (email.matches("(.*)@(.*)")) {
                    String[] forSplitEmail = email.split("@");
                    for (int i = 0; i < forSplitEmail.length; i++) {
                        splittedEmail = forSplitEmail[1];
                    }

                    String pwd = "test";
                    if (universityEmail.equals(splittedEmail)) {
                        this.encryptedPwd = this.encryption.encrypt(pwd);

                        MongoLabUpdateContact tsk = new MongoLabUpdateContact();
                        tsk.execute(db);

                        AsyncMail sd = new AsyncMail();
                        sd.execute(email);

                        Intent intent = new Intent(this, LogIn.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {
                        this.toast.makeText(this, "Email is unvalid. Please use your university email address!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    this.toast.makeText(this, "The user don't Exist!", Toast.LENGTH_SHORT).show();
                }
            } else {
                this.toast.makeText(this, "Unvalid email address!", Toast.LENGTH_SHORT).show();
            }
        } else {
            this.toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_LONG).show();
        }



    }

    final class MongoLabUpdateContact extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... params) {
            DatabaseSchema contact = (DatabaseSchema) params[0];

            try {

                QueryBuilder qb = new QueryBuilder();
                URL url = new URL(qb.buildContactsUpdateURL(contact.getDoc_id()));
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type",
                        "application/json");
                connection.setRequestProperty("Accept", "application/json");

                OutputStreamWriter osw = new OutputStreamWriter(
                        connection.getOutputStream());

                osw.write(qb.setUserData(contact));
                osw.flush();
                osw.close();
                if(connection.getResponseCode() <205)
                {

                    return true;
                }
                else
                {
                    return false;

                }

            } catch (Exception e) {
                e.getMessage();
                return false;

            }

        }

    }

}

