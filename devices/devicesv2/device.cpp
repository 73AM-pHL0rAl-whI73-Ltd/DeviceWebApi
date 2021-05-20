#include "device.h"

Device::Device(char* deviceId, unsigned long interval) {
    strcpy(this->deviceId, deviceId);
    this->interval = interval;
}

void Device::init(char* url, char* SSID, char* password) {
    this->sensor = new Sensor();
    this->client = new APIClient(url);

    initWifi(SSID, password);

    configTime(0, 0, "pool.ntp.org");

    this->previous = 0;
}
void Device::run() {

    if((millis() - this->previous) > this->interval){

        this->sensor->getReadings(&(this->temperature), &(this->humidity));
        generatePayload();

        this->client->postmessage(this->message);

        //updates delay timer.
        this->previous = millis();
    }
}

void Device::initWifi(char* SSID, char* password) {

    WiFi.begin(SSID, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("Connected to WiFi");
    Serial.println(WiFi.localIP());
}
void Device::generatePayload() {
  DynamicJsonDocument doc(1024);
  doc["temperature"] = this->temperature;
  doc["humidity"] = this->humidity;
  doc["timeStamp"] = time(NULL);
  doc["deviceId"] = this->deviceId;
  serializeJson(doc, this->message);
}
