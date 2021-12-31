package com.wmhas.domain;


import lombok.Data;

@Data public class Appointment {
    private String doctor_id;
    private String appointment_date;
    private String patient_id;
    private String department;
    private String appointment_status;
    private String patient_name;

    public Appointment() {
    }

    public Appointment(String doctor_id, String appointment_date, String patient_id, String department, String appointment_status) {
        this.doctor_id = doctor_id;
        this.appointment_date = appointment_date;
        this.department = department;
        this.patient_id = patient_id;
        this.appointment_status = appointment_status;
    }
}
