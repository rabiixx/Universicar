package com.example.universicar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BuscarViajeActivity extends AppCompatActivity {

    TimePickerDialog timePicker;
    final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_viaje);

        final Spinner srcSpinner = findViewById(R.id.origenBuscarViaje);
        final Spinner destSpinner = findViewById(R.id.destinoBuscarViaje);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        srcSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

        final EditText horaEditText = findViewById(R.id.horaBuscarViaje);
        horaEditText.requestFocus();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        horaEditText.setText(currentTime);

        /* Calendario */
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate(calendarView.getDate());

        findViewById(R.id.backBtnBuscarViaje).setOnClickListener(new View.OnClickListener() {
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

                timePicker = new TimePickerDialog(BuscarViajeActivity.this,
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

        Button searchSubmitBtn = findViewById(R.id.searchSubmitBtn);

        searchSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String srcPlace = srcSpinner.getSelectedItem().toString();
                String destPlace = destSpinner.getSelectedItem().toString();

                if (srcPlace == destPlace) {
                    Toast.makeText(BuscarViajeActivity.this, "El origen y desetino no pueden ser iguales", Toast.LENGTH_SHORT).show();
                } else {
                    ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);
                    query.whereEqualTo("origen", srcPlace);
                    query.whereEqualTo("destino", destPlace);
                    query.whereGreaterThanOrEqualTo("fecha", cal.getTime());
                    query.addAscendingOrder("fecha");
                    query.whereGreaterThanOrEqualTo("nPlazasDisp", 1);

                    if ( ParseUser.getCurrentUser().getUsername() != null ){
                        ParseUser user = ParseUser.getCurrentUser();
                        query.whereNotEqualTo("pasajeros", ParseUser.getCurrentUser());
                    }

                    query.findInBackground(new FindCallback<Viaje>() {
                        @Override
                        public void done(List<Viaje> viajeList, ParseException e) {
                        if (e == null) {
                            if (viajeList.isEmpty()) {
                                Intent i = new Intent(BuscarViajeActivity.this, TravelNotFoundActivity.class);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(BuscarViajeActivity.this, ListaViajesActivity.class);
                                i.putExtra("code", 2);
                                i.putExtra("viajes", (Serializable) viajeList);
                                startActivity(i);
                            }
                        } else {
                            Log.i("debug", e.getMessage());
                        }
                        }
                    });
                }
            }
        });
    }
}
