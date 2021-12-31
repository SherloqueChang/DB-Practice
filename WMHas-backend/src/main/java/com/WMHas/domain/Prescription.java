package com.hims.domain;


import lombok.Data;
@Data public class Prescription {
    private String doctor_id;
    private String patient_id;
    private String pres_date;
    private String medicine_name;
    private String medicine_num;
    private String doctor_name;
    private String patient_name;
    public Prescription() {
    }
    public Prescription( String doctor_id, String patient_id, String pres_date, String medicine_name, String medicine_num) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.pres_date = pres_date;
        this.medicine_name = medicine_name;
        this.medicine_num = medicine_num;
    }
}
