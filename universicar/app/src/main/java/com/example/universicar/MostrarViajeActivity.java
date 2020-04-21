package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MostrarViajeActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_viaje);

        final int code = getIntent().getIntExtra("code", 1);
        final Viaje viaje = (Viaje) getIntent().getSerializableExtra("viaje");

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CircleImageView imagenPerfil = findViewById(R.id.imagenPerfil);
        TextView conductor = findViewById(R.id.conductor);
        TextView origen = findViewById(R.id.origen);
        TextView destino = findViewById(R.id.destino);
        TextView fecha = findViewById(R.id.fecha);
        TextView hora = findViewById(R.id.hora);
        TextView nPlazas = findViewById(R.id.nAsientos);
        TextView precio = findViewById(R.id.precio);


        final ParseUser user = viaje.getConductor();

        ParseFile parseFile = user.getParseFile("imagenPerfil");
        Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(imagenPerfil);

        findViewById(R.id.conductor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarViajeActivity.this, PerfilActivity.class);
                intent.putExtra("userId", user.getObjectId());
                startActivity(intent);
            }
        });

        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());
        fecha.setText(viaje.getFecha());
        hora.setText(viaje.getHoraSalida());
        conductor.setText(user.getString("username"));
        final String asientosDisp = viaje.getNPlazasDisp() + " Plazas disponibles";
        nPlazas.setText(asientosDisp);
        precio.setText(String.valueOf(viaje.getPrecio()));

        ParseQuery<Coche> queryCoche = ParseQuery.getQuery(Coche.class);
        queryCoche.whereEqualTo("Conductor", user);

        queryCoche.findInBackground(new FindCallback<Coche>() {
            @Override
            public void done(List<Coche> coches, com.parse.ParseException e) {
                if (e == null) {
                    if (!coches.isEmpty()) {

                        LinearLayout cocheLL = findViewById(R.id.cocheLL);
                        cocheLL.setVisibility(LinearLayout.VISIBLE);

                        TextView marcaCoche = findViewById(R.id.marcaCoche);
                        TextView corlorCoche = findViewById(R.id.colorCoche);
                        ImageView iconoCoche = findViewById(R.id.iconoCoche);

                        Coche coche = coches.get(0);
                        corlorCoche.setText(coche.getColor());
                        marcaCoche.setText(coche.getMarca());

                        switch (coche.getTipoCoche()) {
                            case "Compacto":
                            default:
                                iconoCoche.setImageResource(R.drawable.ic_car_compacto_60dp);
                                break;
                            case "Deportivo":
                                iconoCoche.setImageResource(R.drawable.ic_car_deportivo_60dp);
                                break;
                            case "Familiar":
                                iconoCoche.setImageResource(R.drawable.ic_car_familiar_60dp);
                                break;
                            case "Descapotable":
                                iconoCoche.setImageResource(R.drawable.ic_car_descapotable_60dp);
                                break;
                            case "Biplaza":
                                iconoCoche.setImageResource(R.drawable.ic_car_biplaza_60dp);
                                break;
                            case "Todoterrno":
                                iconoCoche.setImageResource(R.drawable.ic_car_todoterreno_60dp);
                                break;
                        }
                        switch (coche.getColor()) {
                            case "Negro":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.black));
                                break;
                            case "Azul":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.blue));
                                break;
                            case "Verde":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.green));
                                break;
                            case "Gris":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.grey));
                                break;
                            case "Naranja":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.oranje));
                                break;
                            case "Rosa":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.pink));
                                break;
                            case "Rojo":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.red));
                                break;
                            case "Blanco":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.snow));
                                break;
                            case "Amarillo":
                                iconoCoche.setColorFilter(getResources().getColor(R.color.yellow));
                                break;
                            default:
                                Log.i("debug", "Problem loading car color");
                                break;
                        }
                    }
                } else {
                    Log.d("Coche", "Error: " + e.getMessage());
                }
            }
        });

        /* Mis Viajes */
        if (code == 1) {

            Log.i("debug", "code 1");

            ParseQuery<Opinion> queryOpinion = ParseQuery.getQuery(Opinion.class);

            queryOpinion.whereEqualTo("usuario", user);
            queryOpinion.whereEqualTo("creador", ParseUser.getCurrentUser());

            queryOpinion.findInBackground(new FindCallback<Opinion>() {
                @Override
                public void done(List<Opinion> opinion, ParseException e) {
                    if (e == null) {

                        Button btnOpinar = findViewById(R.id.btnOpinar);

                        if ( !ParseUser.getCurrentUser().getUsername().equals(user.getUsername()) ) {
                            if ( opinion.isEmpty() ) {
                                btnOpinar.setVisibility(View.VISIBLE);

                                btnOpinar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("debug", "opinat btn pressed");
                                        Intent intent = new Intent(MostrarViajeActivity.this, OpinarActivity.class);
                                        intent.putExtra("userId", user.getObjectId());
                                        startActivity(intent);
                                    }
                                });
                            } else {

                                findViewById(R.id.opinion).setVisibility(View.VISIBLE);

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
                        }


                    } else {
                        Log.d("debug", "Error: " + e.getMessage());
                    }
                }
            });

        } else {
            Log.i("debug", "code 2");

            Button btnReservar = findViewById(R.id.btnReservar);
            btnReservar.setVisibility(View.VISIBLE);

            btnReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viaje.getPasajeros().getQuery().findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> pasajeros, com.parse.ParseException e) {
                            if (e == null) {
                                viaje.addPasajero(ParseUser.getCurrentUser());
                                viaje.setNPlazasDisp(viaje.getNPlazasDisp() - 1);
                                viaje.saveInBackground();

                                Toast.makeText(MostrarViajeActivity.this, "Reserva Realizada Correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MostrarViajeActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Log.d("Pasajeros", "Error: " + e.getMessage());
                            }
                        }

                    });
                }
            });
        }
    }
}
