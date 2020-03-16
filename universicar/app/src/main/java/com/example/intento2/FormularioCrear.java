package com.example.intento2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intento2.Models.Viaje;
import com.parse.ParseUser;

import java.util.Calendar;

public class FormularioCrear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_crear);

        // Calendario
        final Calendar cal = Calendar.getInstance();
        final CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
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
                setContentView(R.layout.activity_create_travel2);

                final TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker1);
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

                        setContentView(R.layout.activity_create_travel3);

                        //Source Spinner
                        final Spinner srcSpinner = findViewById(R.id.spinner_origen_crear);
                        final Spinner destSpinner = findViewById(R.id.spinner_destino_crear);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(FormularioCrear.this,
                                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        srcSpinner.setAdapter(adapter);
                        destSpinner.setAdapter(adapter);

                        findViewById(R.id.submit3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                 final EditText price = (EditText)findViewById(R.id.price);
                                 final EditText nAsientos = (EditText)findViewById(R.id.nAsientos);

                                if (price.getText().toString().isEmpty() || nAsientos.getText().toString().isEmpty()){
                                    Toast.makeText(FormularioCrear.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                                } else {

                                    Viaje viaje = new Viaje(srcSpinner.getSelectedItem().toString(), destSpinner.getSelectedItem().toString());
                                    viaje.setFecha(cal.getTime());
                                    viaje.setPrecio(Integer.parseInt(price.getText().toString()));
                                    viaje.setNPlazas(Integer.parseInt(nAsientos.getText().toString()));
                                    viaje.setNPlazasDisp(Integer.parseInt(nAsientos.getText().toString()) - 1);
                                    viaje.setConductor(ParseUser.getCurrentUser());
                                    viaje.addPasajero(ParseUser.getCurrentUser());
                                    viaje.saveInBackground();

                                    Toast.makeText(FormularioCrear.this, viaje.getFecha(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FormularioCrear.this, MainActivity.class);
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












