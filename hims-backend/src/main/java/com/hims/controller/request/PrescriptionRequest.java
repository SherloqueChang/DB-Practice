package com.hims.controller.request;

import lombok.Data;
@Data public class PrescriptionRequest {
    String date;
    String doctor;
    String medicine_name;
    String medicine_num;
    public PrescriptionRequest() {
    }
    public PrescriptionRequest(String date, String doctor, String medicine_name, String medicine_num) {
        this.date = date;
        this.doctor = doctor;
        this.medicine_name = medicine_name;
        this.medicine_num = medicine_num;
    }

}
