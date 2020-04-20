package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.universicar.Models.Viaje;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Button btn1 = findViewById(R.id.btn1Main);
        final Button btn2 = findViewById(R.id.btn2Main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        if (ParseUser.getCurrentUser().getUsername() != null) {

            ParseInstallation installation = ParseInstallation.getCurrentInstallation();

            installation.put("username", ParseUser.getCurrentUser().getUsername());
            installation.put("userID", ParseUser.getCurrentUser().getObjectId());
            installation.saveInBackground(new SaveCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null ){
                        btn1.setText("Crear Viaje");
                        btn2.setText("Buscar Viaje");

                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, CrearViajeActivity1.class));
                            }
                        });

                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, BuscarViajeActivity.class));
                            }
                        });
                    }}});

                bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;

                            switch (item.getItemId()) {
                                case R.id.buscarMenu:
                                    startActivity(new Intent(MainActivity.this, BuscarViajeActivity.class));
                                    break;
                                case R.id.crearMenu:
                                    startActivity(new Intent(MainActivity.this, CrearViajeActivity1.class));
                                    break;
                                case R.id.perfilMenu:
                                    startActivity(new Intent(MainActivity.this, MiPerfilActivity.class));
                                    break;
                                case R.id.misViajesMenu:
                                    ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);
                                    query.whereEqualTo("pasajeros", ParseUser.getCurrentUser());
                                    query.addDescendingOrder("fecha");
                                    query.findInBackground(new FindCallback<Viaje>() {
                                        @Override
                                        public void done(List<Viaje> viajes, ParseException e) {
                                            if (e == null) {
                                                Intent i = new Intent(MainActivity.this, ListaViajesActivity.class);
                                                i.putExtra("code", 1);
                                                i.putExtra("viajes", (Serializable) viajes);
                                                startActivity(i);
                                            } else {
                                                Log.d("Viaje", "Error" + e.getMessage());
                                            }
                                        }
                                    });

                                    break;
                            }

                            return true;
                        }
                    });

        } else {

            btn1.setText("Iniciar Sesion");
            btn2.setText("Registrarse");

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                }
            });


            bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.buscarMenu:
                                startActivity(new Intent(MainActivity.this, BuscarViajeActivity.class));
                                break;
                            case R.id.crearMenu:
                            case R.id.misViajesMenu:
                            case R.id.perfilMenu:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                break;
                        }
                        return true;
                    }
                });
        }
    }
}
