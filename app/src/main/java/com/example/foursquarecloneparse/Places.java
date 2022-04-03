package com.example.foursquarecloneparse;

import android.graphics.Bitmap;

/**
 * Created by aarslan on 03/04/2022.
 */
public class Places {

    private static Places instance;

    private Bitmap image;
    private String name;
    private String type;
    private String atmpsphere;

    public Places() {
    }

    public static Places getInstance() {
        if(instance == null){
            instance = new Places();
        }
        return instance;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAtmpsphere() {
        return atmpsphere;
    }

    public void setAtmpsphere(String atmpsphere) {
        this.atmpsphere = atmpsphere;
    }
}
