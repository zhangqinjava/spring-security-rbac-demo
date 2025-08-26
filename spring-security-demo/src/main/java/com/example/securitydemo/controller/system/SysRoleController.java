package com.example.securitydemo.controller.system;

import com.example.securitydemo.bean.dto.system.SysMenuRole;
import com.example.securitydemo.bean.dto.system.SysRoleDto;
import com.example.securitydemo.bean.vo.system.SysRole;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.system.SysRoleService;
import com.example.securitydemo.service.impl.system.SysRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色查询
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleServiceImpl sysRoleServiceImpl;

    /**
     *查询用户角色信息
     * @param sysUserRole
     * @return
     */
    @GetMapping("/query")
    public R query(SysUserRole sysUserRole) {
        return R.ok(sysRoleService.query(sysUserRole));
    }
    /**
     * 用户角色保存
     * @param sysUserRole
     * @return
     */
    @GetMapping("/save")
    public R save(SysUserRole sysUserRole) {
        return R.ok(sysRoleService.save(sysUserRole));
    }

    /**
     * 用户角色更新
     * @param sysUserRole
     * @return
     */
    @GetMapping("/update")
    public R update(SysUserRole sysUserRole) {
        return R.ok(sysRoleService.update(sysUserRole));
    }

    /**
     * 用户角色删除
     * @param sysUserRole
     * @return
     */
    @GetMapping("/delete")
    public R delete(SysUserRole sysUserRole) {
        return R.ok(sysRoleService.delete(sysUserRole));
    }
    /**
     * 角色信息查询
     */
    @GetMapping("/find")
    public R find(SysRoleDto sysRoleDto) {
        return R.ok(sysRoleService.find(sysRoleDto));
    }
    @GetMapping("/list")
    public R list(SysRoleDto sysRoleDto) {
        return R.ok(sysRoleService.list(sysRoleDto));
    }

    /**
     * 角色信息添加
     * @param sysRole
     * @return
     */
    @GetMapping("/add")
    public R add(SysRole sysRole) {
        return R.ok(sysRoleService.insert(sysRole));
    }

    /**
     * 角色信息修改
     * @param sysRole
     * @return
     */
    @GetMapping("/modify")
    public R modify(SysRole sysRole) {
        return R.ok(sysRoleService.modify(sysRole));
    }
    /**
     * 菜单角色查询
     */
    @GetMapping("/findMenuRole")
    public R findMenu(SysMenuRole sysMenuRole) {
        return R.ok(sysRoleService.findMenuRole(sysMenuRole));
    }
    /**
     * 菜单角色添加
     */
    @GetMapping("/addMenuRole")
    public R addMenu(SysMenuRole sysMenuRole) {
        return R.ok(sysRoleService.addMenuRole(sysMenuRole));
    }
}
