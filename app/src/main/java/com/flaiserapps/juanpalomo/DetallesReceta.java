package com.flaiserapps.juanpalomo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.flaiserapps.juanpalomo.model.Receta;

public class DetallesReceta extends AppCompatActivity {
    private Receta receta;
    private TextView titulo_receta, descripcion_receta, ingredientes_receta, elaboracion_receta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta);

        titulo_receta=(TextView) findViewById(R.id.textView_titulo_detalle);
        descripcion_receta=(TextView) findViewById(R.id.textView_descripcion_detalle);
        ingredientes_receta=(TextView) findViewById(R.id.textView_ingredientes_detalle);
        elaboracion_receta=(TextView) findViewById(R.id.textView_elaboracion_detalle);

        //Obtener receta del bundle
        Bundle b= getIntent().getExtras();
        receta=b.getParcelable(getResources().getString(R.string.OBJETO_RECETA));

        titulo_receta.setText(receta.getNombre());
        descripcion_receta.setText(receta.getDescripcion());
        ingredientes_receta.setText("Ingredientes: \n"+receta.getIngredientes().toString());
        elaboracion_receta.setText("Instrucciones de elaboración: \n"+receta.getElaboracion());
    }
}
