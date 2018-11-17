package com.carmanagement.controller;

import com.carmanagement.exception.EntityNotFoundException;
import com.carmanagement.exception.InvalidStateException;
import com.carmanagement.model.Driver;
import com.carmanagement.model.DriverCar;
import com.carmanagement.service.DriverCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by Shwetha on 16-11-2018.
 */

@RestController
public class DriverCarController {

    @Autowired
    DriverCarService driverCarService;


    @RequestMapping(value = "/associateCarToDriver/{carNumber}/{driverLicenseNumber}", method = RequestMethod.POST)
    public ResponseEntity<DriverCar> associateCarToDriver(@PathVariable(name = "carNumber") String carNumber, @PathVariable(name = "driverLicenseNumber") String driverLicenceNumber) throws InvalidStateException, EntityNotFoundException {
        System.out.println("DriverCarController.assignCarToDriver");
        System.out.println("carNumber = [" + carNumber + "], driverLicenceNumber = [" + driverLicenceNumber + "]");
        DriverCar driverCar = driverCarService.associateCarToDriver(carNumber, driverLicenceNumber);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(driverCar.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/disAssociateCarToDriver/{carNumber}/{driverLicenseNumber}", method = RequestMethod.PUT)
    public ResponseEntity<DriverCar> disAssociateCarToDriver(@PathVariable(name = "carNumber") String carNumber, @PathVariable(name = "driverLicenseNumber") String driverLicenceNumber) throws InvalidStateException, EntityNotFoundException {
        System.out.println("DriverCarController.disAssociateCarToDriver");
        System.out.println("carNumber = [" + carNumber + "], driverLicenceNumber = [" + driverLicenceNumber + "]");
        driverCarService.disAssociateCarToDriver(carNumber, driverLicenceNumber);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/filter")
    public List<Driver> findDriversByCarAttributes(@RequestParam Map<String, String> params) {
       /* CarDO query  = DriverMapper.mapRequestParamsToCarDO(params);
        List<DriverDO> driversByCarAttributes = filterDriverService.findDriversByCarAttributes(query);
        return DriverMappe
        r.makeDriverDTOList(driversByCarAttributes);*/
        return null;
    }

}


