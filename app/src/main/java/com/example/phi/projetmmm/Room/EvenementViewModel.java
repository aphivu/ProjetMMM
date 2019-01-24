package com.example.phi.projetmmm.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.phi.projetmmm.model.Evenement;

import java.util.List;

public class EvenementViewModel extends AndroidViewModel {

    private EvenementRepository mRepository;
    private LiveData<List<Evenement>> mEvenements;


    public EvenementViewModel(@NonNull Application application) {
        super(application);

        mRepository = new EvenementRepository(application);
        mEvenements = mRepository.getEvenements();
    }

    public LiveData<List<Evenement>> getEvenements(){
        return mEvenements;
    }

    public void insert(Evenement evenement){
        mRepository.insert(evenement);
    }
}
