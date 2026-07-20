package com.example.tasks.controller;


import com.example.tasks.dto.UserCredentialsDTO;
import com.example.tasks.service.LoginRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks/login")
public class LoginController {
    private final LoginRegisterService loginRegisterService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) throws JoseException {
        return loginRegisterService.login(userCredentialsDTO);
    }

}
