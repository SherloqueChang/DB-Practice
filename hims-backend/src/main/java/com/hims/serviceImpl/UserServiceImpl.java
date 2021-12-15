package com.hims.serviceImpl;

import com.hims.domain.Patient;
import com.hims.domain.User;
import com.hims.exception.BadCredentialsException;
import com.hims.exception.UserNotFoundException;
import com.hims.exception.WardNurseDeleteFailureException;
import com.hims.repository.*;
import com.hims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
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
        User user = find(Integer.parseInt(id));
        if (user == null) {
            throw new UserNotFoundException(id);
        } else if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException();
        } else {
            Map<String, Object> map = new HashMap<>();
            // 在这里根据user的相关属性put不同的key来区分登录用户的身份
            map.put("user", user);
//            map.put("patient", user);
            return map;
        }
    }

    public void register(String name, String id, String password) {
        User user = new User();
        user.setId(Integer.parseInt(id)); user.setName(name); user.setPassword(password);
        save(user);
    }

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
        User user = find(Integer.parseInt(id));
        map.put("worker", user);
        switch (user.getu_type()) {
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

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public User find(int id) {
        return userRepository.find(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findHeadNurseByDoctorId(int id) {
        return treatmentAreaRepository.findHeadNurseByDoctorId(id);
    }

    @Override
    public int findTreatmentAreaIdByDoctorId(int id) {
        return treatmentAreaRepository.findByDoctorId(id);
    }

    @Override
    public int findTreatmentAreaIdByHeadNurseId(int id) {
        return treatmentAreaRepository.findByHeadNurseId(id);
    }

    @Override
    @Transactional
    public List<Integer> findWardIdByDoctorId(int id) {
        int t_area_id = findTreatmentAreaIdByDoctorId(id);
        return wardRepository.findByTreatmentAreaId(t_area_id);
    }

    @Override
    @Transactional
    public List<Integer> findWardIdByHeadNurseId(int id) {
        int t_area_id = findTreatmentAreaIdByHeadNurseId(id);
        return wardRepository.findByTreatmentAreaId(t_area_id);
    }

    @Override
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

    @Override
    public List<User> findWardNurseByWardId(int id) {
        return wardNurseAndWardRepository.findWardNurseByWardId(id);
    }

    @Override
    public List<Patient> findPatientByWardId(int id) {
        return patientRepository.findByWardId(id);
    }

    public void modifyUserInfo(String id, String name, String password, String age, String email, String phone) {
        userRepository.update(Integer.parseInt(id), name, password, age, email, phone);
    }

    @Transactional
    public void addWardNurse(String w_id, String name, String age, String email, String phone) {
        int id = userRepository.saveWNurse(name, age, email, phone);
        wardNurseAndWardRepository.insertWardNurse(id, Integer.parseInt(w_id));
    }
}
