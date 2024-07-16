package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
class SubscribeOnExampleTest {

    @Test
    void subscribeOn_example() {
        Scheduler singleScheduler = Schedulers.newSingle("single-scheduler");

        Mono.defer(() -> {
                log.info("Внутри Mono.defer");
                return Mono.just(1);
            })
            .doOnNext(it -> log.info("Внутри subscribeOn"))
            .subscribeOn(singleScheduler)
            .block();
    }

    @Test
    void subscribeOn_severalOperators() {
        Scheduler singleScheduler = Schedulers.newSingle("single-scheduler");
        Scheduler singleScheduler2 = Schedulers.newSingle("single-scheduler2");

        Mono.just(1)
            .doOnNext(it -> log.info("Внутри subscribeOn"))
            .subscribeOn(singleScheduler2)
            .doOnNext(it -> log.info("Внутри subscribeOn"))
            .subscribeOn(singleScheduler)
            .block();
    }
}