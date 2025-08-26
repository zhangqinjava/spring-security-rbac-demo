package com.example.securitydemo.task.impl;

import com.example.securitydemo.task.TaskRegisterService;
import org.springframework.stereotype.Service;

@Service
public class Test implements TaskRegisterService {
    @Override
    public void registerTask() {
        System.out.printf("registerTask: %s\n", Thread.currentThread().getName());
    }
}
