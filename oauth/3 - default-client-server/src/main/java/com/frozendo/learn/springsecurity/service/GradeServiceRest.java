package com.frozendo.learn.springsecurity.service;

import com.frozendo.learn.springsecurity.dto.GradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GradeServiceRest {

    private final RestTemplate restTemplate;

    @Autowired
    public GradeServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GradeDTO> listGrades() {
        return execute("http://localhost:9090/grade");
    }

    public List<GradeDTO> listStudentGrades(Integer idStudent) {
        var url = String.format("http://localhost:9090/grade/student/%d", idStudent);
        return execute(url);
    }

    public List<GradeDTO> listClassGrades(Integer idClass) {
        var url = String.format("http://localhost:9090/grade/class/%d", idClass);
        return execute(url);
    }

    public List<GradeDTO> listTeacherGrades(Integer idTeacher) {
        var url = String.format("http://localhost:9090/grade/teacher/%d", idTeacher);
        return execute(url);
    }

    private List<GradeDTO> execute(String url) {
        var response =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<GradeDTO>>() {
                        }
                );

        if (HttpStatus.OK.equals(response.getStatusCode())) {
            return response.getBody();
        }
        return List.of();
    }
}
