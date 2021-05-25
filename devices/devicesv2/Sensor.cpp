#include "Sensor.h"


Sensor::Sensor(uint8_t dhtPin, uint8_t DHT_TYPE) {
    this->sensor = new DHT(dhtPin, DHT_TYPE);
    this->sensor->begin();
}

void Sensor::getReadings(float* temperature, float* humidity) {
    *temperature = this->sensor->readTemperature();
    *humidity = this->sensor->readHumidity();
}