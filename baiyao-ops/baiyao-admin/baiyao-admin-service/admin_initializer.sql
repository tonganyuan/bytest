/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.32.39-测试(all)
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.32.39:3306
 Source Schema         : rbac_demo

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 23/08/2021 09:10:27
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `id`                      bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id`              bigint(20) unsigned DEFAULT NULL COMMENT '产品id',
    `client_id`               varchar(64) CHARACTER SET utf8   NOT NULL DEFAULT '' COMMENT '客户端id',
    `resource_ids`            varchar(256) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '授权可以访问的资源服务器集合',
    `client_secret`           varchar(256) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '客户端密钥',
    `scope`                   varchar(256) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '作用域',
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '授权方式',
    `web_server_redirect_uri` varchar(512) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '重定向uri',
    `authorities`             varchar(256) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '授权',
    `access_token_validity`   int(11) unsigned NOT NULL COMMENT 'token有效时长(ms)',
    `refresh_token_validity`  int(11) unsigned NOT NULL COMMENT 'r-token有效时长(ms)',
    `additional_information`  varchar(4096) CHARACTER SET utf8 NOT NULL DEFAULT '',
    `autoapprove`             varchar(256) CHARACTER SET utf8  NOT NULL DEFAULT '',
    `gmt_create`              datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified`            datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_client_id` (`client_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuth2客户端明细表';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `operation_center`.`oauth_client_details`(`id`, `product_id`, `client_id`, `resource_ids`, `client_secret`,
                                                      `scope`, `authorized_grant_types`, `web_server_redirect_uri`,
                                                      `authorities`, `access_token_validity`, `refresh_token_validity`,
                                                      `additional_information`, `autoapprove`, `gmt_create`,
                                                      `gmt_modified`)
VALUES (0, 0, 'operation', '', '{bcrypt}$2a$10$D1YkvDYzrgQn2VOw7HBsKuDCREvI2V5BIcCiMUKjem1DJ9jcvIFMa', 'all',
        'password,authorization_code,refresh_token,client_credentials', '', '', 86400, 864000, '{}', '',
        '2021-07-16 15:53:18', '2021-08-24 15:21:44');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `config_id`    bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `config_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '参数名称',
    `config_key`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '参数键名',
    `config_value` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数键值',
    `config_type`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin      NOT NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '创建者',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '更新者',
    `remark`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`config_id`) USING BTREE,
    UNIQUE INDEX `uk_config_key`(`config_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config`
VALUES (1, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', 'admin', '初始化密码 123456',
        '2021-02-20 07:55:25', '2021-02-20 07:55:25');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `dept_id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `name`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
    `parent_id`    bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '上级部门id',
    `ancestors`    varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表\n',
    `leader`       varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '负责人',
    `phone`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
    `sort`         int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
    `del_flag`     tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '有效状态',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '部门表(暂不用)';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    `dict_code`    bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典数据编码',
    `dict_type`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '字典类型',
    `dict_label`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '字典标签',
    `dict_value`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '字典键值',
    `sort`         int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '字典排序',
    `default_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin     NOT NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '有效状态',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data`
