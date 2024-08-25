package com.taskmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/submission")
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("Welcome to submission service", HttpStatus.OK);
    }
}
