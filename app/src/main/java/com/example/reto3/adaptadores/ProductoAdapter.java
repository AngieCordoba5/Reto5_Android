package com.example.reto3.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reto3.R;
import com.example.reto3.modelos.Producto;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {

    Context context;
    ArrayList<Producto> productos;
    LayoutInflater inflater;

    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
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
        CheckBox checkFavoritos = convertView.findViewById(R.id.checkFavoritos);
        ImageView imgFavoritos = convertView.findViewById(R.id.imageFavoritos);
        TextView idItem = convertView.findViewById(R.id.id_item);
        TextView campo1 = convertView.findViewById(R.id.id_campo1);
        TextView campo2 = convertView.findViewById(R.id.id_campo2);
        TextView campo3 = convertView.findViewById(R.id.id_campo3);

        Producto producto = productos.get(position);
        byte[] image = producto.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);

        idItem.setText("ID : "+producto.getId());
        campo1.setText(producto.getName());
        campo2.setText(producto.getDescription());
        campo3.setText(producto.getPrice());

        /**imgFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFavoritos.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        });*/


        /**
        if(checkFavoritos.isChecked()){
            imgFavoritos.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            imgFavoritos.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }**/


        imageView.setImageBitmap(bitmap);

        return convertView;
    }
}
