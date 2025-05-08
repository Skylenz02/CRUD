package com.bhanu.library.service;

import com.bhanu.library.dto.BitcoinPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BitcoinService {

    private final WebClient.Builder webClientBuilder;

    public Mono<BitcoinPriceResponse> getBitcoinPrice() {
        return webClientBuilder.build()
                .get()
                .uri("https://api.coindesk.com/v1/bpi/currentprice.json")
                .retrieve()  // make the request
                .bodyToMono(BitcoinPriceResponse.class);
    }
}
