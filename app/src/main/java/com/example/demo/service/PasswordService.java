package com.example.demo.service;

import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Boolean validateUserCreation(CreateUserRequest createUserRequest) {

        String password = createUserRequest.getPassword();
        String confirmedPassword = createUserRequest.getConfirmedPassword();

        return password.length() >= 7 && password.equals(confirmedPassword);
    }

    public String encodePassword(String password) {
        return this.bCryptPasswordEncoder.encode(password);
    }
}
