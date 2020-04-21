package com.example.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Coche;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;

public class AnadirCocheActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_coche);


        final Spinner carBrandSpinner = findViewById(R.id.brandSpinner);
        String[] carBrand = {"Audi", "BMW", "Chevrolet", "Citroen", "Ferrari", "Fiat", "Ford",
                        "Hyundai", "Jeep", "Kia", "Mercedes", "Nissan", "Opel", "Peugeot", "Toyota"};

        int[] carBrandIcons = {
                R.drawable.ic_brand_audi, R.drawable.ic_brand_bmw, R.drawable.ic_brand_chevrolet,
                R.drawable.ic_brand_citroen, R.drawable.ic_brand_ferrari, R.drawable.ic_brand_fiat,
                R.drawable.ic_brand_ford, R.drawable.ic_brand_hyundai, R.drawable.ic_brand_jeep,
                R.drawable.ic_brand_kia, R.drawable.ic_brand_mercedes, R.drawable.ic_brand_nissan,
                R.drawable.ic_brand_opel, R.drawable.ic_brand_peugeot, R.drawable.ic_brand_toyota,
        };

        final AdapterColor carBrandAdapter = new AdapterColor(this, carBrandIcons, carBrand);

        carBrandSpinner.setAdapter(carBrandAdapter);



        final Spinner carTypeSpinner = findViewById(R.id.typeSpinner);
        String[] carTypes = {"Compacto", "Deportivo", "Descapotable", "Familiar", "Todoterrno", "Biplaza"};
        int[] carTypesIcons = {
                R.drawable.ic_car_compacto_24dp, R.drawable.ic_car_deportivo_24dp, R.drawable.ic_car_descapotable_24dp,
                R.drawable.ic_car_familiar_24dp, R.drawable.ic_car_todoterrenno_24dp, R.drawable.ic_car_biplaza_24dp
        };

        AdapterColor carTypesAdapter = new AdapterColor(this, carTypesIcons, carTypes);

        carTypeSpinner.setAdapter(carTypesAdapter);

        final Spinner carColorSpinner = findViewById(R.id.colorSpinner);
        String[] colors = {"Negro", "Azul", "Verde", "Gris", "Naranja", "Rosa", "Rojo", "Blanco", "Amarillo"};
        int[] colorIcons = {
                R.drawable.ic_circle_black_24dp, R.drawable.ic_circle_blue_24dp, R.drawable.ic_circle_green_24dp,
                R.drawable.ic_circle_grey_24dp, R.drawable.ic_circle_orange_24dp, R.drawable.ic_circle_pink_24dp,
                R.drawable.ic_circle_red_24dp, R.drawable.ic_circle_white_24dp, R.drawable.ic_circle_yellow_24dp
        };


        AdapterColor colorAdapter = new AdapterColor(this, colorIcons, colors);
        //colorAdapter.setDropDownViewResource(R.layout.adapter_colores);

        carColorSpinner.setAdapter(colorAdapter);

        ImageButton backBtn = findViewById(R.id.backBtnAddCar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button addCar = findViewById(R.id.addCar);

        final Coche coche = (Coche) getIntent().getSerializableExtra("coche");
        if (coche != null) {
            int index;

            index = Arrays.asList(carBrand).indexOf(coche.getMarca());
            carBrandSpinner.setSelection(index);

            index = Arrays.asList(carTypes).indexOf(coche.getTipoCoche());
            carTypeSpinner.setSelection(index);

            index = Arrays.asList(colors).indexOf(coche.getColor());
            carColorSpinner.setSelection(index);

            addCar.setText("Guardar Coche");

            addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String brand = carBrandSpinner.getSelectedItem().toString();
                    final String carType = carTypeSpinner.getSelectedItem().toString();
                    final String carColor = carColorSpinner.getSelectedItem().toString();

                    coche.setMarca(brand);
                    coche.setTipoCoche(carType);
                    coche.setColor(carColor);

                    Toast.makeText(AnadirCocheActivity.this, "Coche modificado correctamente", Toast.LENGTH_SHORT).show();
                    coche.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Intent i = new Intent(AnadirCocheActivity.this, MiPerfilActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    });

                }
            });

        } else {

            addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String brand = carBrandSpinner.getSelectedItem().toString();
                    final String carType = carTypeSpinner.getSelectedItem().toString();
                    final String carColor = carColorSpinner.getSelectedItem().toString();

                    Coche nuevoCoche = new Coche();
                    nuevoCoche.setMarca(brand);
                    nuevoCoche.setTipoCoche(carType);
                    nuevoCoche.setColor(carColor);
                    nuevoCoche.setConductor(ParseUser.getCurrentUser());

                    nuevoCoche.saveInBackground();
                    Toast.makeText(AnadirCocheActivity.this, "Coche añadido correctamente", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(AnadirCocheActivity.this, MiPerfilActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            });
        }








    }
}
