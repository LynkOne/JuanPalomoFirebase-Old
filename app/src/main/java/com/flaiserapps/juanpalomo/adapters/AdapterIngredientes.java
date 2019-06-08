package com.flaiserapps.juanpalomo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.flaiserapps.juanpalomo.R;
import com.flaiserapps.juanpalomo.model.Ingrediente;

import java.util.ArrayList;

public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.IngredientesViewHolder> {
    private ArrayList<Ingrediente> ingredientes_receta;
    private ArrayList<Ingrediente> ingredientes;
    private Context contexto;
    private AutoCompleteIngredienteAdapter adapter;
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

    public ArrayList<Ingrediente> getIngredientes_receta() {
        return ingredientes_receta;
    }

    //Inicio de la clase IngredientesViewHolder
    public static class IngredientesViewHolder extends RecyclerView.ViewHolder{

    public AutoCompleteTextView nombrIngrediente;
    public ImageButton deleteIngrediente;


        public IngredientesViewHolder(@NonNull View itemView){
            super(itemView);
            nombrIngrediente=(AutoCompleteTextView) itemView.findViewById(R.id.editText_ingredienteViewHolder_nombre);
            deleteIngrediente=(ImageButton) itemView.findViewById(R.id.imageButton_ingredienteViewholder_eliminar);


        }
    }
    //Fin de la clase IngredientesViewHolder

    //Constructor de la clase


    public AdapterIngredientes(ArrayList<Ingrediente> ingredientes, ArrayList<Ingrediente> ingredientes_receta, Context contexto) {
        this.ingredientes = ingredientes;
        this.contexto = contexto;
        this.ingredientes_receta=ingredientes_receta;
        this.adapter=new AutoCompleteIngredienteAdapter(contexto, ingredientes);
    }


    @NonNull
    @Override
    public IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Al inicializar cada tarjeta
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingrediente_viewholder, viewGroup, false);
        return new IngredientesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientesViewHolder ingredientesViewHolder, int i) {
        //Java...
        final int pos=i;

        ingredientesViewHolder.nombrIngrediente.setAdapter(adapter);
        ingredientesViewHolder.nombrIngrediente.setText(ingredientes_receta.get(i).getNombre());
        ingredientesViewHolder.nombrIngrediente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ingredientesViewHolder.nombrIngrediente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    ingredientes_receta.get(pos).setNombre(ingredientesViewHolder.nombrIngrediente.getText().toString());
                    ingredientes_receta.get(pos).setId(ingredientesViewHolder.nombrIngrediente.getTag().toString());
                }catch (Exception ex){
                    Log.d("hectorr", "error al guardar el contenido del editext en el arraylist :"+ex.getLocalizedMessage());
                }
                ingredientesViewHolder.nombrIngrediente.setEnabled(false);
                ingredientesViewHolder.nombrIngrediente.clearFocus();
                ingredientesViewHolder.nombrIngrediente.setFocusable(false);
            }
        });

        ingredientesViewHolder.deleteIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hectorr","Eliminando Posicion: "+pos);
                if(ingredientes_receta.size()>1) {
                    Log.d("hectorr"," Posicion: "+pos);

                    ingredientes_receta.remove(pos);
                    //notifyDataSetChanged();
                    notifyItemRemoved(pos);

                    notifyItemRangeChanged(pos, getItemCount());
                }
            }
        });
        if(ingredientesViewHolder.nombrIngrediente.getText().length()==0){
            ingredientesViewHolder.nombrIngrediente.setEnabled(true);
            ingredientesViewHolder.nombrIngrediente.setFocusable(true);
        }else {
            ingredientesViewHolder.nombrIngrediente.setEnabled(false);
            ingredientesViewHolder.nombrIngrediente.clearFocus();
            ingredientesViewHolder.nombrIngrediente.setFocusable(false);
        }

    }

    @Override
    public int getItemCount() {
        //Tiene que devolver el numero de ingredientes del arraylist para indicarselo al recyclerview
        Log.d("hectorr", "numero de ingredientes: "+ingredientes_receta.size());
        return ingredientes_receta.size();
    }

    public void addIngrediente(){
        Log.d("hectorr", "Añadiendo item al arraylist, size anterior: "+ingredientes_receta.size());
        Ingrediente aux=new Ingrediente();
        ingredientes_receta.add(aux);
        notifyItemInserted(ingredientes_receta.size()-1);


    }


}
