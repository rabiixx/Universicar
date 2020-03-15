package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.intento2.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class MisViajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_viajes);


        viaje.getPasajeros().getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> pasajeros, com.parse.ParseException e) {
                if (e == null) {
                    viaje.addPasajero(ParseUser.getCurrentUser());
                    viaje.setNPlazasDisp(viaje.getNPlazasDisp() - 1);
                    viaje.saveInBackground();

                    Toast.makeText(MostrarViaje.this, "Reserva Realizada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MostrarViaje.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("Pasajeros", "Error: " + e.getMessage());
                }
            }

        });











    }
}
