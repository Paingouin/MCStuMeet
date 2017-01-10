package com.example.cogo.mcstumeet.registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.base64.Base;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;
import com.example.cogo.mcstumeet.database.SaveAsyncTask;
import com.example.cogo.mcstumeet.profile.Profile;
import com.example.cogo.mcstumeet.security.Encryption;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 03.01.2017.
 */

public class RegistrationProfile extends AppCompatActivity {
    private Toast toast;
    private Intent intent;

    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private DatabaseSchema db = new DatabaseSchema();
    private final int reqCode = 3;

    private ImageView image;
    private Button image_button;
    private Uri imageURI;
    private Bitmap bit;
    private InputStream inStream;

    private String data_gender, data_interests, data_username, data_password, data_email, data_birthday;
    private String educationItem, hobbies, languages, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_profile);
    }

    public void choosePicture(View view){
        image = (ImageView) findViewById(R.id.user_profile_image);
        image_button = (Button) findViewById(R.id.choose_picture_button);

        this.intent = new Intent(Intent.ACTION_GET_CONTENT);
        this.intent.setType("image/*");
        startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = getIntent().getExtras();
        this.data_gender = extras.getString("gender");
        this.data_interests = extras.getString("interests");
        this.data_username = extras.getString("username");
        this.data_email = extras.getString("email");
        this.data_password = extras.getString("password");
        this.data_birthday = extras.getString("birthday");
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == reqCode){
                this.imageURI = data.getData();
                try {
                    inStream = getContentResolver().openInputStream(this.imageURI);
                    this.bit = BitmapFactory.decodeStream(inStream);
                    image.setImageBitmap(this.bit);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void passData(View view) {
        EditText hobbies_edit = (EditText) findViewById(R.id.registration_hobbies);
        EditText languages_edit = (EditText) findViewById(R.id.registration_languages);
        EditText description_edit = (EditText) findViewById(R.id.registration_description);
        Spinner education = (Spinner) findViewById(R.id.education_spinner);

        this.hobbies = hobbies_edit.getText().toString();
        this.languages = languages_edit.getText().toString();
        this.description = description_edit.getText().toString().toLowerCase();
        this.educationItem = education.getSelectedItem().toString();

        GetUserAsyncTask task = new GetUserAsyncTask();
        try {
            this.returnValues = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if ((!(hobbies.isEmpty() || languages.isEmpty() || description.isEmpty() || educationItem.equals("Choose your education")))
                || (!(hobbies.isEmpty() && languages.isEmpty() && description.isEmpty() && educationItem.equals("Choose your education")))) {
            db.username = this.data_username;
            db.email = this.data_email;
            db.password = this.data_password;
            db.birthday = this.data_birthday;
            db.gender = this.data_gender;
            db.interests = this.data_interests;
            db.hobbies = this.hobbies;
            db.languages = this.languages;
            db.description = this.description;
            db.numberOfDates = "0";
            db.dates = "";
            db.uploadedImages = "";
            if (image.getDrawable() != null) {
                Base base = new Base();
                String imageString = base.convertBitmapToString(bit);
                db.image = imageString;
            } else {
                System.out.println("No image uploaded");
            }
            if (educationItem.equals("Choose your education")) {
                this.toast.makeText(this, "Please choose your education!" +
                        "", Toast.LENGTH_SHORT).show();
            } else {
                db.education = this.educationItem;
                SaveAsyncTask tsk = new SaveAsyncTask();
                tsk.execute(db);
                this.toast.makeText(this, "You have signed in successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Profile.class);
                intent.putExtra("username", this.data_username);
                intent.putExtra("gender", this.data_gender);
                intent.putExtra("education", this.educationItem);
                intent.putExtra("hobbies", this.hobbies);
                intent.putExtra("interested_in", this.data_interests);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }
}
