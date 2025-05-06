package com.bhanu.library.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(1)
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("📥 " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getPath());
        return chain.filter(exchange)
                .doOnSuccess(done -> System.out.println("📤 Response sent for: " + exchange.getRequest().getPath()));
    }
}
