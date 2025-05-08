package com.bhanu.library.service.impl;

import com.bhanu.library.model.User;
import com.bhanu.library.repository.UserRepository;
import com.bhanu.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
