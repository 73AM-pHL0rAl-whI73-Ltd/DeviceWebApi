package com.example.devicewebapi.DAO;


import com.example.devicewebapi.models.Device;
import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.models.DhtMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres") // bean and identifier
@AllArgsConstructor // autogenerated constructor
public class DhtDeviceMeasurementPostgresDAO implements IDeviceMeasurementDAO {

    // java database connector
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private DevicePostgresDAO devicePostgresDAO;

    @Override
    public void addDeviceMeasurement(DhtMessage measurement) {
        var deviceIdMaybe = devicePostgresDAO.getDhtDeviceIdByUUID(measurement.getDeviceId());

        if(deviceIdMaybe.isEmpty()) {
            System.out.println("Returning");
            return;
        }




        String query = "INSERT INTO \"DhtMessages\" " +
                "(temperature, humidity, \"timeStamp\", \"deviceId\" ) " +
                "VALUES (?, ? , ?, ? )";

        // execute query
        jdbcTemplate.update(query,
                measurement.getTemperature(),
                measurement.getHumidity(),
                measurement.getTimeStamp(),
                deviceIdMaybe.get()
        );
    }

    @Override
    public List<DhtMessage> getAllDeviceMeasurements() {
        String query = "SELECT * FROM \"DhtMessages\" ORDER BY \"timeStamp\" DESC";;

        return jdbcTemplate.query(query,
                (resultSet, index) ->  {
                    return new DhtMessage(
                            resultSet.getDouble("temperature"),
                            resultSet.getDouble("humidity"),
                            resultSet.getLong("timeStamp"),
                            devicePostgresDAO.getDeviceUUIDFromDeviceId(resultSet.getInt("deviceId"))
                    );
                }
        );
    }

    @Override
    public List<DhtMessage> getLatestMeasurements(int top){
        String query = "SELECT * FROM \"DhtMessages\" ORDER BY \"timeStamp\" DESC LIMIT ?";
        return jdbcTemplate.query(query,
                (resultSet, index) ->{
                    return new DhtMessage(
                            resultSet.getDouble("temperature"),
                            resultSet.getDouble("humidity"),
                            resultSet.getLong("timeStamp"),
                            devicePostgresDAO.getDeviceUUIDFromDeviceId(resultSet.getInt("deviceId"))
                    );
                },
                top
        );
    }

    @Override
    public List<DhtMessage> getMeasurementByDeviceId(UUID id) {
        var deviceIdMaybe = devicePostgresDAO.getDhtDeviceIdByUUID(id);

        if(deviceIdMaybe.isEmpty()) return null;

        String query = "SELECT * FROM \"DhtMessages\" WHERE \"deviceId\" = ? ORDER BY \"timeStamp\" DESC";

        return jdbcTemplate.query(query,
                (resultSet, index) -> {
                    return new DhtMessage(
                            resultSet.getDouble("temperature"),
                            resultSet.getDouble("humidity"),
                            resultSet.getLong("timeStamp"),
                            devicePostgresDAO.getDeviceUUIDFromDeviceId(resultSet.getInt("deviceId"))
                    );
                },
                deviceIdMaybe.get()
        );
    }

    @Override
    public List<DhtMessage> getLatestDeviceMeasurements(Device device, int amount) {

        int id;

        if(device.getDeviceAlias() != null)
            id = devicePostgresDAO.getDhtDeviceIdByAlias(device.getDeviceAlias()).orElse(-1);
        else
            id = devicePostgresDAO.getDhtDeviceIdByUUID(device.getDeviceId()).orElse(-1);

        String query = "SELECT * FROM \"DhtMessages\" WHERE \"deviceId\" = ? " +
                        "ORDER BY \"timeStamp\" DESC LIMIT ?";

        return jdbcTemplate.query(query,
                (resultSet, index) -> {
                    return new DhtMessage(
                            resultSet.getDouble("temperature"),
                            resultSet.getDouble("humidity"),
                            resultSet.getLong("timeStamp"),
                            devicePostgresDAO.getDeviceUUIDFromDeviceId(resultSet.getInt("deviceId"))
                    );
                },
                id,
                amount
        );
    }
}
