package com.flaiserapps.juanpalomo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.flaiserapps.juanpalomo.R;
import com.flaiserapps.juanpalomo.model.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteIngredienteAdapter extends ArrayAdapter {
    private List<Ingrediente> ingredientesListFull;
    public AutoCompleteIngredienteAdapter(Context context, List ingredientes) {
        super(context, 0, ingredientes);
        ingredientesListFull=new ArrayList<>(ingredientes);
    }


    @Override
    public Filter getFilter() {
        return ingredientesFilter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }
        TextView tvNombreIngrediente=convertView.findViewById(R.id.text_view_list_item);
        Ingrediente ingrediente=(Ingrediente) getItem(position);
        if(ingrediente!=null){
            tvNombreIngrediente.setText(ingrediente.getNombre());
            tvNombreIngrediente.setTag(ingrediente.getId());
        }
        return convertView;
    }

    private Filter ingredientesFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            List<Ingrediente> suggestions=new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                suggestions.addAll(ingredientesListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Ingrediente item: ingredientesListFull){
                    if(item.getNombre().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values=suggestions;
            results.count=suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Ingrediente) resultValue).getNombre();
        }
    };
}
