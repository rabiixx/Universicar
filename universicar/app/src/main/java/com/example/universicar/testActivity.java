package com.example.universicar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.universicar.Models.Imagen;
import com.parse.ParseFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.parse.ui.widget.ParseImageView;

public class testActivity extends AppCompatActivity {

    File photoFile;
    Imagen imagen;
    Uri photoURI;

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
        
        ParseImageView piv = (ParseImageView)findViewById(R.id.imgView);
        
        piv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(testActivity.this, "JHWSDBHJSDHJ", Toast.LENGTH_SHORT).show();
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
                photoURI = FileProvider.getUriForFile(this, "com.example.universicar.fileprovider", photoFile);
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
                    ParseImageView iv = (ParseImageView) findViewById(R.id.imgView);
                    iv.setParseFile(imagen.getMedia());
                    iv.loadInBackground();
//                    Picasso.get().load(photoURI).fit().centerInside().into(iv);
//                    iv.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }
    }

}

