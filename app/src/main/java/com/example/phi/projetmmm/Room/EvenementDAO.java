package com.example.phi.projetmmm.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.phi.projetmmm.model.Evenement;

import java.util.List;

@Dao
public interface EvenementDAO {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Evenement evenement);

    @Query("DELETE FROM evenement_table")
    void deleteAll();

    @Delete
    void delete(Evenement evenement);

    @Query("SELECT * from evenement_table")
    LiveData<List<Evenement>> getAllEvenements();


}
