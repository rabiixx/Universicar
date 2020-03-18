package com.example.universicar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parse.Parse;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class testActivity extends AppCompatActivity {

    // https://medium.com/@hasangi/capture-image-or-choose-from-gallery-photos-implementation-for-android-a5ca59bc6883
    private static final int CAMERA_CODE = 974;
    private static final int GALLERY_CODE = 603;
    private Uri filePath;
    private ParseUser user;

    // Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        final Button btn = (Button)findViewById(R.id.btnChooseImage);
        final Button uploadBtn = (Button)findViewById(R.id.btnUploadImage);
        final ImageView imageView = (ImageView) findViewById(R.id.imgView);
        user = ParseUser.getCurrentUser();


        // Firebase Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
        storageReference.child("images").child(user.getUsername() + "Profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(imageView);
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {

                final CharSequence[] options = { "Hacer Foto", "Escoge desde la galeria", "Cancelar" };

                AlertDialog.Builder builder = new AlertDialog.Builder(testActivity.this);
                builder.setTitle("Escoge tu foto de perfil");
                builder.setIcon(R.drawable.ic_calendar_30dp);

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Hacer Foto")) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, CAMERA_CODE);
                        } else if (options[item].equals("Escoge desde la galeria")) {
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                       Toast.makeText(testActivity.this, "Subida Correctamente", Toast.LENGTH_SHORT).show();
                   }
               })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(testActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
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


    /* Getting back selected images */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_CODE:
                    Toast.makeText(this, "CAMERA", Toast.LENGTH_SHORT).show();

                    if (resultCode == RESULT_OK && data != null) {
                        filePath =  data.getData();
                        Bitmap selectedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                       // imageView.setImageBitmap(selectedImage);
                    }
                    break;
                case GALLERY_CODE:

                    if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                        filePath =  data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                            //imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }




//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (filePath != null) {
//                            Cursor cursor = getContentResolver().query(filePath, filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                            }
//                        }
                    }
                    break;
            }
        }
    }
}
