package com.flaiserapps.juanpalomo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Receta implements Parcelable {

    private String id;
    private String nombre;
    private String descripcion;
    private String elaboracion;
    private ArrayList<String> ingredientes;

    public Receta() {
    }
    public Receta(String nombre, String descripcion, String elaboracion, ArrayList<String> ingredientes) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.elaboracion = elaboracion;
        this.ingredientes = ingredientes;
    }

    public Receta(String id, String nombre, String descripcion, String elaboracion, ArrayList<String> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.elaboracion = elaboracion;
        this.ingredientes = ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getElaboracion() {
        return elaboracion;
    }

    public void setElaboracion(String elaboracion) {
        this.elaboracion = elaboracion;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Receta(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        descripcion = in.readString();
        elaboracion = in.readString();
        if (in.readByte() == 0x01) {
            ingredientes = new ArrayList<String>();
            in.readList(ingredientes, String.class.getClassLoader());
        } else {
            ingredientes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(elaboracion);
        if (ingredientes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredientes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Receta> CREATOR = new Parcelable.Creator<Receta>() {
        @Override
        public Receta createFromParcel(Parcel in) {
            return new Receta(in);
        }

        @Override
        public Receta[] newArray(int size) {
            return new Receta[size];
        }
    };
}