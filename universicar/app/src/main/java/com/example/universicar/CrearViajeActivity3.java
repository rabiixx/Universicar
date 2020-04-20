package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Viaje;
import com.parse.ParseUser;

import java.util.Calendar;

public class CrearViajeActivity3 extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_viaje3);

        final int year = getIntent().getIntExtra("year", Calendar.getInstance().get(Calendar.YEAR));
        final int month = getIntent().getIntExtra("month", Calendar.getInstance().get(Calendar.MONTH));
        final int day = getIntent().getIntExtra("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        final int hour = getIntent().getIntExtra("hour", Calendar.getInstance().get(Calendar.HOUR));
        final int min = getIntent().getIntExtra("min", Calendar.getInstance().get(Calendar.MINUTE));

        /*Log.i("debug", String.valueOf(year));
        Log.i("debug", String.valueOf(month));
        Log.i("debug", String.valueOf(day));
        Log.i("debug", String.valueOf(hour));
        Log.i("debug", String.valueOf(min));
*/
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, min);

        Log.i("debug", cal.getTime().toString());

        findViewById(R.id.backBtnCrearViaje3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Spinner srcSpinner = findViewById(R.id.spinner_origen_crear);
        final Spinner destSpinner = findViewById(R.id.spinner_destino_crear);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CrearViajeActivity3.this,
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
                    Toast.makeText(CrearViajeActivity3.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else if ( srcSpinner.getSelectedItem().toString() == destSpinner.getSelectedItem().toString() ) {
                    Toast.makeText(CrearViajeActivity3.this, "El origen y el destino no pueden ser iguales", Toast.LENGTH_SHORT).show();
                } else {
                    Viaje viaje = new Viaje(srcSpinner.getSelectedItem().toString(), destSpinner.getSelectedItem().toString());
                    viaje.setFecha(cal.getTime());
                    viaje.setPrecio(Integer.parseInt(price.getText().toString()));
                    viaje.setNPlazas(Integer.parseInt(nAsientos.getText().toString()));
                    viaje.setNPlazasDisp(Integer.parseInt(nAsientos.getText().toString()) - 1);
                    viaje.setConductor(ParseUser.getCurrentUser());
                    viaje.addPasajero(ParseUser.getCurrentUser());
                    viaje.saveInBackground();

                    Toast.makeText(CrearViajeActivity3.this, "Viaje creado correctamente!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CrearViajeActivity3.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

    }

}
