package com.example.reto3.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.reto3.FormActivity;
import com.example.reto3.R;
import com.example.reto3.datos.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormSucursales extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;
    private TextView textTitulo, campo3;
    private EditText campo1, campo2, editId;
    private Button btnBuscar, btnInsertar, btnEliminar, btnConsultar, btnActualizar;
    private ImageView img_Selected;
    String name = "";
    private DBHelper dbHelper;

    private MapView mapView;
    private MapController mapController;

    String campo1Insert;
    String campo2Insert;
    String campo3Insert;
    byte[] ImageInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.act_form_sucursales);
/*
        textTitulo = (TextView) findViewById(R.id.textTitulo_suc);
        Intent intent = getIntent();
        editId = (EditText) findViewById(R.id.editIdItem_suc);
        name = intent.getStringExtra("name");
        campo1 = (EditText) findViewById(R.id.editCampo1_suc);
        campo2 = (EditText) findViewById(R.id.editCampo2_suc);
        campo3 = (TextView) findViewById(R.id.editCampo3_suc);
        btnBuscar = (Button) findViewById(R.id.buttonBuscar_suc);
        btnInsertar = (Button) findViewById(R.id.buttonIntertar_suc);
        btnConsultar = (Button) findViewById(R.id.buttonConsultar_suc);
        btnActualizar = (Button) findViewById(R.id.buttonActualizar_suc);
        btnEliminar = (Button) findViewById(R.id.buttonEliminar_suc);
        img_Selected = (ImageView) findViewById(R.id.imagen_item_suc);
        dbHelper = new DBHelper(getApplicationContext());

        int zoom = 6;
         GeoPoint bogota = new GeoPoint(4.56,-76.50);
         mapView = (MapView) findViewById(R.id.openMap);
         mapView.setBuiltInZoomControls(true);
         mapController = (MapController) mapView.getController();
         mapController.setCenter(bogota);
         mapController.setZoom(zoom);

         mapView.setMultiTouchControls(true);



        textTitulo.setText(name);
        if(name.equals("PRODUCTOS")){
            campo1.setHint("Name");
            campo2.setHint("Description");
            campo3.setHint("Price");
        }else if(name.equals("SERVICIOS")){
            campo1.setHint("Name");
            campo2.setHint("Description");
            campo3.setHint("Price");
        }else if(name.equals("SUCURSALES")){
            campo1.setHint("Name");
            campo2.setHint("Description");
            campo3.setHint("Location");
            campo3.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        FormSucursales.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    llenarCampos();
                    dbHelper.insertData(campo1Insert,campo2Insert,campo3Insert,ImageInsert,name);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Insert Sucess", Toast.LENGTH_SHORT).show();
                    View view = findViewById(R.id.linearLayoutForm);
                    Snackbar.make(view,"Se ha Insertado Correctamente",Snackbar.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getDataById(name, editId.getText().toString().trim());
                while (cursor.moveToNext()){
                    campo1.setText(cursor.getString(1));
                    campo2.setText(cursor.getString(2));
                    campo3.setText(cursor.getString(3));
                    byte[] img = cursor.getBlob(4);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    img_Selected.setImageBitmap(bitmap);

                }
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.linearLayoutForm);
                dbHelper.deleteDataById(name, editId.getText().toString().trim());
                limpiarCampos();
                Snackbar.make(view,"Se ha Eliminado Correctamente",Snackbar.LENGTH_SHORT).show();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarCampos();
                try{
                    dbHelper.updateSucursalById(
                            name,
                            editId.getText().toString().trim(),
                            campo1Insert,
                            campo2Insert,
                            campo3Insert,
                            ImageInsert);
                    View view = findViewById(R.id.linearLayoutForm);
                    Snackbar.make(view,"Se ha Actualizado Correctamente",Snackbar.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

*/
    }

    /*
    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream strem = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, strem);
        byte[] byteArray = strem.toByteArray();
        return byteArray;
    }

    public void llenarCampos(){
        campo1Insert = campo1.getText().toString().trim();
        campo2Insert = campo2.getText().toString().trim();
        campo3Insert = campo3.getText().toString().trim();
        ImageInsert = imageViewToByte(img_Selected);
    }

    public void limpiarCampos(){
        campo1.setText("");
        campo2.setText("");
        campo3.setText("");
        img_Selected.setImageResource(R.mipmap.ic_launcher);
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
                img_Selected.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
