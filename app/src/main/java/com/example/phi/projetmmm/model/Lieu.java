package com.example.phi.projetmmm.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Lieu implements Parcelable {

    private String ville;
    private String departement;
    private String region;
    private double longitude;
    private double latitude;

    public Lieu(String ville, String departement, String region, double longitude, double latitude) {
        this.ville = ville;
        this.departement = departement;
        this.region = region;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Lieu(){

    }


    protected Lieu(Parcel in) {
        ville = in.readString();
        departement = in.readString();
        region = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Lieu> CREATOR = new Creator<Lieu>() {
        @Override
        public Lieu createFromParcel(Parcel in) {
            return new Lieu(in);
        }

        @Override
        public Lieu[] newArray(int size) {
            return new Lieu[size];
        }
    };

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getVille());
        dest.writeString(getDepartement());
        dest.writeString(getRegion());
        dest.writeDouble(getLatitude());
        dest.writeDouble(getLongitude());
    }
}
