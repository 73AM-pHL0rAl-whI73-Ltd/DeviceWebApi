package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.DhtMessage;
import com.example.devicewebapi.services.DashboardService;
import com.example.devicewebapi.services.DeviceMeasurementService;

import com.example.devicewebapi.services.DeviceService;
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
    private final DeviceService deviceService;

    @Autowired
    public DeviceApiController(DeviceMeasurementService deviceMeasurementService, DashboardService dashboardService, DeviceService deviceService) {
        this.deviceMeasurementService = deviceMeasurementService;
        this.dashboardService = dashboardService;
        this.deviceService = deviceService;
    }
    @PostMapping
    public void addDeviceMeasurement(@RequestBody DhtMessage message) {

        deviceMeasurementService.addDeviceMeasurement(message);

        // get device from id and send it to webservice
        var device = deviceService.getDeviceById(message.getDeviceId());
        dashboardService.updateDashboard(device);
    }

    @GetMapping
    public List<DhtMessage> getAllMeasurements() {return deviceMeasurementService.getAllDeviceMeasurements();}

    @GetMapping("/latest/")
    public List<DhtMessage> getLatestOneMeasurements() { return deviceMeasurementService.getLatestMeasurements(1);}

    @GetMapping("/latest/{top}")
    public List<DhtMessage> getLatestMeasurements(@PathVariable int top) { return deviceMeasurementService.getLatestMeasurements(top);}

    @GetMapping("/latest/{alias}/{top}")
    public List<DhtMessage> getLatestMeasurementsByAlias(@PathVariable String alias, @PathVariable int top) {
        return deviceMeasurementService.getLatestMeasurementByAlias(alias, top);
    }
    @GetMapping("/latest/{id}/{top}")
    public List<DhtMessage> getLatestMeasurementsById(@PathVariable UUID id, @PathVariable int top) {
        return deviceMeasurementService.getLatestMeasurementById(id, top);
    }

    @GetMapping("/device/{id}")
    public List<DhtMessage> getMeasurementByDeviceId(@PathVariable UUID id) { return deviceMeasurementService.getMeasurementByDeviceId(id);}

    }
