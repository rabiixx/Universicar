package com.example.intento2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intento2.Models.Viaje;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class ListaViajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes);

        final String travelId = getIntent().getStringExtra("travel_id");
        ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);

        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        String[] activities = {"Buscar Viaje", "Crear Viaje", "Mis Viajes", "Login", "Register"};

        ListView myListView = findViewById(R.id.travelList);
        ArrayAdapter myAdapter = new ArrayAdapter<>(ListaViajes.this, android.R.layout.simple_list_item_1, activities);
        myListView.setAdapter(myAdapter);


        query.getInBackground(travelId, new GetCallback<Viaje>() {
            @Override
            public void done(Viaje viaje, ParseException e) {
                if (e == null) {
                   /* myAdapter.add(viaje.getDestino());
                    myAdapter.add("Ruben");
                    //Toast.makeText(ListaViajes.this, myTravelList.get(0), Toast.LENGTH_SHORT).show();
                    myAdapter.notifyDataSetChanged();*/
                }
            }
        });
    }
}
