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
import android.widget.ImageButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gamze on 22.12.2016.
 */

public class SearchUserFragment extends Fragment{
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private ArrayList<DatabaseSchema> userMatch = new ArrayList<DatabaseSchema>();
    private DatabaseSchema searchUserObject = new DatabaseSchema();
    private DatabaseSchema randomUser;
    private String genderBundle, interestedInBundle, usernameBundle, hobbiesBundle, educationBundle, languageBundle;
    private GetUserAsyncTask task = new GetUserAsyncTask();
    private Toast toast;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_search, container, false);

        this.usernameBundle = this.getArguments().getString("usernameBundle");
        this.genderBundle = this.getArguments().getString("genderBundle");
        this.interestedInBundle = this.getArguments().getString("interestedInBundle");
        this.hobbiesBundle = this.getArguments().getString("hobbiesBundle");
        this.educationBundle = this.getArguments().getString("educationBundle");
        this.languageBundle = this.getArguments().getString("languagesBundle");

        ImageButton refresh = (ImageButton) view.findViewById(R.id.imageButton);
        Button language = (Button) view.findViewById(R.id.language_button);
        Button gender = (Button) view.findViewById(R.id.gender_button);
        final Button showButton = (Button) view.findViewById(R.id.button_show);
        final Button dateButton = (Button) view.findViewById(R.id.button_date);
        final Button hobbies = (Button) view.findViewById(R.id.hobbies_button);
        final Button education = (Button) view.findViewById(R.id.education_button);

        try {
            this.returnValues = this.task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        this.interestedInBundle = this.interestedInBundle;
        if(this.interestedInBundle.equals("girls")){
            this.interestedInBundle = "female";
        } else {
            this.interestedInBundle = "male";
        }

        for(DatabaseSchema db: this.returnValues){
            if(this.interestedInBundle.equals(db.getGender())){
                if(!(db.getUsername().equals(this.usernameBundle))) {
                    this.userMatch.add(db);
                }
            }
        }
        this.searchMatch();

        refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchMatch();
            }
        });

        gender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for(DatabaseSchema db: returnValues){
                    if(interestedInBundle.equals(db.getGender())){
                        if(!(db.getUsername().equals(usernameBundle))) {
                            userMatch.add(db);
                        }
                    }
                }
                searchMatch();
            }
        });

        education.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userMatch.clear();
                for(DatabaseSchema db: returnValues){
                    if (interestedInBundle.equals(db.getGender())) {
                        if (educationBundle.equals(db.getEducation())) {
                            if (!(db.getUsername().equals(usernameBundle))) {
                                userMatch.add(db);
                            }
                        }
                    }
                }
                searchMatch();
            }
        });

        language.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                userMatch.clear();
                String splitLanguages[] = languageBundle.split(",");

                for (DatabaseSchema db : returnValues) {
                    if (interestedInBundle.equals(db.getGender())) {
                        if (!(db.getUsername().equals(usernameBundle))) {
                            if (splitLanguages != null) {
                                for (int i=0; i < splitLanguages.length; i++) {
                                    if (db.getLanguages().toLowerCase().contains(splitLanguages[i])) {
                                        userMatch.add(db);
                                        searchMatch();
                                    }
                                }
                            }
                        }
                    }
                }

                for (DatabaseSchema db : userMatch) {
                    System.out.println("alle: " + db.getUsername());
                }
            }
        });

        hobbies.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                userMatch.clear();
                String splitHobbies[] = hobbiesBundle.split(",");

                for (DatabaseSchema db : returnValues) {
                    if (interestedInBundle.equals(db.getGender())) {
                        if (!(db.getUsername().equals(usernameBundle))) {
                            if (splitHobbies != null) {
                                for (int i = 0; i < splitHobbies.length; i++) {
                                    System.out.println("db get hobbies " + db.getHobbies());
                                    if (db.getHobbies().toLowerCase().contains(splitHobbies[i])) {
                                        userMatch.add(db);
                                        searchMatch();
                                    }
                                    if (userMatch.isEmpty()) {
                                       //no match
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        return view;
    }

    private void searchMatch(){
        TextView username = (TextView) this.view.findViewById(R.id.search_profile_username);
        TextView education = (TextView) this.view.findViewById(R.id.search_profile_education_text);
        ImageView image = (ImageView) this.view.findViewById(R.id.user_profile_photo);

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
            Button showButton = (Button) this.view.findViewById(R.id.button_show);
            Button dateButton = (Button) this.view.findViewById(R.id.button_date);
            ViewGroup layoutShow = (ViewGroup) showButton.getParent();
            ViewGroup layoutDate = (ViewGroup) dateButton.getParent();
            if(layoutShow != null && layoutDate != null){
                layoutShow.removeView(showButton);
                layoutDate.removeView(dateButton);
            }
        }
    }

    private DatabaseSchema getRandomUser(){
        this.randomUser = new DatabaseSchema();
        int randomNumber = new Random().nextInt(this.userMatch.size());
        randomUser = this.userMatch.get(randomNumber);

        return this.randomUser;
    }
}
