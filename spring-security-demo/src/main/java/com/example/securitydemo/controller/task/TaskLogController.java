package com.example.securitydemo.controller.task;

import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.bean.dto.task.TaskLogDto;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.task.TaskConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasklog")
public class TaskLogController {
    @Autowired
    private TaskConfigService taskConfigService;
    @GetMapping("/query")
    public R query(TaskLogDto taskLogDto) {
        return R.ok(taskConfigService.query(taskLogDto));
    }
    @GetMapping("/delete")
    public R delete(TaskLogDto taskLogDto){
        return R.ok(taskConfigService.delete(taskLogDto));
    }

}
