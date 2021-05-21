package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.Device;
import com.example.devicewebapi.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RequestMapping("devices")
@RestController
@AllArgsConstructor
public class DeviceController {

    @Autowired
    private final DeviceService deviceService;

    @PostMapping("/new") // generate new device for webservice client
    public UUID generateDevice(@RequestParam("deviceAlias") String deviceAlias) {
        //create empty device and set Alias
        var device = new Device();
        device.setDeviceAlias(deviceAlias);

        // add device, DAO generates new UUID for device
        deviceService.addDevice(device);

        //needs to return UUID to webservice client
        return null;
    }

}
