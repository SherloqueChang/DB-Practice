package com.hims.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import lombok.Data;

@Data public class PatientHistory {
    private String patient_id;
    private String doctor_id;
    private String treat_date;
    private String treat_issue;
    private String diagnosed_disease;
    private String allergens;

    public PatientHistory() {
    }

    public PatientHistory(String patient_id, String doctor_id, String treat_date, String treat_issue, String diagnosed_disease, String allergens) {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.treat_date = treat_date;
        this.treat_issue = treat_issue;
        this.diagnosed_disease = diagnosed_disease;
        this.allergens = allergens;
    }
}