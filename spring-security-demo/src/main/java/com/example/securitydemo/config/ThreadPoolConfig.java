package com.example.securitydemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class ThreadPoolConfig {
    public  static  final String THREAD_POOL_NAME = "THREAD_POOL_NAME";
    public  static  final String ASSET_MANAGE_EXECUTOR_ASYNC = "ASSET_MANAGE_EXECUTOR_ASYNC";
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                10,
                20,
                10,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(50),
                new MyThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    @Bean(ASSET_MANAGE_EXECUTOR_ASYNC)
    @Primary
    public ThreadPoolTaskExecutor assetManageExecutorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //舍弃可配置
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(60);
        executor.setThreadNamePrefix("asset-manage-executor-async-");
        //满了调用线程执行，认为重要任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadFactory(new MyThreadFactory());
        //优雅停机
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //停机等待时间
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        log.info("Initialized ThreadPoolTaskExecutor: {}", ASSET_MANAGE_EXECUTOR_ASYNC);
        return executor;
    }
    @Bean(THREAD_POOL_NAME)
    @Primary
    public ThreadPoolTaskScheduler assetManageExecutorScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("asset-manage-executor-scheduler-");
        //满了调用线程执行，认为重要任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadFactory(new MyThreadFactory( ));
        //优雅停机
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //停机等待时间
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        log.info("Initialized ThreadPoolTaskScheduler: {}", THREAD_POOL_NAME);
        return executor;
    }
}
