package com.example.universicar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universicar.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.List;

public class MisViajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes);

        ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);

        TextView title = (TextView)findViewById(R.id.titleListaViaje);
        title.setText("Mis Viajes");

        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtnListaViajes);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Define query conditions
        query.whereEqualTo("pasajeros", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<Viaje>() {
            @Override
            public void done(List<Viaje> viajes, ParseException e) {
                if (e == null) {
                    Toast.makeText(MisViajes.this, String.valueOf(viajes.size()), Toast.LENGTH_SHORT).show();

                    final ListView listaViajes = findViewById(R.id.travelList);
                    CustomAdapter customAdapter = new CustomAdapter(MisViajes.this, viajes);
                    listaViajes.setAdapter(customAdapter);

                    listaViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Viaje viaje = (Viaje) listaViajes.getItemAtPosition(position);
                            //Toast.makeText(ListaViajes.this, "Selected :" + " " + viaje.getOrigen()+", "+ viaje.getDestino(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MisViajes.this, MostrarViaje.class);
                            i.putExtra("viaje", (Serializable) viaje);
                            startActivity(i);

                        }
                    });
                } else {
                    Toast.makeText(MisViajes.this,"Search Failure" , Toast.LENGTH_SHORT).show();
                    Log.d("Viaje", "Error" + e.getMessage());
                }
            }
        });











    }
}
