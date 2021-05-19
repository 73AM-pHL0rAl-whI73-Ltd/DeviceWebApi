package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.services.DashboardService;
import com.example.devicewebapi.services.DeviceMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("measurements")
@RestController
public class DeviceApiController {

    private final DeviceMeasurementService deviceMeasurementService;
    // services webservice/dashboard
    private final DashboardService dashboardService;

    @Autowired
    public DeviceApiController(DeviceMeasurementService deviceMeasurementService, DashboardService dashboardService) {
        this.deviceMeasurementService = deviceMeasurementService;
        this.dashboardService = dashboardService;
    }
    @PostMapping
    public void addDeviceMeasurement(@RequestBody DeviceMessage message) {
        deviceMeasurementService.addDeviceMeasurement(message);
        // sends update message to webservice/dashboard
        dashboardService.updateDashboard();
    }

    @GetMapping
    public List<DeviceMessage> getAllMeasurements() {return deviceMeasurementService.getAllDeviceMeasurements();}

    @GetMapping("/latest/")
    public List<DeviceMessage> getLatestOneMeasurements() { return deviceMeasurementService.getLatestMeasurements(1);}

    @GetMapping("/latest/{top}")
    public List<DeviceMessage> getLatestMeasurements(@PathVariable int top) { return deviceMeasurementService.getLatestMeasurements(top);}

    @GetMapping("/device/{id}")
    public List<DeviceMessage> getMeasurementByDeviceId(@PathVariable String id) { return deviceMeasurementService.getMeasurementByDeviceId(id);}

    }
