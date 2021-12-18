package com.hims.controller.request;

import lombok.Data;
@Data public class AppointmentRequest {
    String date;
    String department;
    String doctor;
    String status;
}
