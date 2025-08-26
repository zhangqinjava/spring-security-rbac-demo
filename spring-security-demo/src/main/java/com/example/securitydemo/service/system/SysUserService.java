package com.example.securitydemo.service.system;

import com.example.securitydemo.bean.dto.system.SysUserDto;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import com.example.securitydemo.common.BusiException;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysUserService {
    PageInfo<SysUser> findUser(SysUserDto sysUserDto)throws BusiException;
    SysUserRole findRole(String username);
    boolean saveUser(SysUserDto sysUser)throws BusiException;
    boolean updateUser(SysUser SysUser)throws BusiException;
    boolean deleteUser(SysUser sysUser)throws BusiException;
    SysUser login(SysUserDto sysUserDto) throws BusiException;

}
