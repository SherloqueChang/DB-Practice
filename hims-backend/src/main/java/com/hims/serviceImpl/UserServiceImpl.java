package com.hims.serviceImpl;

import com.hims.controller.request.*;
import com.hims.domain.User;
import com.hims.domain.Appointment;
import com.hims.domain.Userinfo;
import com.hims.domain.Doctor;
import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public Map<String, Object> login(String id, String password) {
        User user = find(id);
        Map<String, Object> map = new HashMap<>();
        // System.out.println(password+"\n");
        if (user == null) {
            map.put("error", "未找到用户名，请检查用户名是否正确");
            return map;
        } else if (!password.equals(user.getPwd())) {
            map.put("error", "用户名或密码错误");
            return map;
        } else if(user.getName() == null){
            map.put("needinfo", "请补全您的个人信息");
            return map;
        }
        
        else{
            // 在这里根据user的相关属性put不同的key来区分登录用户的身份
            Userinfo result = new Userinfo();
            result.load_from_user(user);
            if(user.getU_type().equals("doctor") || user.getU_type().equals("leader"))
            {
                Doctor doctor = doctorRepository.find(result.getId());
                result.load_from_doctor(doctor);
            }
            map.put("user",result);
            return map;
        }
    }

    public Map<String, Object> register(String id, String password) {
        //System.out.println(password + "\n");
        Map<String, Object> map = new HashMap<>();
        User user = find(id);
        if(user==null) 
        {
            user = new User();
            user.setId(id);  user.setPwd(password);
            user.setU_type("patient");
            save(user);
            map.put("user",user);
            return map;
        }
        else 
        {
            map.put("error", "用户名已经存在，请尝试其他用户名");
            return map;
        }
    }

    public Map<String, Object> registerinfo(UserInfoRequest request)
    {
        User user = find(request.getId());
        user = new User(user.getId(), user.getPwd(), request.getName(), request.getBirthdate(), request.getIdcard(), request.getGender(), request.getPhone(), request.getEmail(), user.getU_type());
        update(user);
        Map<String, Object> map = new HashMap<>();
        map.put("user",user);
        return map;
    }


    public Map<String, Object> modifyUserInfo (UserInfoRequest request)
    {
        User user = find(request.getId());
        user = new User(user.getId(), request.getPassword(),user.getName(), user.getBirthdate(), user.getIdcard(), user.getGender(), request.getPhone(), request.getEmail(), user.getU_type());
        update(user);
        Map<String, Object> map = new HashMap<>();
        map.put("user",user);
        return map;
    }


    public Object getuserInfo(String userId)
    {
        User user = find(userId);
        Userinfo result = new Userinfo();
        result.load_from_user(user);
        if(user.getU_type().equals("doctor") || user.getU_type().equals("leader"))
        {
            Doctor doctor = doctorRepository.find(result.getId());
            result.load_from_doctor(doctor);
        }
        return result;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(String id) {
        userRepository.delete(id);
    }

    public User find(String id) {
        return userRepository.find(id);
    }

    public List<User> findAllPatient() {
        return userRepository.findAllPatient();
    }
    public List<User> findAllDoctor() {
        return userRepository.findAllDoctor();
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }
    public List<Appointment> findAppointmentByPatientId(String id) {
        return appointmentRepository.findAppointmentByPatientId(id);
    }

}
