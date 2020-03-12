package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.intento2.Models.Viaje;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioCrear extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_crear);

        //SPINNER ORIGEN
        Spinner spinnerOrigen = (Spinner) findViewById(R.id.spinner_origen_crear);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerOrigen.setAdapter(adapter);

        //SPINNER DESTINO
        Spinner spinnerDestino = (Spinner) findViewById(R.id.spinner_destino_crear);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 =  ArrayAdapter.createFromResource(this,
                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDestino.setAdapter(adapter2);


        final Calendar cal = Calendar.getInstance();
        final DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker1);
        final TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);
        final Button submitBtn = (Button)findViewById(R.id.submit);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final int hour, minute;

                if (Build.VERSION.SDK_INT >= 23 ) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else{
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                cal.set(Calendar.HOUR, hour);
                cal.set(Calendar.MINUTE, minute);

                Viaje viaje = new Viaje("Valencia", "Italia");
                viaje.setFecha(cal.getTime());
                //viaje.setConductor(ParseUser.getCurrentUser());
                viaje.saveInBackground();

            }
        });

    }
}
