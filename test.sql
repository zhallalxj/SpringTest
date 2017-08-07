-- 初始SQL 用户默认密码123456
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT '',
  `parentId` bigint(20) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `uri` varchar(128) DEFAULT '',
  `permission_value` varchar(64) NOT NULL DEFAULT '',
  `type` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_value` (`permission_value`) USING BTREE,
  KEY `sys_permission_b16a6265` (`create_time`) USING BTREE,
  KEY `sys_permission_e8a63279` (`modify_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '用户管理', '10010', null, 'user', 'sys_user', '0', '1', '2017-08-03 11:32:28', '2017-08-03 11:32:31');
INSERT INTO `t_permission` VALUES ('2', '查询用户', '1', null, '/user/list.html', 'sys_user_find', '1', '2', '2017-08-03 11:33:14', '2017-08-03 11:33:16');
INSERT INTO `t_permission` VALUES ('3', '新增用户', '1', null, '/user/addUser.html', 'sys_user_addUI', '1', '3', '2017-08-03 11:35:13', '2017-08-03 11:35:17');
INSERT INTO `t_permission` VALUES ('4', '角色管理', '10010', null, 'role', 'sys_role', '0', '4', '2017-08-03 11:35:53', '2017-08-03 11:35:55');
INSERT INTO `t_permission` VALUES ('5', '角色列表', '4', null, '/role/list.html', 'sys_role_find', '1', '5', '2017-08-03 11:36:36', '2017-08-03 11:36:38');
INSERT INTO `t_permission` VALUES ('6', '新增角色', '4', null, '/role/addRole.html', 'sys_role_add', '1', '6', '2017-08-03 11:37:07', '2017-08-03 11:37:11');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT '',
  `role_value` varchar(64) NOT NULL DEFAULT '',
  `description` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_value` (`role_value`),
  KEY `sys_role_b16a6265` (`create_time`),
  KEY `sys_role_e8a63279` (`modify_time`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', 'admin', '超级管理员', '2017-08-03 11:39:06', '2017-08-03 11:39:08');
INSERT INTO `t_role` VALUES ('2', '用户管理员', 'user_manager', '用户管理员', '2017-08-03 11:38:13', '2017-08-03 11:38:14');
INSERT INTO `t_role` VALUES ('3', '角色管理员', 'role_manager', '角色管理员', '2017-08-03 11:38:41', '2017-08-03 11:38:43');
INSERT INTO `t_role` VALUES ('22', '测试', 't', '测试', '2017-08-07 14:50:42', '2017-08-07 14:50:42');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_role_permission_permission_id_84f4f7b6_fk_sys_permission_id` (`permission_id`),
  KEY `sys_role_permission_role_id_ff575af9_fk_sys_role_id` (`role_id`),
  CONSTRAINT `t_role_permission_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
  CONSTRAINT `t_role_permission_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('7', '1', '2', null);
INSERT INTO `t_role_permission` VALUES ('8', '2', '2', null);
INSERT INTO `t_role_permission` VALUES ('9', '3', '2', null);
INSERT INTO `t_role_permission` VALUES ('13', '4', null, '1');
INSERT INTO `t_role_permission` VALUES ('14', '5', null, '1');
INSERT INTO `t_role_permission` VALUES ('15', '1', null, '5');
INSERT INTO `t_role_permission` VALUES ('16', '2', null, '5');
INSERT INTO `t_role_permission` VALUES ('24', '1', '3', null);
INSERT INTO `t_role_permission` VALUES ('25', '2', '3', null);
INSERT INTO `t_role_permission` VALUES ('26', '4', '3', null);
INSERT INTO `t_role_permission` VALUES ('27', '5', '3', null);
INSERT INTO `t_role_permission` VALUES ('28', '6', '3', null);
INSERT INTO `t_role_permission` VALUES ('34', '1', '1', null);
INSERT INTO `t_role_permission` VALUES ('35', '2', '1', null);
INSERT INTO `t_role_permission` VALUES ('36', '3', '1', null);
INSERT INTO `t_role_permission` VALUES ('37', '4', '1', null);
INSERT INTO `t_role_permission` VALUES ('38', '5', '1', null);
INSERT INTO `t_role_permission` VALUES ('39', '6', '1', null);
INSERT INTO `t_role_permission` VALUES ('40', '6', null, '1');
INSERT INTO `t_role_permission` VALUES ('47', '1', '22', null);
INSERT INTO `t_role_permission` VALUES ('48', '2', '22', null);
INSERT INTO `t_role_permission` VALUES ('49', '3', '22', null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '18501665417', '$2a$10$FA8Rp9V7SqJ2LA22TbPHc.ozb9VsgftAiwtjWIY.0KHYzGXOxYHvm', '2017-08-02 11:10:11', '2017-08-02 11:10:11');
INSERT INTO `t_user` VALUES ('3', '15890687905', '$2a$10$RzoIdvxfkp6lw2PiOIp2WutnWQRQNaWeMJY7I4AALV/h3apZl971G', '2017-08-02 11:15:48', '2017-08-02 11:15:48');
INSERT INTO `t_user` VALUES ('5', 'test2', '$2a$10$dDUh3nzsgbMS.laOvYT9ueVVfCxebFeYEmPm2j5kiUow5fa.vP72m', '2017-08-03 18:09:13', '2017-08-03 18:09:13');
INSERT INTO `t_user` VALUES ('9', 'test3', '$2a$10$3WhhKQrI4GMkxTspPHl3v.DM4ezVAiQ40V4nz8PyJAnZDLy579kJi', '2017-08-03 18:13:56', '2017-08-03 18:13:56');
INSERT INTO `t_user` VALUES ('11', 'zhaohang', '$2a$10$/ABUh4md1gAK.HVKQggSVehelGOGXAxxqOeSGWm22XFHh4UQOvsny', '2017-08-04 10:15:59', '2017-08-04 10:15:59');
INSERT INTO `t_user` VALUES ('12', '18621108569', '$2a$10$2rEw2JPSxHDjGc.NTrumUelPe1zNUsWXdTBbvW98UpzT5TyO0ThTm', '2017-08-04 16:40:20', '2017-08-04 16:40:20');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_role_role_id_63624973_fk_sys_role_id` (`role_id`),
  KEY `sys_user_role_user_id_5f2fb964_fk_sys_user_id` (`user_id`),
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '12');
INSERT INTO `t_user_role` VALUES ('51', '2', '1');
