package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    //private String[] activities = {"Buscar Viaje", "Crear Viaje", "Mis Viajes", "Login", "Register", "Añadir Vehiculo", "Mostrar Viaje", "Logout", "Bottom Nav"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn =  (Button)findViewById(R.id.test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, testActivity.class));
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        final Button btn1 = (Button)findViewById(R.id.btn1Main);
        final Button btn2 = (Button)findViewById(R.id.btn2Main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);


        if (ParseUser.getCurrentUser().getUsername() != null) {

            ParseInstallation installation = ParseInstallation.getCurrentInstallation();

            installation.put("username", ParseUser.getCurrentUser().getUsername());
            installation.put("userID", ParseUser.getCurrentUser().getObjectId());
            installation.saveInBackground(new SaveCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null ){
                        btn1.setText("Crear Viaje");
                        btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_black_24dp, 0, 0, 0);
                        btn2.setText("Buscar Viaje");
                        btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_white_24dp, 0, 0, 0);

                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, FormularioCrear.class));
                            }
                        });

                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, FormularioBuscar.class));
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
                                    startActivity(new Intent(MainActivity.this, FormularioBuscar.class));
                                    break;
                                case R.id.crearMenu:
                                    startActivity(new Intent(MainActivity.this, FormularioCrear.class));
                                    break;
                                case R.id.perfilMenu:
                                    startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                                    break;
                                case R.id.misViajesMenu:
                                    startActivity(new Intent(MainActivity.this, MisViajes.class));
                                    break;
                            }

                            return true;
                        }
                    });





        } else {

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
                                startActivity(new Intent(MainActivity.this, FormularioBuscar.class));
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
