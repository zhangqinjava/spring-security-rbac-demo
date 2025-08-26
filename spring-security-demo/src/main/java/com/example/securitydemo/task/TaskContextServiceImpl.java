package com.example.securitydemo.task;

import com.example.securitydemo.bean.vo.task.TaskConfigVo;
import com.example.securitydemo.bean.vo.task.TaskLog;
import com.example.securitydemo.mapper.task.TaskLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TaskContextServiceImpl implements TaskContextService {
    @Autowired
    private Map<String, TaskRegisterService> componentServices;
    @Autowired
    private TaskLogMapper taskLogMapper;
    @Override
    public void execute(TaskConfigVo taskConfigVo) throws InterruptedException {
        TaskLog taskLog = TaskLog.builder().taskId(taskConfigVo.getTaskId()).serverName(taskConfigVo.getCode()).executionTime(LocalDateTime.now()).createTime(LocalDateTime.now()).remarks("执行成功").status(0).build();
        int retry=0;
        boolean success=false;
        while(retry<=taskConfigVo.getRetryCount() && !success){
            try {
                componentServices.get(taskConfigVo.getCode()).registerTask();
                success=true;
            } catch (Exception e) {
                log.error("===执行任务调度异常，任务服务名称：{}，异常信息：{}", taskConfigVo.getCode(), e);
                retry++;
                TimeUnit.SECONDS.sleep(taskConfigVo.getRetryInterval());
                log.error("=====任务【{}】,第{}次执行失败", taskConfigVo.getCode(), retry);
            }
        }
        if(!success){
            taskLog.setStatus(1);
            taskLog.setRemarks("任务服务【" + taskConfigVo.getCode() + "】执行失败");
        }
        taskLogMapper.insert(taskLog);
    }
}
