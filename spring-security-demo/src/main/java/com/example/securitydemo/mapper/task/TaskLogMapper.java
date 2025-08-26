package com.example.securitydemo.mapper.task;

import com.example.securitydemo.bean.dto.task.TaskLogDto;
import com.example.securitydemo.bean.vo.task.TaskLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskLogMapper {
    List<TaskLog> query(TaskLogDto taskLogDto);
    boolean update(TaskLogDto taskLogDto);
    boolean insert(TaskLog taskLogDto);
    boolean delete(TaskLogDto taskLogDto);

}
