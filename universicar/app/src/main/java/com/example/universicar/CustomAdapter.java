package com.example.universicar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.universicar.Models.Viaje;
import com.parse.ParseUser;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Viaje> {

    private static final String TAG = "debug";
    List<Viaje> viajes;

    CustomAdapter(Context context,  List<Viaje> viajes) {
        super(context, 0, viajes);
        this.viajes = viajes;
    }

    @Override
    public int getCount() {
        return viajes.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viaje viaje = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view, parent, false);

        ParseUser user = viaje.getConductor();

        try {
            String nombreConductor = user.fetchIfNeeded().getString("name");
        } catch (com.parse.ParseException e) {
            Log.e(TAG, "Something has gone terribly wrong with Parse", e);
        }

        TextView origen = (TextView) convertView.findViewById(R.id.txtSrc);
        TextView destino = (TextView) convertView.findViewById(R.id.txtDest);
        TextView username = (TextView)convertView.findViewById(R.id.username);
        TextView precio = (TextView)convertView.findViewById(R.id.price);

        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());
        username.setText(user.getString("username"));
        precio.setText(String.valueOf(viaje.getPrecio()));

        return convertView;
    }
}
