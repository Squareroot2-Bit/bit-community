-- 用户表
CREATE TABLE `tb_user`
(
    `id`              bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `bitid`           char(10)         NOT NULL COMMENT '学工号',
    `email`           varchar(64)               DEFAULT NULL COMMENT '邮箱',
    `nickname`        varchar(32)      NOT NULL COMMENT '昵称',
    `password`        varchar(128)     NOT NULL COMMENT '密码',
    `avatar`          varchar(256)     NOT NULL DEFAULT '' COMMENT '头像',
    `last_login_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
    `state`           tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态',
    `is_deleted`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除标志（0表示正常，1表示删除）',
    `create_time`     datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `tb_user_pk2` (`bitid`),
    UNIQUE KEY `tb_user_pk` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

-- 







