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
    private ArrayList<DatabaseSchema> randomValues = new ArrayList<DatabaseSchema>();
    private DatabaseSchema searchUserObject = new DatabaseSchema();
    private int[] randomNumbers;
    private String genderBundle, interestedInBundle, usernameBundle;
    private GetUserAsyncTask task = new GetUserAsyncTask();
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        TextView username1 = (TextView) view.findViewById(R.id.search_profile_username);
        TextView username2 = (TextView) view.findViewById(R.id.search_profile_username2);
        TextView username3 = (TextView) view.findViewById(R.id.search_profile_username3);

        TextView education1 = (TextView) view.findViewById(R.id.search_profile_education_text);
        TextView education2 = (TextView) view.findViewById(R.id.search_profile_education_text2);
        TextView education3 = (TextView) view.findViewById(R.id.search_profile_education_text3);

        TextView hobbies1 = (TextView) view.findViewById(R.id.search_profile_hobbies_text);
        TextView hobbies2 = (TextView) view.findViewById(R.id.search_profile_hobbies_text2);
        TextView hobbies3 = (TextView) view.findViewById(R.id.search_profile_hobbies_text3);

        ImageView image1 = (ImageView) view.findViewById(R.id.user_profile_photo);
        ImageView image2 = (ImageView) view.findViewById(R.id.user_profile_photo2);
        ImageView image3 = (ImageView) view.findViewById(R.id.user_profile_photo3);

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
                    this.randomValues.add(db);
                }
            }
        }

        if(this.randomValues.size() >= 2){
            int[] tempArray = this.getRandomNumbers(3);

            Base base = new Base();

            Bitmap bit = base.convertStringToBitmap(this.randomValues.get(tempArray[0]).getImage());
            image1.setImageBitmap(bit);
            bit = base.convertStringToBitmap(this.randomValues.get(tempArray[1]).getImage());
            image2.setImageBitmap(bit);
            bit = base.convertStringToBitmap(this.randomValues.get(tempArray[2]).getImage());
            image3.setImageBitmap(bit);

            String username_random1 = this.randomValues.get(tempArray[0]).getUsername();
            username1.setText(username_random1);
            String username_random2 = this.randomValues.get(tempArray[1]).getUsername();
            username2.setText(username_random2);
            String username_random3 = this.randomValues.get(tempArray[2]).getUsername();
            username3.setText(username_random3);

            String education_random1 = this.randomValues.get(tempArray[0]).getEducation();
            education1.setText(education_random1);
            String education_random2 = this.randomValues.get(tempArray[1]).getEducation();
            education2.setText(education_random2);
            String education_random3 = this.randomValues.get(tempArray[2]).getEducation();
            education3.setText(education_random3);

            String hobbies_random1 = this.randomValues.get(tempArray[0]).getHobbies();
            hobbies1.setText(hobbies_random1);
            String hobbies_random2 = this.randomValues.get(tempArray[1]).getHobbies();
            hobbies2.setText(hobbies_random2);
            String hobbies_random3 = this.randomValues.get(tempArray[2]).getHobbies();
            hobbies3.setText(hobbies_random3);
        }  else if(this.randomValues.size() == 1){
            int[] tempArray = this.getRandomNumbers(1);

            Base base = new Base();

            Bitmap bit = base.convertStringToBitmap(this.randomValues.get(tempArray[0]).getImage());
            image1.setImageBitmap(bit);
            bit = base.convertStringToBitmap(this.randomValues.get(tempArray[1]).getImage());
            image2.setImageBitmap(bit);

            String username_random1 = this.randomValues.get(tempArray[0]).getUsername();
            username1.setText(username_random1);
            String username_random2 = this.randomValues.get(tempArray[1]).getUsername();
            username2.setText(username_random2);

            String education_random1 = this.randomValues.get(tempArray[0]).getEducation();
            education1.setText(education_random1);
            String education_random2 = this.randomValues.get(tempArray[1]).getEducation();
            education2.setText(education_random2);

            String hobbies_random1 = this.randomValues.get(tempArray[0]).getHobbies();
            hobbies1.setText(hobbies_random1);
            String hobbies_random2 = this.randomValues.get(tempArray[1]).getHobbies();
            hobbies2.setText(hobbies_random2);

        } else if(this.randomValues.size() == 0){
            int[] tempArray = this.getRandomNumbers(0);

            Base base = new Base();

            Bitmap bit = base.convertStringToBitmap(this.randomValues.get(tempArray[0]).getImage());
            image1.setImageBitmap(bit);

            String username_random1 = this.randomValues.get(tempArray[0]).getUsername();
            username1.setText(username_random1);

            String education_random1 = this.randomValues.get(tempArray[0]).getEducation();
            education1.setText(education_random1);

            String hobbies_random1 = this.randomValues.get(tempArray[0]).getHobbies();
            hobbies1.setText(hobbies_random1);
        } else {
            System.out.println("Zu wenige Treffer SearchUserFragment");
        }
        return view;
    }

    public int[] getRandomNumbers(int number){
        this.randomNumbers = new int[number];
        for(int i=0; i< randomNumbers.length; i++){
            int randomNumber = new Random().nextInt(this.randomValues.size());
            this.randomNumbers[i] = randomNumber;
        }
        return this.randomNumbers;
    }

    public void searchGender(){
        Button genderBtn = (Button) getView().findViewById(R.id.gender_button);
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

    public void searchUsername(){
        ImageView usernameBtn = (ImageView) getView().findViewById(R.id.imageView);
        EditText username = (EditText) getView().findViewById(R.id.search_user);
        String usernameSearch = username.getText().toString();

        try {
            this.returnValues = this.task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(DatabaseSchema db: this.returnValues){
            if(usernameSearch.equals(db.getUsername())){
                this.searchUserObject = db;
            }
        }

        if(this.searchUserObject != null) {
            TextView username1 = (TextView) getView().findViewById(R.id.search_profile_username);
            TextView education1 = (TextView) getView().findViewById(R.id.search_profile_education_text);
            TextView hobbies1 = (TextView) getView().findViewById(R.id.search_profile_hobbies_text);
            ImageView image1 = (ImageView) getView().findViewById(R.id.user_profile_photo);

            Base base = new Base();

            Bitmap bit = base.convertStringToBitmap(this.searchUserObject.getImage());
            image1.setImageBitmap(bit);
            username1.setText(this.searchUserObject.getUsername());
            education1.setText(this.searchUserObject.getEducation());
            hobbies1.setText(this.searchUserObject.getHobbies());
        } else {
            this.toast.makeText(getContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show();
        }
    }
}
