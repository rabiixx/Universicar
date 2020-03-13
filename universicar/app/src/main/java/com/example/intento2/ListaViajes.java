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

        Viaje viaje = (Viaje) getIntent().getSerializableExtra("result");

        if (viaje == null) {
            Toast.makeText(ListaViajes.this, " NULL", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ListaViajes.this, "NOT NULL", Toast.LENGTH_SHORT).show();
        }

        //ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);

        //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        final ArrayList<String> activities = new ArrayList<String>();
        activities.add("Manzana");
        activities.add("Pera");
        activities.add("Platano");
        activities.add(viaje.getDestino());

        final ListView myListView = findViewById(R.id.travelList);
        final ArrayAdapter myAdapter = new ArrayAdapter<>(ListaViajes.this, android.R.layout.simple_list_item_1, activities);
        myListView.setAdapter(myAdapter);

     /*   query.getInBackground(travelId, new GetCallback<Viaje>() {
            @Override
            public void done(Viaje viaje, ParseException e) {
                if (e == null) {
                    //myAdapter.add(viaje.getDestino());
                    activities.add("Ruben");
                    Toast.makeText(ListaViajes.this, "hola", Toast.LENGTH_SHORT).show();
                    myAdapter.notifyDataSetChanged();
                }
            }
        });*/
    }
}
