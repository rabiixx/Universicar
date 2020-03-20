package com.example.universicar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Opinion;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class RatingActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        final String userId = getIntent().getStringExtra("userId");

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);

        query.getInBackground(userId, new GetCallback<ParseUser>() {
            public void done(final ParseUser usuario, ParseException e) {
                if (e == null) {
                    final MaterialButtonToggleGroup mbtg;
                    final RatingBar ratingBar = findViewById(R.id.ratingBar);

                    ratingBar.setRating(5);

                    mbtg = findViewById(R.id.toggleGroup);
                    mbtg.check(R.id.btnMuyBien);

                    mbtg.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                        @Override
                        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                            Button checkBtn = findViewById(checkedId);
                            checkBtn.getText();
                            Toast.makeText(RatingActivity.this, checkBtn.getText(), Toast.LENGTH_SHORT).show();
                        }
                    });

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
                            String calidadConduccion = checkBtn.getText().toString();

                            Opinion opinion = new Opinion();
                            opinion.setTitulo(titulo);
                            opinion.setDescripcion(descripcion);
                            opinion.setCalidadConduccion(calidadConduccion);
                            opinion.setPuntuacion(puntuacion);
                            opinion.setCreador(ParseUser.getCurrentUser());
                            opinion.setUsuario(usuario);
                            opinion.saveInBackground();
                            Toast.makeText(RatingActivity.this, "Opinion envidad correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RatingActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
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
