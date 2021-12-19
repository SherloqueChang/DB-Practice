package com.hims.controller;


import com.hims.controller.request.UserInfoRequest;
import com.hims.domain.User;
import com.hims.exception.WardNurseDeleteFailureException;
import com.hims.serviceImpl.BedServiceImpl;
import com.hims.serviceImpl.PatientServiceImpl;
import com.hims.serviceImpl.MedicalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.ArrayList;

@RestController
public class MedicalController {
    @Autowired
    private MedicalServiceImpl medicalService;
    @Autowired
    private PatientServiceImpl patientService;
    
    @Autowired
    public MedicalController(MedicalServiceImpl medicalService, PatientServiceImpl patientService) {
        this.medicalService = medicalService;
        this.patientService = patientService;
    }
    
    @GetMapping("/patientPrescriptionTable")
    ResponseEntity<Map<String, Object>> patientPrescriptionTable(@RequestParam("id") String patientId) {
        return ResponseEntity.ok(medicalService.getPrescriptionTable(patientId));
    }
}
