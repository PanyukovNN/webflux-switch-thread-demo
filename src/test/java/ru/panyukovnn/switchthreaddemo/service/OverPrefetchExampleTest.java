package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import static ru.panyukovnn.switchthreaddemo.service.util.TestUtil.sleep;

@Slf4j
class OverPrefetchExampleTest {

    @Test
    void publishOn_overPrefetch() {
        Scheduler elasticScheduler = Schedulers.newBoundedElastic(10, 10, "bounded-elastic");

        Flux.range(1, 10)
            .doOnNext(it -> log.info("До publishOn: {}", it))
            .publishOn(elasticScheduler, 2) // Пока не закончится обработка prefetch количества элементов, следующие не будут взяты в работу
            .doOnNext(it -> {
                sleep(1000);

                log.info("После publishOn: {}", it);
            })
            .blockLast();
    }
}
