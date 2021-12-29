package com.hims.controller;

import com.hims.serviceImpl.PatientServiceImpl;
import com.hims.serviceImpl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
public class AppointmentController {
    @Autowired
    private PatientServiceImpl patientService;
    @Autowired
    private DoctorServiceImpl doctorService;
    
    @Autowired
    public AppointmentController(PatientServiceImpl patientService, DoctorServiceImpl doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }
    


    @GetMapping("/patientAppointmentInfo")
    ResponseEntity<Map<String, Object>> patientAppointmentInfo(@RequestParam("id") String patientId) {
        return ResponseEntity.ok(patientService.patientAppointmentInfo(patientId));
    }
    
    @GetMapping("/appointmentQuery")
    ResponseEntity<Map<String, Object>> appointmentQuery(@RequestParam("date") String date, @RequestParam("department") String departmentid) {
        Map<String, String> map = new HashMap<>(Map.of(
            "1", "内科", "2", "外科", "3", "妇产科", "4", "儿科", "5", "发热门诊"
        ));
        String departmentname = map.get(departmentid+"");
        return ResponseEntity.ok(doctorService.appointmentQuery(date, departmentname));
    }
}
