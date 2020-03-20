package com.example.universicar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.example.universicar.Models.Opinion;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaOpinionesActivity extends AppCompatActivity {

    private static final String TAG = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_opiniones);

        final String userId = getIntent().getStringExtra("userId");

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);

        query.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {

                    ParseQuery<Opinion> query = ParseQuery.getQuery(Opinion.class);

                    query.whereEqualTo("usuario", user);

                    query.findInBackground(new FindCallback<Opinion>() {
                        public void done(List<Opinion> opiniones, ParseException e) {
                            if (e == null) {
                                final ListView listaOpiniones = findViewById(R.id.listaOpiniones);
                                AdapterOpiniones adapterOpiniones = new AdapterOpiniones(ListaOpinionesActivity.this, opiniones);
                                listaOpiniones.setAdapter(adapterOpiniones);

//                                ImageView iv = findViewById(R.id.hola2);
//                                ParseUser user = opiniones.get(0).getCreador();
//                                try {
//                                    String username = user.fetchIfNeeded().getUsername();
//
//                                    ParseFile pf = user.getParseFile("imagenPerfil");
//                                    String url = pf.getUrl();
//                                    Log.i(TAG, url);
////
////
//                                    Glide.with(ListaOpinionesActivity.this).load("http://rabiixxserver.herokuapp.com/parse/files/universicar/5511d40e8eee17ee668432180a739b6f_JPEG_20200320_230636_8746756111615888408.jpg").into(iv);
////
////
////                                    Picasso.get().load("https://rabiixxserver.herokuapp.com/parse/files/universicar/5511d40e8eee17ee668432180a739b6f_JPEG_20200320_230636_8746756111615888408.jpg").into(iv);
//                                } catch (ParseException ex) {
//                                    ex.printStackTrace();
//                                }


                            } else {
                                Log.d("item", "Error: " + e.getMessage());
                            }
                        }
                    });

                } else {
                    e.printStackTrace();
                }
            }
        });


    }
}
