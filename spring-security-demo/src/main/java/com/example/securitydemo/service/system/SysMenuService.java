package com.example.securitydemo.service.system;

import com.example.securitydemo.bean.vo.system.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> query(SysMenu sysMenu);
    boolean save(SysMenu sysMenu);
    boolean update(SysMenu sysMenu);
    boolean delete(SysMenu sysMenu);
}
