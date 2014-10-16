/*Table structure for table `usi_attachment` */

DROP TABLE IF EXISTS `usi_attachment`;

CREATE TABLE `usi_attachment` (
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `usi_attachment` */

/*Table structure for table `usi_dict_entry` */

DROP TABLE IF EXISTS `usi_dict_entry`;

CREATE TABLE `usi_dict_entry` (
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
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

/*Data for the table `usi_dict_entry` */

INSERT  INTO `usi_dict_entry`(`ID`,`CODE`,`ENABLED`,`NAME`,`ORDER_INDEX`,`DICT_TYPE_ID`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) VALUES (1,'M','Y','男',1,10001,NULL,NULL,'admin',NULL,NULL),(2,'F','Y','女',2,10001,NULL,NULL,'admin',NULL,'admin'),(3,'N','Y','未知',3,10001,NULL,NULL,'admin',NULL,NULL),(4,'Y','Y','叶子节点',1,10002,NULL,NULL,'admin',NULL,NULL),(5,'N','Y','非叶子节点',2,10002,NULL,NULL,'admin',NULL,NULL),(6,'Y','Y','有效',1,10003,NULL,NULL,'admin',NULL,NULL),(7,'N','Y','无效',2,10003,NULL,NULL,'admin',NULL,NULL),(8,'menu','Y','菜单',1,10004,NULL,NULL,'admin',NULL,NULL),(9,'uri','Y','URI',2,10004,NULL,NULL,'admin',NULL,NULL),(10,'method','Y','方法',3,10004,NULL,NULL,'admin',NULL,NULL),(11,'flow','Y','主流程',1,10006,NULL,NULL,NULL,NULL,NULL),(12,'subflow','Y','子流程',2,10006,NULL,NULL,NULL,NULL,NULL), (13,'system','Y','系统管理',1,10005,NULL,NULL,'admin',NULL,NULL),(14,'0551','Y','合肥',1,10007,NULL,NULL,'admin',NULL,NULL),(15,'0552','Y','蚌埠',2,10007,NULL,NULL,'admin',NULL,NULL),(16,'0553','Y','芜湖',3,10007,NULL,NULL,'admin',NULL,NULL);

/*Table structure for table `usi_dict_type` */

DROP TABLE IF EXISTS `usi_dict_type`;

CREATE TABLE `usi_dict_type` (
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
) ENGINE=InnoDB AUTO_INCREMENT=10007 DEFAULT CHARSET=utf8;

/*Data for the table `usi_dict_type` */

INSERT  INTO `usi_dict_type`(`ID`,`MODULE`,`CODE`,`MEMO`,`NAME`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) VALUES (10001,'system','CORE.GENDER','性别：男、女、未知','性别',NULL,NULL,'admin',NULL,NULL),(10002,'system','CORE.NODE.TYPE','节点类型：叶子节点和非叶子节点','节点类型',NULL,NULL,NULL,NULL,NULL),(10003,'system','CORE.ENABLED','状态有效性：有效，无效','状态有效性',NULL,NULL,NULL,NULL,NULL),(10004,'system','CORE.RESOURCE.TYPE','资源类型：URI、方法、菜单','资源类型',NULL,NULL,NULL,NULL,NULL),(10006,'system','CORE.FLOW.TYPE','流程类型','流程类型',NULL,NULL,NULL,NULL,NULL),(10005,'system','CORE.MODULE','模块类型：系统管理','模块类型',NULL,NULL,'admin',NULL,NULL),(10007,'system','CORE.ORG.AREA','机构的所属区域','机构区域',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `usi_login_log` */

DROP TABLE IF EXISTS `usi_login_log`;

CREATE TABLE `usi_login_log` (
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

/*Data for the table `usi_login_log` */

/*Table structure for table `usi_menu` */

DROP TABLE IF EXISTS `usi_menu`;

CREATE TABLE `usi_menu` (
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

/*Data for the table `usi_menu` */

insert  into `usi_menu`(`ID`,`DESCN`,`ICONCLS`,`ISLEAF`,`LEVEL_`,`MENUSEQ`,`NAME`,`PARENTID`,`THESORT`,`RESOURCE_ID`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'21',NULL,'N',1,NULL,'系统管理',0,2,12,NULL,NULL,NULL,NULL,NULL),(2,'资源管理',NULL,'Y',2,NULL,'资源管理',1,4,5,NULL,NULL,NULL,'2011-11-19 13:36:37','admin'),(3,NULL,NULL,'Y',2,NULL,'菜单管理',1,6,6,NULL,NULL,NULL,NULL,'admin'),(4,NULL,NULL,'Y',2,NULL,'角色管理',1,3,7,NULL,NULL,NULL,NULL,'admin'),(5,'机构管理',NULL,'Y',2,NULL,'机构管理',1,1,8,NULL,NULL,'admin',NULL,NULL),(7,'用户管理',NULL,'Y',2,NULL,'用户管理',1,2,10,NULL,NULL,'admin',NULL,NULL),(8,'业务字典',NULL,'Y',2,NULL,'业务字典',1,7,11,NULL,NULL,'admin',NULL,NULL),(10,'个人工作室',NULL,'N',1,NULL,'个人工作室',0,1,15,NULL,NULL,'admin',NULL,'admin'),(11,'工作流管理',NULL,'N',1,NULL,'工作流管理',0,3,17,NULL,NULL,NULL,NULL,NULL),(12,'新建任务',NULL,'Y',2,NULL,'新建任务',10,1,21,NULL,NULL,NULL,NULL,NULL),(13,'',NULL,'Y',2,NULL,'我的任务',10,2,25,NULL,NULL,NULL,NULL,NULL),(14,'',NULL,'Y',2,NULL,'已处理任务',10,3,22,NULL,NULL,NULL,NULL,NULL),(15,'',NULL,'Y',2,NULL,'流程管理',11,1,24,NULL,NULL,NULL,NULL,NULL),(16,'',NULL,'Y',2,NULL,'页面模板管理',11,2,19,NULL,NULL,NULL,NULL,NULL),(17,'',NULL,'Y',2,NULL,'操作管理',11,3,20,NULL,NULL,NULL,NULL,NULL),(18,'',NULL,'Y',2,NULL,'流程业务类型',11,4,18,NULL,NULL,NULL,NULL,NULL),(171,'',NULL,'Y',2,NULL,'文件管理',1,7,221,NULL,NULL,NULL,NULL,NULL),(181,'',NULL,'Y',2,NULL,'日志管理',1,8,231,NULL,NULL,NULL,NULL,NULL),(182,'',NULL,'Y',2,NULL,'SQL管理',1,9,232,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `usi_operation` */

DROP TABLE IF EXISTS `usi_operation`;

CREATE TABLE `usi_operation` (
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

/*Data for the table `usi_operation` */

insert  into `usi_operation`(`ID`,`CODE`,`MEMO`,`NAME`,`ORDER_INDEX`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'core.add','添加','添加',NULL,NULL,NULL,'admin',NULL,'admin'),(2,'core.update','更新','更新',NULL,NULL,NULL,'admin',NULL,NULL),(3,'core.remove','删除','删除',NULL,NULL,NULL,'admin',NULL,NULL),(4,'core.authRole','授予角色','授予角色',NULL,NULL,NULL,'admin',NULL,NULL),(5,'core.authResource','授予资源','授予资源',NULL,NULL,NULL,'admin',NULL,NULL),(6,'core.authUser','授权用户','授权用户',NULL,NULL,NULL,'admin',NULL,NULL),(7,'core.addSiblingNode','添加同级节点','添加同级节点',NULL,NULL,NULL,'admin',NULL,NULL),(8,'core.addChildNode','添加子节点','添加子节点',NULL,NULL,NULL,'admin',NULL,NULL),(9,'core.updateNode','更新节点','更新节点',NULL,NULL,NULL,'admin',NULL,NULL),(10,'core.removeNode','删除节点','删除节点',NULL,NULL,NULL,'admin',NULL,NULL),(11,'core.orderNode','节点排序','节点排序',NULL,NULL,NULL,'admin',NULL,NULL);

/*Table structure for table `usi_organization` */

DROP TABLE IF EXISTS `usi_organization`;

CREATE TABLE `usi_organization` (
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `usi_organization` */

insert  into `usi_organization`(`ID`,`LEVEL_`,`NAME`,`AREACODE`,`ORGSEQ`,`PARENTID`,`THESORT`,`DESCN`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,1,'安徽电信',NULL,'1.',0,1,'安徽电信',0,NULL,'admin',NULL,'admin'),(2,2,'合肥','0551','1.2.',1,1,'合肥',0,NULL,'admin',NULL,'admin'),(3,2,'蚌埠','0552','1.3.',1,2,'蚌埠',0,NULL,'admin',NULL,'admin'),(4,2,'芜湖','0553','1.4.',1,3,'芜湖',0,NULL,'admin',NULL,'admin');

/*Table structure for table `usi_primarykey` */

DROP TABLE IF EXISTS `usi_primarykey`;

CREATE TABLE `usi_primarykey` (
  `CODE` bigint(20) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`CODE`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `usi_primarykey` */

insert  into `usi_primarykey`(`CODE`,`NAME`) values (10,'USI_ATTACHMENT'),(10,'usi_login_log'),(10,'USI_ROLE'),(30,'USI_CONFIG'),(30,'USI_DICT_TYPE'),(40,'USI_ORGANIZATION'),(60,'BusinessStarFlow'),(80,'TEST_FLOW_LEAVE'),(90,'USI_OPERATION'),(100,'USI_USER'),(170,'USI_DICT_ENTRY'),(190,'USI_MENU'),(240,'USI_RESOURCE');

/*Table structure for table `usi_resource` */

DROP TABLE IF EXISTS `usi_resource`;

CREATE TABLE `usi_resource` (
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

/*Data for the table `usi_resource` */

insert  into `usi_resource`(`ID`,`NAME`,`ACTION`,`DESCN`,`ENABLED`,`PRIORITY`,`TYPE`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (4,'所有请求','/**',NULL,'Y',999,'uri',NULL,NULL,NULL,NULL,'admin'),(5,'资源管理','./resource/index','权限管理','Y',4,'menu',NULL,NULL,'admin',NULL,NULL),(6,'菜单管理','./menu/index',NULL,'Y',5,'menu',NULL,NULL,NULL,NULL,'admin'),(7,'角色管理','./role/index',NULL,'Y',6,'menu',NULL,NULL,'admin',NULL,NULL),(8,'机构管理','./org/index','机构管理','Y',7,'menu',NULL,NULL,'admin',NULL,NULL),(10,'用户管理','./user/index','用户管理','Y',9,'menu',NULL,NULL,'admin',NULL,NULL),(11,'业务字典','./dict/index','业务字典','Y',10,'menu',NULL,NULL,'admin',NULL,'admin'),(12,'系统管理','/systemadmin','系统管理','Y',11,'menu',NULL,NULL,NULL,NULL,'admin'),(15,'个人工作室','/workspace','个人工作室','Y',13,'menu',NULL,NULL,'admin',NULL,'admin'),(16,'操作管理','./operation/index','操作管理','Y',14,'menu',NULL,NULL,'admin',NULL,NULL),(17,'工作流管理','/fowadmin','工作流管理','Y',17,'menu',NULL,'2011-08-11 16:05:31','admin',NULL,NULL),(18,'流程业务类型','./businessType/index','流程业务类型','Y',18,'menu',NULL,'2011-08-11 16:09:17','admin',NULL,NULL),(19,'页面模板管理','./pageTemplate/index','页面模板管理','Y',19,'menu',NULL,'2011-08-11 17:02:58','admin',NULL,NULL),(20,'操作管理','./flowOperation/index','操作管理','Y',20,'menu',NULL,'2011-08-11 17:30:33','admin',NULL,NULL),(21,'新建任务','./leave/newLeave','新建任务','Y',22,'menu',NULL,'2011-08-12 16:09:31','admin',NULL,NULL),(22,'已处理任务','./leave/finishedLeave','已处理任务','Y',24,'menu',NULL,'2011-08-12 16:10:24','admin',NULL,NULL),(24,'流程管理','./flow/index','流程管理','Y',21,'menu',NULL,'2011-08-11 19:29:55','admin',NULL,NULL),(25,'我的任务','./leave/personLeave','我的任务','Y',23,'menu',NULL,'2011-08-12 16:09:56','admin',NULL,NULL),(221,'文件管理','/attachment/index','','Y',7,'menu',NULL,NULL,NULL,NULL,NULL),(231,'日志管理','/loginLog/index','','Y',8,'menu',NULL,NULL,NULL,NULL,NULL),(232,'SQL管理','/schemaVersion/index','','Y',9,'menu',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `usi_role` */

DROP TABLE IF EXISTS `usi_role`;

CREATE TABLE `usi_role` (
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

/*Data for the table `usi_role` */

insert  into `usi_role`(`ID`,`CODE`,`NAME`,`ENABLED`,`DESCN`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'ROLE_ADMIN','管理员角色','Y',NULL,NULL,NULL,NULL,NULL,NULL),(2,'ROLE_USER','普通用户','Y',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `usi_role_resource` */

DROP TABLE IF EXISTS `usi_role_resource`;

CREATE TABLE `usi_role_resource` (
  `ROLE_ID` bigint(20) NOT NULL,
  `RESOURCE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `usi_role_resource` */

insert  into `usi_role_resource`(`ROLE_ID`,`RESOURCE_ID`) values (1,4),(1,5),(1,6),(1,7),(1,8),(1,10),(1,11),(1,12),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,221),(1,231),(1,232),(2,221),(2,231),(2,232);

/*Table structure for table `usi_user` */

DROP TABLE IF EXISTS `usi_user`;

CREATE TABLE `usi_user` (
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
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

/*Data for the table `usi_user` */

insert  into `usi_user`(`ID`,`USER_CODE`,`USER_NAME`,`PASSWORD`,`SALT`,`AREA_ID`,`ORG_ID`,`BIRTHDAY`,`GENDER`,`EMAIL`,`PHONE_NO`,`MPHONE_NO`,`ERROR_COUNT`,`LAST_LOGIN_TIME`,`PASSWD_INVAL_TIME`,`LOCK_TIME`,`DEL_FLAG`,`CREATE_TIME`,`CREATE_USER`,`UPDATE_TIME`,`UPDATE_USER`) values (1,'admin','系统管理员','d6d252bac8e22d4db7e11db4a3a6db1d','6gilcjmzowo6vav1lxmqd6cmograasqq',1,1,NULL,'F','bsli123@hotmail.com',NULL,NULL,0,'2013-03-20 13:44:08',NULL,NULL,0,'2011-11-26 09:27:52','anonymousUser','2011-11-26 08:31:06','admin'),(2,'ustc','普通用户','d6d252bac8e22d4db7e11db4a3a6db1d','6gilcjmzowo6vav1lxmqd6cmograasqq',2,2,NULL,'M','bsli123@hotmail.com',NULL,NULL,0,'2012-09-06 11:31:07',NULL,NULL,0,'2011-11-26 09:27:52','anonymousUser','2011-11-26 08:31:06','admin');

/*Table structure for table `usi_user_role` */

DROP TABLE IF EXISTS `usi_user_role`;

CREATE TABLE `usi_user_role` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `usi_user_role` */

insert  into `usi_user_role`(`USER_ID`,`ROLE_ID`) values (1,1),(1,2),(2,2);

