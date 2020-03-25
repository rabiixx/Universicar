package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MostrarMiViajeActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mi_viaje);

        final Viaje viaje = (Viaje) getIntent().getSerializableExtra("viaje");

        TextView origen = (TextView)findViewById(R.id.origenInfoViaje);
        TextView destino = (TextView)findViewById(R.id.destinoInfoViaje);
        TextView fecha = (TextView)findViewById(R.id.fechaInfoViaje);
        TextView hora = (TextView)findViewById(R.id.horaInfoViaje);
        TextView conductor = (TextView)findViewById(R.id.conductorInfoMisViajes);
        TextView nPlazas = (TextView)findViewById(R.id.plazasInfoViaje);
        TextView precio = (TextView)findViewById(R.id.precioInfoViaje);
        final TextView cocheTv = (TextView)findViewById(R.id.cocheInfoViaje);
        final TextView colorTv = (TextView)findViewById(R.id.colorInfoViaje);

        final ParseUser user = viaje.getConductor();

        try {
            String nombreConductor = user.fetchIfNeeded().getUsername();

            CircleImageView imagenPerfil = findViewById(R.id.profileImageMostrarMiViaje);
            LinearLayout conductorLL = findViewById(R.id.conductorLLMostrarMiViaje);

            ParseFile parseFile = user.getParseFile("imagenPerfil");
            Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(imagenPerfil);

            conductorLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MostrarMiViajeActivity.this, PerfilActivity.class);
                    intent.putExtra("userId", user.getObjectId());
                    startActivity(intent);
                }
            });

            origen.setText(viaje.getOrigen());
            destino.setText(viaje.getDestino());
            fecha.setText(viaje.getFecha());
            hora.setText(viaje.getHoraSalida());
            String name = user.getUsername();
            Log.i(TAG, "Username: " + user.getUsername());
            conductor.setText(name);
            final String asientosDisp = String.valueOf(viaje.getNPlazasDisp()) + " Plazas disponibles";
            nPlazas.setText(asientosDisp);
            precio.setText(String.valueOf(viaje.getPrecio()));

            findViewById(R.id.opinarMostrarMiViaje).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MostrarMiViajeActivity.this, RatingActivity.class);
                    intent.putExtra("userId", user.getObjectId());
                    startActivity(intent);
                }
            });

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

        } catch (com.parse.ParseException e) {
            Log.e(TAG, "Something has gone terribly wrong with Parse", e);
        }



    }
}
