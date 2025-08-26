package com.example.securitydemo.mapper.system;

import com.example.securitydemo.bean.dto.system.SysDicDto;
import com.example.securitydemo.bean.vo.system.SysDicVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDicMapper {
    List<SysDicVo> query(SysDicDto sysDicdto);
    boolean save(SysDicDto sysDicdto);
    boolean update(SysDicDto sysDicdto);
    boolean delete(SysDicDto sysDicdto);
    List<SysDicVo> queryAll(SysDicDto sysDicdto);
}
