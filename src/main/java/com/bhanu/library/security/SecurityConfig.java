package com.bhanu.library.security;

import com.bhanu.library.filter.HeaderCheckFilter;
import com.bhanu.library.filter.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            JwtSecurityContextRepository jwtRepo,
                                                            LoggingFilter loggingFilter,
                                                            HeaderCheckFilter headerCheckFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(jwtRepo)
                .addFilterAt(loggingFilter, SecurityWebFiltersOrder.FIRST)
                .addFilterAfter(headerCheckFilter, SecurityWebFiltersOrder.FIRST)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}