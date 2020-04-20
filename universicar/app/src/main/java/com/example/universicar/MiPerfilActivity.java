package com.example.universicar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DataSetObserver;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Opinion;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MiPerfilActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    static final int CAMERA_REQUEST_CODE = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_CODE = 48;
    private static final int READ_STORAGE_PERMISSION_CODE = 501;
    private static final int WRITE_STORAGE_PERMISSION_CODE = 747;


    final String TAG = "debug";

    private final ParseUser user = ParseUser.getCurrentUser();
    private ImageButton moreOptions;
    private File profileImageFile;
    private TextView username;
    private TextView usernameTv;
    private TextView emailTv;
    private String defaultImagePath;
    private ParseFile parseFile;
    private TextView opinionesTv;
    private TextView habilidadCondTv;
    CircleImageView profileImage;
    AdapterCoche carAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        final ListView carList = findViewById(R.id.carListProfile);
        profileImage = findViewById(R.id.imagenPerfil);

        ParseFile parseFile = user.getParseFile("imagenPerfil");
        Picasso.get().load(parseFile.getUrl()).error(R.mipmap.ic_launcher).into(profileImage);

        username = findViewById(R.id.usernamePerfil);
        username.setText(user.getUsername());

        usernameTv = findViewById(R.id.usernamePerfil2);
        usernameTv.setText(user.getUsername());

        emailTv = findViewById(R.id.emailPerfil);
        emailTv.setText(user.getEmail());

        ImageButton backBtn = findViewById(R.id.backBtnPerfil);

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

        /* Consultamos las opiniones sobre el usuario */
        ParseQuery<Opinion> query = ParseQuery.getQuery(Opinion.class);
        query.whereEqualTo("usuario", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<Opinion>() {
            public void done(final List<Opinion> opiniones, ParseException e) {
                if (e == null) {

                    opinionesTv = findViewById(R.id.opinionesTvMiPerfil);
                    float puntuacionAVG = PerfilActivity.puntuacionAVG(opiniones);
                    String str = puntuacionAVG + "/5 - " + opiniones.size() + " opiniones";
                    opinionesTv.setText(str);

                    findViewById(R.id.LlOpinionesPerfil).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MiPerfilActivity.this, ListaOpinionesActivity.class);
                            i.putExtra("opiniones", (Serializable) opiniones);
                            startActivity(i);
                        }
                    });

                    findViewById(R.id.verOpinionesPerfil).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MiPerfilActivity.this, ListaOpinionesActivity.class);
                            i.putExtra("opiniones", (Serializable) opiniones);
                            startActivity(i);
                        }
                    });

                    habilidadCondTv = findViewById(R.id.habilidadCondMiPerfil);

                    switch ((int) Math.round(PerfilActivity.habilidadAVG(opiniones))) {
                        case 1:
                            habilidadCondTv.append("Mal");
                            break;
                        case 2:
                            habilidadCondTv.append("Regular");
                            break;
                        case 3:
                            habilidadCondTv.append("Bien");
                            break;
                        case 4:
                            habilidadCondTv.append("Muy Bien");
                            break;
                    }

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


        Button btnLogout = (Button)findViewById(R.id.logoutPerfil);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(MiPerfilActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


        /* LISTA COCHES USUARIO */

        /* Consultamos los coches que tiene el usuario correspondiente */
        ParseQuery<Coche> queryCoches = ParseQuery.getQuery(Coche.class);

        queryCoches.whereEqualTo("Conductor", user);

        queryCoches.findInBackground(new FindCallback<Coche>() {

            @Override
            public void done(List<Coche> coches, com.parse.ParseException e) {
                if (e == null) {
                    if (coches.isEmpty()) {
                        carList.setVisibility(LinearLayout.GONE);
                    } else {
                        carAdapter = new AdapterCoche(MiPerfilActivity.this, coches);
                        carList.setAdapter(carAdapter);
                        justifyListViewHeightBasedOnChildren(carList);

                        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Coche coche = (Coche) carList.getItemAtPosition(position);
                                Intent i = new Intent(MiPerfilActivity.this, AnadirCocheActivity.class);
                                i.putExtra("coche", (Serializable) coche);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        });

                        carAdapter.registerDataSetObserver(new DataSetObserver() {
                            @Override
                            public void onChanged() {
                                justifyListViewHeightBasedOnChildren(carList);
                            }
                        });
                    }
                } else {
                    Log.d("Coche", "Error: " + e.getMessage());
                }
            }
        });

        findViewById(R.id.añadirCochePerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MiPerfilActivity.this, AnadirCocheActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                carAdapter.notifyDataSetChanged();
//                justifyListViewHeightBasedOnChildren(carList);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( hasAllPermissionsGranted(grantResults) ) {
            Log.i("debug", "estamos dentro julio");

            if (requestCode == CAMERA_PERMISSION_CODE ) {
                captureFromCamera();
            } else {
                captureFromGallery();
            }

        } else {
            Log.i("debug", "estamos fuera julio");
        }
    }

    public boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }




    public void loadProfileImage() {
        final CharSequence[] options = { "Hacer Foto", "Escoger desde la galeria", "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MiPerfilActivity.this);
        builder.setTitle("Escoge tu foto de perfil").setIcon(R.drawable.ic_camera_24dp);
//        builder.setIconAttribute("gravity=center_vertical", R.drawable.ic_camera_24dp);
//        builder.setIcon(R.drawable.ic_camera_24dp);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @SuppressLint("IntentReset")
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Log.i("debug", "click");

                if (options[item].equals("Hacer Foto")) {

                    Log.i("debug", "foto");

                    /* Check for Granted Permissions */
                    if(ContextCompat.checkSelfPermission(MiPerfilActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(MiPerfilActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

                        ActivityCompat.requestPermissions(MiPerfilActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
                    } else {
                        captureFromCamera();
                    }

                } else if (options[item].equals("Escoger desde la galeria")) {

                    if ( ContextCompat.checkSelfPermission(MiPerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MiPerfilActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_CODE);
                    } else {
                        captureFromGallery();
                    }

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

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    public void showMenuProfileImage(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
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
