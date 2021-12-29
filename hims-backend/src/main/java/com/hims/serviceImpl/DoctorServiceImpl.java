package com.hims.serviceImpl;

import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hims.domain.Appointment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DoctorServiceImpl {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired 
    private UserServiceImpl userservice;

    @Autowired
    public DoctorServiceImpl(AppointmentRepository appointmentRepository, UserServiceImpl userserviceImpl) {
        this.appointmentRepository = appointmentRepository;
        this.userservice = userserviceImpl;
    }
    public Map<String, Object> get_todayPatientInfo(String doctorId)
    {
        Map<String, Object> map = new HashMap<>();
        List<Appointment> appointments = appointmentRepository.findTodayAppointmentByDoctorID(doctorId);
        for(Appointment ap : appointments)
            ap.setPatient_name(userservice.find(ap.getPatient_id()).getName());
        map.put("patientToday",appointments);
        return map;
    }
}
