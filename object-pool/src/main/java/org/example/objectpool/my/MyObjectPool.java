package org.example.objectpool.my;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 对象池
 *
 * @author lch
 * @date 2023年03月13日 23:09
 */
public class MyObjectPool<E> {

    /**
     * 默认最大计数
     */
    private static final int DEFAULT_MAX_COUNT = 100;
    /**
     * 正在被使用的对象的集合，已被同步
     */
    private Set<E> activeSet = Collections.synchronizedSet(new HashSet<>());
    /**
     * 空闲的对象的集合，已被同步
     */
    private Set<E> idleSet = Collections.synchronizedSet(new HashSet<>());

    /**
     * 最大对象数，默认值100
     */
    private Integer maxCount = DEFAULT_MAX_COUNT;

    /**
     * 线程等待监视器
     */
    private final Object lock = new Object();

    /**
     * 对象池
     *
     * @param maxCount 最大计数
     */
    public MyObjectPool(Integer maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * 获取对象
     *
     * @param e e
     * @return {@link E}
     * @throws Exception 异常
     */
    public synchronized <T> E borrowObject(Class<E> e) throws Exception {
        E object = null;
        if (idleSet.size() > 0) {
            Iterator<E> iterator = idleSet.iterator();
            object = iterator.next();
            System.out.println("复用老对象...");
        }
        if (object != null) {
            idleSet.remove(object);
            activeSet.add(object);
        }// 如果没有空闲对象
        else {
            int size = activeSet.size() + idleSet.size();
            // 如果size超出对象池容量，则阻塞当前线程，直到阻塞完毕(returnObject方法用notify唤起)后，再次尝试
            if (size >= maxCount) {
                synchronized (lock) {
                    System.out.println("池中无对象，线程等待");
                    lock.wait();
                }
                // 递归
                return this.borrowObject(e);
            }
            // 如果没超出容量，则new对象返回
            else {
                System.out.println("创建新的对象...");
                // 反射调用类中的init方法创建对象
                Method method = e.getMethod("init");
                object = (E) method.invoke(e);
                activeSet.add(object);
            }

        }
        System.out.println("池中总对象数:" + (activeSet.size() + idleSet.size()) +
                ", 使用中:" + activeSet.size() +
                ", 空闲中:" + idleSet.size());
        return object;
    }

    /**
     * 对象使用完毕，归还对象池
     *
     * @param obj obj
     */
    public synchronized void returnObject(E obj) {
        if (obj != null) {
            activeSet.remove(obj);
            idleSet.add(obj);
            synchronized (lock) {
                // LOGGER.info("唤醒等待线程");
                lock.notify();
            }
        }
    }

}

class MyObjectPoolTest {
    public static void main(String[] args) throws Exception {
        //初始化对象池
        MyObjectPool<Money> pool = new MyObjectPool<>(1000);

        // 10个线程并发操作对象池
        for (int i = 0; i < 10; i++) {
            TestThread testThread = new TestThread(pool);
            new Thread(testThread)
                    .start();
        }
    }
}

class TestThread implements Runnable {
    private MyObjectPool<Money> objectPool;

    public TestThread(MyObjectPool<Money> pool) {
        this.objectPool = pool;
    }

    @Override
    public void run() {
        try {
            // 每个线程要大量操作对象池中的对象(1000次)
            for (int i = 0; i < 1000; i++) {
                // 获取对象
                Money money = objectPool.borrowObject(Money.class);
                // 使用对象
                System.out.println(money);
                // 归还对象
                objectPool.returnObject(money);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Money {
    public static Money init() {
        // 假设对象new非常耗时
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Money("USD", new BigDecimal("1"));
    }

    private String type;
    private BigDecimal amount;

    public Money(String type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
