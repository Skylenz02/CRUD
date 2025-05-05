package com.bhanu.library.controller;

import com.bhanu.library.model.User;
import com.bhanu.library.repository.UserRepository;
import com.bhanu.library.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public Mono<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).map(u -> "User registered");
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(dbUser -> passwordEncoder.matches(user.getPassword(), dbUser.getPassword()))
                .map(dbUser -> jwtUtil.generateToken(dbUser.getUsername()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }
}
