/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.24 : Database - bs_fans
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bs_fans` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `bs_fans`;

/*Table structure for table `bs_sequence` */

DROP TABLE IF EXISTS `bs_sequence`;

CREATE TABLE `bs_sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='序列表，命名s_[table_name]';

/*Data for the table `bs_sequence` */

insert  into `bs_sequence`(`name`,`current_value`,`increment`) values ('user_code1',100017,1);

/*Table structure for table `data_dict` */

DROP TABLE IF EXISTS `data_dict`;

CREATE TABLE `data_dict` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` varchar(30) NOT NULL COMMENT '数据字典编码',
  `value` varchar(8) NOT NULL COMMENT '数据项值',
  `name` varchar(30) DEFAULT NULL COMMENT '展示值',
  `desc` varchar(30) DEFAULT NULL COMMENT '描述',
  `parent_code` varchar(30) DEFAULT NULL COMMENT '父节点编码',
  `is_valid` varchar(4) DEFAULT NULL COMMENT '是否生效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人编码',
  `updater` int(11) DEFAULT NULL COMMENT '最后修改人编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `data_dict` */

/*Table structure for table `posts_info` */

DROP TABLE IF EXISTS `posts_info`;

CREATE TABLE `posts_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `title` varchar(60) NOT NULL COMMENT '帖子标题',
  `type` varchar(4) NOT NULL COMMENT '"帖子类型\n【首页】：01消息/02视频/03 MV/04 音乐/05 相册/06 行程\n【饭堂】：101普通帖子/102 视频模仿/103 音乐模仿/104 动态图"',
  `content` varchar(2000) NOT NULL COMMENT '帖子正文',
  `user_code` int(11) NOT NULL COMMENT '创建用户',
  `img_url` varchar(500) DEFAULT NULL COMMENT '图片链接列表，以特殊字符相隔_@_',
  `video_url` varchar(500) DEFAULT NULL COMMENT '视频链接列表，以特殊字符相隔_@_',
  `music_url` varchar(500) DEFAULT NULL COMMENT '音乐链接列表，以特殊字符相隔_@_',
  `other_url` varchar(500) DEFAULT NULL COMMENT '其他链接列表，以特殊字符相隔_@_',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `publish_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布日期',
  `status` varchar(4) NOT NULL DEFAULT '01' COMMENT '"帖子状态\n01待审 check\n02正常 normal\n03锁定 lock\n04删除 delete "',
  `level` varchar(4) NOT NULL DEFAULT 'PT' COMMENT '"帖子级别\n普通 PT\n精品 JP\n高亮绿 GG\n高亮红 GR"',
  `oper_list` varchar(110) DEFAULT NULL COMMENT '操作管理员列表',
  `praise_list1` varchar(1100) DEFAULT NULL COMMENT '点赞用户列表1',
  `praise_list2` varchar(1100) DEFAULT NULL COMMENT '点赞用户列表2',
  `att_list1` varchar(1100) DEFAULT NULL COMMENT '关注用户列表1',
  `att_list2` varchar(1100) DEFAULT NULL COMMENT '关注用户列表2',
  `unatt_list` varchar(1100) DEFAULT NULL COMMENT '取消关注用户列表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `posts_info` */

/*Table structure for table `posts_operate` */

DROP TABLE IF EXISTS `posts_operate`;

CREATE TABLE `posts_operate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `oper_code` int(11) NOT NULL COMMENT '操作员编码',
  `oper_type` varchar(4) NOT NULL COMMENT '"操作类型\n  01 审核发布\n  02 修改【保留】\n  03 删除\n  04 高亮绿色\n  05 高亮红色\n  06 点赞\n  07 减赞\n  08 举报"',
  `oper_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `oper_desc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `posts_operate` */

/*Table structure for table `posts_replay_comment` */

DROP TABLE IF EXISTS `posts_replay_comment`;

