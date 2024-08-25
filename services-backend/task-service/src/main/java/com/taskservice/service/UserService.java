package com.taskservice.service;

import com.taskservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:5001")
public interface UserService {

    @GetMapping("/api/user/profile")
    public UserDTO getUserProfile(@RequestHeader("Authorization") String jwt);
}
