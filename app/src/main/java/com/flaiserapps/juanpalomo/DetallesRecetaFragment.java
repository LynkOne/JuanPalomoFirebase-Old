package com.flaiserapps.juanpalomo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.flaiserapps.juanpalomo.model.Ingredientes;
import com.flaiserapps.juanpalomo.model.Receta;


public class DetallesRecetaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECETA = "receta";
    private static final String ARG_INGREDIENTES = "ingredientes";

    // TODO: Rename and change types of parameters
    private Receta receta;
    private Ingredientes ingredientes;
    private String str_ingredientes="Ingredientes: \n";
    private TextView titulo_receta, descripcion_receta, ingredientes_receta, elaboracion_receta;

    public DetallesRecetaFragment() {

        // Required empty public constructor
    }

    public static DetallesRecetaFragment newInstance(Receta receta, Ingredientes ingredientes) {
        DetallesRecetaFragment fragment = new DetallesRecetaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECETA, receta);
        args.putParcelable(ARG_INGREDIENTES, ingredientes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            receta = getArguments().getParcelable(ARG_RECETA);
            ingredientes = getArguments().getParcelable(ARG_INGREDIENTES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_detalles_receta, container, false);
        titulo_receta=(TextView) v.findViewById(R.id.textView_titulo_detalle);
        descripcion_receta=(TextView) v.findViewById(R.id.textView_descripcion_detalle);
        ingredientes_receta=(TextView) v.findViewById(R.id.textView_ingredientes_detalle);
        elaboracion_receta=(TextView) v.findViewById(R.id.textView_elaboracion_detalle);

        titulo_receta.setText(receta.getNombre());
        descripcion_receta.setText(receta.getDescripcion());
        Log.d("hectorrr", "Rellenando ingredientes....");
        try{
            for(int i=0;i<receta.getIngredientes().size();i++){
                //Log.d("hectorrr", receta.getIngredientes().get(i));
                for(int x=0;x<ingredientes.getIngredientes().size();x++){
                    Log.d("hectorr", "Buscando ingredientes...: "+ingredientes.getIngredientes().get(x).getId().replace("\"", ""));
                    if(receta.getIngredientes().get(i).equals(ingredientes.getIngredientes().get(x).getId())){
                        str_ingredientes=str_ingredientes+ingredientes.getIngredientes().get(x).getNombre()+"\n";
                        Log.d("hectorrr", "Ingredientes str: "+str_ingredientes);
                    }
                }
            }
            ingredientes_receta.setText(str_ingredientes);
        }catch (Exception ex){
            Log.d("hectorr","Error al rellenar ingredientes");
            Toast.makeText(getContext(), "Error al mostrar los ingredientes", Toast.LENGTH_SHORT).show();
        }

        elaboracion_receta.setText("Elaboración: \n"+receta.getElaboracion());
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
