package com.example.securitydemo.service.impl.system;

import com.example.securitydemo.bean.dto.system.SysLogDto;
import com.example.securitydemo.bean.vo.system.SysLogVo;
import com.example.securitydemo.mapper.system.SysLogMapper;
import com.example.securitydemo.service.system.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    public PageInfo<SysLogVo> queryList(SysLogDto sysLogDto) {
        log.info("queryList sysLogVo:{}", sysLogDto);
        PageHelper.startPage(sysLogDto.getCurrent(), sysLogDto.getSize());
        List<SysLogVo> query = sysLogMapper.query(sysLogDto);
        return new PageInfo<SysLogVo>(query);
    }

    @Override
    public boolean save(SysLogVo sysLogVo) {
        log.info("save sysLogVo:{}", sysLogVo);
        return sysLogMapper.insert(sysLogVo);
    }
}
