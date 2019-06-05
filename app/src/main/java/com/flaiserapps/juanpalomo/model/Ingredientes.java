package com.flaiserapps.juanpalomo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ingredientes implements Parcelable {
    private ArrayList<Ingrediente> ingredientes;

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Ingredientes() {
    }

    public Ingredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    protected Ingredientes(Parcel in) {
        if (in.readByte() == 0x01) {
            ingredientes = new ArrayList<Ingrediente>();
            in.readList(ingredientes, Ingrediente.class.getClassLoader());
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
        if (ingredientes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredientes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredientes> CREATOR = new Parcelable.Creator<Ingredientes>() {
        @Override
        public Ingredientes createFromParcel(Parcel in) {
            return new Ingredientes(in);
        }

        @Override
        public Ingredientes[] newArray(int size) {
            return new Ingredientes[size];
        }
    };
}