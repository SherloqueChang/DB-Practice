package com.hims.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Department {
    private String name;
    private String id;
    private String doctor_num;
    private String dept_description;
}
