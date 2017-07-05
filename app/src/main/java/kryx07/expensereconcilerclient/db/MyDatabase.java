package kryx07.expensereconcilerclient.db;

/**
 * Created by sda on 05.07.17.
 */


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kryx07.expensereconcilerclient.model.persontest.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    abstract PersonDao personDao();
}

