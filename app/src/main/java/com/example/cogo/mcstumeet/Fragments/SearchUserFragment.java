package com.example.cogo.mcstumeet.fragments;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.base64.Base;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 22.12.2016.
 */

public class SearchUserFragment extends Fragment{
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private ArrayList<DatabaseSchema> userMatch = new ArrayList<DatabaseSchema>();
    private DatabaseSchema searchUserObject = new DatabaseSchema();
    private DatabaseSchema randomUser;
    private String genderBundle, interestedInBundle, usernameBundle;
    private GetUserAsyncTask task = new GetUserAsyncTask();
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        TextView username = (TextView) view.findViewById(R.id.search_profile_username);
        TextView education = (TextView) view.findViewById(R.id.search_profile_education_text);
        ImageView image = (ImageView) view.findViewById(R.id.user_profile_photo);

        this.genderBundle = this.getArguments().getString("gender");
        this.interestedInBundle = this.getArguments().getString("interested_in");
        try {
            this.returnValues = this.task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String interest = this.interestedInBundle;
        if(interest.equals("girls")){
            interest = "female";
        } else {
            interest = "male";
        }

        for(DatabaseSchema db: this.returnValues){
            if(interest.equals(db.getGender())){
                if(!(db.getUsername().equals(this.usernameBundle))) {
                    this.userMatch.add(db);
                }
            }
        }

        if(this.userMatch.size() >= 0){
            DatabaseSchema tempUser = this.getRandomUser();
            Base base = new Base();

            Bitmap bit = base.convertStringToBitmap(tempUser.getImage());
            image.setImageBitmap(bit);
            String username_random = tempUser.getUsername();
            username.setText(username_random);

            String education_random = tempUser.getEducation();
            education.setText(education_random);
        } else if(this.userMatch.isEmpty()) {
            Button showButton = (Button) view.findViewById(R.id.button_show);
            Button dateButton = (Button) view.findViewById(R.id.button_date);
            ViewGroup layoutShow = (ViewGroup) showButton.getParent();
            ViewGroup layoutDate = (ViewGroup) dateButton.getParent();
            if(layoutShow != null && layoutDate != null){
                layoutShow.removeView(showButton);
                layoutDate.removeView(dateButton);
            }
        }
        return view;
    }

    public DatabaseSchema getRandomUser(){
        this.randomUser = new DatabaseSchema();
        int randomNumber = new Random().nextInt(this.userMatch.size());
        randomUser = this.userMatch.get(randomNumber);

        return this.randomUser;
    }

    public void searchHobbies(){
        Button hobbiesBtn = (Button) getView().findViewById(R.id.hobbies_button);

    }

    public void searchEducation(){
        Button educationBtn = (Button) getView().findViewById(R.id.education_button);

    }

    public void searchLanguage(){
        Button languageBtn = (Button) getView().findViewById(R.id.language_button);

    }
}
