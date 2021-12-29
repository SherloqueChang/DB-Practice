package com.hims.repository;

import com.hims.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<Appointment> findTodayAppointmentByDoctorID(String doctorId){
        String sql = "select * from appointed_info where doctor_id = ? and appointment_date = ?";
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd"); 
        Date date = new Date();// 获取当前时间 
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Appointment.class), doctorId, sdf.format(date));
    }

    public List<Appointment> findAppointmentByDoctorIDAndDate(String doctorId, String date) {
        String sql = "select * from appointed_info where doctor_id = ? and appointment_date = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Appointment.class), doctorId, date);
    }

}