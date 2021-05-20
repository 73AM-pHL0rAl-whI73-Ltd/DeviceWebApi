#ifndef DEVICE_INCLUDED
#define DEVICE_INCLUDED

#include "Arduino.h"
#include <ArduinoJson.h>

#include <WiFi.h>

#include "Sensor.h"
#include "APIClient.h"
#include "string.h"

class Device {

    private:
        // components
        Sensor* sensor;
        APIClient* client;

        float temperature, humidity;

        char message[250];
        char deviceId[100];

        unsigned long interval;
        unsigned long previous;

        void initWifi(char* SSID, char* password);
        void generatePayload();

    public:
        Device(char* deviceId, unsigned long interval);

        void init(char* url, char* SSID, char* password);
        void run();

};

#endif