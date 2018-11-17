package com.carmanagement.dao;

import com.carmanagement.enums.Status;
import com.carmanagement.model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

/**
 * Created by Shwetha on 17-11-2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackages = {"com.carmanagement"})
@ComponentScan
public class CarDaoImplTest {
    @Autowired
    private TestEntityManager entityManager;


    @Autowired
    private CarDaoImpl carDaoImpl;


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

        // to check from JDBC
        Car carFromDBUSingJDBC = carDaoImpl.findByCarPlateId("LP");
        assertEquals(persist.getId(), carFromDBUSingJDBC.getId());

    }

}