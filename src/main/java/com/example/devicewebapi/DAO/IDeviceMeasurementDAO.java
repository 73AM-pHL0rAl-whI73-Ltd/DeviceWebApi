package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.DeviceMessage;

import java.util.List;

public interface IDeviceMeasurementDAO {

    // add measurements
    void addDeviceMeasurement(DeviceMessage measurement);

    // get all measurements
    List<DeviceMessage> getAllDeviceMeasurements();
    List<DeviceMessage> getLatestMeasurements(int top);
    List<DeviceMessage> getMeasurementByDeviceId(String id);
}
