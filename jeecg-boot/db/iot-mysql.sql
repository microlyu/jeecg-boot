
DROP TABLE IF EXISTS `iot_device`;
CREATE TABLE `iot_device` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `device_type` varchar(36) NOT NULL COMMENT '设备类型',
  `device_name` varchar(32) DEFAULT NULL COMMENT '设备名称',
  `device_sn` varchar(64) DEFAULT NULL COMMENT '设备序列号',
  `device_pic` varchar(128) DEFAULT NULL COMMENT '设备图片',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `onboad_point` varchar(36) DEFAULT NULL COMMENT '接入点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `iot_device_sensor`;
CREATE TABLE `iot_device_sensor` (
  `id` varchar(36) NOT NULL,
  `device_id` varchar(36) NOT NULL COMMENT '设备ID',
  `sensor_code` varchar(64) DEFAULT NULL COMMENT '指标编码',
  `sensor_name` varchar(64) DEFAULT NULL COMMENT '指标名称',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `iot_device_type`;
CREATE TABLE `iot_device_type` (
  `id` varchar(36) NOT NULL,
  `type_code` varchar(16) DEFAULT NULL COMMENT '类型编码',
  `type_name` varchar(64) DEFAULT NULL COMMENT '类型名称',
  `type_pic` varchar(256) DEFAULT NULL COMMENT '类型图片',
  `pid` varchar(32) DEFAULT NULL COMMENT '父级节点',
  `has_child` varchar(3) DEFAULT NULL COMMENT '是否有子节点',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `iot_onboard_point`;
CREATE TABLE `iot_onboard_point` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `onboard_name` varchar(32) DEFAULT NULL COMMENT '接入点名称',
  `protocol` varchar(32) DEFAULT NULL COMMENT '接入点协议',
  `observe_interval` int(11) DEFAULT NULL COMMENT '主动拉取的采集间隔（秒）',
  `auth_account` varchar(32) DEFAULT NULL COMMENT '认证账户',
  `auth_password` varchar(32) DEFAULT NULL COMMENT '认证密码',
  `param1` varchar(64) DEFAULT NULL COMMENT '参数一',
  `param2` varchar(32) DEFAULT NULL COMMENT '参数2',
  `param3` varchar(32) DEFAULT NULL COMMENT '参数3',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `access_url` varchar(64) DEFAULT NULL COMMENT '访问地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `sys_permission` (`id`,`parent_id`,`name`,`url`,`component`,`component_name`,`redirect`,`menu_type`,`perms`,`perms_type`,`sort_no`,`always_show`,`icon`,`is_route`,`is_leaf`,`keep_alive`,`hidden`,`description`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`rule_flag`,`status`,`internal_or_external`) VALUES ('1286195059603140609','','设备管理','/iot','layouts/RouteView',NULL,NULL,0,NULL,'1',1.00,0,'shopping',1,0,0,0,NULL,'admin','2020-07-23 15:02:53','admin','2020-07-23 15:07:52',0,0,'1',0);
INSERT INTO `sys_permission` (`id`,`parent_id`,`name`,`url`,`component`,`component_name`,`redirect`,`menu_type`,`perms`,`perms_type`,`sort_no`,`always_show`,`icon`,`is_route`,`is_leaf`,`keep_alive`,`hidden`,`description`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`rule_flag`,`status`,`internal_or_external`) VALUES ('1286195786601852929','1286195059603140609','设备类型','/iot/devicetypes','iot/DeviceTypeList',NULL,NULL,1,NULL,'1',2.00,0,'ordered-list',1,1,0,0,NULL,'admin','2020-07-23 15:05:47','admin','2020-07-24 16:27:42',0,0,'1',0);
INSERT INTO `sys_permission` (`id`,`parent_id`,`name`,`url`,`component`,`component_name`,`redirect`,`menu_type`,`perms`,`perms_type`,`sort_no`,`always_show`,`icon`,`is_route`,`is_leaf`,`keep_alive`,`hidden`,`description`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`rule_flag`,`status`,`internal_or_external`) VALUES ('1286578353238732802','1286195059603140609','设备清单','/iot/devices','iot/DeviceList',NULL,NULL,1,NULL,'1',1.00,0,'car',1,1,0,0,NULL,'admin','2020-07-24 16:25:58',NULL,NULL,0,0,'1',0);
INSERT INTO `sys_permission` (`id`,`parent_id`,`name`,`url`,`component`,`component_name`,`redirect`,`menu_type`,`perms`,`perms_type`,`sort_no`,`always_show`,`icon`,`is_route`,`is_leaf`,`keep_alive`,`hidden`,`description`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`rule_flag`,`status`,`internal_or_external`) VALUES ('1286578739408302081','1286195059603140609','接入点','/iot/onboardpoints','iot/OnboardPointList',NULL,NULL,1,NULL,'1',3.00,0,'border',1,1,0,0,NULL,'admin','2020-07-24 16:27:30','admin','2020-07-24 16:28:41',0,0,'1',0);
