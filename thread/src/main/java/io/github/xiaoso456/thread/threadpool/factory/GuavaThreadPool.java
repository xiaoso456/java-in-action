package io.github.xiaoso456.thread.threadpool.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GuavaThreadPool {
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 16;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(16);
        // 线程工厂
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("my-thread-pool-%d")
                .build();
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
