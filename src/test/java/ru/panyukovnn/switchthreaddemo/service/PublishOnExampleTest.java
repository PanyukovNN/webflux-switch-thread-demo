package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static ru.panyukovnn.switchthreaddemo.service.util.TestUtil.sleep;

@Slf4j
class PublishOnExampleTest {

    @Test
    void publishOnExample() {
        Scheduler singleScheduler = Schedulers.newSingle("single-scheduler");

        Mono.just(1)
            .doOnNext(it -> log.info("До publishOn"))
            .publishOn(singleScheduler)
            .doOnNext(it -> log.info("После publishOn"))
            // 10 других операторов
            .subscribe();
    }

    @Test
    void publishOn_sequentialProcessing() {
        Scheduler elasticScheduler = Schedulers.newBoundedElastic(10, 10, "bounded-elastic");

        Flux.fromIterable(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)) // все 10 элементов сразу попадут в буффер publishOn
            .doOnNext(it -> log.info("До publishOn: {}", it))
            .publishOn(elasticScheduler)
            .doOnNext(it -> { // будут обрабатываться по одному, несмотря на наличие свободных потоков у планировщика
                sleep(1000);

                log.info("После publishOn: {}", it);
            })
            .blockLast();
    }
}