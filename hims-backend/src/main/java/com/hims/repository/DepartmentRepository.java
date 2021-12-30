package com.hims.repository;

import com.hims.domain.Department;
import com.hims.domain.Doctor;
import com.hims.domain.Prescription;
import com.hims.domain.PatientHistory;
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
    @Autowired
    private UserRepository userRepository;

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

    public List<Doctor> getDepartmentDoctor(String doctorId)
    {
        Department d = getDepartment_by_id(doctorId);
        String sql = "select * from doctor where department = ?";
        List<Doctor> doctors = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Doctor.class), d.getName());
        for(Doctor doc : doctors)
            doc.setName(userRepository.find(doc.getId()).getName());
        //System.out.println(doctors);
        return doctors;
    }

    public List<Prescription> getDoctorPrescription(String doctorId)
    {
        String sql = "select * from prescription where doctor_id = ?";
        List<Prescription> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Prescription.class), doctorId);
        return result;
    }

    public List<PatientHistory> getPatientHistory(String doctorId)
    {
        String sql = "select * from patient_history where doctor_id = ?";
        List<PatientHistory> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PatientHistory.class), doctorId);
        return result;
    }

    public void editPrescriptionItem(String doctorId, String patientId, String date, String oname, String onum, String name, String num)
    {
        String sql = "update prescription set medicine_name=?,medicine_num=? where doctor_id=? and patient_id=? and pres_date=? and medicine_name=? and medicine_num=?";
        jdbcTemplate.update(sql, name, num, doctorId, patientId, date, oname, onum);
    }

    public void editMedicalItem(String doctorId, String patientId, String date, String issue, String diagnosed_disease, String allergens)
    {
        String sql = "update patient_history set treat_issue=?, diagnosed_disease=?, allergens=? where patient_id=? and doctor_id=? and treat_date=?";
        jdbcTemplate.update(sql, issue, diagnosed_disease, allergens, patientId, doctorId, date);
    }

    public void deletePrescriptionItem(String doctorId, String patientId, String date, String name, String num)
    {
        String sql = "delete from prescription where doctor_id=? and patient_id=? and pres_date=? and medicine_name=? and medicine_num=?";
        jdbcTemplate.update(sql, doctorId, patientId, date, name, num);
    }

    public void deleteMedicalItem(String doctorId, String patientId, String date)
    {
        String sql = "delete from patient_history where patient_id=? and doctor_id=? and treat_date=?";
        jdbcTemplate.update(sql, patientId, doctorId, date);
    }
}
