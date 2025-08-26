package com.example.securitydemo.service.task;

import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.bean.dto.task.TaskLogDto;
import com.example.securitydemo.bean.vo.task.TaskConfigVo;
import com.example.securitydemo.bean.vo.task.TaskLog;
import com.github.pagehelper.PageInfo;



public interface TaskConfigService {
    PageInfo<TaskConfigVo> query(TaskConfigDto taskConfigDto);
    boolean update(TaskConfigDto taskConfigDto);
    boolean add(TaskConfigDto taskConfigDto);
    boolean delete(TaskConfigDto taskConfigDto);
    boolean trigger(TaskConfigDto taskConfigDto);
    PageInfo<TaskLog> query(TaskLogDto taskLogDto);
    boolean delete(TaskLogDto taskLogDto);

}
