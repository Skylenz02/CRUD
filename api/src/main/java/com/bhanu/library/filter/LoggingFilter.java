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
@Order(1)
public class LoggingFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("📥 " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getPath());
        return chain.filter(exchange)
                .doOnSuccess(done -> log.info("📤 Response sent for: " + exchange.getRequest().getPath()));
    }
}
