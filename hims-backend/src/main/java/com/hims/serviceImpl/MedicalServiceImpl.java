package com.hims.serviceImpl;

import com.hims.domain.Bed;
import com.hims.domain.User;
import com.hims.domain.Prescription;
import com.hims.domain.NatReport;
import com.hims.domain.Patient;
import com.hims.repository.*;
import com.hims.controller.request.PrescriptionRequest;
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
        for (Prescription app : prescriptions) {
            patientPrescriptionTable.add(changePre2Request(app));
        }
        map.put("patientPrescriptionTable", patientPrescriptionTable);
        return map;
    }

    public List<Prescription> findPrescriptionByPatientId(String patientId) {
        return medicalRepository.findPrescriptionByPatientId(patientId);
    }

    
    public PrescriptionRequest changePre2Request(Prescription prescription) {
        PrescriptionRequest prescriptionRequest = new PrescriptionRequest();
        prescriptionRequest.setDate(prescription.getPres_date());
        prescriptionRequest.setMedicine_name(prescription.getMedicine_name());
        prescriptionRequest.setDoctor(userService.find(prescription.getDoctor_id()).getName());
        prescriptionRequest.setMedicine_num(prescription.getMedicine_num());
        return prescriptionRequest;
    }

}
