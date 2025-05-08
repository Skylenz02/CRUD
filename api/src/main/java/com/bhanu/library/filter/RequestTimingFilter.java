package com.bhanu.library.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(3)
public class RequestTimingFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestTimingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();

        exchange.getResponse().beforeCommit(() -> {
            long duration = System.currentTimeMillis() - startTime;
            exchange.getResponse().getHeaders().add("X-Response-Time", duration + "ms");

            log.info("⏱ {} {} took {} ms",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getPath(),
                    duration);
            return Mono.empty();
        });

        return chain.filter(exchange);
    }
}
