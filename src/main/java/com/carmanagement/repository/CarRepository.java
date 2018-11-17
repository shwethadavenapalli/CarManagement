package com.carmanagement.repository;

import com.carmanagement.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shwetha on 14-11-2018.
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    Car findByLicensePlate(String licenseNumber);
    void deleteByLicensePlate(String licenseNumber);

    @Query(value = "SELECT CAR_REF_SEQ.nextval FROM dual", nativeQuery =
            true)
    Long getNextCarRefSeq();
}
