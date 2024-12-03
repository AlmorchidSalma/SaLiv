#include <OneWire.h>
#include <DallasTemperature.h>


#define ONE_WIRE_BUS 2

OneWire oneWire(ONE_WIRE_BUS);

DallasTemperature sensors(&oneWire);

void setup() {
    
    Serial.begin(9600);
    Serial.println("Initialisation du capteur DS18B20...");

    
    sensors.begin();
}

void loop() {
    
    sensors.requestTemperatures();

    
    float temperatureC = sensors.getTempCByIndex(0);

    
    if (temperatureC == DEVICE_DISCONNECTED_C) {
        Serial.println("Erreur : Capteur non connecté !");
    } else {
        Serial.print("Température : ");
        Serial.print(temperatureC);
        Serial.println(" °C");
    }

    
    delay(1000);
}
