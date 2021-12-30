package com.hims.repository;

import com.hims.domain.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class DoctorRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Doctor find(String id) {
        String sql = "select * from doctor where id=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Doctor.class), id);
    }
    
    public List<Doctor> findDoctorByDepartmentName(String departmentname) {
        String sql = "select * from doctor where department=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Doctor.class), departmentname);
    }


}
