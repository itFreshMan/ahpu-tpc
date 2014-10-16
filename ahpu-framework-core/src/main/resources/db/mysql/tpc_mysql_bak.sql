/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.19 : Database - tpc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tpc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tpc`;

/*Table structure for table `tpc_attachment` */

DROP TABLE IF EXISTS `tpc_attachment`;

CREATE TABLE `tpc_attachment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FILE_NAME` varchar(64) DEFAULT NULL,
  `FILE_SIZE` bigint(20) DEFAULT NULL,
  `FILE_TYPE` varchar(32) DEFAULT NULL,
  `ATTACHMENT_TYPE` varchar(32) DEFAULT NULL,
  `DATA_ID` bigint(20) DEFAULT NULL,
  `FILE_CODE` varchar(64) DEFAULT NULL,
  `DESCN` varchar(256) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tpc_attachment` */

/*Table structure for table `tpc_dict_entry` */

DROP TABLE IF EXISTS `tpc_dict_entry`;

CREATE TABLE `tpc_dict_entry` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(64) DEFAULT NULL,
  `ENABLED` char(1) NOT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `ORDER_INDEX` bigint(20) DEFAULT NULL,
  `DICT_TYPE_ID` bigint(20) NOT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK533311343A8CB7FB` (`DICT_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_dict_entry` */

insert  into `tpc_dict_entry`(`ID`,`CODE`,`ENABLED`,`NAME`,`ORDER_INDEX`,`DICT_TYPE_ID`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'M','Y','男',1,10001,NULL,NULL,'admin',NULL,NULL),(2,'F','Y','女',2,10001,NULL,NULL,'admin',NULL,'admin'),(3,'N','Y','未知',3,10001,NULL,NULL,'admin',NULL,NULL),(4,'Y','Y','叶子节点',1,10002,NULL,NULL,'admin',NULL,NULL),(5,'N','Y','非叶子节点',2,10002,NULL,NULL,'admin',NULL,NULL),(6,'Y','Y','有效',1,10003,NULL,NULL,'admin',NULL,NULL),(7,'N','Y','无效',2,10003,NULL,NULL,'admin',NULL,NULL),(8,'menu','Y','菜单',1,10004,NULL,NULL,'admin',NULL,NULL),(9,'uri','Y','URI',2,10004,NULL,NULL,'admin',NULL,NULL),(10,'method','Y','方法',3,10004,NULL,NULL,'admin',NULL,NULL),(11,'flow','Y','主流程',1,10006,NULL,NULL,NULL,NULL,NULL),(12,'subflow','Y','子流程',2,10006,NULL,NULL,NULL,NULL,NULL),(13,'system','Y','系统管理',1,10005,NULL,NULL,'admin',NULL,NULL),(14,'0551','Y','合肥',1,10007,NULL,NULL,'admin',NULL,NULL),(15,'0552','Y','蚌埠',2,10007,NULL,NULL,'admin',NULL,NULL),(16,'0553','Y','芜湖',3,10007,NULL,NULL,'admin',NULL,NULL),(171,'selfPlatform','Y','个人工作室',2,10005,NULL,'2014-09-05 09:07:02','admin','2014-09-05 09:09:37','admin');

/*Table structure for table `tpc_dict_type` */

DROP TABLE IF EXISTS `tpc_dict_type`;

CREATE TABLE `tpc_dict_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE` varchar(64) DEFAULT NULL,
  `CODE` varchar(64) DEFAULT NULL,
  `MEMO` varchar(256) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_dict_type` */

