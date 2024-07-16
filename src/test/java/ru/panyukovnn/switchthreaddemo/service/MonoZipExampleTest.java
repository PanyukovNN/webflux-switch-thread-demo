package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
class MonoZipExampleTest {

    @Test
    void monoZip() {
        Scheduler singleScheduler = Schedulers.newSingle("single");
        Scheduler singleScheduler2 = Schedulers.newSingle("single2");
        Scheduler singleScheduler3 = Schedulers.newSingle("single3");

        Mono<Integer> mono1 = Mono.just(1)
            .subscribeOn(singleScheduler);
        Mono<Integer> mono2 = Mono.just(2)
            .subscribeOn(singleScheduler2);
        Mono<Integer> mono3 = Mono.just(3)
            .subscribeOn(singleScheduler3);

        Mono.zip(mono1, mono2, mono3)
            .doOnNext(it -> log.info("После zip: " + it)) // в потоке последнего издателя
            .block();
    }

    @Test
    void fluxConcat() {
        Scheduler singleScheduler = Schedulers.newSingle("single");
        Scheduler singleScheduler2 = Schedulers.newSingle("single2");

        Flux<Integer> flux1 = Flux.range(0, 2)
            .subscribeOn(singleScheduler);
        Flux<Integer> flux2 = Flux.range(2, 2)
            .subscribeOn(singleScheduler2);

        Flux.concat(flux1, flux2)
            .doOnNext(it -> log.info("После concat: " + it)) // каждый в потоке своего издателя
            .blockLast();
    }
}
