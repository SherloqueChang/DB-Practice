package com.hims.domain;

import lombok.Data;

@Data class Doctor{
    private String id;
    private String department;
    private String graduate_school;
    private String job_title;
    private String specialties;
}
