package com.carmanagement.repository;

import com.carmanagement.enums.Status;
import com.carmanagement.model.DriverCar;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Shwetha on 16-11-2018.
 */
public interface DriverCarRepository extends CrudRepository<DriverCar, Long> {


    DriverCar findByDriverIdOrCarIdAndStatus(Long driverID, Long carId, Status status);

    DriverCar findByDriverIdAndCarIdAndStatus(Long driverByLicenseNumberId, Long carByLicensePlateNumberId, Status status);

    @Modifying
    @Query("update DriverCar DC set DC.status = ?1 where DC.id = ?2")
    int updateStatus(Status status, Long id);


    DriverCar findByDriverIdAndStatusOrCarIdAndStatus(Long driverByLicenseNumberId, Status status, Long carByLicensePlateNumberId, Status status1);

}
