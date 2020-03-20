package com.example.universicar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universicar.Models.Opinion;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ui.widget.ParseImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterOpiniones extends ArrayAdapter<Opinion> {

    private static final String TAG = "debug";

    // View lookup cache
    private static class ViewHolder {
        TextView username;
        CircleImageView fotoPerfil;
        TextView titulo;
        TextView descripcion;
        RatingBar puntuacion;
    }

    public AdapterOpiniones(Context context, List<Opinion> opiniones) {
        super(context, R.layout.opinion_adapter, opiniones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Opinion opinion = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.opinion_adapter, parent, false);

            viewHolder.username = convertView.findViewById(R.id.usernameOpiniones);
            viewHolder.titulo = convertView.findViewById(R.id.tituloOpiniones);
            viewHolder.puntuacion = convertView.findViewById(R.id.puntuacionOpiniones);
            viewHolder.descripcion = convertView.findViewById(R.id.descripcionOpiniones);
            viewHolder.fotoPerfil = convertView.findViewById(R.id.profile_image);

            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ParseUser user = opinion.getCreador();

        try {
            String username = user.fetchIfNeeded().getUsername();
            ParseFile i = user.fetchIfNeeded().getParseFile("imagenPerfil");
            Log.i(TAG, "hack" + i.getUrl());

            viewHolder.username.setText(username);
            viewHolder.titulo.setText(opinion.getTitulo());
            viewHolder.descripcion.setText(opinion.getDescripcion());
            viewHolder.puntuacion.setRating(opinion.getPuntuacion().floatValue());
           // Picasso.get().load(i.getUrl()).fit().centerInside().into(viewHolder.fotoPerfil);


            Picasso.get().load("https://rabiixxserver.herokuapp.com/parse/files/universicar/5511d40e8eee17ee668432180a739b6f_JPEG_20200320_230636_8746756111615888408.jpg").into(viewHolder.fotoPerfil);

//            Picasso.with(getApplicationContext()).load(imageUrl)
//                    .placeholder(R.drawable.images).error(R.drawable.ic_launcher)
//                    .into(viewHolder.fotoPerfil);
//
//            viewHolder.fotoPerfil.setParseFile(i);
//            viewHolder.fotoPerfil.loadInBackground();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }



        return convertView;
    }
}
