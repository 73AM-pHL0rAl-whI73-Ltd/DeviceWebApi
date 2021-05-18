package com.example.devicewebapi.services;

import com.example.devicewebapi.DAO.IDeviceMeasurementDataAccess;
import com.example.devicewebapi.models.DeviceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

        private final IDeviceMeasurementDataAccess iDeviceMeasurementDataAccess;

        @Autowired
        public DeviceService(@Qualifier("postgres") IDeviceMeasurementDataAccess iDeviceMeasurementDataAccess) {
            this.iDeviceMeasurementDataAccess = iDeviceMeasurementDataAccess;
        }

        public void addDeviceMeasurement(DeviceMessage message) {IDeviceMeasurementDataAccess.addDeviceMeasurement(message);}
        public List<DeviceMessage> getAllDeviceMeasurements() { return IDeviceMeasurementDataAccess.getAllDeviceMeasurements();}
    }
}
