package com.example.cogo.mcstumeet.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.base64.Base;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;
import com.example.cogo.mcstumeet.registration.RegistrationGender;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UsersProfileFragment extends Fragment {
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private ArrayList<String> listItems = new ArrayList<String>();
    private Toast toast;

    private TextView text_username, text_birthday, text_interests, text_education, text_language, text_hobby, text_des;
    private ImageView imageView;
    private ImageButton logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String usernameBundle = this.getArguments().getString("usernameBundle");
        View view = inflater.inflate(R.layout.activity_users_profile, container, false);

        GetUserAsyncTask task = new GetUserAsyncTask();
        try {
            this.returnValues = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        this.text_username = (TextView) view.findViewById(R.id.user_profile_name);
        this.text_birthday = (TextView) view.findViewById(R.id.user_profile_birthday);
        this.text_interests = (TextView) view.findViewById(R.id.user_profile_interestedin_text);
        this.text_education = (TextView) view.findViewById(R.id.user_profile_education_text);
        this.text_language = (TextView) view.findViewById(R.id.user_profile_languages_text) ;
        this.text_hobby = (TextView) view.findViewById(R.id.user_profile_hobbies_text);
        this.text_des = (TextView) view.findViewById(R.id.user_profile_short_description_text);
        this.imageView = (ImageView) view.findViewById(R.id.user_profile_photo);
        this.logout = (ImageButton) view.findViewById(R.id.logout_image);

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
        this.logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), RegistrationGender.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
