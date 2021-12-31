package com.wmhas.controller.request;
import lombok.Data;

@Data public class AppointmentQueryRequest {
    String id;
    String doctor;
    String availableAppointment;
}
