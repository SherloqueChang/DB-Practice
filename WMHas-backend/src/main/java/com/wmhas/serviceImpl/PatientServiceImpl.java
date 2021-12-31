package com.wmhas.serviceImpl;

import com.wmhas.domain.Appointment;
import com.wmhas.domain.User;
import com.wmhas.domain.CoronavirusSurvey;
import com.wmhas.repository.CoronavirusSurveyRepository;
import com.wmhas.repository.AppointmentRepository;

import com.wmhas.controller.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl{

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CoronavirusSurveyRepository coronavirusSurveyRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    public PatientServiceImpl(UserServiceImpl userService, CoronavirusSurveyRepository coronavirusSurveyRepository, AppointmentRepository appointmentRepository) {
        this.userService = userService;
        this.coronavirusSurveyRepository = coronavirusSurveyRepository;
        this.appointmentRepository = appointmentRepository;
    }



    public Map<String, Object> patientAppointmentInfo(String patientId) {
      
        Map<String, Object> map = new HashMap<>();
        List<Appointment> appointments = userService.findAppointmentByPatientId(patientId);
        List<AppointmentRequest> appointmentRequests = new ArrayList<AppointmentRequest>();
        for (Appointment app : appointments) {
            appointmentRequests.add(changeApp2Request(app));
        }
        map.put("patientAppointment", appointmentRequests);
        return map;
    }

    public Map<String, Object> submitSurveyForm(String name, String cur_date, String gender, String idcard, String phone, String address, String whether_14days_fever, String fever_info, String whether_14days_area, String area_info, String whether_14days_contact, String contact_info, String whether_14days_contact_area, String contact_area_info) {
        List<User> patients = userService.findByName(name);
        if (patients.size() > 1) {
            throw new RuntimeException("name is not unique");
        }
        if (patients.size() == 0) {
            throw new RuntimeException("name is not exist");
        }
        String patient_id = patients.get(0).getId();
        CoronavirusSurvey coronavirusSurvey = new CoronavirusSurvey(patient_id, cur_date, name, gender, idcard, phone, address, shi2yes(whether_14days_fever), fever_info, shi2yes(whether_14days_area), area_info, shi2yes(whether_14days_contact), contact_info, shi2yes(whether_14days_contact_area), contact_area_info);
        this.coronavirusSurveyRepository.save(coronavirusSurvey);
        Map<String, Object> map = new HashMap<>();
        if(whether_14days_fever.equals("是") || whether_14days_area.equals("是")|| whether_14days_contact.equals("是") || whether_14days_contact_area.equals("是")){
            map.put("risk", "有感染风险，请前往发热门诊就医。");
        }
        else{
            map.put("ok", "无感染风险，请保持安全。");
        }
        return map;
    }

    public Map<String, Object> finalSubmit(String patient_id, String date, String department, String doctor_name) {
        Map<String, Object> map = new HashMap<>();
        String doctorId = userService.findByName(doctor_name).get(0).getId();
        Appointment appointment = new Appointment(doctorId, date, patient_id, department, "Todo");
        this.appointmentRepository.save(appointment);
        map.put("ok", "提交成功");
        return map;

    }

    public AppointmentRequest changeApp2Request(Appointment appointment) {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setDate(appointment.getAppointment_date());
        appointmentRequest.setDepartment(appointment.getDepartment());
        appointmentRequest.setDoctor(userService.find(appointment.getDoctor_id()).getName());
        appointmentRequest.setStatus(appointment.getAppointment_status());
        return appointmentRequest;
    }
    String shi2yes(String text){
        if(text.equals("是")){
            return "yes";
        }
        else{
            return "no";
        }
    }

}
