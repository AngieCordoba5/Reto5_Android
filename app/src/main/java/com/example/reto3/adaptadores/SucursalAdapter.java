package com.example.reto3.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto3.R;
import com.example.reto3.modelos.Producto;
import com.example.reto3.modelos.Sucursal;
import com.example.reto3.ui.ShowMapsActivity;

import java.util.ArrayList;

public class SucursalAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sucursal> sucursales;
    LayoutInflater inflater;

    public SucursalAdapter(Context context, ArrayList<Sucursal> sucursales) {
        this.context = context;
        this.sucursales = sucursales;
    }

    @Override
    public int getCount() {
        return sucursales.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imagen_item2);
        TextView idItem = convertView.findViewById(R.id.id_item);
        TextView campo1 = convertView.findViewById(R.id.id_campo1);
        TextView campo2 = convertView.findViewById(R.id.id_campo2);
        TextView campo3 = convertView.findViewById(R.id.id_campo3);

        Sucursal sucursal = sucursales.get(position);
        byte[] image = sucursal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);

        idItem.setText("ID : "+sucursal.getId());
        campo1.setText(sucursal.getName());
        campo2.setText(sucursal.getDescription());
        campo3.setText(sucursal.getLocation());
        imageView.setImageBitmap(bitmap);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),sucursal.getLocation(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ShowMapsActivity.class);
                intent.putExtra("name", sucursal.getName() );
                intent.putExtra("location", sucursal.getLocation() );
                context.startActivity(intent);

            }
        });

        return convertView;
    }
}
