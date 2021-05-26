package com.example.devicewebapi.services;

import com.example.devicewebapi.models.Device;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class DashboardService {

    private final String webserviceURL = "https://devicewebservice.herokuapp.com";

    public void updateDashboard(Device device) {
        // sends POST request to webservice update endpoint
        new RestTemplate().postForLocation(
                String.join("/", webserviceURL, "update"),
                device);
    }
}
