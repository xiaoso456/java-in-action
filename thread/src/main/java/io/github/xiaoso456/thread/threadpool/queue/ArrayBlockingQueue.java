package io.github.xiaoso456.thread.threadpool.queue;

import java.util.concurrent.*;

public class ArrayBlockingQueue {
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 16;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        java.util.concurrent.ArrayBlockingQueue<Runnable> blockingQueue = new java.util.concurrent.ArrayBlockingQueue<>(16);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                blockingQueue,
                threadFactory,
                abortPolicy);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + "hello");
            });

        }

        threadPoolExecutor.shutdown();
    }
}
