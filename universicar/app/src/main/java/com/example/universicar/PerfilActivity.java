package com.example.universicar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Imagen;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.ui.widget.ParseImageView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private ParseUser user;
    private Uri filePath;
    private ImageButton moreOptions;
    File photoFile;
    Imagen imagen;
    Uri photoURI;

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

        ParseImageView piv = (ParseImageView)findViewById(R.id.imagenPerfil);

        piv.setOnClickListener(new View.OnClickListener() {
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
                            captureFromCamera();
                        } else if (options[item].equals("Escoge desde la galeria")) {
                            captureFromGallery();
                        } else if (options[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });


        user = ParseUser.getCurrentUser();

//        loadProfilePhoto();



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



        /* LISTA COCHES USUARIO */

        /* Consultamos los coches que tiene el usuario correspondiente */
        ParseQuery<Coche> query = ParseQuery.getQuery(Coche.class);

        query.whereEqualTo("Conductor", user);

        query.findInBackground(new FindCallback<Coche>() {

            final ListView carList = (ListView) findViewById(R.id.carListProfile);

            @Override
            public void done(List<Coche> coches, com.parse.ParseException e) {
                if (e == null) {
                    if (coches.isEmpty()) {
                        carList.setVisibility(LinearLayout.GONE);
                    } else {
                        PerfilCarAdapter customAdapter = new PerfilCarAdapter(PerfilActivity.this, coches);
                        carList.setAdapter(customAdapter);

                        //loadCarSettings();
                        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Coche coche = (Coche) carList.getItemAtPosition(position);
                                Toast.makeText(PerfilActivity.this, "Selected :" , Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(PerfilActivity.this, AddVehicleActivity.class);
                                i.putExtra("coche", (Serializable) coche);
                                startActivity(i);
                            }
                        });
                    }
                } else {
                    Log.d("Coche", "Error: " + e.getMessage());
                }
            }
        });
    }


    /* Getting back selected images */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case CAMERA_CODE:
//                    if (resultCode == RESULT_OK && data != null) {
//                        filePath =  data.getData();
//                        Bitmap selectedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
//                    }
//                    break;
//                case GALLERY_CODE:
//                    if (resultCode == RESULT_OK && data != null && data.getData() != null) {
//                        filePath =  data.getData();
//                        uploadImage();
//                    }
//                    break;
//            }
//        }
//    }


    static final int CAMERA_REQUEST_CODE = 1;
    static final int GALLERY_REQUEST_CODE = 2;


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
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void captureFromGallery() {

        //Create an Intent with action as ACTION_PICK
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");

        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//        String[] mimeTypes = {"image/jpeg", "image/png"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK ){
            ParseFile parseFile;
//            Toast.makeText(this, "ONACTIVITYRESULT", Toast.LENGTH_SHORT).show();


            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    Toast.makeText(this, cameraFilePath, Toast.LENGTH_LONG).show();

                    parseFile = new ParseFile(photoFile);
                    imagen = new Imagen();
                    imagen.setMedia(parseFile);
                    imagen.saveInBackground();
                    ParseImageView iv1 = (ParseImageView) findViewById(R.id.imagenPerfil);
                    iv1.setParseFile(imagen.getMedia());
                    iv1.loadInBackground();
//                    Picasso.get().load(photoURI).fit().centerInside().into(iv);
//                    iv.setImageURI(Uri.parse(cameraFilePath));
                    break;
                case GALLERY_REQUEST_CODE:
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(PerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "NO TENEMOS PERMISOS DE READ", Toast.LENGTH_SHORT).show();
                    }

                    if (ContextCompat.checkSelfPermission(PerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "NO TENEMOS PERMISOS DE WRITE", Toast.LENGTH_SHORT).show();
                    }
                    

                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();

//                    String imagepath = getPath(selectedImage);

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    File f = new File(imgDecodableString);
//                    "/storage/emulated/0/Pictures/Screenshots/Screenshot_20200319-133133_UNIVERSICAR.png"
                    //File f = new File("file:///storage/emulated/0/Android/data/com.example.universicar/files/Pictures/JPEG_20200319_141022_753250698868564349.jpg");
//                    File photoFile2 = new File(imagepath);
                    parseFile = (ParseFile) new ParseFile(f);
                    imagen = new Imagen();
                    imagen.setMedia(parseFile);
                    final String TAG = "universicar";
                    Log.i(TAG, "HOLA");


//                    imagen.saveInBackground();

                    imagen.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.i(TAG, "??????");
                            if (e == null) {
                                Log.i(TAG, "SAVED");
                            } else {
                                Log.i(TAG, "NOT SAVED");
                                Log.i(TAG, e.getMessage());
                            }
                        }
                    });

                    Log.i(TAG, "??????");



                    ParseImageView iv2 = (ParseImageView) findViewById(R.id.imagenPerfil);
                    iv2.setParseFile(imagen.getMedia());
                    iv2.loadInBackground();
                    break;

            }
        }
    }



//    public String getPath(Uri uri) {
//        Bitmap yourSelectedImage;
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(projection[0]);
//        String filePath = cursor.getString(columnIndex);
//        cursor.close();
//        yourSelectedImage = BitmapFactory.decodeFile(filePath);
//        return cursor.getString(column_index);
//    }



//    public void uploadImage() {
//
//        if (filePath != null) {
//            Toast.makeText(this, "ENTERED 1", Toast.LENGTH_SHORT).show();
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Subiendo...");
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child("images/" + user.getUsername() + "Profile");
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(PerfilActivity.this, "Subida Correctamente", Toast.LENGTH_SHORT).show();
//                            loadProfilePhoto();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(PerfilActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage("Subida " + (int)progress + "%");
//                        }
//                    });
//        }
//    }

//
//    public void loadProfilePhoto() {
//        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
//        storageReference.child("images").child(user.getUsername() + "Profile").getDownloadUrl()
//            .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Picasso.get().load(uri).fit().centerInside().into(profilePhoto);
//                }
//            })
//            .addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    profilePhoto.setImageResource(R.drawable.defavatar);
//                }
//            });
//
//    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        //popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.getMenuInflater().inflate(R.menu.popup_menu_perfil, popup.getMenu());
        popup.show();
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

}



