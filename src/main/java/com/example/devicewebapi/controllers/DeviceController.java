package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.Device;
import com.example.devicewebapi.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RequestMapping("devices")
@RestController
@AllArgsConstructor
public class DeviceController {

    @Autowired
    private final DeviceService deviceService;

    @PostMapping("/new") // generate new device for webservice client
    public Device generateDevice(@RequestParam("deviceAlias") String deviceAlias) {
        //create empty device and set Alias
        var device = new Device();
        device.setDeviceAlias(deviceAlias);

        // add device, DAO generates new UUID for device
        return deviceService.generateNewDeviceFromAlias(device);
    }

    @GetMapping
    public List<Device> getAllDevices()
    {
        return deviceService.getAllDevices();
    }

    @GetMapping("/id/{id}")
    public Device getDeviceById(@PathVariable UUID id) {return deviceService.getDeviceById(id);}
    @GetMapping("/alias/{alias}")
    public Device getDeviceByAlias(@PathVariable String alias) {return deviceService.getDeviceByAlias(alias);}

}
