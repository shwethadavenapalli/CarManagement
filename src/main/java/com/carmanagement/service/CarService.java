package com.carmanagement.service;

import com.carmanagement.dao.CarDaoImpl;
import com.carmanagement.exception.ConstraintsViolationException;
import com.carmanagement.exception.EntityNotFoundException;
import com.carmanagement.model.Car;
import com.carmanagement.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

/**
 * Created by Shwetha on 14-11-2018.
 */
@Service
public class CarService {


    private CarRepository carRepository;
    private CarDaoImpl carDao;

    @Autowired
    public CarService(CarRepository carRepository, CarDaoImpl carDao) {
        this.carRepository = carRepository;
        this.carDao = carDao;
    }

    @Transactional
    public Car addCar(Car car) {
        System.out.println("CarService.addCar");
        Car persistedCar = carRepository.save(car);
        return persistedCar;
    }

    public Car getCar(String licenseNumber) throws EntityNotFoundException {
        Car carByLicensePlate = carDao.findByCarPlateId(licenseNumber);
        //Car carByLicensePlate = carRepository.findByLicensePlate(licenseNumber);
        if (carByLicensePlate == null)
            throw new EntityNotFoundException("No Car entity found for given licence plate number");
        return carByLicensePlate;
    }

    @Transactional
    public Car updateCar(String licensePlateNumber, Car updatedCar) throws ConstraintsViolationException, EntityNotFoundException {

        try {
            System.out.println("CarService.updateCar");

            Car existingCar = getCar(licensePlateNumber);
            System.out.println("existing car features: " + existingCar.toString());
            System.out.println("updated car features: " + updatedCar.toString());
            updatedCar = carRepository.save(updatedCar);
            return updatedCar;
        } catch (Exception exception) {
            Throwable cause = getCause(exception.getCause());
            if (cause instanceof ConstraintViolationException)
                throw new ConstraintsViolationException(exception.getMessage());
            throw exception;
        }
    }


    @Transactional
    public void deleteCar(String licensePlateNumber) throws EntityNotFoundException {
        carRepository.deleteByLicensePlate(licensePlateNumber);

    }


    Throwable getCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
