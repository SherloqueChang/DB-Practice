package com.wmhas.domain;


import lombok.Data;

@Data public class PatientHistory {
    private String patient_id;
    private String doctor_id;
    private String treat_date;
    private String treat_issue;
    private String diagnosed_disease;
    private String allergens;
    private String patient_name;
    private String doctor_name;

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