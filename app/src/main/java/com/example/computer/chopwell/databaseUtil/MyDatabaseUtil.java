package com.example.computer.chopwell.databaseUtil;

import com.google.firebase.database.FirebaseDatabase;

public class MyDatabaseUtil {
    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }
}
