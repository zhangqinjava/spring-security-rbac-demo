package com.example.securitydemo.bean.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class SysMenuRoleVo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 中文名称
     */
    private String chineseName;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 菜单Id
     */
    private Integer menuId;
    /**
     * 子集
     */
    private List<SysMenuRoleVo> children;
}
