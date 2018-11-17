package com.carmanagement.dao;

import com.carmanagement.model.Car;
import com.carmanagement.rowmapper.CarRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shwetha on 17-11-2018.
 */
@Repository
public class CarDaoImpl {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Car findByCarPlateId(String licensePlateNumber) {

        Car car = null;

        final String query = "select * from car where license_plate=?";
        try {

            car = jdbcTemplate.query(query, new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, licensePlateNumber);
                }
            }, new ResultSetExtractor<Car>() {
                public Car extractData(ResultSet rs) throws SQLException, DataAccessException {
                    Car car = null;
                    while (rs.next()) {
                        car = new CarRowMapper().mapToRow(rs);
                    }
                    return car;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return car;
        }

    }
}
