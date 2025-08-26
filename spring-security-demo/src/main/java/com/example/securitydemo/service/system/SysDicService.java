package com.example.securitydemo.service.system;

import com.example.securitydemo.bean.dto.system.SysDicDto;
import com.example.securitydemo.bean.vo.system.SysDicVo;

import java.util.List;

public interface SysDicService {
    List<SysDicVo>  query(SysDicDto sysDicdto);
    boolean save(SysDicDto sysDicdto);
    boolean update(SysDicDto sysDicdto);
    boolean delete(SysDicDto sysDicdto);
    List<SysDicVo> queryAll(SysDicDto sysDicdto);
}
