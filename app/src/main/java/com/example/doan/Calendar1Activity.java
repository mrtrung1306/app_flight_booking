package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calendar1Activity extends AppCompatActivity implements View.OnClickListener {
    private Date departureDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar1);

        TextView txtDepartureDay = findViewById(R.id.txtDepartureDay);
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");

        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.btnSuccess).setOnClickListener(this);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(today);
        datePicker.selectDate(today, true);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
//                String selectedDate = DateFormat.getDateInstance().format(date);
                Calendar calSelected= Calendar.getInstance();
                calSelected.setTime(date);
                String selectedDate = " " + calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.get(Calendar.MONTH)+1)
                        + " " + (calSelected.get(Calendar.YEAR));
                Toast.makeText(Calendar1Activity.this,selectedDate,Toast.LENGTH_SHORT).show();
                departureDate = date;
                String d = simpleDate.format(departureDate);
                txtDepartureDay.setText(d);
                if (departureDate != null) {
                    findViewById(R.id.layoutbtn).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.layoutbtn).setVisibility(View.GONE);
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnExit){
            finish();
        }
        if(view.getId()==R.id.btnSuccess){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("departure", departureDate);
            String airportday = getIntent().getStringExtra("airport_day");
            bundle.putString("airport_day", airportday);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}