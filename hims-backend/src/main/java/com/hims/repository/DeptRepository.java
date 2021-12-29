package com.hims.repository;

import com.hims.domain.Department;
import com.hims.domain.PatientHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeptRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Department> findAll() {
        String sql = "with doctor_num(num) as (select count(id) from doctor group by department) select * from department, doctor_num where department.name=doctor_num.num";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
        } catch (Exception e) {
            return null;
        }
    }
}
