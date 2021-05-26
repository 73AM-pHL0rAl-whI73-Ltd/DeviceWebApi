package com.example.devicewebapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    private @JsonProperty("deviceId") UUID deviceId;
    private @JsonProperty("deviceAlias") String deviceAlias;
    private @JsonProperty("macAddress") String macAddress;
    private @JsonProperty("sensorType") String sensorType;
}
