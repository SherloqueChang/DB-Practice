package com.wmhas.controller;


import com.wmhas.serviceImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    @PostMapping("/editMedicalRecForm")
    ResponseEntity<Map<String, Object>> editMedicalRecForm(@RequestParam("d_id") String doctorId, 
                                                           @RequestParam("p_id") String patientId, 
                                                           @RequestParam("issue") String issue, 
                                                           @RequestParam("diagnosed_disease") String diagnosed_disease, 
                                                           @RequestParam("allergens") String allergens) {
        return ResponseEntity.ok(doctorService.addmedicalinfo(doctorId, patientId, issue, diagnosed_disease, allergens));
    }

    @PostMapping("/editPrescriptionForm")
    ResponseEntity<Map<String, Object>> editPrescripForm
                (@RequestParam("d_id") String doctorId, 
                @RequestParam("p_id") String patientId, 
                @RequestParam("total_m_num") String total, 
                @RequestParam("medicine_name1") String medicine1, 
                @RequestParam("medicine_num1") String num1,
                @RequestParam("medicine_name2") String medicine2, 
                @RequestParam("medicine_num2") String num2,
                @RequestParam("medicine_name3") String medicine3, 
                @RequestParam("medicine_num3") String num3,
                @RequestParam("medicine_name4") String medicine4, 
                @RequestParam("medicine_num4") String num4) {
        medicine1 = medicine1.split("  ")[0];
        medicine2 = medicine2.split("  ")[0];
        medicine3 = medicine3.split("  ")[0];
        medicine4 = medicine4.split("  ")[0];
        List<String> medicines = new ArrayList<String>();
        List<Integer> nums = new ArrayList<Integer>();
        int total_num = Integer.parseInt(total);
        if(total_num>=1)
        {
            medicines.add(medicine1);
            nums.add(Integer.parseInt(num1));
        }
        if(total_num>=2) 
        {
            medicines.add(medicine2);
            nums.add(Integer.parseInt(num2));
        }
        if(total_num>=3) 
        {
            medicines.add(medicine3);
            nums.add(Integer.parseInt(num3));
        }
        if(total_num>=4) 
        {
            medicines.add(medicine4);
            nums.add(Integer.parseInt(num4));
        } 
        return ResponseEntity.ok(doctorService.add_prescription(doctorId, patientId, medicines, nums));
    }
    @PostMapping("/finishAppointment") ResponseEntity<Map<String, Object>> 
    finishAppointment(@RequestParam("p_id") String patientId,  @RequestParam("d_id") String doctorId)
    {
        return ResponseEntity.ok(doctorService.finishAppointment(doctorId, patientId));
    }
}
