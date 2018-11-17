package com.carmanagement.controller;

import com.carmanagement.enums.EngineType;
import com.carmanagement.model.Car;
import com.carmanagement.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Shwetha on 14-11-2018.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = CarController.class, secure = false)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private JacksonTester<Car> carJacksonTester;

    @Test
    public void testCreateCarWhenProperCarObjectProvided() throws Exception {

        Car mockCar = new Car.CarBuilder().
                setEngineType(EngineType.DIESEL.toString())
                .setLicensePlate("MH 04")
                .setId((long) 1)
                .setSeatCount(10).build();
        String carJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"licensePlate\": \"MH 04\",\n" +
                "    \"seatCount\": 2,\n" +
                "    \"engineType\": \"DIESEL\"\n" +
                "}";
        Mockito.when(
                carService.addCar(
                        Mockito.any(Car.class))).thenReturn(mockCar);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/cars")
                .accept(MediaType.APPLICATION_JSON).content(carJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertTrue(
                response.getHeader(HttpHeaders.LOCATION).endsWith("/cars/1"));

    }

    @Test
    public void getCarWithValidLicencePlateNumber() throws Exception {


        Car mockCar = new Car.CarBuilder().
                setEngineType(EngineType.DIESEL.toString())
                .setLicensePlate("MH 04")
                .setSeatCount(2).build();

        String carJson = "\n" +
                "{\n" +
                "    \"id\": null,\n" +
                "    \"licensePlate\": \"MH 04\",\n" +
                "    \"seatCount\": 2,\n" +
                "    \"engineType\": \"DIESEL\"\n" +
                "}";
        Mockito.when(
                carService.getCar(
                        Mockito.anyString())).thenReturn(mockCar);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cars/MH 04")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        System.out.println(response.getContentAsString());
        System.out.println("from jack " + carJacksonTester.write(mockCar).getJson());

        JSONAssert.assertEquals(carJson, result.getResponse()
                .getContentAsString(), false);

    }

    @Test
    public void testUpdateCarWhenProperCarObjectProvided() throws Exception {

        Car mockCar = new Car.CarBuilder().
                setEngineType(EngineType.PETROL.toString())
                .setLicensePlate("MH 04")
                .setId((long) 1)
                .setSeatCount(10).build();
        String carJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"licensePlate\": \"MH 04\",\n" +
                "    \"seatCount\": 10,\n" +
                "    \"engineType\": \"PETROL\"\n" +
                "}";
        Mockito.when(
                carService.updateCar(Mockito.anyString(),
                        Mockito.any(Car.class))).thenReturn(mockCar);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/cars/{licencePlateNumber]")
                .accept(MediaType.APPLICATION_JSON).content(carJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(carJson, result.getResponse()
                .getContentAsString(), false);
    }


    @Test
    public void testDeleteCarWhenProperCarLicencePlateNumberProvided() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/cars/{licencePlateNumber]");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

}
