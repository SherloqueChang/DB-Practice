package com.hims.serviceImpl;

import com.hims.controller.request.*;
import com.hims.domain.Patient;
import com.hims.domain.User;
import com.hims.exception.BadCredentialsException;
import com.hims.exception.UserNotFoundException;
import com.hims.exception.WardNurseDeleteFailureException;
import com.hims.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TreatmentAreaRepository treatmentAreaRepository;
    @Autowired
    private WardRepository wardRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private WardNurseAndWardRepository wardNurseAndWardRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TreatmentAreaRepository treatmentAreaRepository,
                           WardRepository wardRepository, PatientRepository patientRepository,
                           WardNurseAndWardRepository wardNurseAndWardRepository) {
        this.userRepository = userRepository;
        this.treatmentAreaRepository = treatmentAreaRepository;
        this.wardRepository = wardRepository;
        this.patientRepository = patientRepository;
        this.wardNurseAndWardRepository = wardNurseAndWardRepository;
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
            map.put("user", user);
//            map.put("patient", user);
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
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        User user = find(request.getId());
        try {
            // TODO check the format of the Idcard, phone, and email addresses
            user = new User(user.getId(), user.getPwd(), request.getName(), fmt.parse(request.getBirthdate()), request.getIdcard(), request.getGender(), request.getPhone(), request.getEmail(), user.getU_type());
            //System.out.println(fmt.parse(request.getBirthdate())+"\n");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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




/*
    public Map<String, Object> getDoctorDataPanel(String id) {
        Map<String, Object> map = new HashMap<>();
        User headNurse = findHeadNurseByDoctorId(Integer.parseInt(id));
        map.put("headNurse", headNurse); // headNurse
        List<Integer> wards = findWardIdByDoctorId(Integer.parseInt(id));
        List<User> wardNurse = new ArrayList<>();
        for (Integer integer : wards) {
            wardNurse.addAll(findWardNurseByWardId(integer));
        }
        map.put("wardNurse", wardNurse); // wardNurse
        return map;
    }

    public Map<String, Object> getHeadNurseDataPanel(String id) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> wards = findWardIdByHeadNurseId(Integer.parseInt(id));
        List<User> wardNurse = new ArrayList<>();
        for (Integer integer : wards) {
            wardNurse.addAll(findWardNurseByWardId(integer));
        }
        map.put("wardNurse", wardNurse); // wardNurse
        return map;
    }

    public Map<String, Object> getWorkerInfo(String id) {
        Map<String, Object> map = new HashMap<>();
        User user = find(id);
        map.put("worker", user);
        switch (user.getU_type()) {
            case "e_nurse":
                return map;
            case "doctor": {
                int t_area_id = findTreatmentAreaIdByDoctorId(Integer.parseInt(id));
                map.put("t_area_id", t_area_id);
                List<Integer> w_id = findWardIdByDoctorId(Integer.parseInt(id));
                map.put("w_id", w_id);
                return map;
            }
            case "h_nurse": {
                int t_area_id = findTreatmentAreaIdByHeadNurseId(Integer.parseInt(id));
                map.put("t_area_id", t_area_id);
                List<Integer> w_id = findWardIdByHeadNurseId(Integer.parseInt(id));
                map.put("w_id", w_id);
                return map;
            }
            default: { // w_nurse
                int w_id = wardNurseAndWardRepository.findWardIdByWNurseId(Integer.parseInt(id));
                List<Integer> w_temp = new ArrayList<>();
                w_temp.add(w_id);
                int t_area_id = wardRepository.findTAreaIdByWardId(w_id);
                map.put("t_area_id", t_area_id);
                map.put("w_id", w_temp);
                return map;
            }
        }
    }

//    public Map<String, Object> getFreeNurseData(String h_nurse_id) {
//        Map<String, Object> map = new HashMap<>();
//        List<Integer> wards = findWardIdByHeadNurseId(Integer.parseInt(h_nurse_id));
//        map.put("wardId", wards);
//        List<User> freeNurse = findFreeWardNurse();
//        map.put("freeNurse", freeNurse);
//        return map;
//    }

    */

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

    public List<User> findAll() {
        return userRepository.findAll();
    }
/*
    public User findHeadNurseByDoctorId(int id) {
        return treatmentAreaRepository.findHeadNurseByDoctorId(id);
    }

    public int findTreatmentAreaIdByDoctorId(int id) {
        return treatmentAreaRepository.findByDoctorId(id);
    }

    public int findTreatmentAreaIdByHeadNurseId(int id) {
        return treatmentAreaRepository.findByHeadNurseId(id);
    }

    @Transactional
    public List<Integer> findWardIdByDoctorId(int id) {
        int t_area_id = findTreatmentAreaIdByDoctorId(id);
        return wardRepository.findByTreatmentAreaId(t_area_id);
    }

    @Transactional
    public List<Integer> findWardIdByHeadNurseId(int id) {
        int t_area_id = findTreatmentAreaIdByHeadNurseId(id);
        return wardRepository.findByTreatmentAreaId(t_area_id);
    }

    @Transactional
    public void deleteWardNurseByWNurseId(int id) {
        List<Patient> hospitalizedPatients = patientRepository.findHospitalizedByWNurseId(id);
        if (hospitalizedPatients == null || hospitalizedPatients.size() == 0) {
            wardNurseAndWardRepository.deleteWardNurseByWNurseId(id);
        } else {
            throw new WardNurseDeleteFailureException();
        }
    }
//
//    @Override
//    @Transactional
//    public List<User> findFreeWardNurse() {
//        return wardNurseAndWardRepository.findFreeWardNurse();
//    }
//
//    @Override
//    @Transactional
//    public void insertWardNurse(int w_nurse_id, int id) {
//
//    }

    public List<User> findWardNurseByWardId(int id) {
        return wardNurseAndWardRepository.findWardNurseByWardId(id);
    }

    public List<Patient> findPatientByWardId(int id) {
        return patientRepository.findByWardId(id);
    }
*/

    @Transactional
    public void addWardNurse(String w_id, String name, String age, String email, String phone) {
        int id = userRepository.saveWNurse(name, age, email, phone);
        wardNurseAndWardRepository.insertWardNurse(id, Integer.parseInt(w_id));
    }
}
