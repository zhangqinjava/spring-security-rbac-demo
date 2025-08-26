package com.example.securitydemo.service.system;

import com.example.securitydemo.bean.dto.system.SysMenuRole;
import com.example.securitydemo.bean.dto.system.SysRoleDto;
import com.example.securitydemo.bean.vo.system.SysMenuRoleVo;
import com.example.securitydemo.bean.vo.system.SysRole;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysRoleService {
    List<SysUserRole> query(SysUserRole sysUserRole);
    boolean save(SysUserRole sysUserRole);
    boolean update(SysUserRole sysUserRole);
    boolean delete(SysUserRole sysUserRole);
    PageInfo<SysRole> find(SysRoleDto sysRoleDto);
    List<SysRole> list(SysRoleDto sysRoleDto);
    boolean insert(SysRole sysRole);
    boolean modify(SysRole sysRole);
    List<SysMenuRoleVo> findMenuRole(SysMenuRole sysMenuRole);
    boolean addMenuRole(SysMenuRole sysMenuRole);
}
