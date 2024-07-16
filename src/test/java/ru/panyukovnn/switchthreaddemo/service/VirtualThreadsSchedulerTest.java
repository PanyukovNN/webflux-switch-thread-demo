package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
class VirtualThreadsSchedulerTest {

    @Test
    void virtualSchedulerExample() {
        ExecutorService virtualExecutorService = Executors.newVirtualThreadPerTaskExecutor();

        Scheduler virtualScheduler = Schedulers.fromExecutorService(virtualExecutorService, "virtual"); // не работает

        // -Dreactor.schedulers.defaultBoundedElasticOnVirtualThreads=true

        Mono.just(2)
            .map(it -> it + 1)
            .filter(it -> it >= 3)
            .doOnNext(ignore -> log.info("complete " + Thread.currentThread().threadId()))
//            .subscribeOn(virtualScheduler)
            .subscribeOn(Schedulers.boundedElastic())
            .block();
    }
}
