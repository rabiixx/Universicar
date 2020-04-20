package com.example.universicar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        TextView login = findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/defaultProfileImage.png");
        if ( !getExternalFilesDir(Environment.DIRECTORY_PICTURES).exists() ) {

        }

        Button submit = findViewById(R.id.btnLogin);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = findViewById(R.id.txtName);
                EditText emailEditText = findViewById(R.id.txtEmail);
                EditText pwdEditText = findViewById(R.id.txtPwd);

                // Create Parse User
                ParseUser user = new ParseUser();

                // Set core properties
                user.setUsername(nameEditText.getText().toString());
                user.setEmail(emailEditText.getText().toString());
                user.setPassword(pwdEditText.getText().toString());

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            /* Save default Profile Image */
                            File defaultProfileImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/defaultProfileImage.png");
                            if ( !defaultProfileImage.exists() ) {
                                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.defavatar);
                                saveBitmapToFile( getExternalFilesDir(Environment.DIRECTORY_PICTURES),"defaultProfileImage.png", bm, Bitmap.CompressFormat.PNG,100);
                            }

                            ParseFile parseFile = new ParseFile(defaultProfileImage);

                            try {
                                parseFile.save();
                                ParseUser user = ParseUser.getCurrentUser();
                                user.put("imagenPerfil", parseFile);
                                user.saveInBackground();
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        } else {
                            Log.i("debug", e.getMessage());
                        }
                    }
                });
            }
        });


    }

    public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm, Bitmap.CompressFormat format, int quality) {

        File imageFile = new File(dir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format, quality, fos);

            fos.close();

            return true;
        }
        catch (IOException e) {
            Log.e("app",e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }
}
