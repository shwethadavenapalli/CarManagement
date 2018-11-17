package com.carmanagement.rowmapper;

import com.carmanagement.enums.Status;
import com.carmanagement.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shwetha on 17-11-2018.
 */
public class CarRowMapper {

    public Car mapToRow(ResultSet rs) throws SQLException {
        return new Car.CarBuilder()
                .setCarSeqId(rs.getString("car_seq_id"))
                .setId(rs.getLong("id"))
                .setEngineType(rs.getString("engine_type"))
                .setLicensePlate(rs.getString("license_plate"))
                .setSeatCount(rs.getInt("seat_count"))
                .setStatus(Status.getValue(rs.getString("status")))
                .build();
    }
}
