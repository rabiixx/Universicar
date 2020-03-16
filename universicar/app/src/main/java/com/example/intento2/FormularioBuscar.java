package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.intento2.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class FormularioBuscar extends AppCompatActivity {

    TimePickerDialog timePicker;
    final Calendar cal = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_buscar);


        final Spinner srcSpinner = findViewById(R.id.destinoBuscarViaje);

        final Spinner destSpinner = findViewById(R.id.origenBuscarViaje);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        srcSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

        final EditText horaEditText = (EditText)findViewById(R.id.horaBuscarViaje);
        horaEditText.requestFocus();

        // Calendario
        final CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setMinDate(calendarView.getDate());

        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtnBuscarViaje);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /* Guardamos la fecha del viaje */
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        });


        horaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(FormularioBuscar.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                                if (sMinute < 10)
                                    horaEditText.setText(sHour + ":0" + sMinute);
                                else
                                    horaEditText.setText(sHour + ":" + sMinute);

                                cal.set(Calendar.HOUR, sHour);
                                cal.set(Calendar.MINUTE, sMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });


        Button searchSubmitBtn = (Button)findViewById(R.id.searchSubmitBtn);

        searchSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String srcPlace = srcSpinner.getSelectedItem().toString();
                String destPlace = destSpinner.getSelectedItem().toString();

                Toast.makeText(FormularioBuscar.this, cal.getTime().toString(), Toast.LENGTH_SHORT).show();

                // Define the class to query
                ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);

                // Define query conditions
                query.whereEqualTo("origen", "sdfs");

                query.findInBackground(new FindCallback<Viaje>() {
                    @Override
                    public void done(List<Viaje> viajeList, ParseException e) {
                        if (e == null) {
                            if (viajeList.isEmpty()) {
                                Intent i = new Intent(FormularioBuscar.this, TravelNotFoundActivity.class);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(FormularioBuscar.this, ListaViajes.class);
                                i.putExtra("list", (Serializable) viajeList);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(FormularioBuscar.this,e.getMessage() , Toast.LENGTH_SHORT).show();
                            Log.d("Viaje", "Error" + e.getMessage());
                        }
                    }
                });

            }
        });

    }

}
