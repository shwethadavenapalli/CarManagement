package com.carmanagement.model;

import com.carmanagement.enums.OnlineStatus;
import com.carmanagement.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "driver",
        uniqueConstraints = @UniqueConstraint(name = "license_Number", columnNames = {"license_Number"})
)
public class Driver implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,name = "name")
    @NotNull(message = "Name can not be null!")
    private String name;


    @Column(nullable = false,name = "address")
    @NotNull(message = "address can not be null!")
    private String address;

    @Column(nullable = false,name = "phone_number")
    @NotNull(message = "phonenumber can not be null!")
    private String phoneNumber;

    @Column(nullable = false ,name = "license_Number")
    @NotNull(message = "licenseNumber can not be null!")
    private String licenseNumber;


    @Column(nullable = false,name = "CREATED_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();


    @Column(nullable = false,name = "deleted")
    private Boolean deleted = false;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "ONLINE_STATUS")
    private OnlineStatus onlineStatus;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "STATUS")
    private Status driverStatus;


    private Driver() {
        driverStatus = Status.ACTIVE;
        onlineStatus = OnlineStatus.OFFLINE;
    }


    public Driver(String name, String address, String phoneNumber, String licenseNumber) {
        this.name = name;
        this.address = address;
        this.deleted = false;
        this.phoneNumber = phoneNumber;
        this.onlineStatus = OnlineStatus.OFFLINE;
        this.licenseNumber = licenseNumber;
        this.driverStatus = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Status getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(Status driverStatus) {
        this.driverStatus = driverStatus;
    }
}
