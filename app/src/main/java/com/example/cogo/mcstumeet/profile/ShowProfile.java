package com.example.cogo.mcstumeet.profile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.base64.Base;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 03.01.2017.
 */

public class ShowProfile extends AppCompatActivity {
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private ArrayList<String> listItems = new ArrayList<String>();

    private TextView text_username, text_birthday, text_interests, text_education, text_language, text_hobby, text_des;
    private ImageView imageView;
    private String usernameBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        Bundle extras = getIntent().getExtras();
        this.usernameBundle = extras.getString("usernameBundle");
        GetUserAsyncTask task = new GetUserAsyncTask();
        try {
            this.returnValues = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        this.text_username = (TextView) findViewById(R.id.user_profile_name);
        this.text_birthday = (TextView) findViewById(R.id.user_profile_birthday);
        this.text_interests = (TextView) findViewById(R.id.user_profile_interestedin_text);
        this.text_education = (TextView) findViewById(R.id.user_profile_education_text);
        this.text_language = (TextView) findViewById(R.id.user_profile_languages_text) ;
        this.text_hobby = (TextView) findViewById(R.id.user_profile_hobbies_text);
        this.text_des = (TextView) findViewById(R.id.user_profile_short_description_text);
        this.imageView = (ImageView) findViewById(R.id.user_profile_photo);

        for(DatabaseSchema db : returnValues) {
            if (usernameBundle.contains(db.getUsername())) {
                text_username.setText(db.getUsername());
                text_birthday.setText(db.getBirthday());
                text_interests.setText(db.getInterests());
                text_education.setText(db.getEducation());
                text_language.setText(db.getLanguages());
                text_hobby.setText(db.getHobbies());
                text_des.setText(db.getDescription());

                Base base = new Base();
                Bitmap bit = base.convertStringToBitmap(db.getImage());
                imageView.setImageBitmap(bit);
            }
        }
    }
}
