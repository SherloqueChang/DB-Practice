package com.hims.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Department {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String doctor_num;
}
