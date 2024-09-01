package io.github.xiaoso456.thread.threadpool.reject;

import java.util.concurrent.*;

public class DiscardPolicyReject {
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 2;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(4);
        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                blockingQueue,
                threadFactory,
                discardPolicy);
        for (int i = 0; i < 10; i++) {
            // 当前线程池数量为2，队列为4。也就是能处理6个任务
            // 后续任务直接被丢弃，无异常抛出
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
