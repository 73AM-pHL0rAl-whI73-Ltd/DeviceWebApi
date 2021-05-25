package com.example.devicewebapi.controllers;

import com.example.devicewebapi.models.DhtMessage;
import com.example.devicewebapi.services.DashboardService;
import com.example.devicewebapi.services.DhtMessageService;

import com.example.devicewebapi.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RequestMapping("measurements")
@RestController
public class DhtMessageController {

    private final DhtMessageService dhtMessageService;
    // services webservice/dashboard
    private final DashboardService dashboardService;
    private final DeviceService deviceService;

    @Autowired
    public DhtMessageController(DhtMessageService dhtMessageService, DashboardService dashboardService, DeviceService deviceService) {
        this.dhtMessageService = dhtMessageService;
        this.dashboardService = dashboardService;
        this.deviceService = deviceService;
    }
    @PostMapping
    public void addDeviceMeasurement(@RequestBody DhtMessage message) {
        var device = deviceService.getDeviceById(message.getDeviceId());
        if(device == null) return; // do not add measurement if no device found

        dhtMessageService.addDeviceMeasurement(message);
        dashboardService.updateDashboard(device);
    }

    @GetMapping
    public List<DhtMessage> getAllMeasurements() {return dhtMessageService.getAllDeviceMeasurements();}

    @GetMapping("/latest")
    public List<DhtMessage> getLatestOneMeasurements() { return dhtMessageService.getLatestMeasurements(1);}

    @GetMapping("/latest/{top}")
    public List<DhtMessage> getLatestMeasurements(@PathVariable int top) { return dhtMessageService.getLatestMeasurements(top);}

    @GetMapping("/latest/alias/{alias}/{top}")
    public List<DhtMessage> getLatestMeasurementsByAlias(@PathVariable String alias, @PathVariable int top) {
        return dhtMessageService.getLatestMeasurementByAlias(alias, top);
    }
    @GetMapping("/latest/id/{id}/{top}")
    public List<DhtMessage> getLatestMeasurementsById(@PathVariable UUID id, @PathVariable int top) {
        return dhtMessageService.getLatestMeasurementById(id, top);
    }

    @GetMapping("/device/{id}")
    public List<DhtMessage> getMeasurementByDeviceId(@PathVariable UUID id) { return dhtMessageService.getMeasurementByDeviceId(id);}

    }
