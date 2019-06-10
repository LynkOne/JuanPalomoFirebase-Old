package com.flaiserapps.juanpalomo.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flaiserapps.juanpalomo.ModificarReceta;
import com.flaiserapps.juanpalomo.R;
import com.flaiserapps.juanpalomo.RecyclerRecetasFragment;
import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.flaiserapps.juanpalomo.model.Receta;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterListarRecetas extends  RecyclerView.Adapter<AdapterListarRecetas.RecetaViewHolder>{

    private ArrayList<Receta> recetas;
    private ArrayList<Ingrediente> ingredientes;
    private RecyclerRecetasFragment contexto;
    private recetasInteractionListener interfaz;
    private String contenidoACargar;

    //Inicio getters and setters

    public ArrayList<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(ArrayList<Receta> recetas) {
        recetas = recetas;
    }

    public RecyclerRecetasFragment getContexto() {
        return contexto;
    }

    public void setContexto(RecyclerRecetasFragment contexto) {
        this.contexto = contexto;
    }

    //Fin getters and setters

    //Inicio de la clase RecetaViewHolder
    public static class RecetaViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        TextView nombreReceta, descReceta;
        ImageButton editarReceta, eliminarReceta;

        public RecetaViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.receta_card_view);
            nombreReceta=(TextView) itemView.findViewById(R.id.tv_nombrereceta);
            descReceta=(TextView) itemView.findViewById(R.id.tv_descreceta);
            editarReceta=(ImageButton) itemView.findViewById(R.id.imageButton_editar_receta);
            eliminarReceta=(ImageButton) itemView.findViewById(R.id.imageButton_eliminar_receta);


        }
    }
    //Fin de la clase RecetaViewHolder

    //Constructor de la clase


    public AdapterListarRecetas(ArrayList<Receta> recetas, RecyclerRecetasFragment contexto) {
        this.recetas = recetas;
        this.contexto = contexto;
        try{
            interfaz=(recetasInteractionListener) contexto;
        }catch (ClassCastException ex){
            Log.d("hectorrr","error en la interfaz: "+ex.getMessage());
        }
    }
    public AdapterListarRecetas(ArrayList<Receta> recetas, RecyclerRecetasFragment contexto, String contenidoACargar) {
        this.recetas = recetas;
        this.contexto = contexto;
        this.contenidoACargar=contenidoACargar;
        try{
            interfaz=(recetasInteractionListener) contexto;
        }catch (ClassCastException ex){
            Log.d("hectorrr","error en la interfaz: "+ex.getMessage());
        }
    }
    public AdapterListarRecetas(ArrayList<Receta> recetas, ArrayList<Ingrediente> ingredientes, RecyclerRecetasFragment contexto, String contenidoACargar) {
        this.recetas = recetas;
        this.contexto = contexto;
        this.contenidoACargar=contenidoACargar;
        this.ingredientes=ingredientes;
        try{
            interfaz=(recetasInteractionListener) contexto;
        }catch (ClassCastException ex){
            Log.d("hectorrr","error en la interfaz: "+ex.getMessage());
        }
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Al inicializar cada tarjeta
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receta_viewholder, viewGroup, false);
        return new RecetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecetaViewHolder recetaViewHolder, int i) {
        //Java...
        final int pos=i;
        final Receta recAux=recetas.get(i);
        recetaViewHolder.nombreReceta.setText(recAux.getNombre());
        recetaViewHolder.descReceta.setText(recAux.getDescripcion());
        if (contenidoACargar!=null && contenidoACargar!="") {
            if (contenidoACargar.equals(recetaViewHolder.itemView.getResources().getString(R.string.RECETASPORINGREDIENTES))) {
                //Si entramos desde el menú recetas por ingredientes ocultamos los botones
                recetaViewHolder.eliminarReceta.setVisibility(View.INVISIBLE);
                recetaViewHolder.editarReceta.setVisibility(View.INVISIBLE);
            }
            if (contenidoACargar.equals(recetaViewHolder.itemView.getResources().getString(R.string.MISRECETAS))) {
                //Si entramos desde el menú mis recetas asumimos que son nuestras recetas y mostramos los botones de editar y eliminar
                recetaViewHolder.eliminarReceta.setVisibility(View.VISIBLE);
                recetaViewHolder.editarReceta.setVisibility(View.VISIBLE);
                recetaViewHolder.eliminarReceta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Onclic de eliminar recetas
                        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                        builder.setMessage("¿Seguro que deseas eliminar esta receta?").setTitle("Eliminar Receta");
                        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                eliminarReceta(recAux.getId());
                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();

                    }
                });
                recetaViewHolder.editarReceta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Onclic de editar recetas
                        Intent i =new Intent(v.getContext(), ModificarReceta.class);
                        Bundle bundleModificar =new Bundle();
                        bundleModificar.putParcelableArrayList(v.getResources().getString(R.string.OBJETO_INGREDIENTES), ingredientes);
                        bundleModificar.putParcelable(v.getResources().getString(R.string.OBJETO_RECETA), recAux);
                        i.putExtras(bundleModificar);
                        v.getContext().startActivity(i);
                    }
                });
            }
        }else{
            //Si entramos desde el menú explorar recetas ocultamos los botones
            recetaViewHolder.eliminarReceta.setVisibility(View.INVISIBLE);
            recetaViewHolder.editarReceta.setVisibility(View.INVISIBLE);
        }


        recetaViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar fragment a detalles receta
                interfaz.expandirReceta(recetas.get(pos));
            }
        });

    }
    private void eliminarReceta(String idReceta){
        DatabaseReference dbrEliminarReceta = FirebaseDatabase.getInstance().getReference("recetas").child(idReceta);
        dbrEliminarReceta.removeValue();
    }

    @Override
    public int getItemCount() {
        //Tiene que devolver el numero de recetas del arraylist para indicarselo al recyclerview
        return recetas.size();
    }


    public interface recetasInteractionListener{
        void expandirReceta(Receta receta);
    }

}

