package com.example.securitydemo.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitydemo.bean.dto.system.SysMenuRole;
import com.example.securitydemo.bean.vo.system.SysMenu;
import com.example.securitydemo.mapper.system.MenuMapper;
import com.example.securitydemo.mapper.system.RoleMapper;
import com.example.securitydemo.service.system.SysMenuService;
import com.example.securitydemo.service.system.SysRoleService;
import com.example.securitydemo.util.SecurityUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysMenuServiceImpl  implements SysMenuService {
    @Autowired
    private MenuMapper  menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SysRoleService sysRoleService;
    public List<SysMenu> query(SysMenu sysMenu) {
        log.info("query sysMenu:{}", sysMenu);
        List<SysMenu> list = menuMapper.query(sysMenu);
        list = list.stream().filter(sort -> Objects.nonNull(sort.getSort())).sorted(Comparator.comparingInt(SysMenu::getSort)).distinct().collect(Collectors.toList());
        Map<Integer, List<SysMenu>> map = list.stream().collect(Collectors.groupingBy(SysMenu::getParentId, Collectors.toList()));
        for (SysMenu item : list){
            List<SysMenu> sysMenus = map.get(item.getId());
            if(CollectionUtils.isEmpty(sysMenus)){
                continue;
            }
            sysMenus = sysMenus.stream()
                    .filter(sort -> Objects.nonNull(sort.getSort()))
                    .sorted(Comparator.comparingInt(SysMenu::getSort))
                    .collect(Collectors.toList());
            item.setChildren(sysMenus);
        }
        log.info("map1:{}", map);
        return map.get(0);
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysMenu sysMenu) {
        log.info("save sysMenu:{}", sysMenu);
        if(Objects.isNull(sysMenu.getParentId())){
            //默认为根目录
            sysMenu.setParentId(0);
        }
        //默认正常使用
        sysMenu.setStatus(1);
        boolean save = menuMapper.save(sysMenu);
        if(save){
            //分配权限
            return sysRoleService.addMenuRole(SysMenuRole.builder()
                    .menuId(sysMenu.getId())
                    .roleId(Integer.valueOf(SecurityUserUtils.getUser()
                            .getRole())).build());
        }
        return false;
    }

    public boolean update(SysMenu sysMenu) {
        log.info("update sysMenu:{}", sysMenu);
        return menuMapper.update(sysMenu);
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(SysMenu sysMenu) {
        log.info("delete sysMenu:{}", sysMenu);
        //这里删除会将当前目录下的所有的菜单都给删除
        List<SysMenu> children = menuMapper.getChildren(sysMenu.getId());
        if(CollectionUtils.isEmpty(children)){
            return false;
        }
        children.forEach(sysMenu1 -> {
            menuMapper.delete(sysMenu1);
            roleMapper.deleteMenuRole(SysMenuRole.builder().menuId(sysMenu1.getId()).build());
        });

        return true;
    }


}
