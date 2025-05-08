package com.bhanu.library.service;

import com.bhanu.library.dto.DogImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DogImageService {

    private final WebClient.Builder webClientBuilder;

    public Mono<DogImageResponse> getRandomDogImage() {
        return webClientBuilder.build()
                .get()
                .uri("https://dog.ceo/api/breeds/image/random")
                .retrieve()
                .bodyToMono(DogImageResponse.class);
    }
}
