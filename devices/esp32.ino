#include <HTTPClient.h>
#include <WiFi.h>
#include <ArduinoJson.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>
//Defines pin for temp sensor and creates an instance of DHT class.
#define DHT_PIN 4
#define DHT_TYPE DHT11
DHT dht(DHT_PIN, DHT_TYPE);
//Constant variables for timeserver, network credentials and device ID.
const char* ntpServer = "pool.ntp.org";
const char* ssid = "";
const char* pw = "";
const char* url = "https://devicewebapi.herokuapp.com/measurements";
const char* deviceId = "Williams DHT11";
//variables for delay.
unsigned long prevMillis = 0;
unsigned long timeDelay = 10000;


void setup() {
  //Initiate serial communication.
  Serial.begin(115200);
  //Initates Wifi.
  WiFi.begin(ssid, pw);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Connected to WiFi");
  Serial.println(WiFi.localIP());
  //configure time from time server.
  configTime(0, 0, ntpServer);

}
//gets time from timeserver and returns it.
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

//Creates a JSON formatted String to be sent via HTTP.
String createMessage(float temp, float hum, unsigned long unixTime) {
  String message;
  DynamicJsonDocument doc(1024);
  doc["temperature"] = temp;
  doc["humidity"] = hum;
  doc["timeStamp"] = unixTime;
  doc["deviceId"] = deviceId;
  serializeJson(doc, message);
  return message;
}
//Takes a JSON formatted string and posts via HTTP to the specified url.
int httpPost(String message) {
  HTTPClient http;
  http.begin(url);
  http.addHeader("Content-Type", "application/json");
  int response = http.POST(message);
  http.end();
  return response;
}


void loop() {
  //Reads values from DHT sensor and creates a timestamp.
  float temp = 15.2;//dht.readTemperature();
  float hum  = 20.2;//dht.readHumidity();
  unsigned long unixTime = getTime();
  if((millis() - prevMillis) > timeDelay){
    if (WiFi.status() == WL_CONNECTED) {
      //if delay timer has passed and WiFi is connected, creates a message and sends it via HTTP, prints out send status to serial monitor.
      String message = createMessage(temp, hum, unixTime);
      int response = httpPost(message);
      if ( response == 200) {
        Serial.print("Message sent OK: ");
        Serial.println(message);
      }
      else{
        Serial.print("HTTP ERROR CODE: ");
        Serial.println(response);
      }
    }
    else{
      Serial.println("No WIFI");
    }
    //updates delay timer.
    prevMillis = millis();
  }

}


