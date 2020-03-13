package com.example.intento2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.intento2.Models.Viaje;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Viaje> {

    CustomAdapter(Context context,  List<Viaje> viajes) {
        super(context, 0, viajes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viaje viaje = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view, parent, false);

        TextView origen = (TextView) convertView.findViewById(R.id.txtSrc);
        TextView destino = (TextView) convertView.findViewById(R.id.txtDest);

        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());

        return convertView;
    }
}
