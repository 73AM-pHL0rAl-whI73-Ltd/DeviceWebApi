package com.example.devicewebapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Cleanup;
import lombok.Data;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class DeviceMessage { // represents message from device
    private @NonNull @JsonProperty("temperature") Double temperature;
    private @NonNull @JsonProperty("humidity") Double humidity;
    private @NonNull @JsonProperty("timeStamp") Long timeStamp;
    private @NonNull @JsonProperty("deviceId") String deviceId;

    // returns new DeviceMessage object from resultset
    public static DeviceMessage DeviceMessageFromResultSet(ResultSet resultSet) throws SQLException {
        return new DeviceMessage(resultSet.getDouble("temperature"),
                resultSet.getDouble("humidity"),
                resultSet.getLong("timeStamp"),
                resultSet.getString("deviceId")
        );
    }
}
