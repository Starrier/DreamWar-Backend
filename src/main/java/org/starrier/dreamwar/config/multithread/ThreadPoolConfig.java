package org.starrier.dreamwar.config.multithread;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ThreadPoolConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        log.info(" ThreadPool is using ....");
        log.info(" Asynchronous task is about to execute ");
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("dreamwar-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
