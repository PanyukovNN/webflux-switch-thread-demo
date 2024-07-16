package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import static ru.panyukovnn.switchthreaddemo.service.util.TestUtil.sleep;

@Slf4j
class ParallelExampleTest {

    @Test
    void parallel_example() {
        Scheduler parallelScheduler = Schedulers.newParallel("paralell-scheduler");

        Flux.range(0, 100)
            .map(it -> it + 1)
            .parallel()
            .runOn(parallelScheduler)
            .doOnNext(it -> {
                sleep(1000);

                log.info("После parallel: {}", it);
            })
            .then()
            .block();
    }

    @Test
    void parallel_withDefinedParallelism() {
        Scheduler parallelScheduler = Schedulers.newParallel("paralell-scheduler");

        Flux.range(0, 100)
            .map(it -> it + 1)
            .parallel(4)
            .runOn(parallelScheduler)
            .doOnNext(it -> {
                sleep(1000);

                log.info("После parallel: {}", it);
            })
            .then()
            .block();
    }

}