package com.flaiserapps.juanpalomo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaiserapps.juanpalomo.adapters.AdapterListarRecetas;
import com.flaiserapps.juanpalomo.model.Receta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecyclerRecetasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerRecetas;
    private AdapterListarRecetas adapterListaRec;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Receta> recetas;
    private DatabaseReference dbr;

    private FloatingActionButton fab;


    public RecyclerRecetasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerRecetasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerRecetasFragment newInstance(String param1, String param2) {
        RecyclerRecetasFragment fragment = new RecyclerRecetasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler_recetas, container, false);

        recyclerRecetas=(RecyclerView) v.findViewById(R.id.recycler_recetas);
        dbr= FirebaseDatabase.getInstance().getReference("recetas");
        fab=(FloatingActionButton) v.findViewById(R.id.enviarReceta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        recetas=new ArrayList<>();
        rVLM=new LinearLayoutManager(getContext());
        recyclerRecetas.setLayoutManager(rVLM);
        adapterListaRec=new AdapterListarRecetas(recetas,getContext());
        recyclerRecetas.setAdapter(adapterListaRec);
        Log.d("hectorrr", "accediendo a Firebase ruta: "+dbr.toString());
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("hectorrr", "DataChange, actualizando arraylist...");
                recetas.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()    ) {
                    Log.d("hectorrr", ds.getValue().toString());
                    Log.d("hectorrr", ds.getKey());

                    Receta aux=ds.getValue(Receta.class);
                    aux.setId(ds.getKey());
                    recetas.add(aux);


                }
                Log.d("hectorr", recetas.toString());
                adapterListaRec.setRecetas(recetas);
                adapterListaRec.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
