package com.example.reto3.ui.sucursales;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.reto3.FormActivity;
import com.example.reto3.R;
import com.example.reto3.adaptadores.SucursalAdapter;
import com.example.reto3.casos_uso.CasoUsoSucursal;
import com.example.reto3.databinding.FragmentSucursalesBinding;
import com.example.reto3.datos.ApiOracle;
import com.example.reto3.datos.DBHelper;
import com.example.reto3.modelos.Sucursal;
import com.example.reto3.ui.FormMapsActivity;
import com.example.reto3.ui.FormSucursales;

import java.util.ArrayList;


public class SucursalesFragment extends Fragment {

    private FragmentSucursalesBinding binding;
    private GridView gridView;
    private ProgressBar progressBar;
    private DBHelper dbHelper;
    private ApiOracle apiOracle;
    private String TABLE_NAME ="SUCURSALES";
    private CasoUsoSucursal casoUsoSucursal;
    private ArrayList<Sucursal> sucursales;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sucursales, container, false);

        try{
            //dbHelper = new DBHelper(getContext());
            casoUsoSucursal = new CasoUsoSucursal();
            apiOracle= new ApiOracle(root.getContext());
            gridView =(GridView) root.findViewById(R.id.gridSucursales2);
            progressBar = (ProgressBar) root.findViewById(R.id.progressBar_suc);
            apiOracle.getAllSucursales(gridView, progressBar);

            //***************** SQLITE ******************//
            /*Cursor cursor = dbHelper.getData(TABLE_NAME);
            sucursales = casoUsoSucursal.llenarListaSucursal(cursor);
            gridView =(GridView) root.findViewById(R.id.gridSucursales2);
            SucursalAdapter sucursalAdapter = new SucursalAdapter(root.getContext(), sucursales);
            gridView.setAdapter(sucursalAdapter);*/
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Hola Sucursales", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FormMapsActivity.class);
                intent.putExtra("name","SUCURSALES");
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}