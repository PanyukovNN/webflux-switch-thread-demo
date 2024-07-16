package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
class AnotherPublisherExampleTest {

    @Test
    void anotherPublisher_example() {
        Mono.just(1)
            .doOnNext(it -> log.info("До переключения на внешнего издателя"))
            .flatMap(it -> someMono())
            .doOnNext(it -> log.info("После переключения на внешнего издателя"))
            .block();
    }

    private Mono<Integer> someMono() {
        return Mono.just(1)
            .subscribeOn(Schedulers.boundedElastic());
    }
}