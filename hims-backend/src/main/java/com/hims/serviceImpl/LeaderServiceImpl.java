package com.hims.serviceImpl;

import com.hims.repository.*;
import com.hims.domain.PatientHistory;
import com.hims.domain.Doctor;
import com.hims.domain.Department;
import com.hims.domain.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaderServiceImpl {

    @Autowired
    private DepartmentRepository departmentRepository;
    
    public Map<String, Object> getDepartmentDesc(String doctorId) 
    {
        Map<String, Object> map = new HashMap<>();
        Department d = departmentRepository.getDepartment_by_id(doctorId);
        map.put("leader", d);        
        return map;
    }

    public Map<String, Object> modifyDepartmentDesc(String doctorId, String desc)
    {
        Map<String, Object> map = new HashMap<>();
        departmentRepository.modifyDepartmentDesc(doctorId, desc);
        return map;
    } 

    public Map<String, Object> getDepartmentDoctor(String doctorId)
    {
        Map<String, Object> map = new HashMap<>();
        List<Doctor> result = departmentRepository.getDepartmentDoctor(doctorId);
        map.put("leader", result);
        return map;
    } 

    public Map<String, Object> getdoctorPrescription(String doctorId)
    {
        List<Prescription> result = departmentRepository.getDoctorPrescription(doctorId);
        Map<String, Object> map = new HashMap<>();
        map.put("leader", result);
        return map;
    }

    public Map<String, Object> getdoctorPatientHistory(String doctorId)
    {
        List<PatientHistory> result = departmentRepository.getPatientHistory(doctorId);
        Map<String, Object> map = new HashMap<>();
        map.put("leader", result);
        return map;
    }

    public Map<String , Object> editPrescriptionItem(String doctorId, String patientId, String date, String oname, String onum, String name, String num)
    {
        Map<String, Object> map = new HashMap<>();
        departmentRepository.editPrescriptionItem(doctorId, patientId, date, oname, onum, name, num);
        return map;
    }

    public Map<String , Object> editMedicalItem(String doctorId, String patientId, String date, String issue, String diagnosed_disease, String allergens)
    {
        Map<String, Object> map = new HashMap<>();
        departmentRepository.editMedicalItem(doctorId, patientId, date, issue, diagnosed_disease, allergens);
        return map;
    }

    public Map<String , Object> deletePrescriptionItem(String doctorId, String patientId, String date, String name, String num)
    {
        Map<String, Object> map = new HashMap<>();
        departmentRepository.deletePrescriptionItem(doctorId, patientId, date, name, num);
        return map;
    }

    public Map<String , Object> deleteMedicalItem(String doctorId, String patientId, String date)
    {
        Map<String, Object> map = new HashMap<>();
        departmentRepository.deleteMedicalItem(doctorId, patientId, date);
        return map;
    }
}
