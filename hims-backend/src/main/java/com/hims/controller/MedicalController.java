package com.hims.controller;


import com.hims.serviceImpl.MedicalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MedicalController {
    @Autowired
    private MedicalServiceImpl medicalService;

    
    @Autowired
    public MedicalController(MedicalServiceImpl medicalService) {
        this.medicalService = medicalService;
    }
    
    @GetMapping("/patientPrescriptionTable")
    ResponseEntity<Map<String, Object>> patientPrescriptionTable(@RequestParam("id") String patientId) {
        return ResponseEntity.ok(medicalService.getPrescriptionTable(patientId));
    }

    @GetMapping("/patientHistoryTable")
    ResponseEntity<Map<String, Object>> patientHistoryTable(@RequestParam("id") String patientId) {
        return ResponseEntity.ok(medicalService.getHistoryTable_by_patient(patientId));
    }

    @GetMapping("/getHistoryPatient")
    ResponseEntity<Map<String, Object>> getHistoryPatient(@RequestParam("id") String doctorId) {
        return ResponseEntity.ok(medicalService.getHistoryTable_by_doctor(doctorId));
    }
}
