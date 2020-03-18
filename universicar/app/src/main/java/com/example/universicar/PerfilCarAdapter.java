package com.example.universicar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universicar.Models.Coche;

import java.util.List;

public class PerfilCarAdapter extends BaseAdapter {

    private Context context;
    private List<Coche> coches;
    private LayoutInflater inflater;

    public PerfilCarAdapter(Context appContext, List<Coche> coches) {
        this.context = appContext;
        this.coches = coches;
        inflater = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return coches.size();
    }

    @Override
    public Coche getItem(int position) {
        return coches.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.perfil_car_adapter, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.carIconProfile);
        TextView brand = (TextView) convertView.findViewById(R.id.carBrandProfile);
        TextView color = (TextView) convertView.findViewById(R.id.carColorProfile);

        switch (coches.get(position).getTipoCoche()) {
            case "Compacto":
                icon.setImageResource(R.drawable.ic_car_compacto_80dp);
                break;
            case "Deportivo":
                icon.setImageResource(R.drawable.ic_car_deportivo_80dp);
                break;
            case "Familiar":
                icon.setImageResource(R.drawable.ic_car_familiar_80dp);
                break;
            case "Descapotable":
                icon.setImageResource(R.drawable.ic_car_descapotable_80dp);
                break;
            case "Biplaza":
                icon.setImageResource(R.drawable.ic_car_biplaza_80dp);
                break;
            case "Todoterrno":
                icon.setImageResource(R.drawable.ic_car_todoterreno_80dp);
                break;
            default:
                Toast.makeText(context, "Han error has ocurred", Toast.LENGTH_SHORT).show();
                break;
        }


        String[] colors = {"Negro", "Azul", "Verde", "Gris", "Naranja", "Rosa", "Rojo", "Blanco", "Amarillo"};

        switch (coches.get(position).getColor()) {
            case "Negro":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.black));
                break;
            case "Azul":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.blue));
                break;
            case "Verde":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.green));
                break;
            case "Gris":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.grey));
                break;
            case "Naranja":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.oranje));
                break;
            case "Rosa":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.pink));
                break;
            case "Rojo":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.red));
                break;
            case "Blanco":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.snow));
                break;
            case "Amarillo":
                icon.setColorFilter(convertView.getContext().getResources().getColor(R.color.yellow));
                break;
            default:
                Toast.makeText(context, "Han error has ocurred", Toast.LENGTH_SHORT).show();
                break;
        }

        //icon.setColorFilter(convertView.getContext().getResources().getColor(colorId));
        brand.setText(coches.get(position).getMarca());
        color.setText(coches.get(position).getColor());



        return convertView;

    }
}

