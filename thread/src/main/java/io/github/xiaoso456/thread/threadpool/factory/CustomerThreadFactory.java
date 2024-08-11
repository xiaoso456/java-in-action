package io.github.xiaoso456.thread.threadpool.factory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerThreadFactory implements ThreadFactory {

    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("Thread-" + threadNumber.getAndIncrement());
        return thread;
    }

    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 16;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(16);
        ThreadFactory threadFactory = new CustomerThreadFactory();
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
