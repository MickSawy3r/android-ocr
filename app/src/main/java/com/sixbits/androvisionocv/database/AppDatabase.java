package com.sixbits.androvisionocv.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sixbits.androvisionocv.entity.AttendanceLog;

@Database(entities = {AttendanceLog.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Logs_DB";

    public abstract LogDao logDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
