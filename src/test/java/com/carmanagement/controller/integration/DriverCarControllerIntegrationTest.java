package com.carmanagement.controller.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Shwetha on 17-11-2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.yml")
public class DriverCarControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testAssociateGivenCarToGivenDriverWhenGivenCarAndDriverAreInDB() throws Exception {


        String carNumber = "CARPLATE1";
        String driverLicenseNumber = "LICENSE1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/associateCarToDriver/" + carNumber + "/" + driverLicenseNumber);
        //.accept(MediaType.APPLICATION_JSON)
        //.contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertTrue(
                response.getContentAsString().isEmpty());
    }

    @Test
    public void testDisAssociateGivenCarToGivenDriverWhenGivenCarAndDriverAreInDB() throws Exception {

    }
}
