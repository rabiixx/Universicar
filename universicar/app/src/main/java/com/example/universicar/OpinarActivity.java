package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Opinion;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OpinarActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinar);

        final String userId = getIntent().getStringExtra("userId");

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);

        query.getInBackground(userId, new GetCallback<ParseUser>() {
            public void done(final ParseUser usuario, ParseException e) {
                if (e == null) {
                    final MaterialButtonToggleGroup mbtg;
                    final RatingBar ratingBar = findViewById(R.id.ratingBar);

                    final TextView habilidadConduccionTV = findViewById(R.id.tvHabilidadConduccionRating);
                    habilidadConduccionTV.append(usuario.getUsername() + "?");

                    findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });

                    CircleImageView fotoPerfil = findViewById(R.id.fotoPerfil);
                    ParseFile parseFile = usuario.getParseFile("imagenPerfil");
                    Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(fotoPerfil);

                    TextView nombre = findViewById(R.id.nombre);
                    nombre.setText(usuario.getUsername());

                    fotoPerfil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OpinarActivity.this, PerfilActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        }
                    });

                    nombre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OpinarActivity.this, PerfilActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        }
                    });

                    ratingBar.setRating(5);

                    mbtg = findViewById(R.id.toggleGroup);
                    mbtg.check(R.id.btnMuyBien);

                    findViewById(R.id.submitRating).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        final TextInputEditText tituloTIET = findViewById(R.id.titleRating);
                        final TextInputEditText descripcionTIET = findViewById(R.id.descriptionRating);

                        if ( !(tituloTIET.getText().toString().isEmpty()) && !(descripcionTIET.getText().toString().isEmpty()) ) {

                            String titulo = tituloTIET.getText().toString();
                            String descripcion = descripcionTIET.getText().toString();
                            double puntuacion = ratingBar.getRating();

                            Button checkBtn = findViewById(mbtg.getCheckedButtonId());
                            int calidadConduccion = 4;

                            switch (checkBtn.getText().toString()) {
                                case "Muy Bien":
                                    calidadConduccion = 4;
                                    break;
                                case "Bien":
                                    calidadConduccion = 3;
                                    break;
                                case "Regular":
                                    calidadConduccion = 2;
                                    break;
                                case "Mal":
                                    calidadConduccion = 1;
                                    break;
                            }

                            Opinion opinion = new Opinion();
                            opinion.setTitulo(titulo);
                            opinion.setDescripcion(descripcion);
                            opinion.setHabilidadConduccion(calidadConduccion);
                            try {
                                opinion.save();
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }
                            opinion.setPuntuacion(puntuacion);
                            opinion.setCreador(ParseUser.getCurrentUser());
                            opinion.setUsuario(usuario);
                            opinion.saveInBackground();
                            Toast.makeText(OpinarActivity.this, "Opinion enviada correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OpinarActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(OpinarActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        });


    }
}
