package com.hims.serviceImpl;

import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hims.domain.Appointment;
import com.hims.domain.Doctor;
import com.hims.controller.request.AppointmentQueryRequest;

import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private MedicalRepository medicalRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired 
    private UserServiceImpl userservice;

    @Autowired
    public DoctorServiceImpl(AppointmentRepository appointmentRepository, UserServiceImpl userserviceImpl) {
        this.appointmentRepository = appointmentRepository;
        this.userservice = userserviceImpl;
    }

    private String get_date_of_today()
    {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd"); 
        Date date = new Date();// 获取当前时间 
        return sdf.format(date);
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
        appointmentRepository.addmedicalinfo(doctorId, patientId, get_date_of_today(), issue, diagnosed_disease, allergens);
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    public Map<String, Object> add_prescription(String doctorId, String patientId, List<String> medicines, List<Integer> nums) {
        String error = medicineRepository.addprescription(doctorId, patientId, medicines, nums);
        Map<String, Object> map = new HashMap<>();
        if(error != null)
            map.put("error", error);
        return map;
    }

    public Map<String, Object> finishAppointment(String doctorId, String patientId) {
        medicalRepository.finishAppointment(doctorId, patientId);
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
