package com.example.universicar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.universicar.Models.Imagen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class testActivity extends AppCompatActivity {

    File photoFile;
    Imagen imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        Button btn = (Button)findViewById(R.id.btnChooseImage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureFromCamera();
            }
        });


    }

    static final int REQUEST_TAKE_PHOTO = 1;


    private void captureFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.universicar.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    private String cameraFilePath;
    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";

        //This is the directory in which the file will be created. This is the default location of Camera photos

//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(nvironment.DIRECTORY_DCIM), "Camera");

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user captures an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    ParseFile parseFile = new ParseFile(photoFile);
                    imagen = new Imagen();
                    imagen.setMedia(parseFile);
                    imagen.saveInBackground();
                    ParseImageView iv = (ParseImageView) findViewById(R.id.imageView);
                    iv.setParseFile(imagen.getMedia());
                    iv.loadInBackground();

//                    iv.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }
    }


}

