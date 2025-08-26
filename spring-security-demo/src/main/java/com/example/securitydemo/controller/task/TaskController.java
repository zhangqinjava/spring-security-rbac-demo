package com.example.securitydemo.controller.task;

import com.example.securitydemo.bean.dto.system.SysDicDto;
import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.task.TaskConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskConfigService taskConfigService;

    /**
     * 查询定时任务
     * @param taskConfigDto
     * @return
     */
    @GetMapping("/query")
    public R query(TaskConfigDto  taskConfigDto) {
        return R.ok(taskConfigService.query(taskConfigDto));
    }

    /**
     * 删除定时任务
     * @param taskConfigDto
     * @return
     */
    @GetMapping("/delete")
    public R delete(TaskConfigDto  taskConfigDto) {
        return R.ok(taskConfigService.delete(taskConfigDto));
    }

    /**
     * 更新定时任务
     * @param taskConfigDto
     * @return
     */
    @GetMapping("/update")
    public R update(TaskConfigDto taskConfigDto) {
        return R.ok(taskConfigService.update(taskConfigDto));
    }
    /**
     * 添加定时任务
     */
    @GetMapping("/save")
    public R insert(TaskConfigDto taskConfigDto) {
        return R.ok(taskConfigService.add(taskConfigDto));
    }
    /**
     * 触发执行定时任务
     */
    @GetMapping("/trigger")
    public R get(TaskConfigDto taskConfigDto) {
        return R.ok(taskConfigService.trigger(taskConfigDto));
    }
}
