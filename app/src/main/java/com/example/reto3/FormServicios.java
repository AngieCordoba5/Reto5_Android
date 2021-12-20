package com.example.reto3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto3.datos.ApiOracle;
import com.example.reto3.datos.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormServicios extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;
    private TextView textTitulo;
    private EditText campo1, campo2, campo3, editId;
    private Button btnBuscar, btnInsertar, btnEliminar, btnConsultar, btnActualizar;
    private ImageView img_Selected;
    String name = "";
    private DBHelper dbHelper;
    private ApiOracle apiOracle;


    String campo1Insert;
    String campo2Insert;
    String campo3Insert;
    byte[] ImageInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_servicios);

        textTitulo = (TextView) findViewById(R.id.textTitulo_ser);
        Intent intent = getIntent();
        editId = (EditText) findViewById(R.id.editIdItem_ser);
        name = intent.getStringExtra("name");
        campo1 = (EditText) findViewById(R.id.editCampo1_ser);
        campo2 = (EditText) findViewById(R.id.editCampo2_ser);
        campo3 = (EditText) findViewById(R.id.editCampo3_ser);
        btnBuscar = (Button) findViewById(R.id.buttonBuscar_ser);
        btnInsertar = (Button) findViewById(R.id.buttonIntertar_ser);
        btnConsultar = (Button) findViewById(R.id.buttonConsultar_ser);
        btnActualizar = (Button) findViewById(R.id.buttonActualizar_Ser);
        btnEliminar = (Button) findViewById(R.id.buttonEliminar_ser);
        img_Selected = (ImageView) findViewById(R.id.imagen_item_ser);
        dbHelper = new DBHelper(getApplicationContext());
        apiOracle = new ApiOracle(getApplicationContext());


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
                        FormServicios.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try{
                    llenarCampos();
                    apiOracle.insertServicio(campo1Insert, campo2Insert, campo3Insert, img_Selected);
                    //dbHelper.insertData(campo1Insert,campo2Insert,campo3Insert,ImageInsert,name);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Insert Sucess", Toast.LENGTH_SHORT).show();
                    View view = findViewById(R.id.linearLayoutServicios);
                    Snackbar.make(view,"Se ha Insertado Correctamente",Snackbar.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Cursor cursor = dbHelper.getDataById(name, editId.getText().toString().trim());
                while (cursor.moveToNext()){
                    campo1.setText(cursor.getString(1));
                    campo2.setText(cursor.getString(2));
                    campo3.setText(cursor.getString(3));
                    byte[] img = cursor.getBlob(4);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    img_Selected.setImageBitmap(bitmap);*/

                apiOracle.getServicioById(
                        editId.getText().toString(), img_Selected, campo1, campo2, campo3);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.linearLayoutServicios);
                //dbHelper.deleteDataById(name, editId.getText().toString().trim());
                apiOracle.deleteServicio(editId.getText().toString());
                limpiarCampos();
                Snackbar.make(view,"Se ha Eliminado Correctamente",Snackbar.LENGTH_SHORT).show();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                llenarCampos();
                try{
                    apiOracle.updateServicio(
                            editId.getText().toString(),
                            campo1Insert,
                            campo2Insert,
                            campo3Insert,
                            img_Selected
                    );
                    limpiarCampos();
                    View view = findViewById(R.id.linearLayoutServicios);
                    Snackbar.make(view,"Se ha Actualizado Correctamente",Snackbar.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

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
        img_Selected.setImageResource(R.drawable.guitarra);
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
    }
}