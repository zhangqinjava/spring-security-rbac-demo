package com.example.securitydemo.mapper.system;

import com.example.securitydemo.bean.vo.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper  {
    List<SysMenu> query(SysMenu userId);
    boolean update(SysMenu menu);
    boolean delete(SysMenu menu);
    boolean save(SysMenu menu);
    List<SysMenu> getChildren(Integer id);
}
