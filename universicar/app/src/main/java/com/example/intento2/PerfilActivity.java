package com.example.intento2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

import org.w3c.dom.Text;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button btnLogout = (Button)findViewById(R.id.logoutPerfil);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                startActivity(new Intent(PerfilActivity.this, MainActivity.class));
            }
        });

        Button btnAddCar = (Button)findViewById(R.id.añadirCochePerfil);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PerfilActivity.this, AddVehicleActivity.class));
            }
        });

        final TextView username = (TextView)findViewById(R.id.usernamePerfil);

        ParseUser user = ParseUser.getCurrentUser();

        username.setText(user.getUsername());

        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtnPerfil);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



}
