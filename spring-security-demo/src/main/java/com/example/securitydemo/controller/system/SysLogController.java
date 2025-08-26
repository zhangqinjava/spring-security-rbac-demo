package com.example.securitydemo.controller.system;

import com.example.securitydemo.bean.dto.system.SysLogDto;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;
    @GetMapping("/query")
    public R query(SysLogDto sysLogDto) {
        return R.ok(sysLogService.queryList(sysLogDto));
    }

}
