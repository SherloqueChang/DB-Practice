package com.hims.domain;

import lombok.Data;

@Data class Userinfo {
    private String id;
    private String pwd;
    private String name;
    public String birthdate;
    private String idcard;
    private String gender;
    private String phone;
    private String email;
    private String u_type;
    private String department;
    private String graduate_school;
    private String job_title;
    private String specialties;
    public void load_from_user(User u) 
    {
        this.id = u.getId();
        this.name = u.getName();
        this.pwd = u.getPwd(); 
        this.birthdate = u.getBirthdate();
        this.idcard = u.getIdcard();
        this.gender = u.getGender();
        this.email = u.getEmail();
        this.phone = u.getPhone();
        this.u_type = u.getU_type();
    }
    public void load_from_doctor(Doctor d)
    {
        this.department = d.getDepartment();
        this.graduate_school = d.getGraduate_school();
        this.job_title = d.getJob_title();
        this.specialties = d.getSpecialties();
    }
}
