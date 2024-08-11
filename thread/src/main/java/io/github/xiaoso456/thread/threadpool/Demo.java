package io.github.xiaoso456.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 16;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(16);
        // ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,60L, unit,blockingQueue, blockingQueue, );

    }
}
