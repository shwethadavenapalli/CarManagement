package com.carmanagement.repository;

import com.carmanagement.enums.Status;
import com.carmanagement.model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Shwetha on 17-11-2018.
 */


@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackages = {"com.carmanagement"})
//@ContextConfiguration(classes = {CarRepository.class})
@ComponentScan
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;


    @Test
    public void shouldFetchGivenCarWhenItIsInDB() {
        Car car = new Car.CarBuilder()
                .setEngineType("DIESEL")
                .setSeatCount(2)
                .setLicensePlate("LP")
                .setStatus(Status.ACTIVE)
                .build();
        entityManager.clear();
        Car persist = entityManager.persist(car);
        Car carFromDBUsingJPA = carRepository.findByLicensePlate("LP");
        assertEquals(persist.getId(), carFromDBUsingJPA.getId());

    }


}