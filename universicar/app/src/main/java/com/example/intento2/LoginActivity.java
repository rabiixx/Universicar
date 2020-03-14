package com.example.intento2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseUser;

import java.text.ParseException;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submit = (Button)findViewById(R.id.login);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText username = (EditText)findViewById(R.id.username);
                final EditText password = (EditText)findViewById(R.id.password);

                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() ) {
                    Toast.makeText(LoginActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, com.parse.ParseException e) {
                            if (user != null) {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }

}
