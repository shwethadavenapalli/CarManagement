package com.carmanagement.model;

import com.carmanagement.enums.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(
        name = "car",
        uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"license_plate"})
)
@ConditionalOnProperty(value = "feature.car", havingValue = "true")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "car_seq_id")
    // @NotNull(message = "License plate cannot be null!")
    private String carSeqId;


    @Column(nullable = false, name = "license_plate")
    @NotNull(message = "License plate cannot be null!")
    private String licensePlate;

    @Column(nullable = false, name = "seat_count")
    @NotNull(message = "Seat count cannot be null!")
    private Integer seatCount;


    @Column(nullable = false, name = "engine_type")
    @NotNull(message = "Engine type cannot be null!")
    private String engineType;

    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false, name = "date_updated")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated = ZonedDateTime.now();

    @Enumerated(STRING)
    @Column(nullable = false)
    private Status status;

    public Car() {
        System.out.println("Inside car cosntruction");
        status=Status.ACTIVE;
    }

    private Car(CarBuilder carBuilder) {
        this.licensePlate = carBuilder.licensePlate;
        this.seatCount = carBuilder.seatCount;
        this.engineType = carBuilder.engineType.toString();
        this.id = carBuilder.id;
        this.carSeqId = carBuilder.carSeqId;
        if (carBuilder.status == null) {
            carBuilder.status = Status.ACTIVE;
        }
        this.status = carBuilder.status;

    }


    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public String getEngineType() {
        return engineType;
    }


    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public String getCarSeqId() {
        return carSeqId;
    }

    @Override
    public String toString() {
        return "CarDO{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", carSeqID='" + carSeqId + '\'' +
                ", seatCount=" + seatCount +
                ", engineType='" + engineType + '\'' +
                ", dateCreated=" + dateCreated + '\'' +
                ", dateUpdated=" + dateUpdated +
                '}';
    }

    @Component
    public static class CarBuilder {

        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private String engineType;
        private String carSeqId;
        private Status status;

        public Car build() {
            return new Car(
                    this);
        }

        public CarBuilder() {

        }

        public CarBuilder(Car car) {
            this.licensePlate = car.licensePlate;
            this.seatCount = car.seatCount;
            this.engineType = car.engineType;
            this.id = car.id;
            this.carSeqId = car.getCarSeqId();
            this.status = car.status;
        }

        public CarBuilder setStatus(Status status) {
            this.status = status;
            return this;
        }


        public CarBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarBuilder setCarSeqId(String id) {
            this.carSeqId = id;
            return this;
        }


        public CarBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarBuilder setSeatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }


        public CarBuilder setEngineType(String engineType) {
            this.engineType = engineType;
            return this;
        }
    }
}
