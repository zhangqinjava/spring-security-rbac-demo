package com.example.securitydemo.mapper.system;

import com.example.securitydemo.bean.dto.system.SysUserDto;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<SysUser> findUser(SysUserDto user);
    SysUserRole findRole(String role);
    Boolean saveUser(SysUser sysUser);
    Boolean updateUser(SysUser sysUser);
}
