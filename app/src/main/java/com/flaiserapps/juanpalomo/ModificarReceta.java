package com.flaiserapps.juanpalomo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.flaiserapps.juanpalomo.adapters.AdapterIngredientes;
import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.flaiserapps.juanpalomo.model.Receta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModificarReceta extends AppCompatActivity {

    private EditText nombre, descripcion, elaboracion;
    private Button modificar;
    private ImageButton addIngrediente;
    private RecyclerView recyclerIngredientes;
    private AdapterIngredientes adapterIngr;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Ingrediente> ingredientes_receta;
    private Receta receta;
    private DatabaseReference dbr;
    private DatabaseReference dbrModificar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_receta);
        //Obtener ingredientes del bundle
        Bundle b= getIntent().getExtras();
        if(getResources().getString(R.string.OBJETO_INGREDIENTES)!=null){
            ingredientes=(b.getParcelableArrayList(getResources().getString(R.string.OBJETO_INGREDIENTES)));
        }
        if(getResources().getString(R.string.OBJETO_RECETA)!=null){
            receta=(b.getParcelable(getResources().getString(R.string.OBJETO_RECETA)));
        }
        ingredientes_receta = new ArrayList<Ingrediente>();
        try {
            for (int pos=0;pos<receta.getIngredientes().size();pos++) {
                final Ingrediente ingrediente_auxiliar=new Ingrediente(receta.getIngredientes().get(pos));
                final int posicion=pos;
                dbr= FirebaseDatabase.getInstance().getReference("ingredientes/"+receta.getIngredientes().get(pos)+"/nombre");
                dbr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(posicion==0){
                            ingredientes_receta.clear();
                        }
                        if(posicion==receta.getIngredientes().size()-1){
                            adapterIngr.notifyItemInserted(ingredientes_receta.size()-1);
                        }
                        ingrediente_auxiliar.setNombre(dataSnapshot.getValue().toString());
                        ingredientes_receta.add(ingrediente_auxiliar);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }catch (Exception ex){
            Log.d("hectorr", "Error al cargar los ingredientes de la receta: "+ex.getLocalizedMessage());
        }
        nombre=(EditText) findViewById(R.id.editText_modificar_nombre);
        descripcion=(EditText) findViewById(R.id.editText_modificar_descripcion);
        elaboracion=(EditText) findViewById(R.id.editText_modificar_elaboracion);
        modificar=(Button)findViewById(R.id.button_modificar_receta);
        addIngrediente=(ImageButton)findViewById(R.id.imageButton_modificar_addIngrediente);
        recyclerIngredientes=(RecyclerView) findViewById(R.id.recycler_modificar_ingredientes);
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
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar cambios
                DatabaseReference dbrModificar= FirebaseDatabase.getInstance().getReference("recetas").child(receta.getId());
                Log.d("hecotrr", "Database Reference: "+dbr.toString());
                final Receta receta_auxiliar=receta;
                receta_auxiliar.setDescripcion(descripcion.getText().toString());
                receta_auxiliar.setElaboracion(elaboracion.getText().toString());
                receta_auxiliar.setNombre(nombre.getText().toString());
                ArrayList<String> ingredientes_receta_aux=new ArrayList<>();
                for (Ingrediente ingr:ingredientes_receta) {
                    ingredientes_receta_aux.add(ingr.getId());
                    Log.d("hectorr", ingr.toString());

                }
                receta_auxiliar.setIngredientes(ingredientes_receta_aux);
                dbrModificar.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nombre").setValue(receta_auxiliar.getNombre());
                        dataSnapshot.getRef().child("descripcion").setValue(receta_auxiliar.getDescripcion());
                        dataSnapshot.getRef().child("elaboracion").setValue(receta_auxiliar.getElaboracion());
                        dataSnapshot.getRef().child("autor").setValue(receta_auxiliar.getAutor());
                        dataSnapshot.getRef().child("ingredientes").setValue(receta_auxiliar.getIngredientes());
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        nombre.setText(receta.getNombre());
        descripcion.setText(receta.getDescripcion());
        elaboracion.setText(receta.getElaboracion());

    }
}
