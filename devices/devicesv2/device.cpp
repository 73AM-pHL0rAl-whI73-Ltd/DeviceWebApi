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
        
        Serial.println("getting readings");
        this->sensor->getReadings(&(this->temperature), &(this->humidity));

        if(!isValidReadings())
            return;

        Serial.println("generating payload");
        generatePayload();

        Serial.println("posting message");
        this->client->postmessage(this->message);

        Serial.println("updating timer");
        //updates delay timer.
        this->previous = millis();
    }
}
bool Device::isValidReadings() {
    return !std::isnan(this->temperature) && !std::isnan(this->humidity);
}

void Device::initWifi(char* SSID, char* password) {

    WiFi.begin(SSID, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("%nConnected to WiFi");
    Serial.println(WiFi.localIP());
}
void Device::generatePayload() {
  DynamicJsonDocument doc(1024);
  doc["temperature"] = this->temperature;
  doc["humidity"] = this->humidity;
  doc["timeStamp"] = time(NULL);
  doc["deviceId"] = this->deviceId;
  serializeJson(doc, this->message);

  Serial.print("message: ");
  Serial.println(this->message);
}