VALUES (1, 'sys_user_sex', '男', '1', 0, 'Y', 1, '2021-02-16 17:12:47', '2021-06-29 14:24:40'),
       (2, 'sys_user_sex', '女', '2', 1, 'N', 1, '2021-02-16 17:12:47', '2021-06-29 14:24:40'),
       (3, 'sys_user_sex', '未知', '0', 2, 'N', 1, '2021-02-16 17:12:47', '2021-06-29 14:24:40'),
       (4, 'sys_normal_disable', '正常', '0', 0, 'Y', 1, '2021-02-16 17:12:47', '2021-06-29 14:24:40'),
       (5, 'sys_normal_disable', '停用', '1', 1, 'N', 1, '2021-02-16 17:12:47', '2021-06-29 14:24:40'),
       (6, 'sys_show_hide', '显示', '0', 0, 'Y', 1, '2021-03-06 15:59:25', '2021-06-29 14:24:40'),
       (7, 'sys_show_hide', '隐藏', '1', 1, 'N', 1, '2021-03-06 15:59:25', '2021-06-29 14:24:40'),
       (8, 'bank_name', '中国银行', '1', 0, 'N', 1, '2021-04-25 14:49:01', '2021-06-29 14:24:40'),
       (9, 'bank_name', '中国工商银行', '2', 1, 'N', 1, '2021-04-25 14:50:34', '2021-06-29 14:24:40'),
       (10, 'bank_name', '招商银行', '3', 2, 'N', 1, '2021-04-25 14:50:49', '2021-06-29 14:24:40'),
       (11, 'bank_name', '中国农业银行', '4', 3, 'N', 1, '2021-04-25 14:51:23', '2021-06-29 14:24:40'),
       (12, 'bank_name', '中国建设银行', '5', 4, 'N', 1, '2021-04-25 14:51:51', '2021-06-29 14:24:40'),
       (13, 'bank_name', '交通银行', '6', 5, 'N', 1, '2021-04-25 14:52:13', '2021-06-29 14:24:40'),
       (14, 'bank_name', '中国民生银行', '7', 6, 'N', 1, '2021-04-25 14:52:56', '2021-06-29 14:24:40'),
       (15, 'bank_name', '中国光大银行', '8', 7, 'N', 1, '2021-04-25 14:53:31', '2021-06-29 14:24:40'),
       (16, 'bank_name', '平安银行', '9', 8, 'N', 1, '2021-04-25 14:55:41', '2021-06-29 14:24:40'),
       (17, 'bank_name', '中国邮政储蓄银行', '10', 9, 'N', 1, '2021-04-25 14:56:09', '2021-06-29 14:24:40'),
       (18, 'drugstore_type', '旗舰店', '1', 0, 'N', 1, '2021-04-25 14:58:39', '2021-06-29 14:24:40'),
       (19, 'medical_merchant_nature', '公立医院', '1', 0, 'N', 1, '2021-04-25 14:59:17', '2021-06-29 14:24:40'),
       (20, 'medical_merchant_nature', '私立医院', '0', 1, 'N', 1, '2021-04-25 14:59:34', '2021-06-29 14:24:40'),
       (21, 'sys_profit_nature', '盈利', '1', 0, 'N', 1, '2021-04-25 15:01:24', '2021-06-29 14:24:40'),
       (22, 'sys_profit_nature', '非盈利', '0', 1, 'N', 1, '2021-04-25 15:01:40', '2021-06-29 14:24:40'),
       (23, 'hospital_grade', '三级特等', '1', 0, 'N', 1, '2021-04-25 15:02:35', '2021-06-29 14:24:40'),
       (24, 'hospital_grade', '三级甲等', '2', 1, 'N', 1, '2021-04-25 15:02:57', '2021-06-29 14:24:40'),
       (25, 'hospital_grade', '三级乙等', '3', 2, 'N', 1, '2021-04-25 15:03:07', '2021-06-29 14:24:40'),
       (26, 'hospital_grade', '三级丙等', '4', 3, 'N', 1, '2021-04-25 15:03:10', '2021-06-29 14:24:40'),
       (27, 'hospital_grade', '二级甲等', '5', 4, 'N', 1, '2021-04-25 15:03:13', '2021-06-29 14:24:40'),
       (28, 'hospital_grade', '二级乙等', '6', 5, 'N', 1, '2021-04-25 15:03:15', '2021-06-29 14:24:40'),
       (29, 'hospital_grade', '二级丙等', '7', 6, 'N', 1, '2021-04-25 15:03:17', '2021-06-29 14:24:40'),
       (30, 'hospital_grade', '一级甲等', '8', 7, 'N', 1, '2021-04-25 15:04:48', '2021-06-29 14:24:40'),
       (31, 'hospital_grade', '一级乙等', '9', 8, 'N', 1, '2021-04-25 15:04:51', '2021-06-29 14:24:40'),
       (32, 'hospital_grade', '一级丙等', '10', 9, 'N', 1, '2021-04-25 15:05:01', '2021-06-29 14:24:40'),
       (33, 'administrative_relations', '中央属', '1', 0, 'N', 1, '2021-04-25 15:06:52', '2021-06-29 14:24:40'),
       (34, 'administrative_relations', '省、自治区、直辖市属', '2', 1, 'N', 1, '2021-04-25 15:06:58', '2021-06-29 14:24:40'),
       (35, 'administrative_relations', '直辖市区、省辖市、地区(盟)属', '3', 2, 'N', 1, '2021-04-25 15:07:02',
        '2021-06-29 14:24:40'),
       (36, 'administrative_relations', '省辖市区、地辖市属', '4', 3, 'N', 1, '2021-04-25 15:07:06', '2021-06-29 14:24:40'),
       (37, 'administrative_relations', '县(旗)属', '5', 4, 'N', 1, '2021-04-25 15:07:10', '2021-06-29 14:24:40'),
       (38, 'administrative_relations', '街道办事处属', '6', 5, 'N', 1, '2021-04-25 15:07:13', '2021-06-29 14:24:40'),
       (39, 'administrative_relations', '乡(镇)属', '7', 6, 'N', 1, '2021-04-25 15:07:17', '2021-06-29 14:24:40'),
       (40, 'administrative_relations', '村属', '8', 7, 'N', 1, '2021-04-25 15:07:33', '2021-06-29 14:24:40'),
       (41, 'administrative_relations', '其他', '9', 8, 'N', 1, '2021-04-25 15:07:36', '2021-06-29 14:24:40'),
       (42, 'sys_certificate_type', '身份证', '1', 0, 'N', 1, '2021-04-25 15:10:46', '2021-08-02 10:39:20'),
       (43, 'sys_certificate_type', '护照', '2', 1, 'N', 1, '2021-04-25 15:11:12', '2021-06-29 14:24:40'),
       (44, 'sys_certificate_type', '军官证', '3', 2, 'N', 1, '2021-04-25 15:11:37', '2021-06-29 14:24:40'),
       (45, 'sys_operation_mode', '共同运营', '1', 0, 'N', 1, '2021-04-25 15:12:59', '2021-06-29 14:24:40'),
       (46, 'sys_operation_mode', '独立运营', '0', 1, 'N', 1, '2021-04-25 15:13:11', '2021-06-29 14:24:40'),
       (47, 'drugstore_type', '院边店', '2', 1, 'N', 1, '2021-04-25 15:24:35', '2021-06-29 14:24:40'),
       (48, 'drugstore_type', '社区店', '3', 2, 'N', 1, '2021-04-25 15:25:03', '2021-06-29 14:24:40'),
       (49, 'drugstore_type', 'DTP店', '4', 3, 'N', 1, '2021-04-25 15:25:31', '2021-06-29 14:24:40'),
       (50, 'undertaking_prescription', '中成药方', '1', 0, 'N', 1, '2021-04-25 15:26:08', '2021-06-29 14:24:40'),
       (51, 'undertaking_prescription', '西药方', '2', 1, 'N', 1, '2021-04-25 15:26:17', '2021-06-29 14:24:40'),
       (52, 'undertaking_prescription', '草药方', '3', 2, 'N', 1, '2021-04-25 15:26:51', '2021-06-29 14:24:40'),
       (53, 'delivery_mode', '快递发货', '1', 0, 'N', 1, '2021-05-12 16:45:49', '2021-06-29 14:24:40'),
       (54, 'delivery_mode', '同城配送', '2', 1, 'N', 1, '2021-05-12 16:46:14', '2021-06-29 14:24:40'),
       (55, 'delivery_mode', '上门自提', '3', 2, 'N', 1, '2021-05-12 16:46:38', '2021-06-29 14:24:40'),
       (56, 'reg_channel', '平台', '1', 1, 'N', 1, '2021-05-14 17:41:01', '2021-06-29 14:24:40'),
       (57, 'reg_channel', '药厂', '2', 2, 'N', 1, '2021-05-14 17:41:25', '2021-06-29 14:24:40'),
       (58, 'reg_channel', '活动', '3', 3, 'N', 1, '2021-05-14 17:41:45', '2021-06-29 14:24:40'),
       (59, 'reg_channel', '药店', '4', 4, 'N', 1, '2021-05-14 17:42:08', '2021-06-29 14:24:40'),
       (60, 'reg_channel', 'app', '5', 5, 'N', 1, '2021-05-14 17:42:26', '2021-06-29 14:24:40'),
       (61, 'reg_channel', '云县医供体', '6', 6, 'N', 1, '2021-05-14 17:42:43', '2021-06-29 14:24:40'),
       (62, 'reg_channel', '口腔', '7', 7, 'N', 1, '2021-05-14 17:42:59', '2021-06-29 14:24:40'),
       (63, 'sms_channel_type', '阿里云通道', 'aliCloudSmsProviderStrategy', 1, 'Y', 1, '2021-05-14 17:42:59',
        '2021-07-26 10:09:08');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`
(
    `dict_id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '字典名称',
    `dict_type`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '字典类型',
    `sort`         int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
    `remark`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '有效状态',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`dict_id`) USING BTREE,
    UNIQUE INDEX `uk_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type`
