package com.example.devicewebapi.services;

import com.example.devicewebapi.DAO.IDeviceMeasurementDataAccess;
import com.example.devicewebapi.models.DeviceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DeviceService {

        private final IDeviceMeasurementDataAccess iDeviceMeasurementDataAccess;

        @Autowired
        public DeviceService(@Qualifier("postgres") IDeviceMeasurementDataAccess iDeviceMeasurementDataAccess) {
            this.iDeviceMeasurementDataAccess = iDeviceMeasurementDataAccess;
        }

        public void addDeviceMeasurement(DeviceMessage message) {iDeviceMeasurementDataAccess.addDeviceMeasurement(message);}
        public List<DeviceMessage> getAllDeviceMeasurements() { return iDeviceMeasurementDataAccess.getAllDeviceMeasurements();}
        public List<DeviceMessage> getLatestMeasurements(int top) { return iDeviceMeasurementDataAccess.getLatestMeasurements(top);}
        public List<DeviceMessage> getMeasurementByDeviceId(String id) { return iDeviceMeasurementDataAccess.getMeasurementByDeviceId(id);}
}
