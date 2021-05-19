package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.DeviceMessage;

import java.util.List;

public interface IDeviceMeasurementDataAccess {

    // add measurements
    void addDeviceMeasurement(DeviceMessage measurement);

    // get all measurements
    List<DeviceMessage> getAllDeviceMeasurements();
    List<DeviceMessage> getLatestMeasurements(int top);
}
