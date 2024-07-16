package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.panyukovnn.switchthreaddemo.service.util.TestUtil;

@Slf4j
class DBPractice {


    private final Scheduler elasticScheduler = Schedulers.newBoundedElastic(Runtime.getRuntime().availableProcessors() * 10, 100_000, "elastic-scheduler");
    private final Scheduler dbScheduler = Schedulers.newBoundedElastic(1, 10, "db-scheduler");

    @Test
    void webClient_example() {
        Mono.just(new ClientRequest())
            .publishOn(dbScheduler)
            .doOnNext(it -> {
                dbCall();
                log.info("Выполняем запрос в бд");
            })
            .publishOn(elasticScheduler)
            .doOnNext(it -> log.info("После обращения в бд"))
            .block();
    }

    // runs in transaction
    private void dbCall() {
        TestUtil.sleep(1000);
    }

    record ClientRequest() {}
}