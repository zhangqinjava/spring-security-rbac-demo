package com.example.securitydemo.bean.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SysDicDto {
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
     * 类型0-系统 1-业务
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 字典值
     */
    private Integer dicValue;
    /**
     * 字典标签
     */
    private String dicLabel;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 备注
     */
    private String remarks;
}
