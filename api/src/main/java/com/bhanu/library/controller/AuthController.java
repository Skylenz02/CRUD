package com.bhanu.library.controller;

import com.bhanu.library.model.User;
import com.bhanu.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<String> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody User user) {
        return authService.login(user);
    }
}
