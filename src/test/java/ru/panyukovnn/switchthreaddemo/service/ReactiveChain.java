package ru.panyukovnn.switchthreaddemo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
class ReactiveChain {

    @Test
    void chainExample() {
        Mono.just(2)
            .map(it -> it + 1)
            .filter(it -> it >= 3)
            .doOnNext(it -> log.info("Внути реактивной цепочки"))
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();

        log.info("После вызова реактивной цепочки");
    }

}
