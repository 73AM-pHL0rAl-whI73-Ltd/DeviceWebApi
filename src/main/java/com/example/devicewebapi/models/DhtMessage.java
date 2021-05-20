package com.example.devicewebapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
public class DhtMessage {
    private @NonNull @JsonProperty("temperature") Double temperature;
    private @NonNull @JsonProperty("humidity") Double humidity;
    private @NonNull @JsonProperty("timeStamp") Long timeStamp;
    private @NonNull @JsonProperty("deviceId") UUID deviceId;

    public static DhtMessage DhtMessageFromResultSet(ResultSet resultSet) throws SQLException {
        return new DhtMessage(resultSet.getDouble("temperature"),
                resultSet.getDouble("humidity"),
                resultSet.getLong("timeStamp"),
                resultSet.getObject("deviceId", UUID.class)
        );
    }
}
