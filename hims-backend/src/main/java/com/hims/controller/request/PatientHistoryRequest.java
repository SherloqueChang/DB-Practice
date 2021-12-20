package com.hims.controller.request;

import lombok.Data;
@Data public class PatientHistoryRequest {
    String date;
    String doctor;
    String issue;
    String diagnosed_disease;
    String allergens;
}
