package com.example.devicewebapi.DAO;

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
}
