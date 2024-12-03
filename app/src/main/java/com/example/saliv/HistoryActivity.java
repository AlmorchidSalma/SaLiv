package com.example.saliv;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<VitalSignHistory> historyList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);

        // Créer une liste vide pour les éléments d'historique
        historyList = new ArrayList<>();
        loadHistoryFromDatabase();

        // Créer et initialiser l'adaptateur pour le RecyclerView
        adapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(adapter);
    }

    // Charger l'historique depuis la base de données
    private void loadHistoryFromDatabase() {
        // Ouvrir la base de données en lecture
        Cursor cursor = dbHelper.getAllHistory(dbHelper.getReadableDatabase());

        if (cursor != null) {
            // Se déplacer au début du curseur
            if (cursor.moveToFirst()) {
                do {
                    // Vérifier si les colonnes existent avant de les lire
                    int dateIndex = cursor.getColumnIndex(DBHelper.COLUMN_DATE);
                    int heartRateIndex = cursor.getColumnIndex(DBHelper.COLUMN_HEART_RATE);
                    int spO2Index = cursor.getColumnIndex(DBHelper.COLUMN_SPO2);
                    int temperatureIndex = cursor.getColumnIndex(DBHelper.COLUMN_TEMPERATURE);

                    // Vérifier si l'index de chaque colonne est valide (> 0)
                    if (dateIndex >= 0 && heartRateIndex >= 0 && spO2Index >= 0 && temperatureIndex >= 0) {
                        String date = cursor.getString(dateIndex);
                        int heartRate = cursor.getInt(heartRateIndex);
                        int spO2 = cursor.getInt(spO2Index);
                        float temperature = cursor.getFloat(temperatureIndex);

                        // Ajouter les éléments à la liste
                        historyList.add(new VitalSignHistory(date, heartRate, spO2, temperature));
                    } else {
                        // Log si une des colonnes est manquante
                        android.util.Log.e("HistoryActivity", "Une ou plusieurs colonnes sont manquantes dans le Cursor");
                    }

                } while (cursor.moveToNext()); // Passer à l'élément suivant
            }

            // Fermer le curseur une fois qu'on a fini de l'utiliser
            cursor.close();
        }
    }
}