package com.example.devicewebapi.services;

import com.example.devicewebapi.DAO.IDeviceMeasurementDAO;
import com.example.devicewebapi.models.DeviceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceMeasurementService {

        private final IDeviceMeasurementDAO iDeviceMeasurementDataAccess;

        @Autowired
        public DeviceMeasurementService(@Qualifier("postgres") IDeviceMeasurementDAO iDeviceMeasurementDataAccess) {
            this.iDeviceMeasurementDataAccess = iDeviceMeasurementDataAccess;
        }

        public void addDeviceMeasurement(DeviceMessage message) {iDeviceMeasurementDataAccess.addDeviceMeasurement(message);}
        public List<DeviceMessage> getAllDeviceMeasurements() { return iDeviceMeasurementDataAccess.getAllDeviceMeasurements();}
        public List<DeviceMessage> getLatestMeasurements(int top) { return iDeviceMeasurementDataAccess.getLatestMeasurements(top);}
        public List<DeviceMessage> getMeasurementByDeviceId(String id) { return iDeviceMeasurementDataAccess.getMeasurementByDeviceId(id);}
}
