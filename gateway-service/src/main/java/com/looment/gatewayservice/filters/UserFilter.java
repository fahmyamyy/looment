package com.looment.gatewayservice.filters;

import com.looment.gatewayservice.dtos.ResponseDTO;
import com.looment.gatewayservice.dtos.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserFilter implements GatewayFilter {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request =  exchange.getRequest();
        final List<String> apiEndpoints = List.of("/api/v1/auth/register", "/api/v1/auth/login");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));
        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            String newToken = token.split(" ")[1];
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(newToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ResponseDTO> response = restTemplate.exchange("http://localhost:8081/api/v1/auth/info", HttpMethod.GET, entity, ResponseDTO.class);
            UserDTO userDTO = objectMapper.convertValue(response.getBody().getData(), UserDTO.class);
//            exchange.getRequest().mutate().header("role", userDTO.getRole()).build();
            exchange.getRequest().mutate().header("id", String.valueOf(userDTO.getId())).build();
        }
        return chain.filter(exchange);
    }
}
