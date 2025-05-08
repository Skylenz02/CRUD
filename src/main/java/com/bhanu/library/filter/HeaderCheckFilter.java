package com.bhanu.library.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(2)
public class HeaderCheckFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(HeaderCheckFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.startsWith("/auth")) {
            log.debug("🔓 Public endpoint, skipping header check: {}", path);
            return chain.filter(exchange);
        }

        if (!exchange.getRequest().getHeaders().containsKey("X-Request-ID")) {
            log.warn("❌ Missing X-Request-ID header. Blocking request: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        log.info("✅ Header present. Continuing request: {}", path);
        return chain.filter(exchange);
    }
}
