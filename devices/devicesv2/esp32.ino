#include "device.h"

auto device = new Device("WilliamsDHT11",10000);

void setup() {
  Serial.begin(115200);

  device->init("https://devicewebapi.herokuapp.com/measurements", "", "");
}

void loop() {
  
    device->run();
}