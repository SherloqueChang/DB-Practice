package com.hims.repository;

import com.hims.domain.Medicine;
import com.hims.domain.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicineRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Medicine> findAll() {
        String sql = "select * from medicine";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Medicine.class));
        } catch (Exception e) {
            return null;
        }
    }

    public void setNum(String name, String num) {
        String sql = "update medicine set num=? where name=?";
        jdbcTemplate.update(sql, num, name);
    }
}
