package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Opinion;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_activity);

        final String userId = getIntent().getStringExtra("userId");

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);

        query.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {

                    CircleImageView imagenPerfil = findViewById(R.id.imagenPerfil);
                    ParseFile parseFile = user.getParseFile("imagenPerfil");
                    Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(imagenPerfil);

                    TextView username = findViewById(R.id.usernamePerfil);
                    username.setText(user.getUsername());

                    ParseQuery<Opinion> query = ParseQuery.getQuery(Opinion.class);

                    query.whereEqualTo("usuario", user);

                    query.findInBackground(new FindCallback<Opinion>() {
                        public void done(final List<Opinion> opiniones, ParseException e) {
                            if (e == null) {

                                TextView opinionesTv = findViewById(R.id.opinionesTvPerfil);
                                float puntuacionAVG =  puntuacionAVG(opiniones);
                                String str = puntuacionAVG + "/5 - " + opiniones.size() + " opiniones";
                                opinionesTv.setText(str);

                                RatingBar ratingBar = findViewById(R.id.ratingBarPerfil);
                                ratingBar.setRating(puntuacionAVG);


                                findViewById(R.id.LlOpinionesPerfil).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(PerfilActivity.this, ListaOpinionesActivity.class);
                                        i.putExtra("opiniones", (Serializable) opiniones);
                                        startActivity(i);
                                    }
                                });

                                findViewById(R.id.verOpinionesPerfil).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(PerfilActivity.this, ListaOpinionesActivity.class);
                                        i.putExtra("opiniones", (Serializable) opiniones);
                                        startActivity(i);
                                    }
                                });



                                TextView habilidad = findViewById(R.id.habilidadListaOpiniones);

                                switch ((int) Math.round(habilidadAVG(opiniones))) {
                                    case 1:
                                        habilidad.append("Mal");
                                        break;
                                    case 2:
                                        habilidad.append("Regular");
                                        break;
                                    case 3:
                                        habilidad.append("Bien");
                                        break;
                                    case 4:
                                        habilidad.append("Muy Bien");
                                        break;
                                }

                            } else {
                                Log.d("item", "Error: " + e.getMessage());
                            }
                        }
                    });

                } else {
                    e.printStackTrace();
                }
            }
        });


    }

    static double habilidadAVG(List <Opinion> opiniones) {
        int sum = 0;
        if(!opiniones.isEmpty()) {
            for (Opinion opinion : opiniones) {
                sum += opinion.getHabilidadConduccion();
            }
            return (double) sum / opiniones.size();
        }
        return sum;
    }

    static float puntuacionAVG(List <Opinion> opiniones) {
        int sum = 0;
        if(!opiniones.isEmpty()) {
            for (Opinion opinion : opiniones) {
                sum += opinion.getPuntuacion();
            }
            return (float) sum / opiniones.size();
        }
        return sum;
    }


}
