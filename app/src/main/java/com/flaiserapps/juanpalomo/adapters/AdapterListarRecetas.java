package com.flaiserapps.juanpalomo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.flaiserapps.juanpalomo.R;
import com.flaiserapps.juanpalomo.model.Receta;

import java.util.ArrayList;

public class AdapterListarRecetas extends  RecyclerView.Adapter<AdapterListarRecetas.RecetaViewHolder>{

    private ArrayList<Receta> recetas;
    private Context contexto;

    //Inicio getters and setters

    public ArrayList<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(ArrayList<Receta> recetas) {
        recetas = recetas;
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }



    //Fin getters and setters

    //Inicio de la clase RecetaViewHolder
    public static class RecetaViewHolder extends RecyclerView.ViewHolder{

        TextView nombreReceta, descReceta;

        public RecetaViewHolder(@NonNull View itemView){
            super(itemView);

            nombreReceta=(TextView) itemView.findViewById(R.id.mod_nombrereceta);
            descReceta=(TextView) itemView.findViewById(R.id.mod_descreceta);

        }
    }
    //Fin de la clase RecetaViewHolder

    //Constructor de la clase


    public AdapterListarRecetas(ArrayList<Receta> recetas, Context contexto) {
        this.recetas = recetas;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Al inicializar cada tarjeta
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receta_viewholder, viewGroup, false);
        return new RecetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder recetaViewHolder, int i) {
        //Java...

        final int pos=i;
        Receta recAux=recetas.get(i);
        recetaViewHolder.nombreReceta.setText(recAux.getNombre());
        recetaViewHolder.descReceta.setText(recAux.getDescripcion());

    }

    @Override
    public int getItemCount() {
        //Tiene que devolver el numero de recetas del arraylist para indicarselo al recyclerview
        return recetas.size();
    }
}
