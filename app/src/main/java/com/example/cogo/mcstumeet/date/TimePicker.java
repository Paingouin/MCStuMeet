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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cogo.mcstumeet.R;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by Gamze on 17.01.2017.
 */

@TargetApi(25)
public class TimePicker extends AppCompatActivity {

    private DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    private Calendar dateTime = Calendar.getInstance();
    private Spinner location = (Spinner) findViewById(R.id.location_spinner);
    private String locationItem;
    private TextView textTimeTextView;
    private Button pickDateButton;
    private Button pickTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_request);

        this.textTimeTextView = (TextView) findViewById(R.id.text_time);
        this.pickDateButton = (Button) findViewById(R.id.pick_date);
        this.pickTimeButton = (Button) findViewById(R.id.pick_time);
        this.locationItem = this.location.getSelectedItem().toString();

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


}
