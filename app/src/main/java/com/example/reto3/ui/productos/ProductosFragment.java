package com.example.reto3.ui.productos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.reto3.FormActivity;
import com.example.reto3.R;
import com.example.reto3.adaptadores.ProductoAdapter;
import com.example.reto3.casos_uso.CasoUsoProducto;
//import com.example.reto3.databinding.FragmentProductosBinding;
import com.example.reto3.casos_uso.CasoUsoSucursal;
import com.example.reto3.datos.ApiOracle;
import com.example.reto3.datos.DBHelper;
import com.example.reto3.modelos.Producto;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProductosFragment extends Fragment {

    private String TABLE_NAME ="PRODUCTOS";
    private CasoUsoProducto casoUsoProducto;
    private GridView gridView;
    private DBHelper dbHelper;
    private ApiOracle apiOracle;
    private ArrayList<Producto> productos;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_productos, container, false);
        try{
            Toast.makeText(getContext(), "Hola Productos", Toast.LENGTH_SHORT).show();

            casoUsoProducto = new CasoUsoProducto();
            apiOracle= new ApiOracle(root.getContext());
            gridView =(GridView) root.findViewById(R.id.gridProductos2);
            progressBar = (ProgressBar) root.findViewById(R.id.progressBar_pro);
            apiOracle.getAllProductos(gridView, progressBar);
            /*
            casoUsoProducto = new CasoUsoProducto();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            productos = casoUsoProducto.llenarListaProductos(cursor);
            gridView = (GridView) root.findViewById(R.id.gridProductos2);
            ProductoAdapter productoAdapter = new ProductoAdapter(root.getContext(), productos);
            gridView.setAdapter(productoAdapter);

             */

        }catch(Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("name","PRODUCTOS");
                getActivity().startActivity(intent);
                //Toast.makeText(getContext(), "Hola Productos", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}