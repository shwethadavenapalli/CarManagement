package com.carmanagement.repository;

import com.carmanagement.model.Driver;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Shwetha on 15-11-2018.
 */
public interface DriverRepository extends CrudRepository<Driver, Long> {
    Driver findByLicenseNumber(String licenseNumber);
}
