package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import com.example.intento2.ui.login.LoginActivity;

import com.example.intento2.ui.login.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] activities = {"Buscar Viaje", "Crear Viaje", "Mis Viajes", "Login", "Register"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = (ListView) findViewById(R.id.list);
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
                }
            }
        });
    }
}
