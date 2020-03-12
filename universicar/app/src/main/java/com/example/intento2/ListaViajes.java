package com.example.intento2;

import android.annotation.SuppressLint;
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
        setContentView(R.layout.activity_mis_viajes);

        String travelId = getIntent().getStringExtra("travel_id");
        Toast.makeText(ListaViajes.this, travelId, Toast.LENGTH_SHORT).show();
        ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);

        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        query.getInBackground(travelId, new GetCallback<Viaje>() {
            @Override
            public void done(Viaje viaje, ParseException e) {
                ArrayList<String> travelList = new ArrayList<>();
                travelList.add(viaje.getDestino());
                ListView myListView = findViewById(R.id.travelList);
                ArrayAdapter adapter = new ArrayAdapter<String>(ListaViajes.this, android.R.layout.simple_list_item_1, travelList);
                myListView.setAdapter(adapter);
            }
        });
    }
}
