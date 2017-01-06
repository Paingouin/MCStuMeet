package com.example.cogo.mcstumeet.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database.DatabaseSchema;
import com.example.cogo.mcstumeet.database.GetUserAsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 22.12.2016.
 */

public class UsersProfileFragment extends Fragment {
    private ArrayList<DatabaseSchema> returnValues = new ArrayList<DatabaseSchema>();
    private ArrayList<String> listItems = new ArrayList<String>();
    private TextView text_username;
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String usernameBundle = this.getArguments().getString("usernameBundle");
        View view = inflater.inflate(R.layout.activity_users_profile, container, false);
        GetUserAsyncTask task = new GetUserAsyncTask();
        try {
            returnValues = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        DatabaseSchema db1 = new DatabaseSchema();
        db1.getUsername();

        for(DatabaseSchema db: returnValues){
            listItems.add(db.getUsername());
            System.out.println("db username " + db.getUsername());
        }

        text_username = (TextView) view.findViewById(R.id.user_profile_name);
        username = text_username.getText().toString();
        for(DatabaseSchema x : returnValues) {
            if (usernameBundle.contains(x.getUsername())) {
                text_username.setText(usernameBundle);
            }
        }
        return view;
    }

}
