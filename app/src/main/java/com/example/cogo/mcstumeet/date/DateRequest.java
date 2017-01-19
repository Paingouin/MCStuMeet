package com.example.cogo.mcstumeet.date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.database_date.DatabaseSchemaDate;
import com.example.cogo.mcstumeet.database_date.GetRequestAsyncTask;
import com.example.cogo.mcstumeet.database_date.SaveRequestAsyncTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gamze on 17.01.2017.
 */

@TargetApi(25)
public class DateRequest extends AppCompatActivity {
    private DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    private Calendar dateTime = Calendar.getInstance();
    private DatabaseSchemaDate db = new DatabaseSchemaDate();
    private Spinner location;
    private Toast toast;
    private boolean alreadyHasADate = false;
    private String locationItem, receiver, time, sender;
    private TextView textTimeTextView, showUsername;
    private Button pickDateButton, pickTimeButton, sendButton;
    private ArrayList<DatabaseSchemaDate> dateList = new ArrayList<DatabaseSchemaDate>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_request);

        this.textTimeTextView = (TextView) findViewById(R.id.text_time);
        this.showUsername = (TextView) findViewById(R.id.username_match);
        this.pickDateButton = (Button) findViewById(R.id.pick_date);
        this.pickTimeButton = (Button) findViewById(R.id.pick_time);
        this.location = (Spinner) findViewById(R.id.location_spinner);

        Bundle extras = getIntent().getExtras();
        this.receiver = extras.getString("usernameMatch");
        this.sender = extras.getString("usernameBundle");
        this.showUsername.setText("...to " + this.receiver);

        pickDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateDate();
            }
        });

        pickTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateTime();
            }

        });
        updateTextLabel();
    }

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.DAY_OF_MONTH, day);

            updateTextLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hour, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hour);
            dateTime.set(Calendar.MINUTE, minute);

            updateTextLabel();
        }
    };

    private void updateTextLabel(){
        textTimeTextView.setText(formatDateTime.format(dateTime.getTime()));
    }

    public void passData(View view){
        this.sendButton = (Button) findViewById(R.id.send_request_button);
        this.locationItem = this.location.getSelectedItem().toString();
        this.time = this.textTimeTextView.getText().toString();

        GetRequestAsyncTask getData = new GetRequestAsyncTask();
        try {
            this.dateList = getData.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(DatabaseSchemaDate db: this.dateList){
            if(this.sender.equals(db.getSender()) && this.receiver.equals(db.getReceiver())){
                this.alreadyHasADate = true;
            }
        }

        if(this.alreadyHasADate == false) {
            db.setSender(this.sender);
            db.setReceiver(this.receiver);
            db.setTime(this.time);
            db.setLocation(this.locationItem);
            db.setAccepted("false");

            SaveRequestAsyncTask task = new SaveRequestAsyncTask();
            task.execute(db);
        } else {
            this.toast.makeText(this, "You have sent a date to " + this.receiver + "already!", Toast.LENGTH_SHORT).show();
        }
    }

}
