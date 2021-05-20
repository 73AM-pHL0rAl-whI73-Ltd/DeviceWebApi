package com.example.devicewebapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class DhtMessage {
    private @NonNull @JsonProperty("temperature") Double temperature;
    private @NonNull @JsonProperty("humidity") Double humidity;
    private @NonNull @JsonProperty("timeStamp") Long timeStamp;

    public static DhtMessage DhtMessageFromResultSet(ResultSet resultSet) throws SQLException {
        return new DhtMessage(resultSet.getDouble("temperature"),
                resultSet.getDouble("humidity"),
                resultSet.getLong("timeStamp")
        );
    }
}
