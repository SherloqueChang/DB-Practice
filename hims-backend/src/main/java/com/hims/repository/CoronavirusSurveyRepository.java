package com.hims.repository;

import com.hims.domain.CoronavirusSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CoronavirusSurveyRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(CoronavirusSurvey coronavirusSurvey) {
        String sql = "insert into coronavirus_survey_form(patient_id, fill_time, name, gender, idcard, phone, address, whether_14days_fever, fever_info, whether_14days_area, area_info, whether_14days_contact, contact_info, whether_14days_contact_area, contact_area_info) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, coronavirusSurvey.getPatient_id(), coronavirusSurvey.getFill_time(), coronavirusSurvey.getName(), coronavirusSurvey.getGender(), coronavirusSurvey.getIdcard(), coronavirusSurvey.getPhone(), coronavirusSurvey.getAddress(), coronavirusSurvey.getWhether_14days_fever(), coronavirusSurvey.getFever_info(), coronavirusSurvey.getWhether_14days_area(), coronavirusSurvey.getArea_info(), coronavirusSurvey.getWhether_14days_contact(), coronavirusSurvey.getContact_info(), coronavirusSurvey.getWhether_14days_contact_area(), coronavirusSurvey.getContact_area_info());
    }
}
