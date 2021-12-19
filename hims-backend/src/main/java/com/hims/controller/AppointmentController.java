package com.hims.controller;

import com.hims.controller.request.LoginRequest;
import com.hims.controller.request.RegisterRequest;
import com.hims.controller.request.UserInfoRequest;
import com.hims.controller.request.RegisterInfoRequest;
import com.hims.domain.User;
import com.hims.exception.WardNurseDeleteFailureException;
import com.hims.serviceImpl.BedServiceImpl;
import com.hims.serviceImpl.PatientServiceImpl;
import com.hims.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AppointmentController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PatientServiceImpl patientService;
    
    @Autowired
    public AppointmentController(UserServiceImpl userService, PatientServiceImpl patientService) {
        this.userService = userService;
        this.patientService = patientService;
    }
    /*
    @GetMapping("/patientAppointmentInfo")
    ResponseEntity<Map<String, Object>> patientAppointmentInfo(@RequestParam("id") String patientId) {
        return ResponseEntity.ok(patientService.patientAppointmentInfo(patientId));
    }*/
}
