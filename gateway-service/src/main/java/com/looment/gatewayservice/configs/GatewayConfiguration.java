package com.looment.gatewayservice.configs;

import com.looment.gatewayservice.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    private final UserFilter filter;

    @Value("${port.auth}")
    private String PORT_AUTH;
    @Value("${port.user}")
    private String PORT_USER;
    @Value("${port.post}")
    private String PORT_POST;
    @Value("${port.upload}")
    private String PORT_UPLOAD;
    @Value("${port.messaging}")
    private String PORT_MESSAGING;

    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/user/**")
//                        .and().method("POST", "GET", "PATCH", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_USER))
                .route(r -> r.path("/api/v1/user/user-follow/**")
//                        .and().method("POST", "GET", "PATCH", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_USER))
                .route(r -> r.path("/api/v1/post/**")
//                        .and().method("POST", "GET", "PATCH", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_POST))
                .route(r -> r.path("/api/v1/messaging/**")
//                        .and().method("POST", "GET", "PATCH", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_MESSAGING))
                .route(r -> r.path("/api/v1/upload/**")
//                        .and().method("POST")
                        .filters(f -> f.filter(filter))
                        .uri(PORT_UPLOAD))
                .route(r -> r.path("/api/v1/auth/**")
                        .uri(PORT_AUTH))
                .build();
    }
}
