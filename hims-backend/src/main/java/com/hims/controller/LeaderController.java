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
}