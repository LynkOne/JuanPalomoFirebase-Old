package com.flaiserapps.juanpalomo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flaiserapps.juanpalomo.adapters.AdapterListarRecetas;
import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.flaiserapps.juanpalomo.model.Ingredientes;
import com.flaiserapps.juanpalomo.model.Receta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecyclerRecetasFragment extends Fragment implements AdapterListarRecetas.recetasInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ProgressBar pB;
    private Ingredientes ingredientesClase;
    private RecyclerView recyclerRecetas;
    private AdapterListarRecetas adapterListaRec;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Receta> recetas;
    private ArrayList<Ingrediente> ingredientes;
    private DatabaseReference dbrRecetas;
    private DatabaseReference dbrIngredientes;
    private boolean ingredientesCargados=false;
    private recetasFragmentInteractionListener interfaz;
    private FloatingActionButton fab;
    private FirebaseUser fbUser;
    private FirebaseAuth mAuth;
    private String misRecetas;
    private String recetasPorIngredientes;



    public RecyclerRecetasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecyclerRecetasFragment newInstance(String contenidoACargar) {
        RecyclerRecetasFragment fragment = new RecyclerRecetasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, contenidoACargar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            misRecetas=getResources().getString(R.string.MISRECETAS);
            recetasPorIngredientes=getResources().getString(R.string.RECETASPORINGREDIENTES);
        }


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler_recetas, container, false);
        mAuth=FirebaseAuth.getInstance();
        fbUser= mAuth.getCurrentUser();
        ingredientesClase=new Ingredientes();
        pB=(ProgressBar) v.findViewById(R.id.progressBarFABRecetas);
        pB.setVisibility(View.INVISIBLE);
        recyclerRecetas=(RecyclerView) v.findViewById(R.id.recycler_recetas);
        dbrRecetas= FirebaseDatabase.getInstance().getReference("recetas");
        dbrIngredientes= FirebaseDatabase.getInstance().getReference("ingredientes");
        fab=(FloatingActionButton) v.findViewById(R.id.enviarReceta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OnClick del FloatingActionButton
                pB.setVisibility(View.VISIBLE);

                if(fbUser!=null){
                    //Si el usuario está logueado accede a enviar receta directamente
                    Intent i = new Intent(getContext(), EnviarReceta.class);
                    Bundle bIngredientes=new Bundle();
                    if(ingredientesCargados){
                        bIngredientes.putParcelableArrayList(getResources().getString(R.string.OBJETO_INGREDIENTES), ingredientes);
                        i.putExtras(bIngredientes);
                        pB.setVisibility(View.INVISIBLE);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getContext(), "Error, intentalo de nuevo en unos segundos", Toast.LENGTH_SHORT).show();
                        pB.setVisibility(View.INVISIBLE);
                    }
                }else{
                    //Si no está logueado
                    interfaz.fab_iniciarLogin();
                    pB.setVisibility(View.INVISIBLE);
                }

            }
        });
        recetas=new ArrayList<>();
        ingredientes=new ArrayList<>();
        rVLM=new LinearLayoutManager(getContext());
        recyclerRecetas.setLayoutManager(rVLM);

        if (mParam1!=null && mParam1!=""){
            if(mParam1.equals(getResources().getString(R.string.RECETASPORINGREDIENTES))){
                adapterListaRec=new AdapterListarRecetas(recetas,this, getResources().getString(R.string.RECETASPORINGREDIENTES));
            }
            if(mParam1.equals(getResources().getString(R.string.MISRECETAS))){
                adapterListaRec=new AdapterListarRecetas(recetas,this, getResources().getString(R.string.MISRECETAS));
            }
        }else{
            adapterListaRec=new AdapterListarRecetas(recetas,this);
        }

        recyclerRecetas.setAdapter(adapterListaRec);
        Log.d("hectorrr", "accediendo a Firebase ruta: "+dbrRecetas.toString());
        dbrRecetas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("hectorrr", "DataChange, actualizando arraylist recetas...");
                recetas.clear();
                if (mParam1!=null && mParam1!=""){
                    if (mParam1.equals(misRecetas)){
                        Log.d("hectorr","Rellenando con MIS recetas");
                        for (DataSnapshot ds:dataSnapshot.getChildren()    ) {
                            Log.d("hectorrr", ds.getValue().toString());
                            Log.d("hectorrr", ds.getKey());

                            Receta aux=ds.getValue(Receta.class);
                            aux.setId(ds.getKey());
                            if(aux.getAutor()!=null && aux.getAutor()!=""){
                                if(aux.getAutor().equals(fbUser.getUid())){
                                    recetas.add(aux);
                                }
                            }


                        }
                    }
                    if (mParam1.equals(recetasPorIngredientes)){

                    }
                }else{
                    Log.d("hectorr","Rellenando con TODAS las recetas");
                    for (DataSnapshot ds:dataSnapshot.getChildren()    ) {
                        Log.d("hectorrr", ds.getValue().toString());
                        Log.d("hectorrr", ds.getKey());

                        Receta aux=ds.getValue(Receta.class);
                        aux.setId(ds.getKey());
                        recetas.add(aux);
                    }
                }

                Log.d("hectorr", recetas.toString());
                adapterListaRec.setRecetas(recetas);
                adapterListaRec.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbrIngredientes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("hectorrr", "DataChange, actualizando arraylist ingredientes...");
                ingredientesCargados=false;
                ingredientesClase.setIngredientes(new ArrayList<Ingrediente>());
                ingredientes.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren() ) {
                    //Log.d("hectorrr", ds.getValue().toString());
                    //Log.d("hectorrr", ds.getKey());
                    Ingrediente aux=ds.getValue(Ingrediente.class);
                    aux.setId(ds.getKey());
                    ingredientes.add(aux);
                }
                if(ingredientes.size()>0){
                    ingredientesClase.setIngredientes(ingredientes);
                    ingredientesCargados=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof recetasFragmentInteractionListener) {
            interfaz = (recetasFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement recetasFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void expandirReceta(Receta receta) {
        if(ingredientesCargados){
            interfaz.expandirRecetaFragment(receta, ingredientesClase);
        }
        else{
            Toast.makeText(getContext(), "Error, intentalo de nuevo en unos segundos", Toast.LENGTH_SHORT).show();
        }

    }

    public interface recetasFragmentInteractionListener{
        void expandirRecetaFragment(Receta receta, Ingredientes ingredientes);
        void fab_iniciarLogin();
    }
}
