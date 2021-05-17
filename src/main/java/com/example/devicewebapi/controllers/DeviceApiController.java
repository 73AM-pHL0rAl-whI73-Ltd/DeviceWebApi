package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("measurements")
@RestController
public class DeviceApiController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceApiController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<DeviceMessage> getAllMeasurements() {return deviceService.getAllMessages();}

    @GetMapping(path = "/{id}")
    public DeviceMessage getMeasurementById(@PathVariable("id") long id) {
        return deviceService.getMessageById(id).orElse(null);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteMeasurementById(@PathVariable("id") long id) {deviceService.deleteMessageById(id);}
}
