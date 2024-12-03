package com.example.saliv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "saliv_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_HEART_RATE = "heart_rate";
    public static final String COLUMN_SPO2 = "spo2";
    public static final String COLUMN_TEMPERATURE = "temperature";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_HISTORY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_HEART_RATE + " INTEGER, " +
                    COLUMN_SPO2 + " INTEGER, " +
                    COLUMN_TEMPERATURE + " REAL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // Méthode pour ajouter une entrée d'historique
    public void addHistoryRecord(SQLiteDatabase db, String date, int heartRate, int spo2, float temperature) {
        String insertSQL = "INSERT INTO " + TABLE_HISTORY + " (" +
                COLUMN_DATE + ", " +
                COLUMN_HEART_RATE + ", " +
                COLUMN_SPO2 + ", " +
                COLUMN_TEMPERATURE + ") VALUES ('" +
                date + "', " + heartRate + ", " + spo2 + ", " + temperature + ");";
        db.execSQL(insertSQL);
    }

    // Méthode pour récupérer tous les enregistrements d'historique
    public Cursor getAllHistory(SQLiteDatabase db) {
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY + " ORDER BY " + COLUMN_DATE + " DESC";
        return db.rawQuery(selectQuery, null);
    }
}
