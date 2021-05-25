package com.example.devicewebapi.DAO;


import com.example.devicewebapi.models.Device;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("devicepostgres")
@AllArgsConstructor
public class DevicePostgresDAO implements IDeviceDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addDevice(Device device) {
        String query;

        int deviceInfoId;
        int sensorId;

        // check for already existing rows
        var deviceInfoIdMaybe = getDeviceInfoId(device);
        var sensorTypeIdMaybe = getSensorTypeId(device);

        // insert deviceinfo if it does not exist
        if(deviceInfoIdMaybe.isEmpty())
        {
            deviceInfoId = insertDeviceInfoId(device);
        } else deviceInfoId = deviceInfoIdMaybe.get();

        // insert sensor type if it does not exist
        if(sensorTypeIdMaybe.isEmpty()) {
            query = "INSERT INTO \"SensorTypes\" (\"sensorType\") VALUES (?) RETURNING id";

            sensorId = insertSensorId(device);
        } else sensorId = sensorTypeIdMaybe.get();

        // if there is a device present
        var deviceIdMaybe =  getDeviceId(deviceInfoId, sensorId);

        if(deviceIdMaybe.isEmpty()) // no device present, insert device
        {
            query = "INSERT INTO \"Devices\" (\"sensorType\", \"deviceInfoId\") VALUES (?,?)";

            jdbcTemplate.update(query,
                    sensorId,
                    deviceInfoId);

            return true; // new device added
        }
        return false; // device already exists
    }
    // TODO: Implement this:
    @Override
    public Device getDeviceById(UUID id) {
        String query = "SELECT deviceinfo.\"deviceId\", deviceinfo.\"deviceAlias\", deviceinfo.\"macAddress\", sensortypes.\"sensorType\" FROM (" +
                "\"Devices\" devices " +
                "INNER JOIN  " +
                "\"DeviceInfo\" deviceinfo on devices.\"deviceInfoId\" = deviceinfo.id " +
                "INNER JOIN " +
                "\"SensorTypes\" sensortypes on devices.\"sensorType\" = sensortypes.id) " +
                "WHERE \"deviceId\" = ?";

        return jdbcTemplate.query(
                query,
                resultSet -> {
                    if(!resultSet.next()) return null;
                    return new Device(
                            resultSet.getObject("deviceId", UUID.class),
                            resultSet.getString("deviceAlias"),
                            resultSet.getString("macAddress"),
                            resultSet.getString("sensorType")
                    );
                },
                id
        );
    }

    @Override
    public Device getDeviceByAlias(String alias) {
        String query = "SELECT * FROM " +
                "(\"Devices\" devices " +
                "INNER JOIN  " +
                "\"DeviceInfo\" deviceinfo on devices.\"deviceInfoId\" = deviceinfo.id " +
                "INNER JOIN " +
                "\"SensorTypes\" sensortypes on devices.\"sensorType\" = sensortypes.id) " +
                "WHERE \"deviceAlias\" = ?";

        return jdbcTemplate.query(
                query,
                resultSet -> {
                    if(!resultSet.next()) return null;
                    return new Device(
                            resultSet.getObject("deviceId", UUID.class),
                            resultSet.getString("deviceAlias"),
                            resultSet.getString("macAddress"),
                            resultSet.getString("sensorType")
                    );
                },
                alias
        );
    }

    @Override
    public List<Device> getAllDevices() {
        String query = "SELECT * FROM " +
                "\"Devices\" devices " +
                "INNER JOIN  " +
                "\"DeviceInfo\" deviceinfo on devices.\"deviceInfoId\" = deviceinfo.id " +
                "INNER JOIN " +
                "\"SensorTypes\" sensortypes on devices.\"sensorType\" = sensortypes.id";

        return jdbcTemplate.query(
                query,
                (resultSet,index) -> new Device(
                        resultSet.getObject("deviceId", UUID.class),
                        resultSet.getString("deviceAlias"),
                        resultSet.getString("macAddress"),
                        resultSet.getString("sensorType")
                )
        );
    }

    private Optional<Integer> getDeviceInfoId(Device device) {
        String query = "SELECT id FROM \"DeviceInfo\" WHERE " +
                "\"deviceId\" = ? OR " +
                "\"deviceAlias\" = ? OR " +
                "\"macAddress\" = ?";

        return Optional.ofNullable(jdbcTemplate.query(query,
                (resultSet) -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getInt("Id");
                },
                device.getDeviceId(),
                device.getDeviceAlias(),
                device.getMacAddress())
        );
    }
    private Optional<Integer> getSensorTypeId(Device device) {
        String query = "SELECT id FROM \"SensorTypes\" WHERE \"sensorType\" = ?";

        return Optional.ofNullable(jdbcTemplate.query(
                query,
                (resultSet) -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getInt("Id");
                },
                device.getSensorType())
        );
    }

    private Optional<Integer> getDeviceId(int deviceInfoId, int sensorTypeId) {
        String query = "SELECT id FROM \"Devices\" WHERE " +
                " \"sensorType\" = ? AND \"deviceInfoId\" = ?";

        return Optional.ofNullable(jdbcTemplate.query(
                query,
                (resultSet) -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getInt("Id");
                },
                sensorTypeId,
                deviceInfoId
        ));
    }

    public Optional<Integer> getDhtDeviceIdByUUID(UUID messageDeviceId) {
        // to hold values
        var device = getDeviceById(messageDeviceId);

        var deviceInfoIdMaybe = getDeviceInfoId(device);
        var deviceSensorTypeIdMaybe = getSensorTypeId(device);

        if(deviceInfoIdMaybe.isPresent() & deviceSensorTypeIdMaybe.isPresent())
            return getDeviceId(deviceInfoIdMaybe.get(), deviceSensorTypeIdMaybe.get());
        else
            return Optional.empty();

    }
    public Optional<Integer> getDhtDeviceIdByAlias(String alias) {
        // to hold values
        var device = getDeviceByAlias(alias);
        device.setSensorType("dht");

        var deviceInfoIdMaybe = getDeviceInfoId(device);
        var deviceSensorTypeIdMaybe = getSensorTypeId(device);

        if(deviceInfoIdMaybe.isPresent() & deviceSensorTypeIdMaybe.isPresent())
            // wrong order
            return getDeviceId( deviceInfoIdMaybe.get(),deviceSensorTypeIdMaybe.get());
        else
            return Optional.empty();

    }
    public UUID getDeviceUUIDFromDeviceId(int deviceId) {
        String query = "SELECT * FROM " +
                "(\"Devices\" devices " +
                "INNER JOIN  " +
                "\"DeviceInfo\" deviceinfo on devices.\"deviceInfoId\" = deviceinfo.id) " +
                "WHERE devices.id = ?";

        return jdbcTemplate.query(
                query,
                (resultSet) -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getObject("deviceId", UUID.class);
                },
                deviceId
        );
    }
    private int insertDeviceInfoId(Device device){
        String query =
                "INSERT INTO \"DeviceInfo\" " +
                        "(\"deviceId\", \"deviceAlias\", \"macAddress\") " +
                        "VALUES (?, ? , ?) " +
                        "ON CONFLICT DO NOTHING " +
                        "RETURNING id";
        var deviceInfoId = jdbcTemplate.query(query,
                (resultSet) -> {
                    resultSet.next();
                    return resultSet.getInt("Id");
                },
                new Object[]{
                        device.getDeviceId(),
                        device.getDeviceAlias(),
                        device.getMacAddress()});
        return deviceInfoId;
    }
    private int insertSensorId(Device device){
        String query = "INSERT INTO \"SensorTypes\" (\"sensorType\") VALUES (?) RETURNING id";
        var sensorId = jdbcTemplate.query(query,
                (resultSet) -> {
                    resultSet.next();
                    return resultSet.getInt("Id");
                },
                new Object[]{
                        device.getSensorType()});
        return sensorId;
    }
}
