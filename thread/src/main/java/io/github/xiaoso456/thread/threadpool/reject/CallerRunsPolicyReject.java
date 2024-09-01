package io.github.xiaoso456.thread.threadpool.reject;

import java.util.concurrent.*;

public class CallerRunsPolicyReject {
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 2;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(4);
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                blockingQueue,
                threadFactory,
                callerRunsPolicy);
        for (int i = 0; i < 10; i++) {
            // 当前线程池数量为2，队列为4。也就是能处理6个任务
            // 第7个任务由主线程执行，阻塞主线程1s，此时线程池还未释放，第8个任务由主线程执行，阻塞主线程1s。
            // 执行完第8个任务后，后续任务均被提交到线程池继续执行
            try {
                int finalI = i;
                threadPoolExecutor.execute(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + "hello"+ finalI);
                });
            }catch (RejectedExecutionException rejectedExecutionException){
                System.out.println("任务"+i+"被拒绝执行");
            }


        }

        threadPoolExecutor.shutdown();
    }
}
