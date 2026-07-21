package com.example.tasks.controller;


import com.example.tasks.dto.UserCredentialsDTO;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.service.LoginRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class LoginRegisterController {
    private final LoginRegisterService loginRegisterService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) throws JoseException {
        return loginRegisterService.login(userCredentialsDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDTO userDTO) throws JoseException {
        return loginRegisterService.register(userDTO);
    }

}
