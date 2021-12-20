package com.example.reto3.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reto3.R;
import com.example.reto3.modelos.Producto;
import com.example.reto3.modelos.Servicio;

import java.util.ArrayList;

public class ServicioAdapter extends BaseAdapter {

    Context context;
    ArrayList<Servicio> servicios;
    LayoutInflater inflater;

    public ServicioAdapter(Context context, ArrayList<Servicio> servicios) {
        this.context = context;
        this.servicios = servicios;
    }

    @Override
    public int getCount() {
        return servicios.size();
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

        Servicio servicio = servicios.get(position);
        byte[] image = servicio.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);

        idItem.setText("ID : "+servicio.getId());
        campo1.setText(servicio.getName());
        campo2.setText(servicio.getDescription());
        campo3.setText(servicio.getPrice());
        imageView.setImageBitmap(bitmap);

        return convertView;
    }
}
