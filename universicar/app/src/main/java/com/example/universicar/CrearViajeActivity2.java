package com.example.universicar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CrearViajeActivity2  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_viaje2);

        final int year = getIntent().getIntExtra("year", Calendar.getInstance().get(Calendar.YEAR));
        final int month = getIntent().getIntExtra("month", Calendar.getInstance().get(Calendar.MONTH));
        final int day = getIntent().getIntExtra("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        findViewById(R.id.backBtnCrearViaje2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final TimePicker timePicker = findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);

        findViewById(R.id.submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearViajeActivity2.this, CrearViajeActivity3.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);

                if (Build.VERSION.SDK_INT >= 23 ) {
                    intent.putExtra("hour", timePicker.getHour());
                    intent.putExtra("min", timePicker.getMinute());
                } else{
                    intent.putExtra("hour", timePicker.getCurrentHour());
                    intent.putExtra("min", timePicker.getCurrentMinute());
                }



                startActivity(intent);
            }
        });
    }
}