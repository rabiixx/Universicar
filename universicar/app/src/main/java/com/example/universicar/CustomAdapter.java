package com.example.universicar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.universicar.Models.Viaje;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends ArrayAdapter<Viaje> {

    private static final String TAG = "debug";
    List<Viaje> viajes;
    int code = 1;

    CustomAdapter(Context context,  List<Viaje> viajes, int code) {
        super(context, 0, viajes);
        this.viajes = viajes;
        this.code = code;
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

        TextView origen = convertView.findViewById(R.id.txtSrc);
        TextView destino = convertView.findViewById(R.id.txtDest);
        TextView username = convertView.findViewById(R.id.username);
        TextView precio = convertView.findViewById(R.id.price);
        ImageView disp = convertView.findViewById(R.id.dispImage);

        if (code == 1) {
            TextView fecha = convertView.findViewById(R.id.fechaListaViajes);
            fecha.setText(viaje.getFecha());
            Date date = viaje.getFechaDate();
            Date currentTime = Calendar.getInstance().getTime();

            if ( date.after(currentTime) ) {
                disp.setColorFilter(getContext().getResources().getColor(R.color.green));
            } else {
                disp.setColorFilter(getContext().getResources().getColor(R.color.red));
            }

        } else {
            disp.setVisibility(View.GONE);
        }



        ParseFile parseFile = user.getParseFile("imagenPerfil");
        CircleImageView imagenPerfil = convertView.findViewById(R.id.profileImageListaViajes);
        Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(imagenPerfil);

        origen.setText(viaje.getOrigen());
        destino.setText(viaje.getDestino());
        username.setText(user.getString("username"));
        precio.setText(String.valueOf(viaje.getPrecio()));

        return convertView;
    }
}
