## 简介
该项目探究Java常用类的实践使用，包括：
+ 线程池
+ ...
### ThreadPool
线程池是多线程的一种实现方式，通过线程池可以减少创建和销毁线程的次数，降低线程的创建和销毁的开销，提高线程的效率。
#### ThreadPoolFactory
线程池工厂类，用于创建线程池，推荐在项目中使用ThreadPoolFactory创建线程池，可以给线程命名

线程池工厂类需要实现`ThreadFactory`接口
##### 默认线程工厂DefaultThreadFactory
默认线程工厂实现了`ThreadFactory`接口，可以给线程命名，默认线程工厂的线程名称为`pool-n-thread-m`，其中n表示线程池的编号，m表示线程的编号

使用 `Executors.defaultThreadFactory()`获取

##### 自定义 ThreadFactory

参考 `CustomerThreadFactory`类代码

```java
package io.github.xiaoso456.thread.threadpool.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoThreadFactory implements ThreadFactory {

    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("Thread-" + threadNumber.getAndIncrement());
        return thread;
    }
}

```

##### Guava线程工厂（推荐）

引入guava依赖包

```xml
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>32.0.1-jre</version>
        </dependency>
```

然后设置前缀格式就可以使用了

```java
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("my-thread-pool-%d")
                .build();
```

#### 队列

线程的队列类型是`BlockingQueue<Runnable>`，队列要求多线程安全，常见的实现类有：

+ ArrayBlockingQueue

  有界队列

+ LinkedBlockingQueue

  指定容量时，有界；不指定时无界

+ SynchronousQueue

  同步提交队列，每次插入会等待一个删除

+ PriorityBlockingQueue

  优先级队列，队列元素会根据优先级排序

+ DelayQueue

  无界队列,基于PriorityQueue实现，入队元素类需要实现Delayed接口

  ```java
  public interface Delayed extends Comparable<Delayed> {
      //getDelay 方法返回的是“还剩下多长的延迟时间才会被执行”，
      //如果返回 0 或者负数则代表任务已过期。
      //元素会根据延迟时间的长短被放到队列的不同位置，越靠近队列头代表越早过期。
      long getDelay(TimeUnit unit);
  }
  ```


#### 拒绝策略

线程池常用拒接策略有：

+ AbortPolicy（默认策略）

  当任务无法被线程池执行时，抛出 RejectedExecutionException 异常

+ CallerRunsPolicy

  当任务无法被线程池执行时，会直接在调用者线程中运行这个任务。如果调用者线程正在执行一个任务，则会创建一个新线程来执行被拒绝的任务

 + DiscardPolicy

   当任务无法被线程池执行时，任务将被直接抛弃，不抛出异常

 + DiscardOldestPolicy

   当任务无法被线程池执行时，线程池会丢弃队列最旧的未处理任务，然后重新提交该任务

   

## 参考

[Java基础：线程池拒绝策略详解_java线程池拒绝策略-CSDN博客](https://blog.csdn.net/qq_47183158/article/details/140931384)
