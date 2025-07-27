package org.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @RequestMapping("/fallback/user-service")
    public Mono<String> userServiceFallback() {
        return Mono.just("User Service is taking too long to respond or is down. Please try again later");
    }

    @RequestMapping("/fallback/notification-service")
    public Mono<String> notificationServiceFallback() {
        return Mono.just("Notification Service is taking too long to respond or is down. Please try again later");
    }
}