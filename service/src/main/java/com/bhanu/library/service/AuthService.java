package com.bhanu.library.service;

import com.bhanu.library.model.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> register(User user);
    Mono<String> login(User user);
}
