package com.carmanagement.model;

import com.carmanagement.enums.Status;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "driver_car")
public class DriverCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "car_id")
    private Long carId;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Status status;

    private DriverCar() {
    }

    public DriverCar(Long driverId, Long carId) {
        this.driverId = driverId;
        this.carId = carId;
        this.status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }


    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    @Override
    public String toString() {
        return "DriverCar{" +
                "id=" + id +
                ", driverId=" + driverId +
                ", carId=" + carId +
                ", status=" + status +
                '}';
    }
}