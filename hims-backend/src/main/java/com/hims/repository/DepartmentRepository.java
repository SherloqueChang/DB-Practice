package com.hims.repository;

import com.hims.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class DepartmentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Department getDepartment_by_id(String doctorId)
    {
        String sql = "select name, leader_id as id, dept_description from department where leader_id=?";
        Department result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class), doctorId);
        return result;
    }

    public void modifyDepartmentDesc(String doctorId, String desc)
    {
        String sql = "update department set dept_description = ? where leader_id=?";
        jdbcTemplate.update(sql, desc, doctorId);
    }
}
