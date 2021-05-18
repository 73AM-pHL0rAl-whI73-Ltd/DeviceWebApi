#include <HTTPClient.h>
#include <WiFi.h>
#include <ArduinoJson.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>

#define DHT_PIN 4
#define DHT_TYPE DHT11
DHT dht(DHT_PIN, DHT_TYPE);

const char* ntpServer = "pool.ntp.org";
const char* ssid = "";
const char* pw = "";
const char* url = "https://devicewebapi.herokuapp.com/measurements";
const char* deviceId = "Williams DHT11";

unsigned long prevMillis = 0;
unsigned long timeDelay = 60000;


void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, pw);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Connected to WiFi");
  Serial.println(WiFi.localIP());
  configTime(0, 0, ntpServer);

}
unsigned long getTime() {
  time_t now;
  struct tm timeinfo;
  if (!getLocalTime(&timeinfo)) {
    Serial.println("Failed to obtain time");
    return(0);
  }
  time(&now);
  return now;
}

/*void httpPost(char message[256]) {
  
  
}*/

/*char* createMessage(float temp, float hum, unsigned long unixTime) {
  
  return message;
}*/

void loop() {
  float temp = 15.0;//dht.readTemperature();
  float hum  = 20.0;//dht.readHumidity();
  unsigned long unixTime = getTime();
  if((millis() - prevMillis) > timeDelay){
    Serial.print("Temp: ");
    Serial.println(temp);
    Serial.print("Hum: ");
    Serial.println(hum);
    Serial.print("Time: ");
    Serial.println(unixTime);
    if (WiFi.status() == WL_CONNECTED) {
      HTTPClient http;
      http.begin(url);
      http.addHeader("Content-Type", "application/json");
      char message[256];
      DynamicJsonDocument doc(1024);
      doc["temperature"] = temp;
      doc["humidity"] = hum;
      doc["timeStamp"] = unixTime;
      doc["deviceId"] = deviceId;
      serializeJson(doc, message);
      int response = http.POST(message);
      Serial.print("HTTP response: ");
      Serial.println(response);
      http.end();
    }
    else{
      Serial.println("No WIFI");
    }
    prevMillis = millis();
  }

}


