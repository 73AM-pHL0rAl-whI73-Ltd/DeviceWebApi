package com.example.devicewebapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class DeviceMessage { // represents message from device
    private @NonNull @JsonProperty("temperature") Double temperature;
    private @NonNull @JsonProperty("humidity") Double humidity;
    private @NonNull @JsonProperty("timeStamp") Long timeStamp;
    private @NonNull @JsonProperty("deviceId") String deviceId;
}
