package com.example.phi.projetmmm.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
@Entity(tableName = "evenement_table")
public class Evenement implements Parcelable {

    private String titre;
    private String description;
    private String id;
    private String urlImage;
    private float rate;

    @PrimaryKey
    @NonNull
    private String key;

    @Embedded
    private Lieu lieu;

    @Embedded
    private Note note;

    public Evenement(){}

    public Evenement(String mTitre, String mDescription, String mId, String mUriImage, String mkey) {
        this.titre = mTitre;
        this.description = mDescription;
        this.id = mId;
        this.urlImage = mUriImage;
        this.key = mkey;
    }

    protected Evenement(Parcel in) {
        titre = in.readString();
        description = in.readString();
        id = in.readString();
        lieu = in.readParcelable(Lieu.class.getClassLoader());
        note = in.readParcelable(Note.class.getClassLoader());
        urlImage = in.readString();
        rate = in.readFloat();
        key = in.readString();
    }

    public static final Creator<Evenement> CREATOR = new Creator<Evenement>() {
        @Override
        public Evenement createFromParcel(Parcel in) {
            return new Evenement(in);
        }

        @Override
        public Evenement[] newArray(int size) {
            return new Evenement[size];
        }
    };

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitre());
        dest.writeString(getDescription());
        dest.writeString(getId());
        dest.writeString(getUrlImage());
        dest.writeParcelable(getLieu(),flags);
        dest.writeFloat(getRate());
        dest.writeString(getKey());
        dest.writeParcelable(getNote(),flags);

    }
}
