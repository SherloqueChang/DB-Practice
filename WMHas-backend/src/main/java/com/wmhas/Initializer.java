package com.wmhas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
class Initializer {
    @Value(value = "${spring.datasource.driver-class-name}")
    private String driver;
    @Value(value = "${spring.datasource.initial_url}")
    private String initial_url;
    @Value(value = "${spring.datasource.username}")
    private String userName;
    @Value(value = "${spring.datasource.password}")
    private String password;
    @Value(value = "${spring.datasource.useDatabase}")
    private String useDatabase;

    @Bean
    public void init() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(initial_url, userName, password);
        Statement stat = conn.createStatement();
        stat.close();
        conn.close();
        System.out.println("sql execute done");
    }

}
