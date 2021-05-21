package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.Device;
import com.example.devicewebapi.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("devices")
@RestController
@AllArgsConstructor
public class DeviceController {

    @Autowired
    private final DeviceService deviceService;

    @GetMapping("/new") // generate new device for webservice client
    public Device generateDevice(@RequestParam("deviceAlias")) {
        // needs to create new device with provided deviceAlias
    }

}
