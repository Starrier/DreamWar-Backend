package org.starrier.dreamwar.config.multithread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Starrier
 * @date 2019/1/25.
 */
@Slf4j
@Component
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private void showThreadPoolInfo(String prefix) {

        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(), prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(@NotNull Runnable task) {
        showThreadPoolInfo(" do execute");
        super.execute(task);
    }


    @Override
    public void execute(@NotNull Runnable task, long startTimeout) {
        showThreadPoolInfo("do execute");
        super.execute(task, startTimeout);
    }


    @Override
    public Future<?> submit(@NotNull Runnable task) {
        showThreadPoolInfo("do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(@NotNull Callable<T> task) {
        showThreadPoolInfo("do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(@NotNull Runnable task) {
        showThreadPoolInfo(" do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(@NotNull Callable<T> task) {
        showThreadPoolInfo("do submitListenable");
        return super.submitListenable(task);
    }

}
