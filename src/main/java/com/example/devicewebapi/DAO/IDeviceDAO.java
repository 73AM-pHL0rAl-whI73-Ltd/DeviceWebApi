package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.DevicePojo;

import java.util.List;
import java.util.UUID;

public interface IDeviceDAO {

    // generates new UUID for new device
    default void addDevice(UUID id, DevicePojo device) {
        device.setDeviceId(UUID.randomUUID());
        addDevice(device);
    }
    void addDevice(DevicePojo device);

    DevicePojo getDeviceById(UUID id);
    DevicePojo getDeviceByAlias(String alias);
    List<DevicePojo> getAllDevices();

}
