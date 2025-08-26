package com.example.securitydemo.mapper.system;

import com.example.securitydemo.bean.dto.system.SysMenuRole;
import com.example.securitydemo.bean.dto.system.SysRoleDto;
import com.example.securitydemo.bean.vo.system.SysMenuRoleVo;
import com.example.securitydemo.bean.vo.system.SysRole;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<SysUserRole> query(SysUserRole sysRole);
    boolean update(SysUserRole sysRole);
    boolean delete(SysUserRole sysRole);
    boolean insert(SysUserRole sysRole);
    List<SysRole> find(SysRoleDto sysRoleDto);
    boolean modify(SysRole sysRole);
    boolean save(SysRole sysRole);
    List<SysMenuRoleVo> findMenuRole(SysMenuRole sysMenuRole);
    boolean addMenuRole(SysMenuRole sysMenuRole);
    boolean deleteMenuRole(SysMenuRole sysMenuRole);
}
