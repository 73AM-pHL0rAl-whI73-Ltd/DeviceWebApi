package com.example.devicewebapi.services;

import com.example.devicewebapi.DAO.IDeviceDAO;
import com.example.devicewebapi.models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    private final IDeviceDAO iDeviceDAO;

    @Autowired
    public DeviceService(@Qualifier("devicepostgres") IDeviceDAO iDeviceDAO ) {
        this.iDeviceDAO = iDeviceDAO;
    }

    public Device generateNewDeviceFromAlias(Device device) {return iDeviceDAO.generateNewDeviceFromAlias(device);}

    public Device getDeviceById(UUID id) { return iDeviceDAO.getDeviceById(id);}
    public Device getDeviceByAlias(String alias) { return iDeviceDAO.getDeviceByAlias(alias);}

    public List<Device> getAllDevices() { return iDeviceDAO.getAllDevices();}


}
