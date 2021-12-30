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
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("1", "内科");
        map.put("2", "外科");
        map.put("3", "妇产科");
        map.put("4", "儿科");
        map.put("5", "发热门诊");
        String departmentname = map.get(departmentid+"");
        return ResponseEntity.ok(doctorService.appointmentQuery(date, departmentname));
    }

    @PostMapping("/submitSurveyForm")
    ResponseEntity<Map<String, Object>> submitSurveyForm(
        @RequestParam("name") String name, @RequestParam("cur_date") String cur_date, @RequestParam("gender") String gender,
        @RequestParam("idcard") String idcard, @RequestParam("phone") String phone, @RequestParam("address") String address,
        @RequestParam("whether_14days_fever") String whether_14days_fever, @RequestParam("fever_info") String fever_info,
        @RequestParam("whether_14days_area") String whether_14days_area, @RequestParam("area_info") String area_info,
        @RequestParam("whether_14days_contact") String whether_14days_contact, @RequestParam("contact_info") String contact_info,
        @RequestParam("whether_14days_contact_area") String whether_14days_contact_area, @RequestParam("contact_area_info") String contact_area_info
    ) {
        //System.out.println("\n"+name+"*"+ cur_date+"*"+ gender+"*"+ idcard+"*"+ phone+"*"+ address+"*"+ whether_14days_fever+"*"+ fever_info+"*"+ whether_14days_area+"*"+ area_info+"*"+ whether_14days_contact+"*"+ contact_info+"*"+ whether_14days_contact_area+"*"+ contact_area_info+"\n");
        return ResponseEntity.ok(patientService.submitSurveyForm(name, cur_date, gender, idcard, phone, address, whether_14days_fever, fever_info, whether_14days_area, area_info, whether_14days_contact, contact_info, whether_14days_contact_area, contact_area_info));
    }

    @PostMapping("/finalSubmit")
    ResponseEntity<Map<String, Object>> finalSubmit(
        @RequestParam("patient_id") String patient_id, @RequestParam("date") String date, @RequestParam("department") String departmentid, @RequestParam("doctor_name") String doctorname
    ) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("d1", "内科");
        map.put("d2", "外科");
        map.put("d3", "妇产科");
        map.put("d4", "儿科");
        map.put("d5", "发热门诊");
        String department = map.get(departmentid+"");
        System.out.println("\n"+patient_id+"*"+ date+"*"+ departmentid+"*"+ doctorname+"\n");
        return ResponseEntity.ok(patientService.finalSubmit(patient_id, date, department, doctorname));
    }
}
