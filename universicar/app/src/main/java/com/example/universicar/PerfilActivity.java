package com.example.universicar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

public class PerfilActivity extends AppCompatActivity {

    private ParseUser user;
    private static final int CAMERA_CODE = 974;
    private static final int GALLERY_CODE = 603;
    private Uri filePath;
    private ImageView profilePhoto;

    // Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Firebase Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePhoto = (ImageView)findViewById(R.id.profileImage);
        user = ParseUser.getCurrentUser();

        loadProfilePhoto();

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = { "Hacer Foto", "Escoge desde la galeria", "Cancelar" };

                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
                builder.setTitle("Escoge tu foto de perfil");
                builder.setIcon(R.drawable.ic_calendar_30dp);

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @SuppressLint("IntentReset")
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Hacer Foto")) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, CAMERA_CODE);
                        } else if (options[item].equals("Escoge desde la galeria")) {
                            @SuppressLint("IntentReset") Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            i.setType("image/*");
                            // String[] mimeTypes = {"image/jpeg", "image/png"};
                            //i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            startActivityForResult(i, GALLERY_CODE);
                        } else if (options[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });


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


    /* Getting back selected images */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_CODE:
                    if (resultCode == RESULT_OK && data != null) {
                        filePath =  data.getData();
                        Bitmap selectedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    }
                    break;
                case GALLERY_CODE:
                    if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                        filePath =  data.getData();
                        uploadImage();
                    }
                    break;
            }
        }
    }



    public void uploadImage() {

        if (filePath != null) {
            Toast.makeText(this, "ENTERED 1", Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + user.getUsername() + "Profile");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(PerfilActivity.this, "Subida Correctamente", Toast.LENGTH_SHORT).show();
                            loadProfilePhoto();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PerfilActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Subida " + (int)progress + "%");
                        }
                    });
        }
    }


public void loadProfilePhoto() {
    // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
    storageReference.child("images").child(user.getUsername() + "Profile").getDownloadUrl()
        .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(profilePhoto);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                profilePhoto.setImageResource(R.drawable.defavatar);
            }
        });

}



}
