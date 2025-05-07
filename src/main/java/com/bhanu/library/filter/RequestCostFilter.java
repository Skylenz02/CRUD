package com.bhanu.library.filter;

import com.bhanu.library.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(3) // After LoggingFilter and HeaderCheckFilter
public class RequestCostFilter implements WebFilter {

    private final Map<String, Integer> userCostMap = new ConcurrentHashMap<>();

    @Autowired
    private JwtUtil jwtUtil;

    private static final int MAX_COST = 100;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getPath().value();

        String token = extractToken(exchange);
        String username = token != null && jwtUtil.validateToken(token)
                ? jwtUtil.extractUsername(token)
                : "anonymous";

        int cost = calculateRequestCost(path, method);

        int updatedCost = userCostMap.getOrDefault(username, 0) + cost;

        if (updatedCost > MAX_COST) {
            System.out.println("❌ Cost limit exceeded for: " + username + " | Used: " + updatedCost);
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }

        userCostMap.put(username, updatedCost);

        System.out.println("💰 " + username + " used " + cost + " units | Total: " + updatedCost + " | " + method + " " + path);

        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private int calculateRequestCost(String path, String method) {
        if (path.startsWith("/books/stats")) return 10;
        if (path.startsWith("/books") && method.equals("POST")) return 5;
        if (path.startsWith("/books") && method.equals("DELETE")) return 4;
        if (path.startsWith("/books") && method.equals("PUT")) return 3;
        if (path.startsWith("/books") && method.equals("GET")) return 1;
        return 1;
    }
}
