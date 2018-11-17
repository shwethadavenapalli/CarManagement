package com.carmanagement.service;

import com.carmanagement.enums.Status;
import com.carmanagement.exception.EntityNotFoundException;
import com.carmanagement.exception.InvalidStateException;
import com.carmanagement.model.Car;
import com.carmanagement.model.Driver;
import com.carmanagement.model.DriverCar;
import com.carmanagement.repository.CarRepository;
import com.carmanagement.repository.DriverCarRepository;
import com.carmanagement.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Shwetha on 16-11-2018.
 */
@Service
public class DriverCarService {

    private DriverCarRepository driverCarRepository;
    private DriverRepository driverRepository;
    private CarRepository carRepository;

    @Autowired
    DriverCarService(DriverCarRepository driverCarRepository,
                     DriverRepository driverRepository, CarRepository carRepository) {
        this.driverCarRepository = driverCarRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public DriverCar associateCarToDriver(String carNumber, String driverLicenceNumber) throws EntityNotFoundException, InvalidStateException {

       driverCarRepository.findAll().forEach(System.out::println);
        Driver driverByLicenseNumber = driverRepository.findByLicenseNumber(driverLicenceNumber);
        if (driverByLicenseNumber == null)
            throw new EntityNotFoundException("No Driver found with given license number");
        if (driverByLicenseNumber.getDriverStatus() != Status.ACTIVE) {
            throw new InvalidStateException("Driver is not active");
        }
        Car carByLicensePlateNumber = carRepository.findByLicensePlate(carNumber);
        if (carByLicensePlateNumber == null)
            throw new EntityNotFoundException("No Car found with given license Plate number");
        if (carByLicensePlateNumber.getStatus() != Status.ACTIVE) {
            throw new InvalidStateException("Car is not active");
        }

        DriverCar byDriverIdOrCarId = driverCarRepository.findByDriverIdAndStatusOrCarIdAndStatus(
                driverByLicenseNumber.getId(),Status.ACTIVE, carByLicensePlateNumber.getId(),Status.ACTIVE);
        if (byDriverIdOrCarId != null) {
            if (byDriverIdOrCarId.getDriverId() == driverByLicenseNumber.getId()
                    && byDriverIdOrCarId.getCarId() == carByLicensePlateNumber.getId())
                throw new InvalidStateException("Given Driver is already assigned with Given car");

            if (byDriverIdOrCarId.getDriverId() != driverByLicenseNumber.getId()
                    && byDriverIdOrCarId.getCarId() == carByLicensePlateNumber.getId())
                throw new InvalidStateException("Given Car is already assigned to some other driver");

            if (byDriverIdOrCarId.getDriverId() == driverByLicenseNumber.getId()
                    && byDriverIdOrCarId.getCarId() != carByLicensePlateNumber.getId())
                throw new InvalidStateException("Given Driver is already assigned to some other car");

        }
        DriverCar driverCar = new DriverCar(driverByLicenseNumber.getId(), carByLicensePlateNumber.getId());
        driverCar = driverCarRepository.save(driverCar);
        return driverCar;
    }


    @Transactional
    public void disAssociateCarToDriver(String carNumber, String driverLicenceNumber)
            throws EntityNotFoundException, InvalidStateException {

        driverCarRepository.findAll().forEach(System.out::println);

        Car carByLicensePlateNumber = carRepository.findByLicensePlate(carNumber);
        if (carByLicensePlateNumber == null)
            throw new EntityNotFoundException("No Car found with given license Plate number");
        if (carByLicensePlateNumber.getStatus() != Status.ACTIVE) {
            throw new InvalidStateException("Car is not active to continue request");
        }

        Driver driverByLicenseNumber = driverRepository.findByLicenseNumber(driverLicenceNumber);
        if (driverByLicenseNumber == null)
            throw new EntityNotFoundException("No Driver found with given license number");
        if (driverByLicenseNumber.getDriverStatus() != Status.ACTIVE) {
            throw new InvalidStateException("Driver is not active to continue request");
        }


        DriverCar byDriverIdAndCarId = driverCarRepository.findByDriverIdAndCarIdAndStatus(driverByLicenseNumber.getId(), carByLicensePlateNumber.getId(), Status.ACTIVE);
        if (byDriverIdAndCarId == null) {
            throw new InvalidStateException("Given Driver is not assigned with Given car to disassociate");
        }
        driverCarRepository.updateStatus(Status.RELEASED, byDriverIdAndCarId.getId());

    }

}
