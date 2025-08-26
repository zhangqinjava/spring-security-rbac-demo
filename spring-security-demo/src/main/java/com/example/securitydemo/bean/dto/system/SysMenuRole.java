package com.example.securitydemo.bean.dto.system;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class SysMenuRole {
    /**
     * 主键Id
     */
    private Integer roleId;
    /**
     * 菜单Id
     */
    private Integer menuId;
    /**
     * 当前的角色Id
     */
    private Integer currRoleId;
}
