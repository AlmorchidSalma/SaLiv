package com.example.saliv;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView heartRateText;
    private TextView spo2Text;
    private TextView temperatureText;

    // Handler pour gérer les mises à jour régulières
    private Handler handler = new Handler();
    private Runnable runnable;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartRateText = findViewById(R.id.heartRateText);
        spo2Text = findViewById(R.id.spo2Text);
        temperatureText = findViewById(R.id.temperatureText);

        dbHelper = new DBHelper(this);

        // Définir une tâche qui sera exécutée régulièrement pour simuler la récupération des données
        runnable = new Runnable() {
            @Override
            public void run() {
                // Simuler la lecture des données
                VitalSigns vitalSigns = getSimulatedVitals();

                // Afficher les paramètres vitaux dans les TextViews
                heartRateText.setText("Fréquence cardiaque: " + vitalSigns.getHeartRate() + " BPM");
                spo2Text.setText("SpO2: " + vitalSigns.getSpO2() + "%");
                temperatureText.setText("Température: " + vitalSigns.getTemperature() + " °C");

                // Ajouter un nouvel enregistrement dans la base de données
                addHistoryToDatabase(vitalSigns);

                // Redémarrer le Runnable après 2 secondes
                handler.postDelayed(this, 2000); // 2000 millisecondes = 2 secondes
            }
        };

        // Démarrer la mise à jour régulière
        handler.post(runnable);

        // Initialiser le bouton pour voir l'historique
        Button historyButton = findViewById(R.id.historyButton);

        // Définir l'action du bouton pour ouvrir l'historique
        historyButton.setOnClickListener(v -> {
            // Créer un Intent pour ouvrir HistoryActivity
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    // Méthode pour simuler tous les paramètres vitaux
    private VitalSigns getSimulatedVitals() {
        // Simuler la fréquence cardiaque (60 à 120 BPM)
        int heartRate = (int) (Math.random() * (120 - 60 + 1)) + 60;

        // Simuler le SpO2 (90% à 100%)
        int spO2 = (int) (Math.random() * (100 - 90 + 1)) + 90;

        // Simuler la température corporelle (36°C à 38°C)
        float temperature = (float) (Math.random() * (38 - 36 + 1)) + 36;

        return new VitalSigns(heartRate, spO2, temperature);
    }

    // Ajouter une nouvelle entrée dans la base de données
    private void addHistoryToDatabase(VitalSigns vitalSigns) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Obtenir la date actuelle
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Ajouter l'enregistrement dans la base de données
        dbHelper.addHistoryRecord(db, currentDate, vitalSigns.getHeartRate(), vitalSigns.getSpO2(), vitalSigns.getTemperature());
    }

    // Classe pour encapsuler les signes vitaux
    class VitalSigns {
        private int heartRate;
        private int spO2;
        private float temperature;

        public VitalSigns(int heartRate, int spO2, float temperature) {
            this.heartRate = heartRate;
            this.spO2 = spO2;
            this.temperature = temperature;
        }

        public int getHeartRate() {
            return heartRate;
        }

        public int getSpO2() {
            return spO2;
        }

        public float getTemperature() {
            return temperature;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);  // Arrêter les mises à jour quand l'activité est détruite
    }
}