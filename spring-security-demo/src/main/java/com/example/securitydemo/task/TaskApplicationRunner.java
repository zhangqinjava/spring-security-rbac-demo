package com.example.securitydemo.task;

import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.bean.vo.task.TaskConfigVo;
import com.example.securitydemo.mapper.task.TaskConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class TaskApplicationRunner implements ApplicationRunner {
    @Autowired
    private TaskConfigMapper taskConfigService;
    @Autowired
    private DynamicTaskScheduler dynamicTaskScheduler;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            log.info("==============================TaskApplicationRunner start=====================");
            List<TaskConfigVo> tasks = taskConfigService.query(TaskConfigDto.builder().status(1).build());
            log.info("初始化定时任务数量:{}", tasks.size());
            if (Objects.nonNull(tasks)) {
                dynamicTaskScheduler.refreshTasks(tasks);
            }
            log.info("=============================TaskApplicationRunner end=======================");
        }catch (Exception e){
            log.error("TaskApplicationRunner initiate fail :{}",e.getMessage());
        }
    }
}
