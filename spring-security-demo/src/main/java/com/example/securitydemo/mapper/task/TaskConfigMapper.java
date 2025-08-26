package com.example.securitydemo.mapper.task;

import com.example.securitydemo.bean.dto.task.TaskConfigDto;
import com.example.securitydemo.bean.vo.task.TaskConfigVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskConfigMapper {
    List<TaskConfigVo> query(TaskConfigDto taskConfigDto);
    boolean add(TaskConfigDto taskConfigDto);
    boolean delete(TaskConfigDto taskConfigDto);
    boolean update(TaskConfigDto taskConfigDto);
}
