package com.example.securitydemo.bean.vo.system;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysDicVo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer parentId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 类型 0-系统 1-业务
     */
    private Integer type;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 值
     */
    private Integer dicValue;
    /**
     * 标签
     */
    private String dicLabel;
    /**
     * 顺序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 子目录
     */
    private List<SysDicVo> children;
}
