package com.bhanu.library.service;

import com.bhanu.library.model.User;
import reactor.core.publisher.Flux;

public interface UserService {
    Flux<User> getAllUsers();
}
