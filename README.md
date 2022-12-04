# Spring Cloud Alibaba Mall Demo

参考文章: https://www.lingluoyun.com/spring/spring-cloud-alibaba/spring-cloud-alibaba-seata/

## 前期 MySQL 数据库准备

1. 创建 `seata_order` 数据库

```sql
-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `user_id`    bigint         DEFAULT NULL COMMENT '用户id',
    `product_id` bigint         DEFAULT NULL COMMENT '产品id',
    `count`      int            DEFAULT NULL COMMENT '数量',
    `money`      decimal(11, 0) DEFAULT NULL COMMENT '金额',
    `status`     int            DEFAULT NULL COMMENT '订单状态：0：未完成；1：已完结',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `branch_id`     bigint       NOT NULL COMMENT 'branch transaction id',
    `xid`           varchar(128) NOT NULL COMMENT 'global transaction id',
    `context`       varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` longblob     NOT NULL COMMENT 'rollback info',
    `log_status`    int          NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   datetime(6) NOT NULL COMMENT 'create datetime',
    `log_modified`  datetime(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

2. 创建 `seata_storage` 数据库

```sql
-- ----------------------------
-- Table structure for t_storage
-- ----------------------------
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `product_id` bigint DEFAULT NULL COMMENT '产品id',
    `total`      int    DEFAULT NULL COMMENT '总库存',
    `used`       int    DEFAULT NULL COMMENT '已用库存',
    `residue`    int    DEFAULT NULL COMMENT '剩余库存',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_storage
-- ----------------------------
INSERT INTO `t_storage`
VALUES ('1', '1', '100', '0', '100');

-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `branch_id`     bigint       NOT NULL COMMENT 'branch transaction id',
    `xid`           varchar(128) NOT NULL COMMENT 'global transaction id',
    `context`       varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` longblob     NOT NULL COMMENT 'rollback info',
    `log_status`    int          NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   datetime(6) NOT NULL COMMENT 'create datetime',
    `log_modified`  datetime(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';
```

3. 创建 `seata_account` 数据库

```sql
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`
(
    `id`      bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` bigint         DEFAULT NULL COMMENT '用户id',
    `total`   decimal(10, 0) DEFAULT NULL COMMENT '总额度',
    `used`    decimal(10, 0) DEFAULT NULL COMMENT '已用余额',
    `residue` decimal(10, 0) DEFAULT '0' COMMENT '剩余可用额度',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account`
VALUES ('1', '1', '1000', '0', '1000');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `branch_id`     bigint       NOT NULL COMMENT 'branch transaction id',
    `xid`           varchar(128) NOT NULL COMMENT 'global transaction id',
    `context`       varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` longblob     NOT NULL COMMENT 'rollback info',
    `log_status`    int          NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   datetime(6) NOT NULL COMMENT 'create datetime',
    `log_modified`  datetime(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 模块说明

- `seata-order`: 订单（Order）服务
- `seata-storage`: 库存（Storage）服务
- `seata-account`: 账户（Account）服务