CREATE TABLE `posts_replay_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `posts_id` int(11) NOT NULL COMMENT '帖子ID',
  `replay_type` varchar(4) NOT NULL DEFAULT '01' COMMENT '"操作类型\n  01 评论\n  02 回复"',
  `user_code` int(11) NOT NULL COMMENT '评论/回复用户编码',
  `comment` varchar(800) NOT NULL COMMENT '回复内容',
  `annex_url` varchar(200) DEFAULT NULL COMMENT '附件url',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级评论/回复id',
  `parent_user_code` int(11) DEFAULT NULL COMMENT '上级评论/回复用户编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `posts_replay_comment` */

/*Table structure for table `user_attention` */

DROP TABLE IF EXISTS `user_attention`;

CREATE TABLE `user_attention` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `att_type` varchar(4) NOT NULL COMMENT '类型，0 关注，1 取消关注',
  `from_user_code` int(11) NOT NULL COMMENT '发起关注用户编码',
  `to_user_code` int(11) DEFAULT NULL COMMENT '被关注用户编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_attention` */

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_code` int(11) NOT NULL COMMENT '用户编码',
  `user_name` varchar(50) NOT NULL COMMENT '用户昵称',
  `phone` varchar(11) NOT NULL COMMENT '手机号，登录账号',
  `password` varchar(50) NOT NULL COMMENT '登录密码',
  `user_status` tinyint(1) NOT NULL COMMENT '状态  1 正常，2 冻结，3 黑名单',
  `bg_img_url` varchar(500) DEFAULT NULL COMMENT '背景图片url',
  `head_img_url` varchar(100) DEFAULT NULL COMMENT '头像图片url',
  `gender` varchar(4) DEFAULT 'un' COMMENT '性别，男 m，女 w，未知 un',
  `constellation` varchar(4) DEFAULT NULL COMMENT '星座,参看详细枚举值',
  `city_code` varchar(4) DEFAULT NULL COMMENT '所在城市，参看详细城市枚举值',
  `active_level` tinyint(4) NOT NULL COMMENT '活跃等级，自然数',
  `user_role` varchar(4) NOT NULL COMMENT '角色，01 普通用户，02 管理员，03 超级管理员',
  `signature` varchar(150) DEFAULT NULL COMMENT '个性签名',
  `platform` varchar(12) NOT NULL COMMENT '注册终端平台，ios，android',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '2099-12-31 23:59:59' COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`user_code`,`user_name`,`phone`,`password`,`user_status`,`bg_img_url`,`head_img_url`,`gender`,`constellation`,`city_code`,`active_level`,`user_role`,`signature`,`platform`,`create_time`,`update_time`) values (2,10025001,'MRlee','18612983626','bb4c039ff449764e7a04433beb0bd11e',0,'fdg','fdg','1','1','010',12,'1','1423453','ios','2016-03-20 13:24:16','2099-12-31 23:59:59'),(4,100015,'18612983627','18612983627','f2eaee5cd287adc9c7b47cc5b10873df',1,NULL,NULL,'un',NULL,NULL,0,'01',NULL,'ios','2016-03-20 15:38:33','2099-12-31 23:59:59'),(5,100016,'13661008708','13661008708','70d6711cb4301242217c89003c858803',1,NULL,NULL,'un',NULL,NULL,0,'01',NULL,'ios','2016-03-20 20:27:42','2099-12-31 23:59:59'),(6,100017,'13661008708','13661008708','70d6711cb4301242217c89003c858803',1,NULL,NULL,'un',NULL,NULL,0,'01',NULL,'ios','2016-03-20 20:28:40','2099-12-31 23:59:59');

/*Table structure for table `user_operate_record` */

DROP TABLE IF EXISTS `user_operate_record`;

CREATE TABLE `user_operate_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `oper_code` int(11) NOT NULL COMMENT '管理员编码',
  `user_code` int(11) DEFAULT NULL COMMENT '被操作用户编码',
  `oper_type` varchar(4) NOT NULL COMMENT '"操作类型：\n01 冻结\n02 解除冻结\n03 拉黑名单\n04 解除黑名单\n05 设置管理员"',
  `oper_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `oper_desc` varchar(100) DEFAULT NULL COMMENT '操作描述，01 冻结和03 拉黑，不能为空，必须写明原因。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_operate_record` */

/*Table structure for table `user_praise` */

DROP TABLE IF EXISTS `user_praise`;

CREATE TABLE `user_praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `from_user_code` int(11) NOT NULL COMMENT '发起赞用户编码',
  `to_user_code` int(11) NOT NULL COMMENT '被赞用户编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_praise` */

/* Function  structure for function  `currval` */

/*!50003 DROP FUNCTION IF EXISTS `currval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN  
  
DECLARE S_VALUE INTEGER;  
  
SET S_VALUE = 0;  
  
SELECT current_value INTO S_VALUE FROM bs_sequence WHERE NAME = seq_name;  
  
RETURN S_VALUE;  
  
END */$$
DELIMITER ;

/* Function  structure for function  `nextval` */

/*!50003 DROP FUNCTION IF EXISTS `nextval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN  
  
UPDATE bs_sequence SET current_value = current_value + increment WHERE NAME = seq_name;  
  
RETURN currval(seq_name);  
  
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