insert  into `tpc_dict_type`(`ID`,`MODULE`,`CODE`,`MEMO`,`NAME`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (10001,'system','CORE.GENDER','性别：男、女、未知','性别',NULL,NULL,'admin',NULL,NULL),(10002,'system','CORE.NODE.TYPE','节点类型：叶子节点和非叶子节点','节点类型',NULL,NULL,NULL,NULL,NULL),(10003,'system','CORE.ENABLED','状态有效性：有效，无效','状态有效性',NULL,NULL,NULL,NULL,NULL),(10004,'system','CORE.RESOURCE.TYPE','资源类型：URI、方法、菜单','资源类型',NULL,NULL,NULL,NULL,NULL),(10005,'system','CORE.MODULE','模块类型：系统管理','模块类型',NULL,NULL,'admin',NULL,NULL),(10006,'system','CORE.FLOW.TYPE','流程类型','流程类型',NULL,NULL,NULL,NULL,NULL),(10007,'system','CORE.ORG.AREA','机构的所属区域','机构区域',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tpc_login_log` */

DROP TABLE IF EXISTS `tpc_login_log`;

CREATE TABLE `tpc_login_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SESSION_ID` varchar(256) NOT NULL,
  `ORG_NAME` varchar(64) NOT NULL,
  `USER_CODE` varchar(64) NOT NULL,
  `CLIENT_TYPE` varchar(128) DEFAULT NULL,
  `IP` varchar(64) DEFAULT NULL,
  `LOGIN_DATE` datetime DEFAULT NULL,
  `LOGOUT_DATE` datetime DEFAULT NULL,
  `LOGIN_STATUS` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_login_log` */

insert  into `tpc_login_log`(`ID`,`SESSION_ID`,`ORG_NAME`,`USER_CODE`,`CLIENT_TYPE`,`IP`,`LOGIN_DATE`,`LOGOUT_DATE`,`LOGIN_STATUS`) values (31,'1seq2fqz59p18eeb90efjdh6s','安徽工程大学','admin','Firefox','127.0.0.1','2014-09-04 21:50:11',NULL,'SUCCESS'),(41,'8esvd3e68pny1olvogy83yt2s','安徽工程大学','admin','Firefox','127.0.0.1','2014-09-05 09:01:19',NULL,'SUCCESS');

/*Table structure for table `tpc_menu` */

DROP TABLE IF EXISTS `tpc_menu`;

CREATE TABLE `tpc_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCN` varchar(256) DEFAULT NULL,
  `ICONCLS` varchar(30) DEFAULT NULL,
  `ISLEAF` char(1) DEFAULT NULL,
  `LEVEL_` int(11) DEFAULT NULL,
  `MENUSEQ` varchar(128) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `PARENTID` bigint(20) DEFAULT NULL,
  `THESORT` int(11) NOT NULL,
  `RESOURCE_ID` bigint(20) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKBB5B107D3B1AA04E` (`RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_menu` */

insert  into `tpc_menu`(`ID`,`DESCN`,`ICONCLS`,`ISLEAF`,`LEVEL_`,`MENUSEQ`,`NAME`,`PARENTID`,`THESORT`,`RESOURCE_ID`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'21',NULL,'N',1,NULL,'系统管理',0,2,12,NULL,NULL,NULL,NULL,NULL),(2,'资源管理',NULL,'Y',2,NULL,'资源管理',1,4,5,NULL,NULL,NULL,'2011-11-19 13:36:37','admin'),(3,NULL,NULL,'Y',2,NULL,'菜单管理',1,6,6,NULL,NULL,NULL,NULL,'admin'),(4,NULL,NULL,'Y',2,NULL,'角色管理',1,3,7,NULL,NULL,NULL,NULL,'admin'),(5,'机构管理',NULL,'Y',2,NULL,'机构管理',1,1,8,NULL,NULL,'admin',NULL,NULL),(7,'用户管理',NULL,'Y',2,NULL,'用户管理',1,2,10,NULL,NULL,'admin',NULL,NULL),(8,'业务字典',NULL,'Y',2,NULL,'业务字典',1,7,11,NULL,NULL,'admin',NULL,NULL),(10,'个人工作室',NULL,'N',1,NULL,'个人工作室',0,1,15,NULL,NULL,'admin',NULL,'admin'),(171,'',NULL,'Y',2,NULL,'文件管理',1,7,221,NULL,NULL,NULL,NULL,NULL),(181,'',NULL,'Y',2,NULL,'日志管理',1,8,231,NULL,NULL,NULL,NULL,NULL),(182,'',NULL,'Y',2,NULL,'SQL管理',1,9,232,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tpc_operation` */

DROP TABLE IF EXISTS `tpc_operation`;

CREATE TABLE `tpc_operation` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(64) DEFAULT NULL,
  `MEMO` varchar(256) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `ORDER_INDEX` bigint(20) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_operation` */

insert  into `tpc_operation`(`ID`,`CODE`,`MEMO`,`NAME`,`ORDER_INDEX`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'core.add','添加','添加',NULL,NULL,NULL,'admin',NULL,'admin'),(2,'core.update','更新','更新',NULL,NULL,NULL,'admin',NULL,NULL),(3,'core.remove','删除','删除',NULL,NULL,NULL,'admin',NULL,NULL),(4,'core.authRole','授予角色','授予角色',NULL,NULL,NULL,'admin',NULL,NULL),(5,'core.authResource','授予资源','授予资源',NULL,NULL,NULL,'admin',NULL,NULL),(6,'core.authUser','授权用户','授权用户',NULL,NULL,NULL,'admin',NULL,NULL),(7,'core.addSiblingNode','添加同级节点','添加同级节点',NULL,NULL,NULL,'admin',NULL,NULL),(8,'core.addChildNode','添加子节点','添加子节点',NULL,NULL,NULL,'admin',NULL,NULL),(9,'core.updateNode','更新节点','更新节点',NULL,NULL,NULL,'admin',NULL,NULL),(10,'core.removeNode','删除节点','删除节点',NULL,NULL,NULL,'admin',NULL,NULL),(11,'core.orderNode','节点排序','节点排序',NULL,NULL,NULL,'admin',NULL,NULL);

/*Table structure for table `tpc_organization` */

DROP TABLE IF EXISTS `tpc_organization`;

CREATE TABLE `tpc_organization` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LEVEL_` int(11) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `AREACODE` varchar(8) DEFAULT NULL,
  `ORGSEQ` varchar(128) DEFAULT NULL,
  `PARENTID` bigint(20) NOT NULL,
  `THESORT` int(11) NOT NULL,
  `DESCN` varchar(256) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_organization` */

insert  into `tpc_organization`(`ID`,`LEVEL_`,`NAME`,`AREACODE`,`ORGSEQ`,`PARENTID`,`THESORT`,`DESCN`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,1,'安徽工程大学','0553','1.',0,1,'安徽工程大学',NULL,NULL,'admin','2014-09-04 21:33:20','admin'),(2,2,'机电学院','0553','1.2.',1,2,'机电学院',NULL,NULL,'admin','2014-09-04 21:33:36','admin'),(3,2,'计算机学院','0553','1.3.',1,3,'计算机学院',NULL,NULL,'admin','2014-09-04 21:33:53','admin'),(41,2,'人文学院','0553','1.41.',1,4,'人文学院',NULL,'2014-09-04 21:34:20','admin','2014-09-04 21:34:20','admin');

/*Table structure for table `tpc_primarykey` */

DROP TABLE IF EXISTS `tpc_primarykey`;

CREATE TABLE `tpc_primarykey` (
  `CODE` bigint(20) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`CODE`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tpc_primarykey` */

insert  into `tpc_primarykey`(`CODE`,`NAME`) values (10,'TPC_ATTACHMENT'),(10,'TPC_ROLE'),(30,'TPC_CONFIG'),(30,'TPC_DICT_TYPE'),(50,'tpc_login_log'),(50,'TPC_ORGANIZATION'),(60,'BusinessStarFlow'),(80,'TEST_FLOW_LEAVE'),(90,'TPC_OPERATION'),(100,'TPC_USER'),(180,'TPC_DICT_ENTRY'),(190,'TPC_MENU'),(240,'TPC_RESOURCE');

/*Table structure for table `tpc_resource` */

DROP TABLE IF EXISTS `tpc_resource`;

CREATE TABLE `tpc_resource` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE` varchar(64) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `ACTION` varchar(128) DEFAULT NULL,
  `DESCN` varchar(256) DEFAULT NULL,
  `ENABLED` char(1) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `TYPE` varchar(32) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_resource` */

insert  into `tpc_resource`(`ID`,`MODULE`,`NAME`,`ACTION`,`DESCN`,`ENABLED`,`PRIORITY`,`TYPE`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (4,NULL,'所有请求','/**',NULL,'Y',999,'uri',NULL,NULL,NULL,NULL,'admin'),(5,'system','资源管理','./resource/index','权限管理','Y',4,'menu',NULL,NULL,'admin',NULL,NULL),(6,'system','菜单管理','./menu/index','','Y',5,'menu',NULL,NULL,NULL,NULL,NULL),(7,'system','角色管理','./role/index','','Y',6,'menu',NULL,NULL,'admin',NULL,NULL),(8,'system','机构管理','./org/index','机构管理','Y',7,'menu',NULL,NULL,'admin',NULL,NULL),(10,'system','用户管理','./user/index','用户管理','Y',9,'menu',NULL,NULL,'admin',NULL,NULL),(11,'system','业务字典','./dict/index','业务字典','Y',10,'menu',NULL,NULL,'admin',NULL,NULL),(12,'system','系统管理','/systemadmin','系统管理','Y',11,'menu',NULL,NULL,NULL,NULL,NULL),(15,'selfPlatform','个人工作室','/workspace','个人工作室','Y',13,'menu',NULL,NULL,'admin',NULL,NULL),(221,'system','文件管理','/attachment/index','','Y',7,'menu',NULL,NULL,NULL,NULL,NULL),(231,'system','日志管理','/loginLog/index','','Y',8,'menu',NULL,NULL,NULL,NULL,NULL),(232,'','SQL管理','/schemaVersion/index','无效的菜单。。','N',9,'menu',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tpc_role` */

DROP TABLE IF EXISTS `tpc_role`;

CREATE TABLE `tpc_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(64) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `ENABLED` char(1) NOT NULL,
  `DESCN` varchar(256) DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_role` */

insert  into `tpc_role`(`ID`,`CODE`,`NAME`,`ENABLED`,`DESCN`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'ROLE_ADMIN','管理员角色','Y',NULL,NULL,NULL,NULL,NULL,NULL),(2,'ROLE_USER','普通用户','Y',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tpc_role_resource` */

DROP TABLE IF EXISTS `tpc_role_resource`;

CREATE TABLE `tpc_role_resource` (
  `ROLE_ID` bigint(20) NOT NULL,
  `RESOURCE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tpc_role_resource` */

insert  into `tpc_role_resource`(`ROLE_ID`,`RESOURCE_ID`) values (1,4),(1,5),(1,6),(1,7),(1,8),(1,10),(1,11),(1,12),(1,15),(1,23),(1,221),(1,231),(2,221),(2,231);

/*Table structure for table `tpc_user` */

DROP TABLE IF EXISTS `tpc_user`;

CREATE TABLE `tpc_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_CODE` varchar(64) NOT NULL,
  `USER_NAME` varchar(64) NOT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `SALT` varchar(32) DEFAULT NULL,
  `AREA_ID` bigint(20) DEFAULT NULL,
  `ORG_ID` bigint(20) DEFAULT NULL,
  `BIRTHDAY` varchar(16) DEFAULT NULL,
  `GENDER` char(1) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `PHONE_NO` varchar(16) DEFAULT NULL,
  `MPHONE_NO` varchar(16) DEFAULT NULL,
  `ERROR_COUNT` int(11) DEFAULT NULL,
  `LAST_LOGIN_TIME` datetime DEFAULT NULL,
  `PASSWD_INVAL_TIME` datetime DEFAULT NULL,
  `LOCK_TIME` datetime DEFAULT NULL,
  `DEL_FLAG` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tpc_user` */

insert  into `tpc_user`(`ID`,`USER_CODE`,`USER_NAME`,`PASSWORD`,`SALT`,`AREA_ID`,`ORG_ID`,`BIRTHDAY`,`GENDER`,`EMAIL`,`PHONE_NO`,`MPHONE_NO`,`ERROR_COUNT`,`LAST_LOGIN_TIME`,`PASSWD_INVAL_TIME`,`LOCK_TIME`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'admin','系统管理员','d6d252bac8e22d4db7e11db4a3a6db1d','6gilcjmzowo6vav1lxmqd6cmograasqq',1,1,NULL,'M','jhuaishuang@126.com',NULL,NULL,0,'2014-09-05 09:01:19',NULL,NULL,0,'2011-11-26 09:27:52','anonymousUser','2011-11-26 08:31:06','admin');

/*Table structure for table `tpc_user_role` */

DROP TABLE IF EXISTS `tpc_user_role`;

CREATE TABLE `tpc_user_role` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tpc_user_role` */

insert  into `tpc_user_role`(`USER_ID`,`ROLE_ID`) values (1,1),(1,2),(2,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
