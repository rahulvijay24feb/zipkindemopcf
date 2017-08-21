package com.cvs.pcfdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/pcfdemo")
	public String getDetails(){
		return "This is a PCF Demo!";
	}
	
    @RequestMapping("/Hello")
    public String hello() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/hello", String.class);
        return "Hello + " + response.getBody();
    }
}
