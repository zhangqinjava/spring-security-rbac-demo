package com.example.securitydemo.service.system;

import com.example.securitydemo.bean.dto.system.SysLogDto;
import com.example.securitydemo.bean.vo.system.SysLogVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysLogService {
    PageInfo<SysLogVo> queryList(SysLogDto sysLogDto);
    boolean save(SysLogVo sysLogVo);
}
