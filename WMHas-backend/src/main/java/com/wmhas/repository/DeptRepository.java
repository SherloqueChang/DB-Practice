package com.wmhas.repository;

import com.wmhas.domain.Department;
import com.wmhas.domain.DepartmentCnt;
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
        jdbcTemplate.update(sql, name);
    }

    public void setDeptLeader(String id, String dept) {
        String sql = "update department set leader_id = ? where name = ?";
        jdbcTemplate.update(sql, id, dept);
    }
}
