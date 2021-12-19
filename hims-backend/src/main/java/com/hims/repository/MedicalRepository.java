package com.hims.repository;

import com.hims.domain.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Repository
public class MedicalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Prescription> findPrescriptionByPatientId(String patientId) {
        String sql = "select * from prescription where patient_id = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Prescription.class), patientId);
        } catch (Exception e) {
            return null;
        }
    }
}
