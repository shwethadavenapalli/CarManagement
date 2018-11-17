package com.carmanagement.controller;

import com.carmanagement.exception.ConstraintsViolationException;
import com.carmanagement.exception.EntityNotFoundException;
import com.carmanagement.model.Car;
import com.carmanagement.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by Shwetha on 14-11-2018.
 */
@RestController
@RequestMapping
public class CarController {

    @Autowired
    CarService carService;

    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public ResponseEntity<Void> createCar(@RequestBody Car car) {

        Car createdCar = carService.addCar(car);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(createdCar.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/cars/{licenseNumber}", method = RequestMethod.GET)
    public ResponseEntity getCar(@PathVariable String licenseNumber) throws EntityNotFoundException {
        Car createdCar = carService.getCar(licenseNumber);
        return ResponseEntity.ok(createdCar);
    }

    @RequestMapping(value = "/cars/{licensePlateNumber}", method = RequestMethod.PUT)
    public ResponseEntity updateCar(@PathVariable String licensePlateNumber, @RequestBody Car updateCar) throws ConstraintsViolationException, EntityNotFoundException {
        updateCar = carService.updateCar(licensePlateNumber, updateCar);
        return ResponseEntity.ok(updateCar);
    }

    @RequestMapping(value = "/cars/{licensePlateNumber}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable String licensePlateNumber) throws ConstraintsViolationException, EntityNotFoundException {
        carService.deleteCar(licensePlateNumber);

    }
}
