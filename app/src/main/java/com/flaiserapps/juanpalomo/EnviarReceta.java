package com.flaiserapps.juanpalomo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.flaiserapps.juanpalomo.adapters.AdapterIngredientes;
import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.flaiserapps.juanpalomo.model.Receta;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class EnviarReceta extends AppCompatActivity {
    private RecyclerView recyclerIngredientes;
    private AdapterIngredientes adapterIngr;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Ingrediente> ingredientes_receta;
    private EditText nombre_Receta, descripcion_Receta, elaboracion_Receta;
    private ImageButton addIngrediente;
    private Button enviarReceta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_receta);

        //Obtener ingredientes del bundle
        Bundle b= getIntent().getExtras();
        if(getResources().getString(R.string.OBJETO_INGREDIENTES)!=null){
            ingredientes=(b.getParcelableArrayList(getResources().getString(R.string.OBJETO_INGREDIENTES)));
        }
        ingredientes_receta = new ArrayList<Ingrediente>();
        Ingrediente auxiliar = new Ingrediente();
        ingredientes_receta.add(auxiliar);
        nombre_Receta=(EditText)findViewById(R.id.editText_enviar_nombre);
        descripcion_Receta=(EditText)findViewById(R.id.editText_enviar_descripcion);
        elaboracion_Receta=(EditText)findViewById(R.id.editText_enviar_elaboracion);
        addIngrediente=(ImageButton)findViewById(R.id.imageButton_addIngrediente);
        enviarReceta=(Button) findViewById(R.id.button_enviar_receta);
        recyclerIngredientes=(RecyclerView) findViewById(R.id.recycler_enviar_ingredientes);
        adapterIngr=new AdapterIngredientes(ingredientes,ingredientes_receta,getApplicationContext());
        rVLM=new LinearLayoutManager(getApplicationContext());
        recyclerIngredientes.setLayoutManager(rVLM);
        recyclerIngredientes.setAdapter(adapterIngr);

        addIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterIngr.addIngrediente();
            }
        });
        enviarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ingredientes_receta=adapterIngr.getIngredientes_receta();
                Receta rec_aux=new Receta();
            }
        });
    }
}
