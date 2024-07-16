package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
@Service
class SubscribeAndBlockTest {

    @Test
    void subscribeExample() {
        Mono.just(1)
            .delayElement(Duration.ofMillis(1000))
            .map(it -> it + 1)
            .filter(it -> it >= 3)
            .doFinally(ignore -> log.info("complete"))
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();

        log.info("After .subscribe()");
    }

    @Test
    void blockExample() {
        Mono.just(1)
            .delayElement(Duration.ofMillis(1000))
            .map(it -> it + 1)
            .filter(it -> it >= 3)
            .doFinally(ignore -> log.info("complete"))
            .subscribeOn(Schedulers.boundedElastic())
            .block();

        log.info("After .block()");
    }

}
