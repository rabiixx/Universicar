package com.example.universicar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Viaje;
import com.parse.ParseUser;

import java.util.Calendar;

public class CrearViajeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_viaje1);

        ImageButton backBtn1 = findViewById(R.id.backBtnCrearViaje1);
        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrearViajeActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        // Calendario
        final Calendar cal = Calendar.getInstance();
        final CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate(calendarView.getDate());

        /* Guardamos la fecha del viaje */
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
                setContentView(R.layout.activity_crear_viaje2);

                ImageButton backBtn2 = findViewById(R.id.backBtnCrearViaje2);
                backBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_crear_viaje1);
                    }
                });


                final TimePicker timePicker = findViewById(R.id.timePicker1);
                timePicker.setIs24HourView(true);

                findViewById(R.id.submit2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        /* Guardamos la hora del viaje */
                        if (Build.VERSION.SDK_INT >= 23 ) {
                            cal.set(Calendar.HOUR, timePicker.getHour());
                            cal.set(Calendar.MINUTE, timePicker.getMinute());

                        } else{
                            cal.set(Calendar.HOUR, timePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        }

                        setContentView(R.layout.activity_crear_viaje3);

                        ImageButton backBtn3 = findViewById(R.id.backBtnCrearViaje3);
                        backBtn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContentView(R.layout.activity_crear_viaje2);
                            }
                        });

                        //Source Spinner
                        final Spinner srcSpinner = findViewById(R.id.spinner_origen_crear);
                        final Spinner destSpinner = findViewById(R.id.spinner_destino_crear);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CrearViajeActivity.this,
                                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        srcSpinner.setAdapter(adapter);
                        destSpinner.setAdapter(adapter);

                        findViewById(R.id.submit3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                 final EditText price = findViewById(R.id.price);
                                 final EditText nAsientos = findViewById(R.id.nAsientos);

                                if (price.getText().toString().isEmpty() || nAsientos.getText().toString().isEmpty()){
                                    Toast.makeText(CrearViajeActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                                } else if ( srcSpinner.getSelectedItem().toString() == destSpinner.getSelectedItem().toString() ) {
                                    Toast.makeText(CrearViajeActivity.this, "El origen y el destino no pueden ser iguales", Toast.LENGTH_SHORT).show();
                                } else {

                                    Viaje viaje = new Viaje(srcSpinner.getSelectedItem().toString(), destSpinner.getSelectedItem().toString());
                                    viaje.setFecha(cal.getTime());
                                    viaje.setPrecio(Integer.parseInt(price.getText().toString()));
                                    viaje.setNPlazas(Integer.parseInt(nAsientos.getText().toString()));
                                    viaje.setNPlazasDisp(Integer.parseInt(nAsientos.getText().toString()) - 1);
                                    viaje.setConductor(ParseUser.getCurrentUser());
                                    viaje.addPasajero(ParseUser.getCurrentUser());
                                    viaje.saveInBackground();

                                    Toast.makeText(CrearViajeActivity.this, "Viaje creado correctamente!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CrearViajeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}












