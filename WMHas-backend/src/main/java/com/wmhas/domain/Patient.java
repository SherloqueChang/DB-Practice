package com.wmhas.domain;
import lombok.Data;

@Data public class Patient{
    private String id;
    private String pwd;
    private String name;
    public String birthdate;
    private String idcard;
    private String gender;
    private String phone;
    private String email;
    private String u_type;

}