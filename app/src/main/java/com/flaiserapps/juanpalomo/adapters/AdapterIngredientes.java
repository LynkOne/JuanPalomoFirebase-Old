package com.flaiserapps.juanpalomo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flaiserapps.juanpalomo.R;
import com.flaiserapps.juanpalomo.model.Ingrediente;
import com.flaiserapps.juanpalomo.model.Receta;

import java.util.ArrayList;

public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.IngredientesViewHolder> {

    private ArrayList<Ingrediente> ingredientes;
    private Context contexto;
    //Inicio getters and setters

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }



    //Inicio de la clase IngredientesViewHolder
    public static class IngredientesViewHolder extends RecyclerView.ViewHolder{




        public IngredientesViewHolder(@NonNull View itemView){
            super(itemView);

        }
    }
    //Fin de la clase IngredientesViewHolder

    //Constructor de la clase


    public AdapterIngredientes(ArrayList<Ingrediente> ingredientes, Context contexto) {
        this.ingredientes = ingredientes;
        this.contexto = contexto;
    }


    @NonNull
    @Override
    public IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Al inicializar cada tarjeta
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingrediente_viewholder, viewGroup, false);
        return new IngredientesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientesViewHolder ingredientesViewHolder, int i) {
        //Java...
        final int pos=i;


    }

    @Override
    public int getItemCount() {
        //Tiene que devolver el numero de ingredientes del arraylist para indicarselo al recyclerview
        return ingredientes.size();
    }


}
