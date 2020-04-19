package com.example.universicar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.universicar.Models.Opinion;
import com.parse.ParseFile;
import com.parse.ParseUser;
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
            ParseFile parseFile = user.fetchIfNeeded().getParseFile("imagenPerfil");

            viewHolder.username.setText(username);
            viewHolder.titulo.setText(opinion.getTitulo());
            viewHolder.descripcion.setText(opinion.getDescripcion());
            viewHolder.puntuacion.setRating(opinion.getPuntuacion().floatValue());

            Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(viewHolder.fotoPerfil);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }



        return convertView;
    }
}
