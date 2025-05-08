package com.bhanu.library.controller;

import com.bhanu.library.dto.DogImageResponse;
import com.bhanu.library.service.DogImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class DogImageController {

    private final DogImageService dogImageService;

    @GetMapping("/dog")
    public Mono<DogImageResponse> getDogImage() {
        return dogImageService.getRandomDogImage();
    }
}
