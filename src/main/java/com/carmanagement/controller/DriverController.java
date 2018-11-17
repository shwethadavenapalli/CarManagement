package com.carmanagement.controller;

import com.carmanagement.exception.EntityNotFoundException;
import com.carmanagement.model.Driver;
import com.carmanagement.service.DriverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by Shwetha on 15-11-2018.
 */

@RestController
@RequestMapping
public class DriverController {


    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/drivers", method = RequestMethod.POST)
    public ResponseEntity<Void> createDriver(@RequestBody Driver driver) throws JsonProcessingException {
        //driver.setOnlineStatus(OnlineStatus.OFFLINE);
        Driver createdDriver = driverService.addDriver(driver);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(createdDriver.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/drivers/{licenseNumber}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriver(@PathVariable String licenseNumber) throws EntityNotFoundException {
        Driver existingDriver = driverService.getDriver(licenseNumber);
        if (existingDriver == null) throw new EntityNotFoundException("No Driver found for given license number");
        return ResponseEntity.ok(existingDriver);
    }

}
