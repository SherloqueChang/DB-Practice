package com.hims.repository;

import com.hims.domain.Doctor;
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

}
