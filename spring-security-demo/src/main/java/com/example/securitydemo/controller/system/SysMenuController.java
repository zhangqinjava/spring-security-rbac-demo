package com.example.securitydemo.controller.system;

import com.example.securitydemo.bean.vo.system.SysMenu;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.system.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/query")
    public R query(SysMenu sysMenu) {
        return R.ok(sysMenuService.query(sysMenu));
    }
    @GetMapping("/save")
    public R save(SysMenu sysMenu) {
        return R.ok(sysMenuService.save(sysMenu));
    }
    @GetMapping("/update")
    public R update(SysMenu sysMenu) {
        return R.ok(sysMenuService.update(sysMenu));
    }
    @GetMapping("/delete")
    public R delete(SysMenu sysMenu) {
        return R.ok(sysMenuService.delete(sysMenu));
    }
}
