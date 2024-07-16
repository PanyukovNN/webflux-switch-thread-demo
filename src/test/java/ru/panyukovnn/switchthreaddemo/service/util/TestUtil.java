package ru.panyukovnn.switchthreaddemo.service.util;

import lombok.SneakyThrows;

public class TestUtil {

    @SneakyThrows
    public static void sleep(int millis) {
        Thread.sleep(millis);
    }
}
