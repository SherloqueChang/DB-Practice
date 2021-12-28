package com.hims.serviceImpl;

import com.hims.domain.Bed;
import com.hims.domain.User;
import com.hims.domain.Prescription;
import com.hims.domain.PatientHistory;
import com.hims.domain.NatReport;
import com.hims.domain.Patient;
import com.hims.repository.*;
import com.hims.controller.request.PrescriptionRequest;
import com.hims.controller.request.PatientHistoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalServiceImpl{
    @Autowired
    private MedicalRepository medicalRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DailyReportRepository dailyReportRepository;
    @Autowired
    private NATReportRepository natReportRepository;
    @Autowired
    private TreatmentAreaRepository treatmentAreaRepository;
    @Autowired
    private WardRepository wardRepository;
    @Autowired
    private BedRepository bedRepository;
    @Autowired
    private WardNurseAndWardRepository wardNurseAndWardRepository;

    @Autowired
    public MedicalServiceImpl(MedicalRepository medicalRepository, UserServiceImpl userService,
                              DailyReportRepository dailyReportRepository, NATReportRepository natReportRepository,
                              TreatmentAreaRepository treatmentAreaRepository, WardRepository wardRepository,
                              BedRepository bedRepository, WardNurseAndWardRepository wardNurseAndWardRepository) {
        this.medicalRepository = medicalRepository;
        this.userService = userService;
        this.dailyReportRepository = dailyReportRepository;
        this.natReportRepository = natReportRepository;
        this.treatmentAreaRepository = treatmentAreaRepository;
        this.wardRepository = wardRepository;
        this.bedRepository = bedRepository;
        this.wardNurseAndWardRepository = wardNurseAndWardRepository;
    }


    public Map<String, Object> getPrescriptionTable(String patientId) {
      
        Map<String, Object> map = new HashMap<>();
        List<Prescription> prescriptions = findPrescriptionByPatientId(patientId);
        List<PrescriptionRequest> patientPrescriptionTable = new ArrayList<PrescriptionRequest>();
        for (Prescription pre : prescriptions) {
            patientPrescriptionTable.add(changePre2Request(pre));
        }
        map.put("patientPrescriptionTable", patientPrescriptionTable);
        return map;
    }

    public Map<String, Object> getHistoryTable(String patientId) {
        Map<String, Object> map = new HashMap<>();
        List<PatientHistory> patientHistory = findPatientHistoryByPatientId(patientId);
//        List<PatientHistoryRequest> patientHistoryTable = new ArrayList<>();
//        for (PatientHistory pathis : patientHistory) {
//            patientHistoryTable.add(changePathis2Request(pathis));
//        }
//        map.put("patientHistoryTable", patientHistoryTable);
        map.put("patientHistoryTable", patientHistory);
        return map;
    }

    public List<Prescription> findPrescriptionByPatientId(String patientId) {
        return medicalRepository.findPrescriptionByPatientId(patientId);
    }

    public List<PatientHistory> findPatientHistoryByPatientId(String patientId) {
        return medicalRepository.findPatientHistoryByPatientId(patientId);
    }

    
    public PrescriptionRequest changePre2Request(Prescription prescription) {
        PrescriptionRequest prescriptionRequest = new PrescriptionRequest();
        prescriptionRequest.setDate(prescription.getPres_date());
        prescriptionRequest.setMedicine_name(prescription.getMedicine_name());
        prescriptionRequest.setDoctor(userService.find(prescription.getDoctor_id()).getName());
        prescriptionRequest.setMedicine_num(prescription.getMedicine_num());
        return prescriptionRequest;
    }

    public PatientHistoryRequest changePathis2Request(PatientHistory patientHistory) {
        PatientHistoryRequest patientHistoryRequest = new PatientHistoryRequest();
        patientHistoryRequest.setDate(patientHistory.getTreat_date());
        patientHistoryRequest.setDoctor(userService.find(patientHistory.getDoctor_id()).getName());
        patientHistoryRequest.setIssue(patientHistory.getTreat_issue());
        patientHistoryRequest.setDiagnosed_disease(patientHistory.getDiagnosed_disease());
        patientHistoryRequest.setAllergens(patientHistory.getAllergens());
        return patientHistoryRequest;
    }

}
