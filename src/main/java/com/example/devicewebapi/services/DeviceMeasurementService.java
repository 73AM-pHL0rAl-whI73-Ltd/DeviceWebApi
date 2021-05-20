package com.example.devicewebapi.services;

import com.example.devicewebapi.DAO.IDeviceMeasurementDAO;
import com.example.devicewebapi.models.DhtMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceMeasurementService {

        private final IDeviceMeasurementDAO iDeviceMeasurementDataAccess;

        @Autowired
        public DeviceMeasurementService(@Qualifier("postgres") IDeviceMeasurementDAO iDeviceMeasurementDataAccess) {
            this.iDeviceMeasurementDataAccess = iDeviceMeasurementDataAccess;
        }

        public void addDeviceMeasurement(DhtMessage message) {iDeviceMeasurementDataAccess.addDeviceMeasurement(message);}
        public List<DhtMessage> getAllDeviceMeasurements() { return iDeviceMeasurementDataAccess.getAllDeviceMeasurements();}
        public List<DhtMessage> getLatestMeasurements(int top) { return iDeviceMeasurementDataAccess.getLatestMeasurements(top);}
        public List<DhtMessage> getMeasurementByDeviceId(UUID id) { return iDeviceMeasurementDataAccess.getMeasurementByDeviceId(id);}
}
