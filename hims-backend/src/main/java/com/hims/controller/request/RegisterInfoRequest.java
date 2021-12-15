package com.hims.controller.request;

import lombok.Data;

@Data public class RegisterInfoRequest {
    String id;
    String name;
    String birthdate;
    String idcard;
    String gender;
    String phone;
    String email;
}
