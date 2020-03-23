package com.example.universicar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaOpinionesActivity extends AppCompatActivity {

    private static final String TAG = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_opiniones);

        final String userId = getIntent().getStringExtra("userId");

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);

        query.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {

                    CircleImageView imagenPerfil = findViewById(R.id.profileImageListaOpiniones);
                    ParseFile parseFile = user.getParseFile("imagenPerfil");
                    Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(imagenPerfil);

                    TextView username = findViewById(R.id.usernameListaOpiniones);
                    username.setText(user.getUsername());

                    ParseQuery<Opinion> query = ParseQuery.getQuery(Opinion.class);

                    query.whereEqualTo("usuario", user);

                    query.findInBackground(new FindCallback<Opinion>() {
                        public void done(List<Opinion> opiniones, ParseException e) {
                            if (e == null) {
                                final ListView listaOpiniones = findViewById(R.id.listaOpiniones);
                                AdapterOpiniones adapterOpiniones = new AdapterOpiniones(ListaOpinionesActivity.this, opiniones);
                                listaOpiniones.setAdapter(adapterOpiniones);

                                RatingBar puntuacion = findViewById(R.id.puntuacionListaOpiniones);
                                puntuacion.setRating(puntuacionAVG(opiniones));

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


    private double habilidadAVG(List <Opinion> opiniones) {
        int sum = 0;
        if(!opiniones.isEmpty()) {
            for (Opinion opinion : opiniones) {
                sum += opinion.getHabilidadConduccion();
            }
            return (double) sum / opiniones.size();
        }
        return sum;
    }


    private float puntuacionAVG(List <Opinion> opiniones) {
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
