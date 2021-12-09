package com.hims.controller.request;

import lombok.Getter;
import lombok.Setter;


public class RegisterRequest {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String password;

    public RegisterRequest(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }
}