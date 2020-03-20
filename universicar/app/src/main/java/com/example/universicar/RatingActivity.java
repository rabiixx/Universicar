package com.example.universicar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Opinion;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;

public class RatingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        final TextInputEditText tituloTIET;
        final TextInputEditText descripcionTIET;
        final MaterialButtonToggleGroup mbtg;
        final RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        ratingBar.setRating(5);

        mbtg = (MaterialButtonToggleGroup)findViewById(R.id.toggleGroup);
        mbtg.check(R.id.btnMuyBien);

        mbtg.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                Button checkBtn = (Button)findViewById(checkedId);
                checkBtn.getText();
                Toast.makeText(RatingActivity.this, checkBtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.submitRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputEditText tituloTIET = (TextInputEditText)findViewById(R.id.titleRating);
                TextInputEditText descripcionTIET = (TextInputEditText)findViewById(R.id.descriptionRating);

                if ( (tituloTIET.getText().toString().isEmpty()) && (descripcionTIET.getText().toString().isEmpty()) ) {

                    String titulo = tituloTIET.getText().toString();
                    String descripcion = descripcionTIET.getText().toString();
                    double puntuacion = ratingBar.getRating();

                    Button checkBtn = (Button)findViewById(mbtg.getCheckedButtonId());
                    String calidadConduccion = checkBtn.getText().toString();

                    Opinion opinion = new Opinion();
                    opinion.setTitulo(titulo);
                    opinion.setDescripcion(descripcion);
                    opinion.setPuntuacion(puntuacion);
                    opinion.setUsuario(ParseUser.getCurrentUser());
                    opinion.saveInBackground();

                } else {
                    Toast.makeText(RatingActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
