package com.example.reto3.ui.servicios;

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
import com.example.reto3.FormServicios;
import com.example.reto3.R;
import com.example.reto3.adaptadores.ProductoAdapter;
import com.example.reto3.adaptadores.ServicioAdapter;
import com.example.reto3.casos_uso.CasoUsoProducto;
import com.example.reto3.casos_uso.CasoUsoServicio;
import com.example.reto3.databinding.FragmentServiciosBinding;
import com.example.reto3.datos.ApiOracle;
import com.example.reto3.datos.DBHelper;
import com.example.reto3.modelos.Producto;
import com.example.reto3.modelos.Servicio;

import java.util.ArrayList;

public class ServiciosFragment extends Fragment {

    private FragmentServiciosBinding binding;
    private String TABLE_NAME ="SERVICIOS";
    private CasoUsoServicio casoUsoServicio;
    private GridView gridView;
    private DBHelper dbHelper;
    private ApiOracle apiOracle;
    private ArrayList<Servicio> servicios;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_servicios, container, false);
        try{
            casoUsoServicio = new CasoUsoServicio();
            apiOracle= new ApiOracle(root.getContext());
            gridView =(GridView) root.findViewById(R.id.gridServicios2);
            progressBar = (ProgressBar) root.findViewById(R.id.progressBar_ser);
            apiOracle.getAllServicios(gridView, progressBar);

        }catch(Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
                Intent intent = new Intent(getContext(), FormServicios.class);
                intent.putExtra("name","SERVICIOS");
                getActivity().startActivity(intent);
                //Toast.makeText(getContext(), "Hola Servicios", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}