package com.example.universicar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Opinion;

import java.util.List;

public class ListaOpinionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_opiniones_activity);

        @SuppressWarnings("unchecked") final List<Opinion> opiniones = (List<Opinion>) getIntent().getSerializableExtra("opiniones");

        ListView listaOpiniones = findViewById(R.id.listaOpinionesPerfil);
        AdapterOpiniones adapterOpiniones = new AdapterOpiniones(ListaOpinionesActivity.this, opiniones);
        listaOpiniones.setAdapter(adapterOpiniones);

        RatingBar puntuacion = findViewById(R.id.puntuacionListaOpiniones);
        puntuacion.setRating(puntuacionAVG(opiniones));

        TextView habilidad = findViewById(R.id.habilidadListaOpiniones);

        switch ((int) Math.round(habilidadAVG(opiniones))) {
            case 1:
                habilidad.append("Mal");
                break;
            case 2:
                habilidad.append("Regular");
                break;
            case 3:
                habilidad.append("Bien");
                break;
            case 4:
                habilidad.append("Muy Bien");
                break;
        }
        //        justifyListViewHeightBasedOnChildren(listaOpiniones);
    }

    private double habilidadAVG(List <Opinion> opiniones) {
        int sum = 0;
        if(!opiniones.isEmpty()) {
            for (Opinion opinion : opiniones) {
                sum += opinion.getHabilidadConduccion();
            }
            return (double) sum / opiniones.size();
        }
        return sum;
    }


    private float puntuacionAVG(List <Opinion> opiniones) {
        int sum = 0;
        if(!opiniones.isEmpty()) {
            for (Opinion opinion : opiniones) {
                sum += opinion.getPuntuacion();
            }
            return (float) sum / opiniones.size();
        }
        return sum;
    }


    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}