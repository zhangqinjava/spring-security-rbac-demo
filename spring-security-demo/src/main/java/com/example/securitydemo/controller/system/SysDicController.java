package com.example.securitydemo.controller.system;

import com.example.securitydemo.bean.dto.system.SysDicDto;
import com.example.securitydemo.bean.vo.system.SysDicVo;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.system.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dic")
public class SysDicController {
    @Autowired
    private SysDicService sysDicService;
    @GetMapping("/query")
    public R query(SysDicDto sysDicDto) {
        return R.ok(sysDicService.query(sysDicDto));
    }
    @GetMapping("/save")
    public R save(SysDicDto sysDicDto) {
        return R.ok(sysDicService.save(sysDicDto));
    }
    @GetMapping("/update")
    public R update(SysDicDto sysDicDto) {
        return R.ok(sysDicService.update(sysDicDto));
    }
    @GetMapping("/delete")
    public R delete(SysDicDto sysDicDto) {
        return R.ok(sysDicService.delete(sysDicDto));
    }
    @GetMapping("/getGroupNameByList")
    public R getGroupNameByList(SysDicDto sysDicDto) {
        return R.ok(sysDicService.queryAll(sysDicDto));
    }
}
