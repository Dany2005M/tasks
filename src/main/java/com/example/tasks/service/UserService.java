package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        log.info("Users retrieved!");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("User created!");

        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

}
