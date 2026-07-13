package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    public List<UserDTO> getAllUsers() {
        log.info("Users retrieved!");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        log.info("User with id {} retrieved!", id);

        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElse(null);
    }

    public List<UserDTO> getUserByIsInternal(Boolean isInternal) {
        log.info("Users with isInternal {} retrieved!", isInternal);

        return userRepository.findByIsInternal(isInternal)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO createUser(UserDTO userDTO) {
        log.info("User created!");

        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public List<UserDTO> createUsers(List<UserDTO> userDTOs) {
        log.info("Users created!");

        List<User> users = userDTOs.stream()
                .map(userMapper::toEntity)
                .toList();

        userRepository.saveAll(users);

        return getAllUsers();
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("User updated!");

        User existingUser = userRepository.findById(id)
                .orElse(null);

        User savedUser = null;

        if(existingUser != null){
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setBirthDate(userDTO.getBirthDate());
            existingUser.setIsInternal(userDTO.getIsInternal());
            existingUser.setCreatedByFullName(userDTO.getCreatedByFullName());

            savedUser = userRepository.save(existingUser);
        }

        return userMapper.toDTO(savedUser);
    }

    public void deleteAllUsers() {
        log.info("Users deleted!");

        userRepository.deleteAll();
    }

    public List<UserDTO> deleteUserById(Long id) {
        log.info("User with id {} deleted!", id);

        userRepository.deleteById(id);

        return getAllUsers();
    }



}
