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

public class ListaViajesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes);

        final int code = getIntent().getIntExtra("code", 1);

        TextView headerTitle = findViewById(R.id.headerTitle);
        if (code == 1) {
            headerTitle.setText("Mis Viajes");
        } else {
            headerTitle.setText("Viajes Disponibles");
        }

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        @SuppressWarnings("unchecked") final List<Viaje> viajes = (List<Viaje>) getIntent().getSerializableExtra("viajes");

        final ListView listaViajes = findViewById(R.id.travelList);
        AdapterViaje adapterViaje = new AdapterViaje(this, viajes, code);
        listaViajes.setAdapter(adapterViaje);

        listaViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Viaje viaje = (Viaje) listaViajes.getItemAtPosition(position);
                Intent i = new Intent(ListaViajesActivity.this, MostrarViajeActivity.class);
                i.putExtra("code", code);
                i.putExtra("viaje", (Serializable) viaje);
                startActivity(i);
            }
        });

    }
}
