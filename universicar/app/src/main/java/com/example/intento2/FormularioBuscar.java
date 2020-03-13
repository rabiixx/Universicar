package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intento2.Models.Viaje;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class FormularioBuscar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_buscar);

        //SPINNER ORIGEN
        final Spinner srcSpinner = findViewById(R.id.searchSrcPlace);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        srcSpinner.setAdapter(adapter);

        //SPINNER DESTINO
        final Spinner destSpinner = findViewById(R.id.searchDestPlace);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 =  ArrayAdapter.createFromResource(this,
                R.array.barrios_pamplona, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        destSpinner.setAdapter(adapter2);

        Button searchSubmitBtn = (Button)findViewById(R.id.searchSubmitBtn);

        searchSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String srcPlace = srcSpinner.getSelectedItem().toString();
                String destPlace = destSpinner.getSelectedItem().toString();

                // Define the class to query
                ParseQuery<Viaje> query = ParseQuery.getQuery(Viaje.class);

                // Define query conditions
                query.whereEqualTo("origen", "Barañain");

                query.findInBackground(new FindCallback<Viaje>() {
                    @Override
                    public void done(List<Viaje> viajeList, ParseException e) {
                        if (e == null) {
                            String viajeId = viajeList.get(1).getObjectId();
                            //Toast.makeText(FormularioBuscar.this, viajeId, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(FormularioBuscar.this, ListaViajes.class);
                            i.putExtra("travel_id", viajeList.get(2).getObjectId());
                            startActivity(i);
                        } else {
                            //Toast.makeText(FormularioBuscar.this,"Search Failure" , Toast.LENGTH_SHORT).show();
                            Log.d("Viaje", "Error" + e.getMessage());
                        }
                    }
                });

            }
        });

    }

}
