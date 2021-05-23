package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.Device;
import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.models.DhtMessage;

import java.util.List;
import java.util.UUID;

public interface IDeviceMeasurementDAO {

    // add measurements
    void addDeviceMeasurement(DhtMessage measurement);

    // get all measurements
    List<DhtMessage> getAllDeviceMeasurements();
    List<DhtMessage> getLatestMeasurements(int top);
    List<DhtMessage> getMeasurementByDeviceId(UUID id);


    List<DhtMessage> getLatestDeviceMeasurements(Device device, int amount);

    default List<DhtMessage> getLatestMeasurementByAlias(String alias, int amount) {
        // TODO : lookup builder pattern to make this look cleaner
        var device = new Device();
        device.setDeviceAlias(alias);

        return getLatestDeviceMeasurements(device, amount);
    }
    default List<DhtMessage> getLatestMeasurementById(UUID id, int amount) {
        // TODO : lookup builder pattern to make this look cleaner
        var device = new Device();
        device.setDeviceId(id);

        return getLatestDeviceMeasurements(device, amount);
    }
}
