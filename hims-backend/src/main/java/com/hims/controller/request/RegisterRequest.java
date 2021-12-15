package com.hims.controller.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String id;
    private String password;

    public RegisterRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
    
}