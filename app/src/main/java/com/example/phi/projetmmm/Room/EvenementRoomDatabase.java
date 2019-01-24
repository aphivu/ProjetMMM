package com.example.phi.projetmmm.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.phi.projetmmm.model.Evenement;

@Database(entities = {Evenement.class}, version = 1)
public abstract class EvenementRoomDatabase extends RoomDatabase {

    public abstract EvenementDAO evenementDAO();

    private static volatile EvenementRoomDatabase INSTANCE;

    static EvenementRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EvenementRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EvenementRoomDatabase.class, "evenement_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
