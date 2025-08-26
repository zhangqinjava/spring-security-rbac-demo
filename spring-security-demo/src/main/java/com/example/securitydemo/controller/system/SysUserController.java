package com.example.securitydemo.controller.system;

import com.example.securitydemo.bean.dto.system.SysUserDto;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.common.Constants;
import com.example.securitydemo.common.aop.Log;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户信息查询
     * @param sysUserDto
     * @return
     */
    @GetMapping("/query")
    public R findUser( SysUserDto sysUserDto) {
        return R.ok(sysUserService.findUser(sysUserDto));
    }

    /**
     * 用户信息更新
     * @param sysUser
     * @return
     */
    @GetMapping("/update")
    @Log(model = "用户模块",type = Constants.UPDATE,desc = "用户更新")
    public R update( SysUser sysUser) {
        return R.ok(sysUserService.updateUser(sysUser));
    }

    /**
     * 用户信息删除
     * @param sysUser
     * @return
     */
    @GetMapping("/delete")
    @Log(model = "用户模块",type = Constants.DELETE,desc = "用户删除")
    public R delete( SysUser sysUser) {
        return R.ok(sysUserService.deleteUser(sysUser));
    }

    /**
     * 用户信息保存
     * @param sysUser
     * @return
     */
    @GetMapping("/save")
    @Log(model = "用户模块",type =Constants.INSERT,desc = "用户添加")
    public R save( SysUserDto sysUser) {
        return R.ok(sysUserService.saveUser(sysUser));
    }
}
