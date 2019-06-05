package com.flaiserapps.juanpalomo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.flaiserapps.juanpalomo.adapters.AdapterIngredientes;
import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class EnviarReceta extends AppCompatActivity {
    private RecyclerView recyclerIngredientes;
    private AdapterIngredientes adapterIngr;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Ingrediente> ingredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_receta);
        recyclerIngredientes=(RecyclerView) findViewById(R.id.recycler_enviar_ingredientes);
        adapterIngr=new AdapterIngredientes(ingredientes,getApplicationContext());
        //Obtener ingredientes del bundle
        Bundle b= getIntent().getExtras();
        ingredientes=b.getParcelable(getResources().getString(R.string.OBJETO_INGREDIENTES));

    }
}
