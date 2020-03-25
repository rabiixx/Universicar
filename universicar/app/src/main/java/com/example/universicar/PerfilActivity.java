package com.example.universicar;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.universicar.Models.Coche;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    static final int CAMERA_REQUEST_CODE = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    final String TAG = "debug";

    private final ParseUser user = ParseUser.getCurrentUser();
    private ImageButton moreOptions;
    private File profileImageFile;
    private TextView username;
    private String defaultImagePath;
    private ParseFile parseFile;
    CircleImageView profileImage;
    Uri URI;
    PerfilCarAdapter carAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_perfil_activity);

        final ListView carList = findViewById(R.id.carListProfile);
        profileImage = findViewById(R.id.imagenPerfil);

        ParseFile parseFile = user.getParseFile("imagenPerfil");
        Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(profileImage);

        username = (TextView)findViewById(R.id.usernamePerfil);
        username.setText(user.getUsername());

        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtnPerfil);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadProfileImage();
            }
        });


//        findViewById(R.id.verOpiniones).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PerfilActivity.this, ListaOpinionesActivity.class);
//                intent.putExtra("userId", user.getObjectId());
//                startActivity(intent);
//            }
//        });

        Button btnLogout = (Button)findViewById(R.id.logoutPerfil);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                startActivity(new Intent(PerfilActivity.this, MainActivity.class));
            }
        });


        /* LISTA COCHES USUARIO */

        /* Consultamos los coches que tiene el usuario correspondiente */
        ParseQuery<Coche> query = ParseQuery.getQuery(Coche.class);

        query.whereEqualTo("Conductor", user);

        query.findInBackground(new FindCallback<Coche>() {

            @Override
            public void done(List<Coche> coches, com.parse.ParseException e) {
                if (e == null) {
                    if (coches.isEmpty()) {
                        carList.setVisibility(LinearLayout.GONE);
                    } else {
                        carAdapter = new PerfilCarAdapter(PerfilActivity.this, coches);
                        carList.setAdapter(carAdapter);
                        justifyListViewHeightBasedOnChildren(carList);

                        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Coche coche = (Coche) carList.getItemAtPosition(position);
                                Toast.makeText(PerfilActivity.this, "Selected :" , Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(PerfilActivity.this, AddVehicleActivity.class);
                                i.putExtra("coche", (Serializable) coche);
                                startActivity(i);
                                carAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                } else {
                    Log.d("Coche", "Error: " + e.getMessage());
                }
            }
        });

//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        findViewById(R.id.añadirCochePerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PerfilActivity.this, AddVehicleActivity.class));
                carAdapter.notifyDataSetChanged();
                justifyListViewHeightBasedOnChildren(carList);

            }
        });


        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.defavatar);

//        boolean doSave = true;
//        if (!dir.exists()) {
//            Log.i(TAG, "BUGUGUGUGUGU");
//            doSave = dir.mkdirs();
//        }
//
//        if (doSave) {
            saveBitmapToFile( getExternalFilesDir(Environment.DIRECTORY_PICTURES),"defaultProfileImage.png",bm,Bitmap.CompressFormat.PNG,100);
//        }
//        else {
//            Log.e("app","Couldn't create target directory.");
//        }


    }


    public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm, Bitmap.CompressFormat format, int quality) {

        File imageFile = new File(dir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format,quality,fos);

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


    public void loadProfileImage() {
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

    private void captureFromCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            profileImageFile = null;
            try {
                profileImageFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (profileImageFile != null) {
                Uri profileImageURI = FileProvider.getUriForFile(this, "com.example.universicar.fileprovider", profileImageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void captureFromGallery() {

        Intent intent= new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");

        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK ){

            switch (requestCode) {
                case CAMERA_REQUEST_CODE:

                    ParseFile parseFile = new ParseFile(profileImageFile);

                    try {
                        parseFile.save();
                        user.put("imagenPerfil", parseFile);
                        user.saveInBackground();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Picasso.get().load(user.getParseFile("imagenPerfil").getUrl()).error(R.mipmap.ic_launcher).into(profileImage);

                    break;
                case GALLERY_REQUEST_CODE:

                    Uri selectedImage = data.getData();

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    String imgpath = cursor.getString(columnIndex);
                    cursor.close();

                    profileImageFile = new File(imgpath);
                    parseFile = new ParseFile(profileImageFile);

                    try {
                        parseFile.save();
                        user.put("imagenPerfil", parseFile);
                        user.saveInBackground();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Picasso.get().load(user.getParseFile("imagenPerfil").getUrl()).error(R.mipmap.ic_launcher).into(profileImage);

                    break;
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


    //    public void showPopup(View view) {
//        PopupMenu popup = new PopupMenu(this, view);
//        popup.getMenuInflater().inflate(R.menu.popup_menu_perfil, popup.getMenu());
//        popup.show();
//    }

    public void showMenuProfileImage(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(PerfilActivity.this , "MENUEKJ 1", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.editMenu:
                        loadProfileImage();
                        return true;
                    case R.id.deleteMenu:

                        profileImageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "defaultProfileImage.png");
                        ParseFile parseFile2 = new ParseFile(profileImageFile);

                        try {
                            parseFile2.save();
                            user.put("imagenPerfil", parseFile2);
                            user.saveInBackground();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Picasso.get().load(user.getParseFile("imagenPerfil").getUrl()).error(R.mipmap.ic_launcher).into(profileImage);

                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.popup_menu_perfil);
        popup.show();
    }


    public void showMenuCars(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.popup_menu_cars);
        popup.show();
    }




//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        Toast.makeText(this, "MENUEKJ", Toast.LENGTH_SHORT).show();
//        switch (item.getItemId()) {
//            case R.id.editMenu:
//                loadProfileImage();
//                return true;
//            case R.id.deleteMenu:
//
//                profileImageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "defaultProfileImage.png");
//                ParseFile parseFile2 = new ParseFile(profileImageFile);
//
//                try {
//                    parseFile2.save();
//                    user.put("imagenPerfil", parseFile2);
//                    user.saveInBackground();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                Picasso.get().load(user.getParseFile("imagenPerfil").getUrl()).error(R.mipmap.ic_launcher).into(profileImage);
//
//                return true;
//            default:
//                return false;
//        }
//    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}

//https://medium.com/@sriramaripirala/android-10-open-failed-eacces-permission-denied-da8b630a89df





/* UPLOAD IMAGES WITH FIREBASE STORAGE AND PICCASSO */


    // Firebase
//    FirebaseStorage storage;
//    StorageReference storageReference;

// Firebase Init
//        storage = FirebaseStorage.getInstance();
//                storageReference = storage.getReference();


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
//                    Picasso.get().load(photoURI).fit().centerInside().into(iv);
//                    iv.setImageURI(Uri.parse(cameraFilePath));

