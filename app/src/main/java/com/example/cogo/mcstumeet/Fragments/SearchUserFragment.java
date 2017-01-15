package com.example.cogo.mcstumeet.fragments;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class SearchUserFragment extends Fragment {
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private ArrayList<DatabaseSchema> userMatch = new ArrayList<DatabaseSchema>();
    private String interestedInBundle, usernameBundle, hobbiesBundle, educationBundle, languageBundle;
    private DatabaseSchema randomUser;
    private GetUserAsyncTask task = new GetUserAsyncTask();
    private Toast toast;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_search, container, false);

        this.usernameBundle = this.getArguments().getString("usernameBundle");
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
        if(!(this.userMatch.isEmpty())){
            this.searchMatch();
        } else {
            this.noMatch();
        }
        refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!(userMatch.isEmpty())){
                    searchMatch();
                } else {
                    noMatch();
                }
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
                if(!(userMatch.isEmpty())){
                    searchMatch();
                } else {
                    noMatch();
                    toast.makeText(getActivity(), "No match!", Toast.LENGTH_LONG).show();
                }
            }
        });

        education.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
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
                if(!(userMatch.isEmpty())){
                    searchMatch();
                } else {
                    noMatch();
                }
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
                                for (int i=0; i<splitLanguages.length; i++) {
                                    System.out.println("lan" + splitLanguages[i]);
                                    System.out.println("user " + db.getLanguages());
                                    if (db.getLanguages().toLowerCase().contains(splitLanguages[i])) {
                                        userMatch.add(db);
                                    }
                                }
                            }
                        }
                    }
                }
                if(!(userMatch.isEmpty())){
                    searchMatch();
                } else {
                    noMatch();
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
                                    }
                                }
                            }
                        }
                    }
                }
                if (!(userMatch.isEmpty())) {
                    searchMatch();
                } else {
                    noMatch();
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

        }
    }

    private void noMatch(){
        toast.makeText(getActivity(), "We are sorry, but there is no match!", Toast.LENGTH_LONG).show();
        TextView usernameNoMatch = (TextView) this.view.findViewById(R.id.search_profile_username);
        TextView educationNoMatch = (TextView) this.view.findViewById(R.id.search_profile_education_text);
        ImageView imageNoMatch = (ImageView) this.view.findViewById(R.id.user_profile_photo);

        imageNoMatch.setImageDrawable(null);
        usernameNoMatch.setText("No match");
        educationNoMatch.setText("-");
    }

    private DatabaseSchema getRandomUser(){
        this.randomUser = new DatabaseSchema();
        int randomNumber = new Random().nextInt(this.userMatch.size());
        randomUser = this.userMatch.get(randomNumber);

        return this.randomUser;
    }
}
