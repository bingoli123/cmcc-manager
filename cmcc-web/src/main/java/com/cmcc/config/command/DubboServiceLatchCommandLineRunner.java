package com.cmcc.config.command;

import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.CountDownLatch;

/**
 * DubboServiceLatchCommandLineRunner
 *
 * @author YL-S
 * @Create 2018/11/15
 */
public class DubboServiceLatchCommandLineRunner implements CommandLineRunner {
    public DubboServiceLatchCommandLineRunner() {
    }

    public void run(String... args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}
