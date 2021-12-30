package com.hims.repository;

import com.hims.domain.Department;
import com.hims.domain.DepartmentCnt;
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
        String sql = "select name, leader_id as id from department";
        List<Department> departments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
        sql = "select department as name, count(id) as doctor_num from doctor group by department";
        List<DepartmentCnt> deptCnts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DepartmentCnt.class));
        for(Department dept : departments) {
            for(DepartmentCnt deptCnt : deptCnts) {
                if(dept.getName().equals(deptCnt.getName())) {
                    dept.setDoctor_num(deptCnt.getDoctor_num());
                }
            }
        }
        return departments;
    }

    public void delete(String name) {
        String sql = "delete from department where name = ?";
        jdbcTemplate.update(sql);
    }
}
