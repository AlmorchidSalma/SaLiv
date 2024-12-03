#include <Wire.h>
#include "MAX30100_PulseOximeter.h"

#define REPORTING_PERIOD_MS 1000 
PulseOximeter pox; 
uint32_t tsLastReport = 0; 

void onPulseDetected() {
    Serial.println("Pouls détecté !");
}

void setup() {
    Serial.begin(9600); 
    Serial.println("Initialisation du capteur MAX30100...");

    
    if (!pox.begin()) {
        Serial.println("Erreur : Impossible de détecter le MAX30100.");
        for (;;);
    } else {
        Serial.println("MAX30100 initialisé.");
    }

    
    pox.setIRLedCurrent(MAX30100_LED_CURR_7_6MA);
    pox.setOnPulseCallback(onPulseDetected);
}

void loop() {
    
    pox.update();

    
    if (millis() - tsLastReport > REPORTING_PERIOD_MS) {
        tsLastReport = millis();
        Serial.print("Fréquence cardiaque (BPM) : ");
        Serial.print(pox.getHeartRate());
        Serial.print(" | Taux d'oxygène (SpO2) : ");
        Serial.println(pox.getSpO2());
    }
}
