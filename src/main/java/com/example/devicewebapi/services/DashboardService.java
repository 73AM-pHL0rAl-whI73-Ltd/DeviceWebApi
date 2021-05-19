package com.example.devicewebapi.services;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@Component
public class DashboardService {

    private final String webserviceURL = "https://devicewebservice.herokuapp.com";

    public void updateDashboard() {

        // sends POST request to webservice update endpoint
        new RestTemplate().postForEntity(
                String.join("/", webserviceURL, "update"),
                null,
                HttpResponse.class
        );
    }
}
