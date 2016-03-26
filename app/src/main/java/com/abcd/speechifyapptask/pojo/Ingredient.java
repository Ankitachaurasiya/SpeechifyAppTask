package com.abcd.speechifyapptask.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Vishal Sharma on 25-Mar-16.
 */
public class Ingredient implements Parcelable {
    int id;
    String name;

    public Ingredient(){

    }

    public Ingredient(int x ,String str){
        this.id = x;
        this.name = str;
    }

    protected Ingredient(Parcel in) {
        id = in.readInt();
        name = in.readString();
        Log.d("Ingredient Pojo", "Reading Time->"+id+" ,, "+name);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        Log.d("Incredient Pojo", "Writing Parcel->"+id+" , "+name);
    }
}
