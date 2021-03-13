package com.frozendo.learn.springsecurity.service;

import com.frozendo.learn.springsecurity.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceRest {

    private final RestTemplate restTemplate;

    @Autowired
    public StudentServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StudentDTO> listStudent() {
        var response =
                restTemplate.exchange("http://localhost:9090/student",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentDTO>>() {}
                );

        if (HttpStatus.OK.equals(response.getStatusCode())) {
            return response.getBody();
        }
        return List.of();
    }

    public Optional<StudentDTO> getStudent(Integer id) {
        var url = String.format("http://localhost:9090/student/%d", id);
        var response = restTemplate.getForEntity(url, StudentDTO.class);

        if (Objects.nonNull(response.getBody()) &&
                HttpStatus.OK.equals(response.getStatusCode())) {
            return Optional.of(response.getBody());
        }
        return Optional.empty();
    }
}
