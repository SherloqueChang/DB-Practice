package com.hims.controller;


import com.hims.serviceImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DoctorController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DoctorServiceImpl doctorService;

    
    @Autowired
    public DoctorController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/getPatientInfo")
    ResponseEntity<Map<String, Object>> PatientInfo(@RequestParam("id") String patientId) {
        Object result = userService.getuserInfo(patientId);
        Map<String, Object> map = new HashMap<>();
        map.put("patient",result);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getPatientTodayInfo")
    ResponseEntity<Map<String, Object>> PatientTodayInfo(@RequestParam("id") String doctorId) {
        return ResponseEntity.ok(doctorService.get_todayPatientInfo(doctorId));
    }
}