package com.wmhas.repository;

import com.wmhas.domain.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Medicine find_by_name(String name){
        String sql = "select * from medicine where name=?";
        Medicine result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Medicine.class), name);
        return result;
    }

    public String addprescription(String doctorId, String patientId, List<String> medicines, List<Integer> nums)
    {
        String sql1 = "update medicine set num=? where name=?";
        String sql2 = "INSERT INTO prescription(doctor_id, patient_id, pres_date, medicine_name, medicine_num) VALUES (?,?,?,?,?)";
        int len = medicines.size();
        String error = "";
        for(int i=0;i<len;i++)
        {
            Medicine m = find_by_name(medicines.get(i));
            if(Integer.parseInt(m.getNum()) < nums.get(i))
                error+="药品"+m.getName()+"数量不足，请检查!\n";
        }
        if(error.equals(""))
        {
            for(int i=0;i<len;i++)
            {
                Medicine m = find_by_name(medicines.get(i));
                int left = Integer.parseInt(m.getNum()) - nums.get(i);
                jdbcTemplate.update(sql1, left+"", m.getName());
                SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
                sdf.applyPattern("yyyy-MM-dd"); 
                Date date = new Date();// 获取当前时间 
                jdbcTemplate.update(sql2, doctorId, patientId, sdf.format(date), m.getName(), nums.get(i));
            }
            return null;
        }
        return error;
    }
}
