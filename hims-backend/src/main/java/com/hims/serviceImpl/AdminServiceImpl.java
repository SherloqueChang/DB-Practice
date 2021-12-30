package com.hims.serviceImpl;

import com.hims.domain.Department;
import com.hims.domain.Medicine;
import com.hims.domain.User;
import com.hims.domain.Userinfo;
import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    public Map<String, Object> getPatientTable() {
        Map<String, Object> map = new HashMap<>();
        List<User> patients = new ArrayList<>();
        patients = userRepository.findAllPatient();
        map.put("patient", patients);
        return map;
    }

    @Autowired
    public Map<String, Object> getDoctorTable() {
        Map<String, Object> map = new HashMap<>();
        List<User> doctors = new ArrayList<>();
        doctors = userRepository.findAllDoctor();
        map.put("doctor", doctors);
        return map;
    }

    public Map<String, Object> deleteDoctor(String id) {
        Map<String, Object> map = new HashMap<>();
        userRepository.delete(id);
        return map;
    }

    public Map<String, Object> getDoctorInfo(String id) {
        Map<String, Object> map = new HashMap<>();
        Userinfo user = new Userinfo();
        user.load_from_user(userRepository.find(id));
        user.load_from_doctor(doctorRepository.find(id));
        map.put("doctor", user);
        return map;
    }

    public Map<String, Object> getDepartmentTable() {
        Map<String, Object> map = new HashMap<>();
        List<Department> dept = deptRepository.findAll();
        map.put("department", dept);
        return map;
    }

    public Map<String, Object> deleteDepartment(String name) {
        Map<String, Object> map = new HashMap<>();
        deptRepository.delete(name);
        map.put("department", new Department());
        return map;
    }

    public Map<String, Object> getMedicineTable() {
        Map<String, Object> map = new HashMap<>();
        List<Medicine> dept = medicineRepository.findAll();
        map.put("medicine", dept);
        return map;
    }

    public Map<String, Object> editMedicine(String name, String num) {
        Map<String, Object> map = new HashMap<>();
        medicineRepository.setNum(name, num);
        map.put("medicine", new Medicine());
        return map;
    }
}
