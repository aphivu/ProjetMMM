package com.example.phi.projetmmm.Room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.phi.projetmmm.model.Evenement;

import java.util.List;

public class EvenementRepository {

    private EvenementDAO mDao;
    private LiveData<List<Evenement>> mEvenements;


    EvenementRepository(Application application){
        EvenementRoomDatabase database = EvenementRoomDatabase.getDatabase(application);
        mDao = database.evenementDAO();
        mEvenements = mDao.getAllEvenements();
    }

    LiveData<List<Evenement>> getEvenements(){
        return mEvenements;
    }

    public void insert(Evenement evenement){
        new insertAsyncTask(mDao).execute(evenement);
    }

    public static class insertAsyncTask extends AsyncTask<Evenement, Void, Void> {
        private EvenementDAO mAsyncDao;

        insertAsyncTask(EvenementDAO dao){
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Evenement ... params) {
            mAsyncDao.insert(params[0]);
            return null;
        }
    }
}
