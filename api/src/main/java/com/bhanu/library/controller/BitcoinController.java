package com.bhanu.library.controller;

import com.bhanu.library.dto.BitcoinPriceResponse;
import com.bhanu.library.service.BitcoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class BitcoinController {

    private final BitcoinService bitcoinService;

    @GetMapping("/bitcoin")
    public Mono<BitcoinPriceResponse> getBitcoinPrice() {
        return bitcoinService.getBitcoinPrice();
    }
}
