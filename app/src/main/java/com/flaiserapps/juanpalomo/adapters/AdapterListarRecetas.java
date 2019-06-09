package com.flaiserapps.juanpalomo.adapters;

import android.content.Context;
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
import android.widget.TextView;

import com.flaiserapps.juanpalomo.R;
import com.flaiserapps.juanpalomo.RecyclerRecetasFragment;
import com.flaiserapps.juanpalomo.model.Receta;

import java.util.ArrayList;

public class AdapterListarRecetas extends  RecyclerView.Adapter<AdapterListarRecetas.RecetaViewHolder>{

    private ArrayList<Receta> recetas;
    private RecyclerRecetasFragment contexto;
    private recetasInteractionListener interfaz;

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

        public RecetaViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.receta_card_view);
            nombreReceta=(TextView) itemView.findViewById(R.id.tv_nombrereceta);
            descReceta=(TextView) itemView.findViewById(R.id.tv_descreceta);

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
        Receta recAux=recetas.get(i);
        recetaViewHolder.nombreReceta.setText(recAux.getNombre());
        recetaViewHolder.descReceta.setText(recAux.getDescripcion());



        recetaViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Crear subactivity
               // Intent i = new Intent(contexto, DetallesReceta.class);
               // Bundle bReceta=new Bundle();
               // bReceta.putParcelable(contexto.getResources().getString(R.string.OBJETO_RECETA), recetas.get(pos));
               // i.putExtras(bReceta);
               // contexto.startActivity(i);


                //Cambiar fragment a detalles receta
                interfaz.expandirReceta(recetas.get(pos));
            }
        });

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

