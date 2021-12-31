package com.wmhas.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DepartmentCnt {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String doctor_num;
}
