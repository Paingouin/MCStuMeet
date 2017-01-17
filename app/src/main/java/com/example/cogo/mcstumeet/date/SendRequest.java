package com.example.cogo.mcstumeet.date;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.cogo.mcstumeet.R;
import com.example.cogo.mcstumeet.fragments.DatePickerFragment;

import org.w3c.dom.Text;

/**
 * Created by Gamze on 16.01.2017.
 */

public class SendRequest extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_request);
    }

    public void datePicker(View view){
        FragmentManager fragment = this.getFragmentManager();
        DatePickerFragment date = new DatePickerFragment();
        date.show(fragment, "date");
    }

    @TargetApi(25)
    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.text_date)).setText(dateFormat.format(calendar.getTime()));
    }

    @TargetApi(25)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        setDate(calendar);
    }
}
