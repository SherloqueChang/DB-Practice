package com.hims.repository;

import com.hims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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

    public int saveWNurse(String name, String age, String email, String phone) {
        String sql = "insert into user(name,password,age,email,phone,u_type) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, "123456");
            ps.setString(3, age);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, "w_nurse");
            return ps;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void delete(String id) {
        String sql = "delete from user where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void update(String id, String name, String password, String age, String email, String phone) {
        String sql = "update user set name=?,password=?,age=?,email=?,phone=? where id=?";
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

    public List<User> findAll() {
        String sql = "select * from user";
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

}
