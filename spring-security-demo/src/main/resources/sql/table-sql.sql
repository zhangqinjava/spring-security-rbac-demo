
#创建用户表
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `username` varchar(50) NOT NULL COMMENT '用户名',
                            `nickname` varchar(50) DEFAULT '' COMMENT '用户昵称',
                            `password` varchar(100) NOT NULL COMMENT '加密后的密码',
                            `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                            `open_id` varchar(20) DEFAULT '' COMMENT '微信标识',
                            `first_flag` int DEFAULT '0' COMMENT '首次登录(0-首次登录，1-非首次登录)',
                            `email` varchar(20) DEFAULT '' COMMENT '邮箱',
                            `flag` tinyint DEFAULT '1' COMMENT '状态（1正常，0禁用）',
                            `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
                            `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gender` tinyint NOT NULL COMMENT '性别(0-男1-女)',
                            `birthday` varchar(8) DEFAULT NULL COMMENT '出生日期',
                            `address` varchar(255) DEFAULT NULL COMMENT '地址',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表'
#创建角色
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `name` varchar(100) NOT NULL COMMENT '名称',
                            `status` int DEFAULT '0' COMMENT '状态',
                            `type` int DEFAULT NULL COMMENT '类型',
                            `creator` varchar(64) DEFAULT NULL COMMENT '创建人',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='权限'
#创建用户角色表
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `username` varchar(50) NOT NULL COMMENT '用户名称',
                                 `role_id` int DEFAULT NULL COMMENT '角色',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `name_role` (`username`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色表'
#创建菜单角色表
CREATE TABLE `sys_menu_role` (
                                 `role_id` int NOT NULL COMMENT '角色id',
                                 `menu_id` int NOT NULL COMMENT '菜单id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单角色'
#创建菜单表
CREATE TABLE `sys_menu` (
                            `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `parent_id` int NOT NULL COMMENT '父id',
                            `name` varchar(255) NOT NULL COMMENT '菜单名称',
                            `url` varchar(255) NOT NULL COMMENT '菜单路由',
                            `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
                            `type` int NOT NULL COMMENT '类型：0、按钮，1、菜单，2、嵌套链接，3、跳转链接',
                            `status` int DEFAULT NULL COMMENT '状态：0、未上架，1、正常，2、下架',
                            `chinese_name` varchar(100) DEFAULT NULL COMMENT '中文名称',
                            `english_name` varchar(100) DEFAULT NULL COMMENT '英文名称',
                            `sort` int DEFAULT NULL COMMENT '排序',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单'
#
#创建字典表
CREATE TABLE `sys_dic` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
                           `parent_id` int NOT NULL COMMENT '父ID',
                           `group_name` varchar(30) NOT NULL COMMENT '分组名称',
                           `type` int DEFAULT NULL COMMENT '类型：0、系统，1、业务',
                           `dic_value` int NOT NULL COMMENT '值',
                           `dic_label` varchar(50) NOT NULL COMMENT '名称',
                           `sort` int DEFAULT NULL COMMENT '排序',
                           `text1` varchar(100) DEFAULT NULL COMMENT '预留字段1',
                           `text2` varchar(100) DEFAULT NULL COMMENT '预留字段2',
                           `text3` varchar(100) DEFAULT NULL COMMENT '预留字段3',
                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `group_name` (`group_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典表'
# 创建定时任务表
CREATE TABLE `task_config` (
                               `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                               `code` varchar(255) NOT NULL COMMENT '任务服务唯一编码（英文标识）',
                               `name` varchar(255) NOT NULL COMMENT '任务名称（中文显示）',
                               `task_id` varchar(64) DEFAULT NULL COMMENT '第三方任务ID或内部关联ID',
                               `expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
                               `status` tinyint(1) DEFAULT '0' COMMENT '状态：0未启动，1运行中，2暂停，3已删除',
                               `concurrent` tinyint(1) DEFAULT '0' COMMENT '是否允许并发执行：0否，1是',
                               `retry_count` int DEFAULT '0' COMMENT '失败重试次数',
                               `retry_interval` int DEFAULT '0' COMMENT '失败重试间隔（秒）',
                               `last_run_time` datetime DEFAULT NULL COMMENT '最后一次执行时间',
                               `next_run_time` datetime DEFAULT NULL COMMENT '下次执行时间',
                               `creater` varchar(64) DEFAULT NULL COMMENT '创建人',
                               `updater` varchar(64) DEFAULT NULL COMMENT '更新人',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `remarks` varchar(255) DEFAULT NULL COMMENT '描述',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='任务配置表'
#创建定时任务日志表
CREATE TABLE `task_log` (
                            `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `task_id` varchar(64) DEFAULT NULL COMMENT '调度id',
                            `server_name` varchar(100) DEFAULT NULL COMMENT '任务服务名称',
                            `status` int DEFAULT NULL COMMENT '状态：0、成功，1、失败',
                            `execution_time` datetime DEFAULT NULL COMMENT '执行时间',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `remarks` varchar(255) DEFAULT NULL COMMENT '描述',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='调度执行日志'
#创建操作日志记录表
CREATE TABLE `log_info` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `module` varchar(255) DEFAULT NULL COMMENT '功能模块',
                            `type` varchar(255) DEFAULT NULL COMMENT '操作类型',
                            `message` varchar(255) DEFAULT NULL COMMENT '操作描述',
                            `req_param` text COMMENT '请求参数',
                            `res_param` text COMMENT '响应参数',
                            `take_up_time` int DEFAULT NULL COMMENT '耗时',
                            `user_id` varchar(64) DEFAULT NULL COMMENT '操作用户id',
                            `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作用户名称',
                            `method` varchar(255) DEFAULT NULL COMMENT '操作方法',
                            `uri` varchar(255) DEFAULT NULL COMMENT '请求url',
                            `ip` varchar(50) DEFAULT NULL COMMENT '请求IP',
                            `version` varchar(50) DEFAULT NULL COMMENT '版本号',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='操作日志'