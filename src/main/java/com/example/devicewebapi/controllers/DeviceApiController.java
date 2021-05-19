package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.services.DashboardService;
import com.example.devicewebapi.services.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("measurements")
@RestController
public class DeviceApiController {

    private final DeviceService deviceService;
    // services webservice/dashboard
    private final DashboardService dashboardService;

    @Autowired
    public DeviceApiController(DeviceService deviceService, DashboardService dashboardService) {
        this.deviceService = deviceService;
        this.dashboardService = dashboardService;
    }
    @PostMapping
    public void addDeviceMeasurement(@RequestBody DeviceMessage message) {
        deviceService.addDeviceMeasurement(message);
        // sends update message to webservice/dashboard
        dashboardService.updateDashboard();
    }

    @GetMapping
    public List<DeviceMessage> getAllMeasurements() {return deviceService.getAllDeviceMeasurements();}

    @GetMapping("/latest/")
    public List<DeviceMessage> getLatestOneMeasurements() { return deviceService.getLatestMeasurements(1);}

    @GetMapping("/latest/{top}")
    public List<DeviceMessage> getLatestMeasurements(@PathVariable int top) { return deviceService.getLatestMeasurements(top);}

    @GetMapping("/device/{id}")
    public List<DeviceMessage> getMeasurementByDeviceId(@PathVariable String id) { return deviceService.getMeasurementByDeviceId(id);}

    }
