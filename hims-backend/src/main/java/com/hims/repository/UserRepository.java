package com.hims.repository;

import com.hims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    Date parse_date(String birthdate)
    {
        if(birthdate == null) return null;
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
        try {
            return fmt.parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(User user) {
        String sql = "insert into user(id,pwd,name,birthdate,idcard,gender,phone,email,u_type) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getPwd(), user.getName(), parse_date(user.getBirthdate()), user.getIdcard(), user.getGender(), user.getPhone(), user.getEmail(), user.getU_type());
    }

    public void update(User user) {
        String sql = "update user set pwd = ?, name = ?, birthdate = ?, idcard = ?, gender = ?, phone = ?, email = ? where id = ?";
        jdbcTemplate.update(sql, user.getPwd(), user.getName(), parse_date(user.getBirthdate()), user.getIdcard(), user.getGender(), user.getPhone(), user.getEmail(), user.getId());
    }


    public void delete(String id) {
        String sql = "delete from user where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void update(String id, String name, String password, String age, String email, String phone) {
        String sql = "update user set name=?,pwd=?,age=?,email=?,phone=? where id=?";
        jdbcTemplate.update(sql, name, password, age, email, phone, id);
    }

    public User find(String id) {
        String sql = "select * from user where id=?";
        try {
            User result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
            DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
            if(result.birthdate == null) return result;
            result.birthdate = fmt.format(fmt.parse(result.birthdate));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> findAllPatient() {
        String sql = "select * from user where u_type='patient'";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> findAllDoctor() {
        String sql = "select * from user where u_type='doctor'";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> findByType(String type) {
        String sql = "select * from user where u_type=?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), type);
        } catch (Exception e) {
            return null;
        }
    }
    public List<User> findByName(String name) {
        String sql = "select * from user where name=?";
        //System.out.println("\nname:"+name);
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), name);
        } catch (Exception e) {
            return null;
        }
    }

    public void setDeptLeader(String id, String dept) {
        String sql = "update user set u_type = 'leader' where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void setDoctor(String id) {
        String sql = "update user set u_type = 'doctor' where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
