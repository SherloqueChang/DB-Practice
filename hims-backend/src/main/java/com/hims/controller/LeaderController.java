package com.hims.controller;


import com.hims.serviceImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LeaderController {
    @Autowired
    private LeaderServiceImpl leaderService;

    @GetMapping("/getDepartmentDesc")
    ResponseEntity<Map<String, Object>> getDepartmentDesc(@RequestParam("id") String doctorId) {
        return ResponseEntity.ok(leaderService.getDepartmentDesc(doctorId));
    }

    @PostMapping("/modifyDepartmentDesc")
    ResponseEntity<Map<String, Object>> modifyDepartmentDesc(@RequestParam("id") String doctorId,
                                                            @RequestParam("desc") String desc) {
        return ResponseEntity.ok(leaderService.modifyDepartmentDesc(doctorId, desc));
    }

    @GetMapping("/getDepartmentDoctor")
    ResponseEntity<Map<String, Object>> getDepartmentDoctor(@RequestParam("id") String doctorId) {
        return ResponseEntity.ok(leaderService.getDepartmentDoctor(doctorId));
    }

    @GetMapping("/getDoctorPrescription")
    ResponseEntity<Map<String, Object>> getdoctorPrescription(@RequestParam("id") String doctorId) {
        return ResponseEntity.ok(leaderService.getdoctorPrescription(doctorId));
    }

    @GetMapping("/getDoctorMedicalHistory")
    ResponseEntity<Map<String, Object>> getDoctorMedicalHistory(@RequestParam("id") String doctorId) {
        return ResponseEntity.ok(leaderService.getdoctorPatientHistory(doctorId));
    }

    @PostMapping("/editPrescriptionItem")
    ResponseEntity<Map<String, Object>> editPrescriptionItem
                                        (@RequestParam("d_id") String doctorId,
                                        @RequestParam("p_id") String patientId,
                                        @RequestParam("date") String date,
                                        @RequestParam("o_medicine_name") String oname,
                                        @RequestParam("o_medicine_num") String onum,
                                        @RequestParam("medicine_name") String name,
                                        @RequestParam("medicine_num") String num) {
        return ResponseEntity.ok(leaderService.editPrescriptionItem(doctorId, patientId, date, oname, onum, name, num));
    }

    @PostMapping("/editMedicalItem")
    ResponseEntity<Map<String, Object>> editMedicalItem
                                        (@RequestParam("d_id") String doctorId,
                                        @RequestParam("p_id") String patientId,
                                        @RequestParam("date") String date,
                                        @RequestParam("issue") String issue,
                                        @RequestParam("diagnosed_disease") String diagnosed_disease,
                                        @RequestParam("allergens") String allergens) {
        return ResponseEntity.ok(leaderService.editMedicalItem(doctorId, patientId, date, issue, diagnosed_disease, allergens));
    }

    @PostMapping("/deletePrescriptionItem")
    ResponseEntity<Map<String, Object>> deletePrescriptionItem
                                        (@RequestParam("d_id") String doctorId,
                                        @RequestParam("p_id") String patientId,
                                        @RequestParam("date") String date,
                                        @RequestParam("medicine_name") String name,
                                        @RequestParam("medicine_num") String num) {
        return ResponseEntity.ok(leaderService.deletePrescriptionItem(doctorId, patientId, date, name, num));
    }

    @PostMapping("/deleteMedicalItem")
    ResponseEntity<Map<String, Object>> deleteMedicalItem
                                        (@RequestParam("d_id") String doctorId,
                                        @RequestParam("p_id") String patientId,
                                        @RequestParam("date") String date) {
        return ResponseEntity.ok(leaderService.deleteMedicalItem(doctorId, patientId, date));
    }
}