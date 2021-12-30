package com.hims.serviceImpl;

import com.hims.domain.Prescription;
import com.hims.domain.PatientHistory;
import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalServiceImpl{
    @Autowired
    private MedicalRepository medicalRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public MedicalServiceImpl(MedicalRepository medicalRepository, UserServiceImpl userService) {
        this.medicalRepository = medicalRepository;
        this.userService = userService;
    }


    public Map<String, Object> getPrescriptionTable(String patientId) {
      
        Map<String, Object> map = new HashMap<>();
        List<Prescription> prescriptions = findPrescriptionByPatientId(patientId);
        for (Prescription pre : prescriptions) {
            pre.setDoctor_name(userService.find(pre.getDoctor_id()).getName());
            pre.setPatient_name(userService.find(pre.getPatient_id()).getName());
        }
        map.put("patientPrescriptionTable", prescriptions);
        return map;
    }

    public Map<String, Object> getHistoryTable_by_patient(String patientId) {
        Map<String, Object> map = new HashMap<>();
        List<PatientHistory> patientHistory = findPatientHistoryByPatientId(patientId);
        for(PatientHistory his : patientHistory){
            his.setDoctor_name(userService.find(his.getDoctor_id()).getName());
            his.setPatient_name(userService.find(his.getPatient_id()).getName());
        }
        map.put("patientHistoryTable", patientHistory);
        return map;
    }

    public Map<String, Object> getHistoryTable_by_doctor(String doctorId) {
        Map<String, Object> map = new HashMap<>();
        List<PatientHistory> patientHistory = findPatientHistoryByDoctorId(doctorId);
        for(PatientHistory his : patientHistory){
            his.setDoctor_name(userService.find(his.getDoctor_id()).getName());
            his.setPatient_name(userService.find(his.getPatient_id()).getName());
            //System.out.println(his);
        }
        map.put("historyPatient", patientHistory);
        return map;
    }

    public List<Prescription> findPrescriptionByPatientId(String patientId) {
        return medicalRepository.findPrescriptionByPatientId(patientId);
    }



    public List<PatientHistory> findPatientHistoryByPatientId(String patientId) {
        return medicalRepository.findPatientHistoryByPatientId(patientId);
    }

    public List<PatientHistory> findPatientHistoryByDoctorId(String doctor) {
        return medicalRepository.findPatientHistoryByDoctorId(doctor);
    }
    

}
