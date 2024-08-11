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

#### 队列 TODO

#### 拒绝策略 TODO



## 参考
