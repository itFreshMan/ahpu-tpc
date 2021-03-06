/*USI_DICT_TYPE 增加 MODULE 字段*/
ALTER TABLE USI_DICT_TYPE ADD MODULE VARCHAR2(64); 
INSERT  INTO USI_DICT_TYPE(ID,MODULE,CODE,MEMO,NAME,DEL_FLAG,CREATE_TIME,CREATE_USER,UPDATE_TIME,UPDATE_USER) VALUES (10005,'system','CORE.MODULE','模块类型：系统管理','模块类型',NULL,NULL,'admin',NULL,NULL);
UPDATE USI_DICT_TYPE SET MODULE = 'system' WHERE ID IN(10001,10002,10003,10004,10006);

INSERT  INTO USI_DICT_ENTRY(ID,CODE,ENABLED,NAME,ORDER_INDEX,DICT_TYPE_ID,DEL_FLAG,CREATE_TIME,CREATE_USER,UPDATE_TIME,UPDATE_USER) VALUES (13,'system','Y','系统管理',1,10005,NULL,NULL,'admin',NULL,NULL);

/*USI_RESOURCE 增加 MODULE 字段*/
ALTER TABLE USI_RESOURCE ADD MODULE VARCHAR2(64);

/*USI_ORGANIZATION 增加AREACODE字段*/
ALTER TABLE USI_ORGANIZATION ADD AREACODE VARCHAR2(8);
INSERT  INTO USI_DICT_ENTRY(ID,CODE,ENABLED,NAME,ORDER_INDEX,DICT_TYPE_ID,DEL_FLAG,CREATE_TIME,CREATE_USER,UPDATE_TIME,UPDATE_USER)
VALUES (14,'0551','Y','合肥',1,10007,NULL,NULL,'admin',NULL,NULL);
INSERT  INTO USI_DICT_ENTRY(ID,CODE,ENABLED,NAME,ORDER_INDEX,DICT_TYPE_ID,DEL_FLAG,CREATE_TIME,CREATE_USER,UPDATE_TIME,UPDATE_USER)
VALUES (15,'0552','Y','蚌埠',2,10007,NULL,NULL,'admin',NULL,NULL);
INSERT  INTO USI_DICT_ENTRY(ID,CODE,ENABLED,NAME,ORDER_INDEX,DICT_TYPE_ID,DEL_FLAG,CREATE_TIME,CREATE_USER,UPDATE_TIME,UPDATE_USER)
VALUES (16,'0553','Y','芜湖',3,10007,NULL,NULL,'admin',NULL,NULL);
INSERT  INTO USI_DICT_TYPE(ID,MODULE,CODE,MEMO,NAME,DEL_FLAG,CREATE_TIME,CREATE_USER,UPDATE_TIME,UPDATE_USER)
VALUES (10007,'system','CORE.ORG.AREA','机构的所属区域','机构区域',NULL,NULL,NULL,NULL,NULL);
UPDATE USI_ORGANIZATION SET AREACODE = '0551' WHERE NAME='合肥';
UPDATE USI_ORGANIZATION SET AREACODE = '0552' WHERE NAME='蚌埠';
UPDATE USI_ORGANIZATION SET AREACODE = '0553' WHERE NAME='芜湖';
