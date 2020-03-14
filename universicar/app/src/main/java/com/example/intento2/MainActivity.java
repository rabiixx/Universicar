package com.example.intento2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String[] activities = {"Buscar Viaje", "Crear Viaje", "Mis Viajes", "Login", "Register", "Añadir Vehiculo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activities);
        myListView.setAdapter(adapter);


        //Set an item click listener for ListView
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent listaIntent;
                if (position == 0) {
                    listaIntent = new Intent(MainActivity.this, FormularioBuscar.class);
                    startActivity(listaIntent);
                } else if (position == 1) {
                    listaIntent = new Intent(MainActivity.this, FormularioCrear.class);
                    startActivity(listaIntent);
                } else if (position == 2) {
                    listaIntent = new Intent(MainActivity.this, MisViajes.class);
                    startActivity(listaIntent);
                } else if (position == 3) {
                    listaIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(listaIntent);
                } else if (position == 4) {
                    listaIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                    startActivity(listaIntent);
                } else if (position == 5){
                    listaIntent = new Intent(MainActivity.this, AddVehicleActivity.class);
                    startActivity(listaIntent);
                }
            }
        });
    }
}
