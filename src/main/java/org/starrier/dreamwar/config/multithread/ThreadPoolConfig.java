package org.starrier.dreamwar.config.multithread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Starrier
 * @Time 2018/11/10.
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolConfig.class);

    /**
     *  设置核心线程数
     *  设置最大线程数
     *  设置队列容量
     *  设置线程活跃时间（秒）
     *  设置默认线程名称
     *  设置拒绝策略
     *  等待所有任务结束后再关闭线程池
     * */
    @Bean
    public TaskExecutor taskExecutor() {

        LOGGER.info(" ThreadPool is using ....");
        LOGGER.info(" Asynchronous task is about to execute ");

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("dreamwar-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 等待所有任务结束后再关闭线程池
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时间还么有销毁，就强制销毁，以确保应用最后能关闭，而不时阻塞
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();

        return taskExecutor;
    }
}
