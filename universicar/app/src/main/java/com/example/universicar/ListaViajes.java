package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Viaje;

import java.io.Serializable;
import java.util.List;

public class ListaViajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes);

        TextView title = findViewById(R.id.titleListaViaje);
        title.setText("Viajes Disponibles");

        ImageButton backBtn = findViewById(R.id.backBtnListaViajes);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        @SuppressWarnings("unchecked") final List<Viaje> viajes = (List<Viaje>) getIntent().getSerializableExtra("list");

        final ListView listaViajes = findViewById(R.id.travelList);
        CustomAdapter customAdapter = new CustomAdapter(this, viajes, 2);
        listaViajes.setAdapter(customAdapter);


        listaViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Viaje viaje = (Viaje) listaViajes.getItemAtPosition(position);

                Intent i = new Intent(ListaViajes.this, MostrarViajeActivity.class);
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
