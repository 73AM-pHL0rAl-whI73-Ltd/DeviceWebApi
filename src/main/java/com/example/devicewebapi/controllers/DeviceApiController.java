package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.DeviceMessage;
import com.example.devicewebapi.models.DhtMessage;
import com.example.devicewebapi.services.DashboardService;
import com.example.devicewebapi.services.DeviceMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

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
    public void addDeviceMeasurement(@RequestBody DhtMessage message) {
        deviceMeasurementService.addDeviceMeasurement(message);
        // sends update message to webservice/dashboard
        dashboardService.updateDashboard();
    }

    @GetMapping
    public List<DhtMessage> getAllMeasurements() {return deviceMeasurementService.getAllDeviceMeasurements();}

    @GetMapping("/latest/")
    public List<DhtMessage> getLatestOneMeasurements() { return deviceMeasurementService.getLatestMeasurements(1);}

    @GetMapping("/latest/{top}")
    public List<DhtMessage> getLatestMeasurements(@PathVariable int top) { return deviceMeasurementService.getLatestMeasurements(top);}

    @GetMapping("/latest/{alias}/{top}")
    public List<DhtMessage> getLatestMeasurementsByAlias(@PathVariable String alias, @PathVariable int top) {

    }
    @GetMapping("/latest/{id}/{top}")
    public List<DhtMessage> getLatestMeasurementsByAlias(@PathVariable UUID id, @PathVariable int top) {

    }

    @GetMapping("/device/{id}")
    public List<DhtMessage> getMeasurementByDeviceId(@PathVariable UUID id) { return deviceMeasurementService.getMeasurementByDeviceId(id);}

    }
