package com.example.intento2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intento2.Models.Coche;
import com.example.intento2.Models.Palette;
import com.example.intento2.Models.Viaje;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

public class AddVehicleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        final Spinner carBrandSpinner = (Spinner)findViewById(R.id.brandSpinner);
        String[] carBrand = {"Audi", "BMW", "Chevrolet", "Citroen", "Ferrari", "Fiat", "Ford",
                        "Hyundai", "Jeep", "Kia", "Mercedes", "Nissan", "Opel", "Peugeot", "Toyota"};

        int[] carBrandIcons = {
                R.drawable.ic_brand_audi, R.drawable.ic_brand_bmw, R.drawable.ic_brand_chevrolet,
                R.drawable.ic_brand_citroen, R.drawable.ic_brand_ferrari, R.drawable.ic_brand_fiat,
                R.drawable.ic_brand_ford, R.drawable.ic_brand_hyundai, R.drawable.ic_brand_jeep,
                R.drawable.ic_brand_kia, R.drawable.ic_brand_mercedes, R.drawable.ic_brand_nissan,
                R.drawable.ic_brand_opel, R.drawable.ic_brand_peugeot, R.drawable.ic_brand_toyota,
        };

        final ColorsAdapter carBrandAdapter = new ColorsAdapter(this, carBrandIcons, carBrand);

        carBrandSpinner.setAdapter(carBrandAdapter);


        final Spinner carTypeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        String[] carTypes = {"Compacto", "Deportivo", "Descapotable", "Familiar", "Todoterrno", "Biplaza"};
        int[] carTypesIcons = {
                R.drawable.ic_car_compacto_24dp, R.drawable.ic_car_deportivo_24dp, R.drawable.ic_car_descapotable_24dp,
                R.drawable.ic_car_familiar_24dp, R.drawable.ic_car_todoterrenno_24dp, R.drawable.ic_car_biplaza_24dp
        };

        ColorsAdapter carTypesAdapter = new ColorsAdapter(this, carTypesIcons, carTypes);

        carTypeSpinner.setAdapter(carTypesAdapter);

        final Spinner carColorSpinner = (Spinner)findViewById(R.id.colorSpinner);
        String[] colors = {"Negro", "Azul", "Verde", "Gris", "Naranja", "Rosa", "Rojo", "Blanco", "Amarillo"};
        int[] colorIcons = {
                R.drawable.ic_circle_black_24dp, R.drawable.ic_circle_blue_24dp, R.drawable.ic_circle_green_24dp,
                R.drawable.ic_circle_grey_24dp, R.drawable.ic_circle_orange_24dp, R.drawable.ic_circle_pink_24dp,
                R.drawable.ic_circle_red_24dp, R.drawable.ic_circle_white_24dp, R.drawable.ic_circle_yellow_24dp
        };


        ColorsAdapter colorAdapter = new ColorsAdapter(this, colorIcons, colors);
        //colorAdapter.setDropDownViewResource(R.layout.colors_adapter);

        carColorSpinner.setAdapter(colorAdapter);




        Button addCar = (Button)findViewById(R.id.addCar);

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Toast.makeText(AddVehicleActivity.this, carBrandSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
             //   Toast.makeText(AddVehicleActivity.this, carTypeSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
              //  Toast.makeText(AddVehicleActivity.this, carColorSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                final String brand = carBrandSpinner.getSelectedItem().toString();
                final String carType = carTypeSpinner.getSelectedItem().toString();
                final String carColor = carColorSpinner.getSelectedItem().toString();


                Coche coche = new Coche();
                coche.setMarca(brand);
                coche.setTipoCoche(carType);
                coche.setColor(carColor);
                coche.saveInBackground();
            }
        });
    }
}
