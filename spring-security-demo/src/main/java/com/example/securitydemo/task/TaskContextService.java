package com.example.securitydemo.task;

import com.example.securitydemo.bean.vo.task.TaskConfigVo;

public interface TaskContextService {
    void execute(TaskConfigVo code) throws InterruptedException;
}
