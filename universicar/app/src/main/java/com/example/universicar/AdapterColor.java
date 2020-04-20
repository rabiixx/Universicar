package com.example.universicar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterColor extends BaseAdapter {

    private Context context;
    private int[] colorIcons;
    private String[] colors;
    private LayoutInflater inflater;

    public AdapterColor(Context appContext, int[] colorIcons, String[] colors) {
        this.context = appContext;
        this.colorIcons = colorIcons;
        this.colors = colors;
        inflater = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return colorIcons.length;
    }

    @Override
    public String getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_colores, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.colorIcon);
        TextView color = (TextView) convertView.findViewById(R.id.color);
        icon.setImageResource(colorIcons[position]);
        color.setText(colors[position]);
        return convertView;

    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
//
//        TextView textview1 = (TextView) v.findViewById(R.id.text1);
//        TextView textview2 = (TextView) v.findViewById(R.id.text2);
//
//        Log.v("textview1",textview1.getText().toString().trim());
//        Log.v("textview2",textview2.getText().toString().trim());
//
//    }


}
