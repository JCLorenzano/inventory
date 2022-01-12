package com.tutorial.keycloakbackend.controller;

import com.tutorial.keycloakbackend.dto.ResponseMessage;
import com.tutorial.keycloakbackend.model.User;
import com.tutorial.keycloakbackend.service.KeycloakService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/inventory")
@CrossOrigin
public class UserController {
    
    @Autowired
    private KeycloakService keycloakService;

    @PostMapping("/user/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody User user) {
        Object[] obj = keycloakService.createUser(user);
        int status = (int)obj[0];
        ResponseMessage message = (ResponseMessage)obj[1];
        return ResponseEntity.status(status).body(message);

    }

}
