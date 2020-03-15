package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intento2.Models.Viaje;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.List;

public class MostrarViaje extends AppCompatActivity {

    private static final String TAG = "dsxf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_viaje);

        final Viaje viaje = (Viaje) getIntent().getSerializableExtra("viaje");

        TextView origen = (TextView)findViewById(R.id.origenInfoViaje);
        TextView destino = (TextView)findViewById(R.id.destinoInfoViaje);
        TextView fecha = (TextView)findViewById(R.id.fechaInfoViaje);
        TextView hora = (TextView)findViewById(R.id.horaInfoViaje);
        TextView conductor = (TextView)findViewById(R.id.conductorInfoViaje);
        TextView nPlazas = (TextView)findViewById(R.id.plazasInfoViaje);
        TextView precio = (TextView)findViewById(R.id.precioInfoViaje);
        TextView coche = (TextView)findViewById(R.id.cocheInfoViaje);
        TextView color = (TextView)findViewById(R.id.colorInfoViaje);

        Button reservar = (Button)findViewById(R.id.submitInfoViaje);

        ParseUser user = viaje.getConductor();


        try {
            String nombreConductor = user.fetchIfNeeded().getString("name");
        } catch (com.parse.ParseException e) {
            Log.e(TAG, "Something has gone terribly wrong with Parse", e);
        }

        Toast.makeText(MostrarViaje.this, viaje.getPrecio(), Toast.LENGTH_SHORT).show();


        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());
        fecha.setText(viaje.getFecha());
        hora.setText(viaje.getHoraSalida());
        conductor.setText(user.getString("username"));
        //nPlazas.setText(viaje.getnPlazasDisp());
        //precio.setText(viaje.getPrecio());

        //coche.setText(viaje.getOrigen());
        //color.setText(viaje.getOrigen());








        //mainLayout.setVisibility(LinearLayout.GONE);


    }
}
