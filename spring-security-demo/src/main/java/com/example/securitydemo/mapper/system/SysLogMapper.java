package com.example.securitydemo.mapper.system;

import com.example.securitydemo.bean.dto.system.SysLogDto;
import com.example.securitydemo.bean.vo.system.SysLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLogMapper {
    List<SysLogVo> query(SysLogDto sysLogDto);
    boolean insert(SysLogVo sysLogVo);
}
