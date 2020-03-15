package com.example.intento2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intento2.Models.Viaje;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaViajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes);

        @SuppressWarnings("unchecked") final List<Viaje> viajes = (List<Viaje>) getIntent().getSerializableExtra("list");

        //Toast.makeText(ListaViajes.this, viajes.get(2).getDestino(), Toast.LENGTH_SHORT).show();

        //ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);ç
        //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        final ListView listaViajes = findViewById(R.id.travelList);
        CustomAdapter customAdapter = new CustomAdapter(this, viajes);
        listaViajes.setAdapter(customAdapter);


        listaViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Viaje viaje = (Viaje) listaViajes.getItemAtPosition(position);
                //Toast.makeText(ListaViajes.this, "Selected :" + " " + viaje.getOrigen()+", "+ viaje.getDestino(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ListaViajes.this, MostrarViaje.class);
                i.putExtra("viaje", (Serializable) viaje);
                startActivity(i);

            }
        });

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
