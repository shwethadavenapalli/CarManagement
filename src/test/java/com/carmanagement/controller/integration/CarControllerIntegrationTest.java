package com.carmanagement.controller.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Shwetha on 17-11-2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    public String getCarJson(String licensePlate) {
        return "{\n" +
                "    \"id\": 1,\n" +
                "    \"licensePlate\":\"" + licensePlate + "\",\n" +
                "    \"seatCount\": 2,\n" +
                "    \"engineType\": \"DIESEL\"\n" +
                "}";

    }

    @Test
    public void testGivenValidCarObjectIsSavedSuccessfully() throws Exception {
        System.out.println("CarControllerIntegrationTest.testGivenValidCarObjectIsSavedSuccessfully");

        String carJson = getCarJson("MH04");
        validateCarSave(carJson);
    }

    public void validateCarSave(String carJson) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/cars")
                .accept(MediaType.APPLICATION_JSON).content(carJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        System.out.println("Response @ Get " + response.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertTrue(
                response.getHeader(HttpHeaders.LOCATION).endsWith("/cars/1"));
    }

    @Test
    public void testGetCarObjectWhenSpecificRequestedDataIsNotInDataBase() throws Exception {
        String carPlateNumber = "NonExistent";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cars/" + carPlateNumber)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        assertThat(response.getContentAsString()).isEmpty();

    }

    @Test
    public void testGetCarObjectWhenSpecificRequestedDataIsInDataBase() throws Exception {

        //Save In DB Fi

        String carJson = getCarJson("MH05");
        validateCarSave(carJson);
        System.out.println("CarControllerIntegrationTest.testGetCarObjectWhenSpecificRequestedDataIsInDataBase");

        String carPlateNumber = "MH05";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cars/" + carPlateNumber)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertThat(response.getContentAsString()).isNotEmpty();
        System.out.println("Response @ Get " + result.getResponse().getContentAsString());

        JSONAssert.assertEquals(carJson, result.getResponse()
                .getContentAsString(), false);
    }

}
