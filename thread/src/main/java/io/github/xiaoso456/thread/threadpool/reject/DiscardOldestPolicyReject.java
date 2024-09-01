package io.github.xiaoso456.thread.threadpool.reject;

import java.util.concurrent.*;

public class DiscardOldestPolicyReject {
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 2;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(4);
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                blockingQueue,
                threadFactory,
                discardOldestPolicy);
        for (int i = 0; i < 10; i++) {
            // 当前线程池数量为2，队列为4。
            // 提交第7、8、9、10任务时，由于队列已满，开始丢弃最老的3、4、5、6任务
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
