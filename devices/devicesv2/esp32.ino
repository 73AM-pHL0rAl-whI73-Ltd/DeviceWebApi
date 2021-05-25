#include "device.h"

auto device = new Device("enter deviceid/connstring",5000);

void setup() {
  Serial.begin(115200);

  device->init("https://devicewebapi.herokuapp.com/measurements", "SSID", "password");
}

void loop() {
  
    device->run();
}