VALUES (1, '用户性别', 'sys_user_sex', 1, '', 0, '2021-01-18 07:56:10', '2021-01-18 07:59:48'),
       (2, '菜单状态', 'sys_show_hide', 1, '', 0, '2021-01-18 07:56:52', '2021-01-18 07:57:44'),
       (3, '系统开关', 'sys_normal_disable', 1, '', 0, '2021-01-18 07:57:27', '2021-02-16 17:17:41'),
       (4, '证件类型', 'sys_certificate_type', 1, '', 1, '2021-04-22 15:20:44', '2021-07-05 17:46:24'),
       (5, '运营方式', 'sys_operation_mode', 2, '', 0, '2021-04-25 14:21:04', '2021-04-25 14:21:04'),
       (6, '商户等级', 'hospital_grade', 3, '', 0, '2021-04-25 14:37:34', '2021-05-12 15:37:52'),
       (7, '商户性质', 'medical_merchant_nature', 4, '', 0, '2021-04-25 14:38:32', '2021-04-25 14:39:32'),
       (8, '盈利性质', 'sys_profit_nature\n\n', 5, '', 0, '2021-04-25 14:40:35', '2021-04-25 14:42:17'),
       (9, '行政隶属关系\n', 'administrative_relations\n\n', 6, '', 0, '2021-04-25 14:42:14', '2021-04-25 14:42:21'),
       (10, '配送方式', 'delivery_mode\n\n', 7, '', 0, '2021-04-25 14:44:23', '2021-04-25 14:44:23'),
       (11, '承接处方类型', 'undertaking_prescription\n\n', 8, '', 0, '2021-04-25 14:45:17', '2021-04-25 14:45:24'),
       (12, '药店类型', 'drugstore_type', 9, '', 0, '2021-04-25 14:46:07', '2021-04-25 14:46:07'),
       (13, '银行名称', 'bank_name', 10, '', 0, '2021-04-25 14:46:58', '2021-04-25 14:46:58'),
       (14, '注册渠道', 'reg_channel', 11, '', 0, '2021-05-14 17:40:19', '2021-05-14 17:40:22'),
       (15, '三方短信通道', 'sms_channel_type', 12, '', 0, '2021-05-14 17:40:19', '2021-05-14 17:40:22'),
       (16, '短信模板类型', 'sms_template_type', 13, '', 1, '2021-07-29 14:04:40', '2021-07-29 14:04:40');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单id',
    `parent_id`    bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '父目录id',
    `menu_name`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '菜单名称',
    `perms`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
    `path`         varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '前端跳转URL',
    `component`    varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '组件地址',
    `sort`         int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
    `menu_type`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `icon`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图标',
    `visible`      tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '显示状态（0显示 1隐藏）',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '有效状态',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`menu_id`) USING BTREE,
    UNIQUE INDEX `uq_name`(`menu_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu`
VALUES (1, 0, '系统管理', 'sys', '', '', 1, 'M', 'sprite-menu_member', 0, 1, '2019-06-11 18:59:57', '2021-06-29 13:40:16'),
       (10, 1, '用户管理', 'sys:user', '/userManage', 'system/user/index', 1, 'C', 'user', 0, 1, '2020-06-05 02:48:20',
        '2021-06-29 13:39:57'),
       (11, 1, '角色管理', 'sys:role', '/roleManage', 'system/role/index', 2, 'C', 'tree', 0, 1, '2020-06-05 02:49:26',
        '2021-06-29 13:39:56'),
       (12, 1, '菜单管理', 'sys:menu', '/permission', 'system/menu/index', 3, 'C', 'form', 0, 1, '2020-06-05 02:49:58',
        '2021-06-29 13:39:57'),
       (45, 0, '运营管理', '', '', '', 3, 'M', 'sprite-menu_report', 0, 1, '2021-04-26 14:06:13', '2021-06-29 13:40:10'),
       (49, 12, '菜单查询', 'sys:menu:query', '', '', 1, 'F', '', 0, 1, '2021-04-27 16:00:18', '2021-06-29 13:40:12'),
       (50, 12, '菜单新增', 'sys:menu:add', '', '', 2, 'F', '', 0, 1, '2021-04-27 16:00:38', '2021-06-29 13:40:11'),
       (51, 12, '菜单修改', 'sys:menu:edit', '', '', 3, 'F', '', 0, 1, '2021-04-27 16:01:01', '2021-06-29 13:40:10'),
       (52, 12, '菜单删除', 'sys:menu:delete', '', '', 4, 'F', '', 0, 1, '2021-04-27 16:01:26', '2021-06-29 13:40:11'),
       (53, 11, '角色查询', 'sys:role:query', '', '', 1, 'F', '', 0, 1, '2021-04-27 16:04:12', '2021-06-29 13:40:11'),
       (54, 11, '角色新增', 'sys:role:add', '', '', 2, 'F', '', 0, 1, '2021-04-27 16:04:32', '2021-06-29 13:40:08'),
       (55, 11, '角色编辑', 'sys:role:edit', '', '', 3, 'F', '', 0, 1, '2021-04-27 16:04:42', '2021-06-29 13:40:08'),
       (56, 11, '角色状态切换', 'sys:role:changeStatus', '', '', 4, 'F', '', 0, 1, '2021-04-27 16:04:54',
        '2021-06-29 13:40:07'),
       (57, 10, '用户查询', 'sys:user:query', '', '', 1, 'F', '', 0, 1, '2021-04-27 16:05:37', '2021-06-29 13:40:07'),
       (58, 10, '用户新增', 'sys:user:add', '', '', 2, 'F', '', 0, 1, '2021-04-27 16:06:17', '2021-06-29 13:40:12'),
       (59, 10, '用户编辑', 'sys:user:edit', '', '', 3, 'F', '', 0, 1, '2021-04-27 16:06:56', '2021-06-29 13:40:12'),
       (60, 10, '重置密码', 'sys:user:resetPwd', '', '', 4, 'F', '', 0, 1, '2021-04-27 16:07:09', '2021-06-29 13:40:13'),
       (61, 10, '用户状态切换', 'sys:user:changeStatus', '', '', 5, 'F', '', 0, 1, '2021-04-27 16:10:51',
        '2021-06-29 13:40:13'),
       (62, 1, '日志管理', 'sys:log', '/logManage', '', 4, 'C', '', 0, 1, '2021-04-27 16:12:07', '2021-06-29 13:40:16'),
       (63, 62, '日志查询', 'sys:log:query', '', '', 1, 'F', '', 0, 1, '2021-04-27 16:12:20', '2021-06-29 13:40:15');
COMMIT;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`
(
    `oper_id`       bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `username`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '账户名称，即用户名',
    `fullname`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '姓名',
    `oper_ip`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '主机地址',
    `title`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL DEFAULT '' COMMENT '模块标题',
    `business_type` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `browser`       varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '浏览器',
    `gmt_create`    datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85312 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作日志记录';


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
    `role_code`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '角色唯一标示',
    `role_name`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '角色名称',
    `data_scope`   char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL DEFAULT '' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `role_desc`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
    `sort`         tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '有效状态',
    `del_flag`     tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标识（0-正常,1-删除）',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`role_id`) USING BTREE,
    UNIQUE INDEX `uk_role_name`(`role_name`) USING BTREE,
    UNIQUE INDEX `uk_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role`
VALUES (1, 'ROLE_ADMIN', '超级管理员', '1', '超级管理员', 0, 1, 0, '2020-06-05 07:36:49', '2021-08-16 17:51:06');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`
(
    `id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
    `dept_id` bigint(20) UNSIGNED NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色部门表(暂不用)';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) UNSIGNED NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1270 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `username`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL DEFAULT '' COMMENT '用户名',
    `nickname`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL DEFAULT '' COMMENT '昵称',
    `password`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '密码',
    `salt`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '盐值',
    `avatar`       varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像url',
    `email`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL DEFAULT '' COMMENT '邮箱',
    `phone`        varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL DEFAULT '' COMMENT '手机号',
    `sex`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci       NOT NULL DEFAULT '2' COMMENT '性别(男:1, 女:0, 未知:2)',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否有效',
    `del_flag`     tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `gmt_create`   datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `uk_username`(`username`) USING BTREE,
    UNIQUE INDEX `uk_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '运营用户表\r\n';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user`
VALUES (1, 'admin', '', '{bcrypt}$2a$10$ZLx57sJjo/ofxINlQ4l9MuTg921AIBQJrxzoah.I2LtJhvoWEWy4.',
        'c1ecfbe9c3897ee801c0e2890878c61d',
        'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3155998395,3600507640&fm=26&gp=0.jpg', '1113@qq.com',
        '18510649334', '1', 1, 0, '2020-07-10 16:32:28', '2021-08-03 16:46:22');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_user_role`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role`
VALUES (1, 1, 1);
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
