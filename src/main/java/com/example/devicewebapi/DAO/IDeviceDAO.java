package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.Device;

import java.util.List;
import java.util.UUID;

public interface IDeviceDAO {

    // generates new UUID for new device
    default void addDevice(UUID id, Device device) {
        device.setDeviceId(UUID.randomUUID());
        addDevice(device);
    }
    void addDevice(Device device);

    default Device generateNewDeviceFromAlias(Device device) {
        device.setDeviceId(UUID.randomUUID());
        addDevice(device);
        return device;
    }

    Device getDeviceById(UUID id);
    Device getDeviceByAlias(String alias);
    List<Device> getAllDevices();

}
