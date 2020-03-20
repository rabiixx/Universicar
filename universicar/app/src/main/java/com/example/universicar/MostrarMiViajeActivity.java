package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MostrarMiViajeActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mi_viaje);

        final Viaje viaje = (Viaje) getIntent().getSerializableExtra("viaje");

        if (viaje == null) {
            Log.i(TAG, "Viaje es null");
        }

        TextView origen = (TextView)findViewById(R.id.origenInfoViaje);
        TextView destino = (TextView)findViewById(R.id.destinoInfoViaje);
        TextView fecha = (TextView)findViewById(R.id.fechaInfoViaje);
        TextView hora = (TextView)findViewById(R.id.horaInfoViaje);
        TextView conductor = (TextView)findViewById(R.id.conductorInfoViaje);
        TextView nPlazas = (TextView)findViewById(R.id.plazasInfoViaje);
        TextView precio = (TextView)findViewById(R.id.precioInfoViaje);
        final TextView cocheTv = (TextView)findViewById(R.id.cocheInfoViaje);
        final TextView colorTv = (TextView)findViewById(R.id.colorInfoViaje);

        Button reservar = (Button)findViewById(R.id.submitInfoMiViaje);

        final ParseUser user = viaje.getConductor();

        try {
            String nombreConductor = user.fetchIfNeeded().getString("name");
        } catch (com.parse.ParseException e) {
            Log.e(TAG, "Something has gone terribly wrong with Parse", e);
        }

        //Toast.makeText(MostrarViaje.this, user.getObjectId(), Toast.LENGTH_SHORT).show();


        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());
        fecha.setText(viaje.getFecha());
        hora.setText(viaje.getHoraSalida());
        conductor.setText(user.getString("username"));
        final String asientosDisp = String.valueOf(viaje.getNPlazasDisp()) + " Plazas disponibles";
        nPlazas.setText(asientosDisp);
        precio.setText(String.valueOf(viaje.getPrecio()));

        ParseQuery<Coche> query = ParseQuery.getQuery(Coche.class);

        query.whereEqualTo("Conductor", user);

        query.findInBackground(new FindCallback<Coche>() {
            @Override
            public void done(List<Coche> coches, com.parse.ParseException e) {
                if (e == null) {
                    if (coches.isEmpty()) {
                        Toast.makeText(MostrarMiViajeActivity.this, "No CARS", Toast.LENGTH_SHORT).show();
                        LinearLayout ll = (LinearLayout)findViewById(R.id.cocheLayoutInfoViaje);
                        ll.setVisibility(LinearLayout.GONE);
                    } else {
                        assert coches.get(0) == null;
                        Coche coche = coches.get(0);
                        String color = coche.getColor();
                        String marca = coches.get(0).getMarca();
                        cocheTv.setText(marca);
                        colorTv.setText(color);
                    }
                } else {
                    Log.d("Coche", "Error: " + e.getMessage());
                }
            }
        });

        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Log.i(TAG, "User 1 es null");
                }
                Intent intent = new Intent(MostrarMiViajeActivity.this, RatingActivity.class);
                intent.putExtra("userId", user.getObjectId());
                startActivity(intent);
            }
        });

    }
}
