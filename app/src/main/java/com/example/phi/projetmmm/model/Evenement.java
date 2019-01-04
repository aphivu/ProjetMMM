package com.example.phi.projetmmm.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Evenement {

    private String mTitre;
    private String mDescription;
    private String mId;

    public Evenement(){}

    public Evenement(String mTitre, String mDescription, String mId) {
        this.mTitre = mTitre;
        this.mDescription = mDescription;
        this.mId = mId;
    }

    public String getTitre() {
        return mTitre;
    }

    public void setTitre(String mTitre) {
        this.mTitre = mTitre;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
}
