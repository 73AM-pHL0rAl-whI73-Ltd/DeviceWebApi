package com.example.devicewebapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Device {
    private @JsonProperty("deviceId") UUID deviceId;
    private @JsonProperty("deviceAlias") String deviceAlias;
    private @JsonProperty("macAddress") String macAddress;
    private @JsonProperty("sensorType") String sensorType;
}
