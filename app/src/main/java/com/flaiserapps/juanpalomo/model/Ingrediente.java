package com.flaiserapps.juanpalomo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingrediente implements Parcelable {

    private String id;
    private String nombre;


    public Ingrediente() {
    }

    public Ingrediente(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected Ingrediente(Parcel in) {
        id = in.readString();
        nombre = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingrediente> CREATOR = new Parcelable.Creator<Ingrediente>() {
        @Override
        public Ingrediente createFromParcel(Parcel in) {
            return new Ingrediente(in);
        }

        @Override
        public Ingrediente[] newArray(int size) {
            return new Ingrediente[size];
        }
    };
}