package com.inovision.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCountDownLatch {

    private class Worker implements Runnable {

        private List<String> outputScraper;
        private CountDownLatch countDownLatch;

        public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            doSomeWork();
            outputScraper.add("Counted down");
            countDownLatch.countDown();
        }

        private void doSomeWork() {
            int seconds = (int) (10 * Math.random());
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ignored) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestCountDownLatch t = new TestCountDownLatch();
        t.testBlockMain_UntilChildThreadCompletion();
    }

    public void testBlockMain_UntilChildThreadCompletion() throws InterruptedException {
        int threadCount = 5;
        final List<String> list = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker(list, countDownLatch)))
                .limit(threadCount)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();
        list.add("Latch released");

        System.out.println(list);
    }
}
