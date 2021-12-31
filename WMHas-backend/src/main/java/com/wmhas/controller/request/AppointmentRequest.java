package com.wmhas.controller.request;

import lombok.Data;
@Data public class AppointmentRequest {
    String date;
    String department;
    String doctor;
    String status;
}
