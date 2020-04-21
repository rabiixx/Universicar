package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CrearViajeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_viaje1);

        ImageButton backBtn1 = findViewById(R.id.backBtnCrearViaje1);
        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Calendar cal = Calendar.getInstance();
        final CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate(calendarView.getDate());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        });

        findViewById(R.id.submit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearViajeActivity1.this, CrearViajeActivity2.class);
                intent.putExtra("year", cal.get(Calendar.YEAR));
                intent.putExtra("month", cal.get(Calendar.MONTH));
                intent.putExtra("day", cal.get(Calendar.DAY_OF_MONTH));
                startActivity(intent);
            }
        });
    }
}












