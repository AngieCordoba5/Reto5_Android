package com.example.reto3.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.lights.LightsManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto3.BuildConfig;
import com.example.reto3.FormActivity;
import com.example.reto3.R;
import com.example.reto3.databinding.ActivityFormMapsBinding;
import com.example.reto3.datos.ApiOracle;
import com.example.reto3.datos.DBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import com.example.reto3.databinding.ActivityFormMapsBinding;

import org.osmdroid.config.Configuration;

public class FormMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationManager locationManager;
    private Location location;

    private Button insertar_s, consultar_s, eliminar_s, actualizar_s, escoger_s;
    private EditText id_s, name_s, description_s;
    private ImageView image_s;
    private TextView location_s;
    private GoogleMap mMap_s;
    private ActivityFormMapsBinding binding_s;
    private final int REQUEST_CODE_GALLERY = 999;

    private DBHelper dbHelper;
    private ApiOracle apiOracle;

    private ActivityFormMapsBinding binding;

    String idInsert;
    String nameInsert;
    String descriptionInsert;
    String locationInsert;
    byte[] imageInsert = new byte[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);


        binding = ActivityFormMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        insertar_s = (Button) findViewById(R.id.buttonIntertar_suc);
        consultar_s = (Button) findViewById(R.id.buttonConsultar_suc);
        eliminar_s = (Button) findViewById(R.id.buttonEliminar_suc);
        actualizar_s = (Button) findViewById(R.id.buttonActualizar_suc);
        escoger_s = (Button) findViewById(R.id.buttonBuscar_suc);

        id_s = (EditText) findViewById(R.id.editIdItem_suc);
        name_s = (EditText) findViewById(R.id.editCampo1_suc);
        description_s = (EditText) findViewById(R.id.editCampo2_suc);
        location_s = (TextView) findViewById(R.id.editCampo3_suc);
        image_s = (ImageView) findViewById(R.id.imagen_item_suc);

        name_s.setHint("Name");
        description_s.setHint("Description");


        dbHelper = new DBHelper(getApplicationContext());
        apiOracle = new ApiOracle(getApplicationContext());

        escoger_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        FormMapsActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        insertar_s.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                llenarCampos();
                //dbHelper.insertData(nameInsert, descriptionInsert, locationInsert, imageInsert, "SUCURSALES");
                apiOracle.insertSucursal(nameInsert, descriptionInsert, locationInsert, image_s);
                limpiarCampos();
                Toast.makeText(getApplicationContext(), "Insert Sucess", Toast.LENGTH_SHORT).show();
                View view = findViewById(R.id.linearLayoutMaps);
                Snackbar.make(view,"Se ha Insertado Correctamente",Snackbar.LENGTH_SHORT).show();
            }
        });
        consultar_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Cursor cursor = dbHelper.getDataById("SUCURSALES", id_s.getText().toString().trim());
                while (cursor.moveToNext()){
                    name_s.setText(cursor.getString(1));
                    description_s.setText(cursor.getString(2));
                    location_s.setText(cursor.getString(3));
                    byte[] img = cursor.getBlob(4);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    image_s.setImageBitmap(bitmap);

                );}*/
                llenarCampos();
                apiOracle.getSucursalById(
                        idInsert, image_s, name_s, description_s, location_s, mMap_s);
            }
        });

        actualizar_s.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                llenarCampos();
                apiOracle.updateSucursal(
                        idInsert,
                        nameInsert,
                        descriptionInsert,
                        locationInsert,
                        image_s
                );
                //dbHelper.updateSucursalById("SUCURSALES", idInsert, nameInsert, descriptionInsert, locationInsert, imageInsert);
                limpiarCampos();
                View view = findViewById(R.id.linearLayoutMaps);
                Snackbar.make(view,"Se ha Actualizado Correctamente",Snackbar.LENGTH_SHORT).show();


            }
        });

        eliminar_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.linearLayoutMaps);
                //dbHelper.deleteDataById("SUCURSALES", id_s.getText().toString().trim());
                apiOracle.deleteSucursal(id_s.getText().toString());
                limpiarCampos();
                Snackbar.make(view,"Se ha Eliminado Correctamente",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap_s = googleMap;
        //locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Add a marker in Sydney and move the camera
        LatLng bogota = new LatLng(3.52, -72);
        //googleMap.addMarker(new MarkerOptions().position(bogota).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 5));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                location_s.setText(latLng.latitude+","+latLng.longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                googleMap.clear();
                googleMap.addMarker(markerOptions);
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Sin Permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image_s.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void llenarCampos(){
        idInsert = id_s.getText().toString();
        nameInsert = name_s.getText().toString();
        descriptionInsert = description_s.getText().toString();
        locationInsert = location_s.getText().toString();
        imageInsert = imageViewToByte(image_s);
    }

    public void limpiarCampos(){
        name_s.setText("");
        description_s.setText("");
        location_s.setText("");
        image_s.setImageResource(R.drawable.home1);
    }

}