package com.example.demo.service;

import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public Boolean validateUserCreation(CreateUserRequest createUserRequest) {

        String password = createUserRequest.getPassword();
        String confirmedPassword = createUserRequest.getConfirmedPassword();

        return password.length() >= 7 && password.equals(confirmedPassword);
    }
}
