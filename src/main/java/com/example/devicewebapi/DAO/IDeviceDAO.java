package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.Device;

import java.util.List;
import java.util.UUID;

public interface IDeviceDAO {

    // generates new UUID for new device
    default Boolean addDevice(UUID id, Device device) {
        device.setDeviceId(UUID.randomUUID());
        return addDevice(device);
    }
    Boolean addDevice(Device device);

    default Device generateNewDeviceFromAlias(Device device) {
        // abort if device with alias already exists
        if(getDeviceByAlias(device.getDeviceAlias()) != null)
            return null;

        // generate new UUID for device
        device.setDeviceId(UUID.randomUUID());

        // return null if device already exists
        return addDevice(device) ? device : null;
    }

    Device getDeviceById(UUID id);
    Device getDeviceByAlias(String alias);
    List<Device> getAllDevices();

}
