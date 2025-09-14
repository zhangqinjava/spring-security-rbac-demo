package com.example.securitydemo.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory implements ThreadFactory {
    public MyThreadFactory() {
    }

    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to
     * create a thread is rejected
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "thread-"+System.currentTimeMillis() + threadNumber.getAndIncrement());
        t.setDaemon(false);
        return t;
    }
}
