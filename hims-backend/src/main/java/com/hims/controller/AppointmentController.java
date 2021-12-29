package com.hims.controller;

import com.hims.serviceImpl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AppointmentController {
    @Autowired
    private PatientServiceImpl patientService;
    
    @Autowired
    public AppointmentController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }
    
    @GetMapping("/patientAppointmentInfo")
    ResponseEntity<Map<String, Object>> patientAppointmentInfo(@RequestParam("id") String patientId) {
        return ResponseEntity.ok(patientService.patientAppointmentInfo(patientId));
    }
}
