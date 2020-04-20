/*
package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Opinion;
import com.example.universicar.Models.Viaje;



import com.parse.FindCallback;
import com.parse.ParseException;
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

        findViewById(R.id.backBtnMiViaje).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final Viaje viaje = (Viaje) getIntent().getSerializableExtra("viaje");

        TextView origen = findViewById(R.id.origenInfoViaje);
        TextView destino = findViewById(R.id.destinoInfoViaje);
        TextView fecha = findViewById(R.id.fechaInfoViaje);
        TextView hora = findViewById(R.id.horaInfoViaje);
        TextView conductor = findViewById(R.id.conductorInfoMisViajes);
        TextView nPlazas = findViewById(R.id.plazasInfoViaje);
        TextView precio = findViewById(R.id.precioInfoViaje);
        final TextView cocheTv = findViewById(R.id.cocheInfoViaje);
        final TextView colorTv = findViewById(R.id.colorInfoViaje);

        final ParseUser user = viaje.getConductor();

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

        conductor.setText(name);
        final String asientosDisp = String.valueOf(viaje.getNPlazasDisp()) + " Plazas disponibles";
        nPlazas.setText(asientosDisp);
        precio.setText(String.valueOf(viaje.getPrecio()));

        ParseQuery<Opinion> query = ParseQuery.getQuery(Opinion.class);

        query.whereEqualTo("usuario", user);
        query.whereEqualTo("creador", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<Opinion>() {
            @Override
            public void done(List<Opinion> opinion, ParseException e) {
                if (e == null) {
                    Button btn = findViewById(R.id.opinarMostrarMiViaje);
                    CardView cv = findViewById(R.id.opinionMiViaje);
                    if (opinion.isEmpty()) {
                        btn.setVisibility(View.VISIBLE);
                    } else {

                        cv.setVisibility(View.VISIBLE);

                        TextView username = findViewById(R.id.userOpinionMiViaje);
                        TextView titulo = findViewById(R.id.tituloOpinionMiViaje);
                        RatingBar puntuacion = findViewById(R.id.puntOpinionMiViaje);
                        TextView descripcion = findViewById(R.id.descOpinionMiViaje);
                        CircleImageView fotoPerfil = findViewById(R.id.imagenPerfilOpinionMiViaje);

                        try {
                            username.setText(opinion.get(0).getCreador().fetchIfNeeded().getUsername());
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                        titulo.setText(opinion.get(0).getTitulo());
                        descripcion.setText(opinion.get(0).getDescripcion());
                        puntuacion.setRating(opinion.get(0).getPuntuacion().floatValue());

                        ParseFile parseFile = opinion.get(0).getCreador().getParseFile("imagenPerfil");
                        Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(fotoPerfil);
                    }
                } else {
                    Log.d("debug", "Error: " + e.getMessage());
                }
            }
        });

        findViewById(R.id.opinarMostrarMiViaje).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarMiViajeActivity.this, RatingActivity.class);
                intent.putExtra("userId", user.getObjectId());
                startActivity(intent);
            }
        });

        ParseQuery<Coche> query2 = ParseQuery.getQuery(Coche.class);

        query2.whereEqualTo("Conductor", user);

        query2.findInBackground(new FindCallback<Coche>() {
            @Override
            public void done(List<Coche> coches, ParseException e) {
                if (e == null) {
                    if (!coches.isEmpty()) {
                        LinearLayout ll = findViewById(R.id.cocheLayoutInfoViaje);
                        ll.setVisibility(LinearLayout.VISIBLE);
                        Coche coche = coches.get(0);
                        String color = coche.getColor();
                        String marca = coches.get(0).getMarca();
                        cocheTv.setText(marca);
                        colorTv.setText(color);
                    }
                } else {
                    Log.d("debug", "Error: " + e.getMessage());
                }
            }
        });


    }
}
*/
