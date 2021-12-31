package com.wmhas.controller.request;

import lombok.Data;

@Data public class UserInfoRequest {
    String id;
    String password;
    String name;
    String birthdate;
    String idcard;
    String gender;
    String phone;
    String email;
    String u_type;

    public UserInfoRequest() {
        
    }

    public UserInfoRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
    public UserInfoRequest(String id, String name, String birthdate, String idcard, String gender, String phone, String email) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.idcard = idcard;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }
    public UserInfoRequest(String id, String password, String email, String phone) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}