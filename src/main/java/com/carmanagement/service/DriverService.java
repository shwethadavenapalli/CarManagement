package com.carmanagement.service;

import com.carmanagement.model.Driver;
import com.carmanagement.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Shwetha on 15-11-2018.
 */
@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    public Driver addDriver(Driver driver) {
        Driver save = driverRepository.save(driver);
        return save;
    }

    public Driver getDriver(String licenseNumber) {
        return driverRepository.findByLicenseNumber(licenseNumber);

    }
}
