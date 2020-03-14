package com.example.intento2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

        final ListView myListView = findViewById(R.id.travelList);
        CustomAdapter customAdapter = new CustomAdapter(this, viajes);

//        String[] carTypes = {"Compacto", "Deportivo", "Descapotable", "Familiar", "Todoterrno", "Biplaza"};
//        int[] carTypesIcons = {
//                R.drawable.ic_car_compacto_24dp, R.drawable.ic_car_deportivo_24dp, R.drawable.ic_car_descapotable_24dp,
//                R.drawable.ic_car_familiar_24dp, R.drawable.ic_car_todoterrenno_24dp, R.drawable.ic_car_biplaza_24dp
//        };
//
//        ColorsAdapter carTypesAdapter = new ColorsAdapter(this, carTypesIcons, carTypes);



        //final ColorsAdapter carBrandAdapter = new ColorsAdapter(this, carBrandIcons, carBrand);
        myListView.setAdapter(customAdapter);

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
