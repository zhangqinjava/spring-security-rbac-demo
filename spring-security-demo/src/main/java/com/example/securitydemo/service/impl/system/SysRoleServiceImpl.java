package com.example.securitydemo.service.impl.system;

import com.example.securitydemo.bean.dto.system.SysMenuRole;
import com.example.securitydemo.bean.dto.system.SysRoleDto;
import com.example.securitydemo.bean.vo.system.SysMenuRoleVo;
import com.example.securitydemo.bean.vo.system.SysRole;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import com.example.securitydemo.mapper.system.RoleMapper;
import com.example.securitydemo.service.system.SysRoleService;
import com.example.securitydemo.util.SecurityUserUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PageHelperProperties pageHelperProperties;

    public List<SysUserRole> query(SysUserRole sysUserRole) {
        log.info("user role query:{}",sysUserRole);
        return roleMapper.query(sysUserRole);
    }



    public boolean save(SysUserRole sysUserRole) {
        log.info("user role save:{}", sysUserRole);
        return roleMapper.insert(sysUserRole);
    }

    public boolean update(SysUserRole sysUserRole) {
        log.info("user role update:{}",sysUserRole);
        return roleMapper.update(sysUserRole);
    }

    public boolean delete(SysUserRole sysUserRole) {
        log.info("user role delete:{}",sysUserRole);
        return roleMapper.delete(sysUserRole);
    }

    @Override
    public PageInfo<SysRole> find(SysRoleDto sysRoleDto) {
        log.info("find role:{}",sysRoleDto);
        PageHelper.startPage(sysRoleDto.getCurrent(), sysRoleDto.getSize());
        List<SysRole> sysRoles = roleMapper.find(sysRoleDto);
        return new PageInfo<SysRole>(sysRoles);
    }
    @Override
    public List<SysRole> list(SysRoleDto sysRoleDto) {
        log.info("find role:{}",sysRoleDto);
        //如果当前的用户是超级管理员的角色，则查看所有的角色。
        List<SysRole> sysRoles =null;
        if (Objects.equals(SecurityUserUtils.getUser().getType(), 0)){
            //超级管理员的权限
            sysRoleDto.setType(null);
            sysRoles = roleMapper.find(sysRoleDto);
        }else{
            //非超级管理员的权限
            sysRoleDto.setType(SecurityUserUtils.getUser().getType());
            sysRoleDto.setId(Integer.valueOf(SecurityUserUtils.getUser().getRole()));
            sysRoles = roleMapper.find(sysRoleDto);

        }
        //如果是非超级管理员角色，则查询当前自己的角色
        return sysRoles;
    }

    @Override
    public boolean insert(SysRole sysRole) {
        log.info("insert role:{}",sysRole);
        return roleMapper.save(sysRole);
    }

    @Override
    public boolean modify(SysRole sysRole) {
        log.info("modify role:{}",sysRole);
        return roleMapper.modify(sysRole);
    }

    @Override
    public List<SysMenuRoleVo> findMenuRole(SysMenuRole sysMenuRole) {
        log.info("find menu role:{}", sysMenuRole);
        List<SysMenuRoleVo> roleVos;
        if (!Objects.equals(0, SecurityUserUtils.getUser().getType())) {
            sysMenuRole.setCurrRoleId(Integer.parseInt(SecurityUserUtils.getUser().getRole()));
        }
        roleVos = roleMapper.findMenuRole(sysMenuRole);
        roleVos = roleVos.stream().filter(sort -> Objects.nonNull(sort.getSort())).sorted(Comparator.comparingInt(SysMenuRoleVo::getSort)).distinct().collect(Collectors.toList());
        Map<Integer, List<SysMenuRoleVo>> map = roleVos.stream().collect(Collectors.groupingBy(SysMenuRoleVo::getParentId, Collectors.toList()));
        for(SysMenuRoleVo item:roleVos){
            List<SysMenuRoleVo> sysMenuRoleVos = map.get(item.getId());
            if(CollectionUtils.isEmpty(sysMenuRoleVos)){
                continue;
            }
            sysMenuRoleVos = sysMenuRoleVos.stream()
                    .filter(sort -> Objects.nonNull(sort.getSort()))
                    .sorted(Comparator.comparingInt(SysMenuRoleVo::getSort))
                    .collect(Collectors.toList());
            item.setChildren(sysMenuRoleVos);
        }
        return map.get(0);
    }

    @Override
    public boolean addMenuRole(SysMenuRole sysMenuRole) {
        log.info("add menu role:{}", sysMenuRole);
        return roleMapper.addMenuRole(sysMenuRole);
    }
}
