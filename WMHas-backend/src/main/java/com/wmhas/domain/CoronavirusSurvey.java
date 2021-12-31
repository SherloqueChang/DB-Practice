package com.hims.domain;
import lombok.Data;


@Data public class CoronavirusSurvey {
    private String patient_id;
    private String fill_time;
    private String name;
    private String gender;
    private String idcard;
    private String phone;
    private String address;
    private String whether_14days_fever;
    private String fever_info;
    private String whether_14days_area;
    private String area_info;
    private String whether_14days_contact;
    private String contact_info;
    private String whether_14days_contact_area;
    private String contact_area_info;
    
    public CoronavirusSurvey() {
    }
    public CoronavirusSurvey(String patient_id, String fill_time, String name, String gender, String idcard, String phone, String address, String whether_14days_fever, String fever_info, String whether_14days_area, String area_info, String whether_14days_contact, String contact_info, String whether_14days_contact_area, String contact_area_info) {
        this.patient_id = patient_id;
        this.fill_time = fill_time;
        this.name = name;
        this.gender = gender;
        this.idcard = idcard;
        this.phone = phone;
        this.address = address;
        this.whether_14days_fever = whether_14days_fever;
        this.fever_info = fever_info;
        this.whether_14days_area = whether_14days_area;
        this.area_info = area_info;
        this.whether_14days_contact = whether_14days_contact;
        this.contact_info = contact_info;
        this.whether_14days_contact_area = whether_14days_contact_area;
        this.contact_area_info = contact_area_info;
    }
}
