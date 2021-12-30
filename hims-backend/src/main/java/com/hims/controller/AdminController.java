package com.hims.controller;

import com.hims.serviceImpl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getPatientTable")
    ResponseEntity<Map<String, Object>> patientTable() {
        return ResponseEntity.ok(adminService.getPatientTable());
    }

    @GetMapping("/getDoctorTable")
    ResponseEntity<Map<String, Object>> doctorTable() {
        return ResponseEntity.ok(adminService.getDoctorTable());
    }

    @PostMapping("/deleteDoctor")
    ResponseEntity<Map<String, Object>> deleteDoctor(@RequestParam("id") String p_id) {
        return ResponseEntity.ok(adminService.deleteDoctor(p_id));
    }

    @GetMapping("/getDoctorInfo")
    ResponseEntity<Map<String, Object>> DoctorInfo(@RequestParam("id") String id) {
        return ResponseEntity.ok(adminService.getDoctorInfo(id));
    }

    @GetMapping("/getDepartmentTable")
    ResponseEntity<Map<String, Object>> getDepartmentTable() {
        return ResponseEntity.ok(adminService.getDepartmentTable());
    }

    @PostMapping("/deleteDepartment")
    ResponseEntity<Map<String, Object>> deleteDepartment(@RequestParam("name") String name) {
        return ResponseEntity.ok(adminService.deleteDepartment(name));
    }

    @GetMapping("/getMedicineTable")
    ResponseEntity<Map<String, Object>> getMedicineTable() {
        return ResponseEntity.ok(adminService.getMedicineTable());
    }

    @PostMapping("/editMedicine")
    ResponseEntity<Map<String, Object>> editMedicine(@RequestParam("name") String name,
                                                     @RequestParam("num") String num) {
        return ResponseEntity.ok(adminService.editMedicine(name, num));
    }

    @PostMapping("/setDeptLeader")
    ResponseEntity<Map<String, Object>> setDeptLeader(@RequestParam("id") String id,
                                                      @RequestParam("department") String dept) {
        return ResponseEntity.ok(adminService.setDeptLeader(id, dept));
    }
}
