package com.example.securitydemo.service.impl.task;
import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.bean.dto.task.TaskLogDto;
import com.example.securitydemo.bean.vo.task.TaskConfigVo;
import com.example.securitydemo.bean.vo.task.TaskLog;
import com.example.securitydemo.mapper.task.TaskConfigMapper;
import com.example.securitydemo.mapper.task.TaskLogMapper;
import com.example.securitydemo.service.task.TaskConfigService;
import com.example.securitydemo.task.DynamicTaskScheduler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Slf4j
public class TaskConfigServiceImpl implements TaskConfigService {
    @Autowired
    private TaskConfigMapper taskConfigMapper;
    @Autowired
    private DynamicTaskScheduler dynamicTaskScheduler;
    @Autowired
    private TaskLogMapper taskLogMapper;
    @Override
    public PageInfo<TaskConfigVo> query(TaskConfigDto taskConfigDto) {
        log.info("query task config");
        PageHelper.startPage(taskConfigDto.getPageNum(), taskConfigDto.getPageSize());
        List<TaskConfigVo> list = taskConfigMapper.query(taskConfigDto);
        return new PageInfo<>(list);
    }

    @Override
    public boolean update(TaskConfigDto taskConfigDto) {
        log.info("update task config:{}",taskConfigDto);
        boolean update = taskConfigMapper.update(taskConfigDto);
        if (update) {
            dynamicTaskScheduler.refreshTasks(taskConfigMapper.query(TaskConfigDto.builder().status(1).build()));
            return true;
        }
        return false;
    }

    @Override
    public boolean add(TaskConfigDto taskConfigDto) {
        log.info("add task config:{}",taskConfigDto);
        boolean add = taskConfigMapper.add(taskConfigDto);
        if (add) {
            dynamicTaskScheduler.refreshTasks(taskConfigMapper.query(TaskConfigDto.builder().status(1).build()));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(TaskConfigDto taskConfigDto) {
        log.info("delete task config:{}",taskConfigDto);
        boolean delete = taskConfigMapper.delete(taskConfigDto);
        if (delete) {
            dynamicTaskScheduler.refreshTasks(taskConfigMapper.query(TaskConfigDto.builder().status(1).build()));
            return true;
        }
        return false;
    }

    @Override
    public boolean trigger(TaskConfigDto taskConfigDto) {
        log.info("trigger task config:{}",taskConfigDto);
        dynamicTaskScheduler.triggerTask(taskConfigDto);
        return true;
    }

    @Override
    public PageInfo<TaskLog> query(TaskLogDto taskLogDto) {
        log.info("query task log:{}",taskLogDto);
        PageHelper.startPage(taskLogDto.getPageNum(), taskLogDto.getPageSize());
        List<TaskLog> list = taskLogMapper.query(taskLogDto);
        return new PageInfo<>(list);
    }

    @Override
    public boolean delete(TaskLogDto taskLogDto) {
        log.info("delete task log:{}",taskLogDto);
        return taskLogMapper.delete(taskLogDto);
    }

}
