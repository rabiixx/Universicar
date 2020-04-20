package com.example.universicar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universicar.Models.Coche;
import com.parse.DeleteCallback;
import com.parse.ParseException;

import java.io.Serializable;
import java.util.List;

public class AdapterCoche extends BaseAdapter {

    private Context context;
    private List<Coche> coches;
    private LayoutInflater inflater;

    public AdapterCoche(Context appContext, List<Coche> coches) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_lista_coches, null);
        ImageView icon = convertView.findViewById(R.id.carIconProfile);
        TextView brand = convertView.findViewById(R.id.carBrandProfile);
        ImageView brandIcon = convertView.findViewById(R.id.carBrandIconProfile);
        TextView color =  convertView.findViewById(R.id.carColorProfile);
        ImageView btn = convertView.findViewById(R.id.moreOptions);

        switch (coches.get(position).getTipoCoche()) {
            case "Compacto":
                icon.setImageResource(R.drawable.ic_car_compacto_60dp);
                break;
            case "Deportivo":
                icon.setImageResource(R.drawable.ic_car_deportivo_60dp);
                break;
            case "Familiar":
                icon.setImageResource(R.drawable.ic_car_familiar_60dp);
                break;
            case "Descapotable":
                icon.setImageResource(R.drawable.ic_car_descapotable_60dp);
                break;
            case "Biplaza":
                icon.setImageResource(R.drawable.ic_car_biplaza_60dp);
                break;
            case "Todoterrno":
                icon.setImageResource(R.drawable.ic_car_todoterreno_60dp);
                break;
            default:
                Toast.makeText(context, "Han error has ocurred", Toast.LENGTH_SHORT).show();
                break;
        }

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
        String brandIconStr = "ic_brand_" + coches.get(position).getMarca().toLowerCase();
        int brandIconId = convertView.getContext().getResources().getIdentifier(brandIconStr, "drawable", convertView.getContext().getPackageName());

        brandIcon.setImageResource(brandIconId);
        brand.setText(coches.get(position).getMarca());
        color.setText(coches.get(position).getColor());

        final View finalConvertView = convertView;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(parent.getContext(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editarCoche:
                                Intent i = new Intent(parent.getContext(), AnadirCocheActivity.class);
                                i.putExtra("coche", (Serializable) getItem(position));
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(i);
                                ((Activity)context).finish();
                                return true;
                            case R.id.eliminarCoche:
                                getItem(position).deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Toast.makeText(context, "Coche eliminado correctamente", Toast.LENGTH_SHORT).show();
                                        coches.remove(position);
                                        notifyDataSetChanged();
//                                        MiPerfilActivity.justifyListViewHeightBasedOnChildren(coches);
                                    }

                                });
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.popup_menu_cars);
                popup.show();
            }
        });

        return convertView;

    }
}

