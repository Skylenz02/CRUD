package com.bhanu.library.service.impl;

import com.bhanu.library.model.User;
import com.bhanu.library.repository.UserRepository;
import com.bhanu.library.security.JwtUtil;
import com.bhanu.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Mono<String> register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).map(saved -> "User registered");
    }

    @Override
    public Mono<String> login(User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(dbUser -> passwordEncoder.matches(user.getPassword(), dbUser.getPassword()))
                .map(dbUser -> jwtUtil.generateToken(dbUser.getUsername()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }
}
