package com.example.securitydemo.service.impl.system;

import com.example.securitydemo.bean.dto.system.SysUserDto;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.bean.vo.system.SysUserRole;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.mapper.system.UserMapper;
import com.example.securitydemo.service.system.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class SysUserServiceImpl   implements SysUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public PageInfo<SysUser> findUser(SysUserDto sysUserDto) {
        log.info("查询用户信息:{}",sysUserDto);
        PageHelper.startPage(sysUserDto.getPage(), sysUserDto.getSize());
        List<SysUser> user = userMapper.findUser(sysUserDto);
        return new PageInfo<SysUser>(user);
    }

    public SysUserRole findRole(String username) {
        return userMapper.findRole(username);
    }
    @Transactional
    public boolean saveUser(SysUserDto sysUser) throws BusiException {
        log.info("新增商户:{}",sysUser);
        SysUser user = new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        user.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        return userMapper.saveUser(user);
    }
    @Transactional
    public boolean updateUser(SysUser sysUserDto) throws BusiException {
        log.info("开始修改用户信息:{}",sysUserDto);
        if(!StringUtils.isEmpty(sysUserDto.getPassword())){
            sysUserDto.setPassword(passwordEncoder.encode(sysUserDto.getPassword()));
        }
        return  userMapper.updateUser(sysUserDto);

    }
    public boolean deleteUser(SysUser sysUser) throws BusiException {
        log.info("删除用户信息:{}",sysUser);
        sysUser.setFlag(1);//伪删除
        sysUser.setPassword(null);
        return userMapper.updateUser(sysUser);
    }

    public SysUser login(SysUserDto sysUserDto) throws BusiException {
        List<SysUser> users = userMapper.findUser(sysUserDto);
        log.info("查询到的用户信息:{}",users);
        if (users == null || users.size() == 0) {
            throw new BusiException("用户不存在");
        }
        SysUser user=users.get(0);
        SysUserRole role = userMapper.findRole(user.getUsername());
        if (role == null) {
            throw new BusiException("用户未分配角色");
        }
        user.setRole(String.valueOf(role.getRoleId()));
        return user;
    }
}
