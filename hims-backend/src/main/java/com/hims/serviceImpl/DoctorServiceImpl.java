package com.hims.serviceImpl;

import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hims.domain.Appointment;
import com.hims.domain.Doctor;
import com.hims.controller.request.AppointmentQueryRequest;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class DoctorServiceImpl {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
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
    public Map<String, Object> appointmentQuery(String date, String departmentname) {
        Map<String, Object> map = new HashMap<>();
        
        List<Doctor> doctors = doctorRepository.findDoctorByDepartmentName(departmentname);
        List<AppointmentQueryRequest> doctorInfo = new ArrayList<AppointmentQueryRequest>();
        for(Doctor doctor : doctors) {
            String doctorId = doctor.getId();
            List<Appointment> appointments = appointmentRepository.findAppointmentByDoctorIDAndDate(doctorId, date);
            String doctorName = userservice.find(doctorId).getName();
            AppointmentQueryRequest appointmentQueryRequest = new AppointmentQueryRequest();
            appointmentQueryRequest.setId(doctorId);
            appointmentQueryRequest.setDoctor(doctorName);
            appointmentQueryRequest.setAvailableAppointment(30 - appointments.size() + "");
            doctorInfo.add(appointmentQueryRequest);
        }
        map.put("appointmentQuery",doctorInfo);
        return map;
    }
    public Map<String, Object> addmedicalinfo(String doctorId, String patientId, String issue, String diagnosed_disease, String allergens) {
        // TODO
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
