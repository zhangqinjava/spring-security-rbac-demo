package com.example.securitydemo.bean.vo.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 权限ID
     */
    private Integer parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 地址
     */
    private String url;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 类型 0-按钮 1-菜单 2-嵌套连接 3-跳转链接
     */
    private Integer type;
    /**
     * 状态 0-未上架 1-正常 2-下架
     */
    private Integer status;
    /**
     * 中文名称
     */
    private String chineseName;
    /**
     * 英文名称
     */
    private String englishName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 子集
     */
    private List<SysMenu> children;


}
