package com.example.devicewebapi.DAO;


import com.example.devicewebapi.models.Device;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("devicepostgres")
@AllArgsConstructor
public class DevicePostgresDAO implements IDeviceDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    //TODO: Check if this works?
    @Override
    public void addDevice(Device device) {
        KeyHolder holder = new GeneratedKeyHolder();

        String query = "INSERT INTO \"DeviceInfo\" " +
                "(\"deviceId\", \"deviceAlias\", \"macAddress\") " +
                "VALUES (?, ? , ?) RETURNING id";

        int deviceId = jdbcTemplate.query(query,
                (resultSet) -> {
                    resultSet.next();
                    return resultSet.getInt("Id");
                },
                new Object[]{
                        device.getDeviceId(),
                        device.getDeviceAlias(),
                        device.getMacAddress()});

        query = "INSERT INTO \"SensorTypes\" (\"sensorType\") VALUES (?) RETURNING id";

        int sensorId = jdbcTemplate.query(query,
                (resultSet) -> {
                    resultSet.next();
                    return resultSet.getInt("Id");
                },
                new Object[]{
                        device.getSensorType()});

        query = "INSERT INTO \"Devices\" (\"sensorType\", \"deviceInfoId\") VALUES (?,?)";

        jdbcTemplate.update(query,
                sensorId,
                deviceId);
    }
    // TODO: Implement this:
    @Override
    public Device getDeviceById(UUID id) {
        return null;
    }

    @Override
    public Device getDeviceByAlias(String alias) {
        return null;
    }

    @Override
    public List<Device> getAllDevices() {
        return null;
    }
}
