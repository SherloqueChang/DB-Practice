package com.wmhas.repository;

import com.wmhas.domain.Prescription;
import com.wmhas.domain.PatientHistory;
import com.wmhas.domain.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MedicalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Prescription> findPrescriptionByPatientId(String patientId) {
        String sql = "select * from prescription where patient_id = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Prescription.class), patientId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<PatientHistory> findPatientHistoryByPatientId(String patientId) {
        String sql = "select * from patient_history where patient_id = ? order by treat_date desc";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PatientHistory.class), patientId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<PatientHistory> findPatientHistoryByDoctorId(String doctorId) {
        String sql = "select * from patient_history where doctor_id = ? order by treat_date desc";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PatientHistory.class), doctorId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Medicine> getAllMedicine()
    {
        String sql = "select * from medicine";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Medicine.class));
    }

    public void finishAppointment(String doctorId, String patientId)
    {
        String sql = "update appointed_info set appointment_status = 'Done' where doctor_id = ? and patient_id = ?";
        jdbcTemplate.update(sql, doctorId, patientId);
    }
}
