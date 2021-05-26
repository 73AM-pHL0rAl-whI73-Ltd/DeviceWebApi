#ifndef SENSOR_INCLUDED
#define SENSOR_INCLUDED

#include <Adafruit_Sensor.h>
#include <DHT.h>

class Sensor {
    private:
        DHT* sensor;
    public:
        Sensor(uint8_t dhtPin = 4, uint8_t DHT_TYPE = 11);
        void getReadings(float* temperature, float* humidity);

};

#endif