package com.example.securitydemo.task;

import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.bean.vo.task.TaskConfigVo;
import com.example.securitydemo.config.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
public class DynamicTaskScheduler implements SchedulingConfigurer {
    /**
     * 注册器
     */
    private volatile ScheduledTaskRegistrar registrar;
    @Autowired
    private TaskContextService taskContextService;
    private final Map<String, ScheduledFuture> taskMap=new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier(ThreadPoolConfig.THREAD_POOL_NAME)
    private ThreadPoolTaskScheduler threadPoolExecutor;

    /**
     * 刷新定时任务框架
     * @param tasks
     */
    public void refreshTasks(List<TaskConfigVo> tasks){
        try {
            log.info("refreshTasks: " + tasks);
            // 删除已经取消任务
            taskMap.keySet().forEach(key -> {
                if (Objects.isNull(tasks) || tasks.size() == 0) {
                    taskMap.get(key).cancel(false);
                    taskMap.remove(key);
                    cronTasks.remove(key);
                    return;
                }
                tasks.forEach(task -> {
                    if (!Objects.equals(key, task.getTaskId())) {
                        taskMap.get(key).cancel(false);
                        taskMap.remove(key);
                        cronTasks.remove(key);
                        return;
                    }
                });
            });
            log.info("refreshTasks: " + taskMap);
            // 添加新任务、更改执行规则任务
            tasks.forEach(item -> {
                String expression = item.getExpression();
                // 任务表达式为空则跳过
                if (StringUtils.isEmpty(expression)) {
                    return;
                }

                // 任务已存在并且表达式未发生变化则跳过
                if (taskMap.containsKey(item.getTaskId()) && cronTasks.get(item.getTaskId()).getExpression().equals(expression)) {
                    return;
                }

                // 任务执行时间发生了变化，则删除该任务
                if (taskMap.containsKey(item.getTaskId())) {
                    taskMap.get(item.getTaskId()).cancel(false);
                    taskMap.remove(item.getTaskId());
                    cronTasks.remove(item.getTaskId());
                }

                CronTask task = new CronTask(() -> {
                    // 执行业务逻辑
                    try {
                        log.info("====执行单个任务，任务ID【{}】执行规则【{}】=======", item.getTaskId(), item.getExpression());
                        taskContextService.execute(item);
                    } catch (Exception e) {
                        log.error("执行任务异常，异常信息：", e);
                    }
                }, expression);
                ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
                cronTasks.put(item.getTaskId(), task);
                taskMap.put(item.getTaskId(), future);
            });
        }catch (Exception e){
            log.error("refreshTasks fail:【{}】: " ,e.getMessage() );
        }

    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(threadPoolExecutor);
        this.registrar = taskRegistrar;
    }
    @PreDestroy
    public void destroy(){
        this.registrar.destroy();
    }
    public void triggerTask(TaskConfigDto task){
        TaskConfigVo taskConfig = TaskConfigVo.builder().taskId(task.getTaskId()).retryCount(0).retryInterval(0).code(task.getCode()).expression(task.getExpression()).build();
        ((Runnable)()->{
            try {
                taskContextService.execute(taskConfig);
            }catch (Exception e){
                log.error("执行任务:【{}】报错", task.getCode());
                try {
                    throw e;
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).run();

    }
}
