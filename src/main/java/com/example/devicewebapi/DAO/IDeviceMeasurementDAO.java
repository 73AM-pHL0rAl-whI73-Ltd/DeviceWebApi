package com.example.devicewebapi.DAO;

import com.example.devicewebapi.models.Device;
import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.models.DhtMessage;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public interface IDeviceMeasurementDAO {

    // add measurements
    void addDeviceMeasurement(DhtMessage measurement);

    // get all measurements
    List<DhtMessage> getAllDeviceMeasurements();
    List<DhtMessage> getLatestMeasurements(int top);
    List<DhtMessage> getMeasurementByDeviceId(UUID id);


    List<DhtMessage> getLatestDeviceMeasurements(Device.DeviceBuilder device, int amount);

    default List<DhtMessage> getLatestMeasurementByAlias(String alias, int amount) {

        return getLatestDeviceMeasurements(Device.builder().deviceAlias(alias), amount);
    }
    default List<DhtMessage> getLatestMeasurementById(UUID id, int amount) {

        return getLatestDeviceMeasurements(Device.builder().deviceId(id), amount);
    }
}
