package com.carmanagement.controller.integration;

import com.carmanagement.model.Driver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
public class DriverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    JacksonTester<Driver> driverJacksonTester;


    @Test
    public void testGivenValidDriverObjectIsSavedSuccessfully() throws Exception {
        System.out.println("CarControllerIntegrationTest.testGivenValidCarObjectIsSavedSuccessfully");

        String driverJson = getJson("DL1");
        validateDriverObjSave(driverJson);

    }

    public void validateDriverObjSave(String driverJson) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/drivers")
                .accept(MediaType.APPLICATION_JSON).content(driverJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        // assertTrue(response.getHeader(HttpHeaders.LOCATION).endsWith("/drivers/1"));
    }

    @Test
    public void testGetDriverObjectWhenSpecificRequestedDataIsNotInDataBase() throws Exception {
        String driverLicenceNumber = "NonExistent";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/drivers/" + driverLicenceNumber)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        assertThat(response.getContentAsString()).isEmpty();

    }

    @Test
    public void testGetDriverObjectWhenSpecificRequestedDataIsInDataBase() throws Exception {

        //Save In DB Fi
        String driverJson = getJson("DL2");
        validateDriverObjSave(driverJson);


        String driverLicenceNumber = "DL2";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/drivers/" + driverLicenceNumber)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertThat(response.getContentAsString()).isNotEmpty();


     /*   JacksonTester.initFields(this, new ObjectMapper());
        assertThat(response.getContentAsString()).isEqualTo(
                driverJacksonTester.write(driver).getJson()
        );*/

        JSONAssert.assertEquals(driverJson, result.getResponse()
                .getContentAsString(), false);
    }

    public String getJson(String driverLicenseNumber) {


        return "{\n" +
                "\t\"name\":\"Shwetha\",\n" +
                "\t\"address\":\"Mumbai\",\n" +
                "\t\"phoneNumber\":\"8884441317\",\n" +
                "\t\"licenseNumber\":\"" + driverLicenseNumber + "\"\n" +
                "}";

    }
}
