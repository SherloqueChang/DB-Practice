package com.hims.repository;

import com.hims.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Appointment> findAppointmentByPatientId(String patientId) {
        String sql = "select * from appointed_info where patient_id = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Appointment.class), patientId);
        } catch (Exception e) {
            return null;
        }
    }

}