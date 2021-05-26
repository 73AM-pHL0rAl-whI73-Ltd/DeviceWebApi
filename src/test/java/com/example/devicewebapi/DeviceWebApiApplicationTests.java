package com.example.devicewebapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceWebApiApplicationTests {
    @Autowired
    MockMvc mvc;

    @Test
    public void test_GetAllMeasurements()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/measurements")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_GetLatest1()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/measurements/latest/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void test_GetLatest10()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/measurements/latest/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(10)));
    }

    @Test
    public void test_GetByDeviceId()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/measurements/device/testdevice0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void test_ValidJsonResponse()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/measurements")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deviceId").isString())
                .andExpect(jsonPath("$[0].timeStamp").isNumber())
                .andExpect(jsonPath("$[0].temperature").isNumber())
                .andExpect(jsonPath("$[0].humidity").isNumber());
    }

    @Test
    public void test_ValidJsonResponseForDevice()
            throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/measurements/device/testdevice0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deviceId").value("testdevice0"))
                .andExpect(jsonPath("$[0].timeStamp").isNumber())
                .andExpect(jsonPath("$[0].temperature").isNumber())
                .andExpect(jsonPath("$[0].humidity").isNumber());
    }
}
