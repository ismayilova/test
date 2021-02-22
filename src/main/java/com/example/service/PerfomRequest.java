package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class PerfomRequest {

    RestTemplate restTemplate = new RestTemplate();
    String url
            = "http://localhost:8080/test/insert";


    public void getRequst(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);
        System.out.println(responseEntity.toString());
    }
}
