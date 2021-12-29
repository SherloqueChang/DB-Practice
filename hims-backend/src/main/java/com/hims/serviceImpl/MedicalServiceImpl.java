package com.hims.serviceImpl;

import com.hims.domain.Prescription;
import com.hims.domain.PatientHistory;
import com.hims.repository.*;
import com.hims.controller.request.PrescriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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
        List<PrescriptionRequest> patientPrescriptionTable = new ArrayList<PrescriptionRequest>();
        for (Prescription pre : prescriptions) {
            patientPrescriptionTable.add(changePre2Request(pre));
        }
        map.put("patientPrescriptionTable", patientPrescriptionTable);
        return map;
    }

    public Map<String, Object> getHistoryTable(String patientId) {
        Map<String, Object> map = new HashMap<>();
        List<PatientHistory> patientHistory = findPatientHistoryByPatientId(patientId);
        map.put("patientHistoryTable", patientHistory);
        return map;
    }

    public List<Prescription> findPrescriptionByPatientId(String patientId) {
        return medicalRepository.findPrescriptionByPatientId(patientId);
    }

    public List<PatientHistory> findPatientHistoryByPatientId(String patientId) {
        return medicalRepository.findPatientHistoryByPatientId(patientId);
    }

    
    public PrescriptionRequest changePre2Request(Prescription prescription) {
        PrescriptionRequest prescriptionRequest = new PrescriptionRequest();
        prescriptionRequest.setDate(prescription.getPres_date());
        prescriptionRequest.setMedicine_name(prescription.getMedicine_name());
        prescriptionRequest.setDoctor(userService.find(prescription.getDoctor_id()).getName());
        prescriptionRequest.setMedicine_num(prescription.getMedicine_num());
        return prescriptionRequest;
    }
}
