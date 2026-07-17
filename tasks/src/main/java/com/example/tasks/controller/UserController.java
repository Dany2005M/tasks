package com.example.tasks.controller;


import com.example.tasks.dto.UserCredentialsDTO;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.UserResponseDTO;
import com.example.tasks.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/is-internal/{isInternal}")
    public List<UserDTO> getUsersByIsInternal(@PathVariable Boolean isInternal) {
        return userService.getUserByIsInternal(isInternal);
    }


    @PostMapping
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping("/multiple-users")
    public List<UserDTO> createUsers(@RequestBody @Valid List<UserDTO> userDTOs) {
        return userService.createUsers(userDTOs);
    }

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody @Valid UserCredentialsDTO credentials){
        return userService.login(credentials);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @DeleteMapping("/{id}")
    public List<UserDTO> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
