package com.example.phi.projetmmm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Evenement implements Parcelable {

    private String mTitre;
    private String mDescription;
    private String mId;
    private String mUrlImage;
    private float mRate;
    private String mkey;

    private Lieu mLieu;
    private Note mNote;


    public Evenement(){}

    public Evenement(String mTitre, String mDescription, String mId, String mUriImage, String mkey) {
        this.mTitre = mTitre;
        this.mDescription = mDescription;
        this.mId = mId;
        this.mUrlImage = mUriImage;
        this.mkey = mkey;
    }

    protected Evenement(Parcel in) {
        mTitre = in.readString();
        mDescription = in.readString();
        mId = in.readString();
        mLieu = in.readParcelable(Lieu.class.getClassLoader());
        mNote = in.readParcelable(Note.class.getClassLoader());
        mUrlImage = in.readString();
        mRate = in.readFloat();
        mkey = in.readString();
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

    public Lieu getLieu() {
        return mLieu;
    }

    public void setLieu(Lieu mLieu) {
        this.mLieu = mLieu;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String mUriImage) {
        this.mUrlImage = mUriImage;
    }

    public float getRate() {
        return mRate;
    }

    public void setRate(float mRate) {
        this.mRate = mRate;
    }

    public String getKey(){
        return this.mkey;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(Note mNote) {
        this.mNote = mNote;
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
