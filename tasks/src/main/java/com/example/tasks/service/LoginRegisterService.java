package com.example.tasks.service;


import com.example.tasks.domain.User;
import com.example.tasks.dto.UserCredentialsDTO;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.util.security.Credential;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class LoginRegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${jwt.secret: }") String jwtSecret;
    @Value("${jwt.expiration.ms: }") String jwtExpiration;

    public ResponseEntity<String> login(UserCredentialsDTO userCredentialsDTO) throws JoseException {
        userCredentialsDTO.setEmail(new String(Base64.getDecoder().decode(userCredentialsDTO.getEmail())));
        userCredentialsDTO.setPassword(new String(Base64.getDecoder().decode(userCredentialsDTO.getPassword())));

        String hashPassword = Credential.MD5.digest(userCredentialsDTO.getPassword()).replaceFirst("MD5:", "").toLowerCase();
        User dbPassword = userRepository.findByEmail(userCredentialsDTO.getEmail())
                .orElse(null);

        if(dbPassword != null && dbPassword.getPassword().equals(hashPassword)) {
            return ResponseEntity.ok(createJWToken(userCredentialsDTO.getEmail(), dbPassword.getUsername()));
        }
        else{
            return ResponseEntity.status(401).build();
        }

    }

    public ResponseEntity<String> register(UserDTO userDTO) throws JoseException {
        String decodedEmail = new String(Base64.getDecoder().decode(userDTO.getEmail()));
        String decodedPassword = new String(Base64.getDecoder().decode(userDTO.getPassword()));

        if(userRepository.findByEmail(decodedEmail).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        String hashPassword = Credential.MD5.digest(decodedPassword).replaceFirst("MD5:", "").toLowerCase();
        User newUser = userMapper.toEntity(userDTO);

        newUser.setPassword(hashPassword);
        newUser.setEmail(decodedEmail);
        newUser.setUsername(userDTO.getUsername());
        newUser.setBirthDate(userDTO.getBirthDate());

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    private String createJWToken(String email, String username) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture((float) Long.parseLong(jwtExpiration) / (1000 * 60));
        claims.setSubject(email);
        claims.setStringClaim("username", username);
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return jws.getCompactSerialization();
    }
}
