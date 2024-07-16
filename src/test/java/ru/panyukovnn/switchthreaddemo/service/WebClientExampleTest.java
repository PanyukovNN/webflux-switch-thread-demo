package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
class WebClientExampleTest {

    private final WebClient webClient = WebClient.builder().build();

    @Test
    void webClient_example() {
        Mono.just(1)
            .map(it -> it + 1)
            .doOnNext(it -> log.info("До вызова webClient'а"))
            .flatMap(it -> webClient.get()
                .uri("https://httpbin.org/get")
                .retrieve()
                .bodyToMono(String.class))
            .doOnNext(it -> log.info("После вызова webClient'а"))
            .block();
    }

}