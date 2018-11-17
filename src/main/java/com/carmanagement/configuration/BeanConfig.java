package com.carmanagement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
//@EnableAutoConfiguration
public class BeanConfig {


    @Value("${spring.datasource.url}")
    private String dbURL;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driverClassName}")
    private String dbDriverClass;


    //@Bean()
    public DataSource dataSource() {
        System.out.println("BeanConfig.dataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbURL);
        dataSource.setPassword(dbPassword);
        dataSource.setUsername(dbUsername);
        dataSource.setDriverClassName(dbDriverClass);

        return dataSource;
    }

/*
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql").build();
    }*/

    //@Bean()
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }


}
