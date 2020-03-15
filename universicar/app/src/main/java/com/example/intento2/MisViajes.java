package com.example.intento2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MisViajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_viajes);

        new ParseQuery("Post")
                .whereEqualTo("author",  ParseObject.createWithoutData(Author.class, "yiadffao89 "))
                .find();













    }
}
