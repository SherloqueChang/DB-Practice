package com.hims.domain;

import lombok.Data;

@Data public class User {
    private String id;
    private String pwd;
    private String name;
    public String birthdate;
    private String idcard;
    private String gender;
    private String phone;
    private String email;
    private String u_type;

    public User() {
    }

    public User(String id, String pwd, String name, String birthdate, String idcard, String gender, String phone, String email, String u_type) {
        this.id = id;
        this.name = name;
        this.pwd = pwd; 
        this.birthdate = birthdate;
        this.idcard = idcard;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.u_type = u_type;
    }
}
