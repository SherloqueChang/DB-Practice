package com.hims.serviceImpl;

import com.hims.domain.Appointment;

import com.hims.controller.request.AppointmentRequest;
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
    public PatientServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
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
    
    public AppointmentRequest changeApp2Request(Appointment appointment) {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setDate(appointment.getAppointment_date());
        appointmentRequest.setDepartment(appointment.getDepartment());
        appointmentRequest.setDoctor(userService.find(appointment.getDoctor_id()).getName());
        appointmentRequest.setStatus(appointment.getAppointment_status());
        return appointmentRequest;
    }

}
