/*
Navicat MySQL Data Transfer

Source Server         : 120.25.170.124_3306
Source Server Version : 50627
Source Host           : 120.25.170.124:3306
Source Database       : photo

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2017-08-02 16:01:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '2017-02-12');

-- ----------------------------
-- Table structure for `t_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `worksId` int(11) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `worksId` (`worksId`),
  CONSTRAINT `t_attachment_ibfk_1` FOREIGN KEY (`worksId`) REFERENCES `t_works` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attachment
-- ----------------------------
INSERT INTO `t_attachment` VALUES ('2', '3', '/uploads/images/13b01e36-efcc-45a2-a567-55b085e8c490.jpg', '2017-03-03', '0');
INSERT INTO `t_attachment` VALUES ('3', '3', '/uploads/images/41540dad-f941-4bea-8ba1-8dd1b9076067.jpg', '2017-03-03', '0');
INSERT INTO `t_attachment` VALUES ('4', '4', '/uploads/images/bfd325f0-0854-4d89-84ae-0e6a7c481beb.jpg', '2017-03-03', '0');
INSERT INTO `t_attachment` VALUES ('5', '4', '/uploads/images/0b5fb8ea-033e-4c40-8304-ea24e47d8294.jpg', '2017-03-03', '0');
INSERT INTO `t_attachment` VALUES ('6', '5', '/uploads/images/939c1799-8705-4fef-a011-bf9102d04e1b.jpg', '2017-04-26', '0');
INSERT INTO `t_attachment` VALUES ('7', '5', '/uploads/images/1fa90123-9b19-451b-89ec-d0407980cd18.jpg', '2017-04-26', '0');
INSERT INTO `t_attachment` VALUES ('8', '5', '/uploads/images/89c69f92-a5ea-4f10-98c1-04c63e773782.jpg', '2017-04-26', '0');
INSERT INTO `t_attachment` VALUES ('9', '5', '/uploads/images/df0d93e5-0cad-48cb-b550-2f666dffd238.jpg', '2017-04-26', '0');
INSERT INTO `t_attachment` VALUES ('10', '6', '/uploads/images/a26b3f53-0213-44fc-b894-f7a6939f89a3.jpg', '2017-06-08', '0');
INSERT INTO `t_attachment` VALUES ('11', '7', '/uploads/images/affd05ca-227e-4dc0-8434-834c630f57ab.jpg', '2017-06-08', '0');
INSERT INTO `t_attachment` VALUES ('12', '8', '/uploads/images/228289b6-606e-4be5-b3e5-50bc07b595f2.png', '2017-06-08', '0');
INSERT INTO `t_attachment` VALUES ('13', '9', '/uploads/images/7805382a-20d4-455f-95b1-5afe14ccc607.png', '2017-06-08', '0');
INSERT INTO `t_attachment` VALUES ('14', '10', '/uploads/images/9030f36e-89ef-4ffc-85f2-a7b3e43b0798.png', '2017-06-08', '0');
INSERT INTO `t_attachment` VALUES ('15', '11', '/uploads/images/db1f9ab3-2ae7-416b-a574-465632f7d846.png', '2017-06-08', '0');
INSERT INTO `t_attachment` VALUES ('16', '12', '/uploads/images/e05f24bd-a03a-49d7-8f12-2d6e8c2af1af.png', '2017-06-08', '0');

-- ----------------------------
-- Table structure for `t_banner`
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_banner
-- ----------------------------
INSERT INTO `t_banner` VALUES ('14', '首页图1', '/uploads/banner/fd635ea3-d530-41bc-afa5-106bdccdfea9.jpg', '2017-06-08');
INSERT INTO `t_banner` VALUES ('15', '首页图2', '/uploads/banner/26b197ba-54ee-4f35-8edf-ff9fb6ca80c6.jpg', '2017-06-08');
INSERT INTO `t_banner` VALUES ('16', '首页图3', '/uploads/banner/2da90f90-f400-4ce0-854d-40aacbb0c102.jpg', '2017-06-08');
INSERT INTO `t_banner` VALUES ('17', '首页图4', '/uploads/banner/0493735f-8448-4e30-947d-f37e4986c392.jpg', '2017-06-08');
INSERT INTO `t_banner` VALUES ('20', '111', '/uploads/banner/d66e88f1-a90d-4226-bcf1-984d672b3417.jpg', '2017-06-27');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `photographerId` int(11) NOT NULL,
  `comment` text,
  `createTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `photographerId` (`photographerId`),
  CONSTRAINT `t_comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`),
  CONSTRAINT `t_comment_ibfk_2` FOREIGN KEY (`photographerId`) REFERENCES `t_photographer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('5', '5', '10', '真不错', '2017-03-03');

-- ----------------------------
-- Table structure for `t_infomation`
-- ----------------------------
DROP TABLE IF EXISTS `t_infomation`;
CREATE TABLE `t_infomation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `path` varchar(255) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_infomation
-- ----------------------------
INSERT INTO `t_infomation` VALUES ('12', '测试资讯1', '测试资讯1', '/uploads/info/54e37d80-9953-4225-beab-1a0d5c340ddc.jpg', '2017-03-03', '0');
INSERT INTO `t_infomation` VALUES ('13', '测试资讯2', '测试资讯2', '/uploads/info/0b5dd431-dd6b-48a8-960e-a50b61871a26.jpg', '2017-03-03', '0');
INSERT INTO `t_infomation` VALUES ('14', '测试资讯3', '测试资讯3', '/uploads/info/4a4478d8-cfef-4920-9288-69554c7b8fdf.jpg', '2017-03-03', '0');
INSERT INTO `t_infomation` VALUES ('15', '测试资讯4', '测试资讯4', '/uploads/info/4ad959b6-82d5-429e-bedd-b49e035166ba.jpg', '2017-03-03', '0');
INSERT INTO `t_infomation` VALUES ('16', '测试资讯5', '测试资讯5', '/uploads/info/f120d81e-acbd-4548-b6b8-89562a1260e2.jpg', '2017-03-03', '0');
INSERT INTO `t_infomation` VALUES ('17', '测试资讯6', '测试资讯6', '/uploads/info/49478fe0-81ac-46b1-af1b-cdbe0afe938f.jpg', '2017-03-03', '0');
INSERT INTO `t_infomation` VALUES ('18', '1', '<p>1<img src=\"http://localhost:8080/Photo//uploadsinfo360e1fb5-fb6b-4fb7-b299-00851e9fd7c1.jpg\"/></p>', '/uploads\\info\\fc0790dc-8f19-4c79-8dda-2033ab565013.jpg', '2017-06-27', '1');

-- ----------------------------
-- Table structure for `t_label`
-- ----------------------------
DROP TABLE IF EXISTS `t_label`;
CREATE TABLE `t_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_label
-- ----------------------------
INSERT INTO `t_label` VALUES ('10', '户外', '2017-03-03', '0');
INSERT INTO `t_label` VALUES ('11', '室内', '2017-03-03', '0');
INSERT INTO `t_label` VALUES ('12', '远景', '2017-03-03', '0');
INSERT INTO `t_label` VALUES ('13', '近景', '2017-03-03', '0');

-- ----------------------------
-- Table structure for `t_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_level`;
CREATE TABLE `t_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_level
-- ----------------------------
INSERT INTO `t_level` VALUES ('8', '总监级', '2017-03-03', '0');
INSERT INTO `t_level` VALUES ('9', '名师级', '2017-03-03', '0');
INSERT INTO `t_level` VALUES ('10', '首席级', '2017-03-03', '0');

-- ----------------------------
-- Table structure for `t_photographer`
-- ----------------------------
DROP TABLE IF EXISTS `t_photographer`;
CREATE TABLE `t_photographer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `summary` text,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_photographer
-- ----------------------------
INSERT INTO `t_photographer` VALUES ('10', '/uploads/photographer/5a9a4178-ad1a-4687-b058-07757f5928c3.jpg', '测试摄影师1', '测试摄影师1', '2017-03-03', '0');
INSERT INTO `t_photographer` VALUES ('11', '/uploads/photographer/e3359267-d09d-4b14-a762-088574760c77.jpg', '测试摄影师2', '测试摄影师2', '2017-03-03', '0');
INSERT INTO `t_photographer` VALUES ('12', '/uploads/photographer/66aa01fb-6866-42ca-9f0e-ebcb84c0e2b6.jpg', '测试摄影师3', '测试摄影师3', '2017-03-03', '0');
INSERT INTO `t_photographer` VALUES ('13', '/uploads/photographer/8a6f6f90-2043-4e6a-b1e2-2b3ceb2e23a6.jpg', '测试摄影师4', '测试摄影师4', '2017-03-03', '0');

-- ----------------------------
-- Table structure for `t_photographer_label`
-- ----------------------------
DROP TABLE IF EXISTS `t_photographer_label`;
CREATE TABLE `t_photographer_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `labelId` int(11) NOT NULL,
  `photographerId` int(11) NOT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `labelId` (`labelId`),
  KEY `photographerId` (`photographerId`),
  CONSTRAINT `t_photographer_label_ibfk_1` FOREIGN KEY (`labelId`) REFERENCES `t_label` (`id`),
  CONSTRAINT `t_photographer_label_ibfk_2` FOREIGN KEY (`photographerId`) REFERENCES `t_photographer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_photographer_label
-- ----------------------------
INSERT INTO `t_photographer_label` VALUES ('7', '10', '10', '2017-03-03', '0');
INSERT INTO `t_photographer_label` VALUES ('8', '11', '11', '2017-03-03', '0');
INSERT INTO `t_photographer_label` VALUES ('9', '12', '12', '2017-03-03', '0');
INSERT INTO `t_photographer_label` VALUES ('10', '13', '13', '2017-03-03', '0');

-- ----------------------------
-- Table structure for `t_photographer_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_photographer_level`;
CREATE TABLE `t_photographer_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `levelId` int(11) NOT NULL,
  `photographer` int(11) NOT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `levelId` (`levelId`),
  KEY `photographer` (`photographer`),
  CONSTRAINT `t_photographer_level_ibfk_1` FOREIGN KEY (`levelId`) REFERENCES `t_level` (`id`),
  CONSTRAINT `t_photographer_level_ibfk_2` FOREIGN KEY (`photographer`) REFERENCES `t_photographer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_photographer_level
-- ----------------------------
INSERT INTO `t_photographer_level` VALUES ('7', '8', '10', '2017-03-03', '0');
INSERT INTO `t_photographer_level` VALUES ('8', '9', '11', '2017-03-03', '0');
INSERT INTO `t_photographer_level` VALUES ('9', '10', '12', '2017-03-03', '0');
INSERT INTO `t_photographer_level` VALUES ('10', '8', '13', '2017-03-03', '0');

-- ----------------------------
-- Table structure for `t_photographer_spots`
-- ----------------------------
DROP TABLE IF EXISTS `t_photographer_spots`;
CREATE TABLE `t_photographer_spots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spotsId` int(11) NOT NULL,
  `photographerId` int(11) NOT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `spotsId` (`spotsId`),
  KEY `photographerId` (`photographerId`),
  CONSTRAINT `t_photographer_spots_ibfk_1` FOREIGN KEY (`spotsId`) REFERENCES `t_spots` (`id`),
  CONSTRAINT `t_photographer_spots_ibfk_2` FOREIGN KEY (`photographerId`) REFERENCES `t_photographer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_photographer_spots
-- ----------------------------
INSERT INTO `t_photographer_spots` VALUES ('7', '20', '10', '2017-03-03', '0');
INSERT INTO `t_photographer_spots` VALUES ('8', '21', '11', '2017-03-03', '0');
INSERT INTO `t_photographer_spots` VALUES ('9', '22', '12', '2017-03-03', '0');
INSERT INTO `t_photographer_spots` VALUES ('10', '24', '13', '2017-03-03', '0');

-- ----------------------------
-- Table structure for `t_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule`;
CREATE TABLE `t_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `photographerId` int(11) NOT NULL,
  `start` date DEFAULT NULL,
  `end` date DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0' COMMENT '0有空 1有预约 2无档期3过期',
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `remarks` text,
  PRIMARY KEY (`id`),
  KEY `photographerId` (`photographerId`),
  CONSTRAINT `t_schedule_ibfk_2` FOREIGN KEY (`photographerId`) REFERENCES `t_photographer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_schedule
-- ----------------------------
INSERT INTO `t_schedule` VALUES ('20', '5', '10', '2017-03-10', '2017-03-10', '2017-03-03', '1', '测试', '男', '13539767578', '', '');
INSERT INTO `t_schedule` VALUES ('21', '5', '12', '2017-03-03', '2017-03-03', '2017-03-03', null, '测试2', '男', '13111111111', '', '');
INSERT INTO `t_schedule` VALUES ('22', '1', '10', '2017-03-11', '2017-03-13', '2017-03-03', '1', null, null, null, null, null);
INSERT INTO `t_schedule` VALUES ('23', '1', '11', '2017-03-09', '2017-03-13', '2017-03-03', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_spots`
-- ----------------------------
DROP TABLE IF EXISTS `t_spots`;
CREATE TABLE `t_spots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `content` text,
  `path` varchar(255) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_spots
-- ----------------------------
INSERT INTO `t_spots` VALUES ('9', '宫殿', '<p><img src=\"http://localhost:8080/Photo//uploads/spots/60dbb34d-92da-4492-a0fc-8f528481eb05.jpg\"/></p>', '/uploads/spots/d69bccc6-690a-4e2c-b49a-316f1aa6feb5.jpg', '2017-03-03', '1');
INSERT INTO `t_spots` VALUES ('10', '测试景点2', '测试景点2', '/uploads/spots/2ee9397a-c368-484d-aff9-175ed28a7e93.jpg', '2017-03-03', '1');
INSERT INTO `t_spots` VALUES ('11', '测试景点3', '测试景点3', '/uploads/spots/d7953a3f-8d84-4a3d-89f9-4fdde4e642e2.jpg', '2017-03-03', '1');
INSERT INTO `t_spots` VALUES ('12', '测试景点4', '测试景点4', '/uploads/spots/b852214b-5e13-4d9d-a8e8-e5c0ddc82adf.jpg', '2017-03-03', '1');
INSERT INTO `t_spots` VALUES ('13', '测试景点5', '测试景点5', '/uploads/spots/e251217b-bed9-4475-a0d7-9d0c84ea1a48.jpg', '2017-03-03', '1');
INSERT INTO `t_spots` VALUES ('14', '宫殿', '宫殿', '/uploads/spots/f370244e-46d2-4ca0-ad3c-8cfc2fb4e362.jpg', '2017-06-08', '1');
INSERT INTO `t_spots` VALUES ('15', '教堂', '教堂', '', '2017-06-08', '1');
INSERT INTO `t_spots` VALUES ('16', '教堂', '教堂', '/uploads/spots/6c56050e-cd2b-4db0-8c07-7be4b9f2a31c.jpg', '2017-06-08', '1');
INSERT INTO `t_spots` VALUES ('17', '田野', '田野', '/uploads/spots/e515c8bf-f53a-4ce8-b1c7-ad34ba343027.jpg', '2017-06-08', '1');
INSERT INTO `t_spots` VALUES ('18', '街道', '街道', '/uploads/spots/c5dfd514-2b3a-4f0e-a821-2914d448e11e.jpg', '2017-06-08', '1');
INSERT INTO `t_spots` VALUES ('19', '城市', '城市', '/uploads/spots/3469b496-848b-4e72-be6d-0ba379f0853b.jpg', '2017-06-08', '1');
INSERT INTO `t_spots` VALUES ('20', '碧海蓝天的心动', '碧海蓝天的心动', '/uploads/spots/0878c03e-cf32-407c-b7e4-e0a04150b9b7.jpg', '2017-06-08', '0');
INSERT INTO `t_spots` VALUES ('21', '泰国普吉岛海景', '泰国普吉岛海景', '/uploads/spots/a75ecd34-633c-4766-9138-75447d3a49dc.JPG', '2017-06-08', '0');
INSERT INTO `t_spots` VALUES ('22', '泰国普吉岛礁石', '泰国普吉岛礁石', '/uploads/spots/7e3afe59-0d09-426b-9101-753bcc21eb0c.JPG', '2017-06-08', '0');
INSERT INTO `t_spots` VALUES ('23', '泰国普吉岛街景', '泰国普吉岛街景', '/uploads/spots/4f1eb8dc-bc57-4388-a707-1b1a3e310b9a.png', '2017-06-08', '0');
INSERT INTO `t_spots` VALUES ('24', '泰国普吉岛夜景', '泰国普吉岛夜景', '/uploads/spots/35bc5588-ade3-4642-b61d-448a2ea874c6.png', '2017-06-08', '0');
INSERT INTO `t_spots` VALUES ('25', '泰国沙滩普吉岛海景', '泰国沙滩普吉岛海景', '/uploads/spots/fe4a3b67-f058-499a-9653-de93db2fa13f.png', '2017-06-08', '0');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '管理员', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '1', '2017-02-13');
INSERT INTO `t_user` VALUES ('5', '测试', 'e10adc3949ba59abbe56e057f20f883e', '13533333333', null, '2017-03-03');
INSERT INTO `t_user` VALUES ('6', 'zzzdk', '75e266f182b4fa3625d4a4f4f779af54', '13936251458', null, '2017-06-07');
INSERT INTO `t_user` VALUES ('7', 'yzddd', '75e266f182b4fa3625d4a4f4f779af54', '15013268027', null, '2017-06-08');
INSERT INTO `t_user` VALUES ('8', 'dddd', '75e266f182b4fa3625d4a4f4f779af54', '15013268027', null, '2017-06-08');
INSERT INTO `t_user` VALUES ('9', '11', 'c4ca4238a0b923820dcc509a6f75849b', '13539767578', null, '2017-06-27');

-- ----------------------------
-- Table structure for `t_works`
-- ----------------------------
DROP TABLE IF EXISTS `t_works`;
CREATE TABLE `t_works` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `content` text,
  `spotsId` int(11) NOT NULL,
  `photographerId` int(11) NOT NULL,
  `createTime` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `photographerId` (`photographerId`),
  KEY `spotsId` (`spotsId`),
  CONSTRAINT `t_works_ibfk_1` FOREIGN KEY (`photographerId`) REFERENCES `t_photographer` (`id`),
  CONSTRAINT `t_works_ibfk_2` FOREIGN KEY (`spotsId`) REFERENCES `t_spots` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_works
-- ----------------------------
INSERT INTO `t_works` VALUES ('3', '测试作品1', '/uploads/images/1558171c-5ced-4bd7-be1b-05450fe40437.jpg', '测试作品1', '9', '10', '2017-03-03', '1');
INSERT INTO `t_works` VALUES ('4', '测试作品2', '/uploads/images/cc131e23-03eb-498c-bbd8-eb4d7722fb0c.jpg', '测试作品2', '10', '10', '2017-03-03', '1');
INSERT INTO `t_works` VALUES ('5', '测试作品A', '/uploads/images/f3cb9d30-c745-41c2-ab74-f01b8a33c58f.jpg', '测试使用', '11', '12', '2017-04-26', '1');
INSERT INTO `t_works` VALUES ('6', '宫殿拍摄图片1', null, '宫殿拍摄图片1', '14', '10', '2017-06-08', '1');
INSERT INTO `t_works` VALUES ('7', '碧海蓝天拍摄', '/uploads/images/b1837e73-99b3-402d-b787-67bf05d164b4.jpg', '碧海蓝天拍摄', '20', '10', '2017-06-08', '0');
INSERT INTO `t_works` VALUES ('8', '泰国普吉岛海景', '/uploads/images/c19e6214-b09e-4482-babb-ef152b2dc0f9.png', '泰国普吉岛海景', '21', '11', '2017-06-08', '0');
INSERT INTO `t_works` VALUES ('9', '泰国普吉岛礁石', '/uploads/images/aa30b10f-7b54-4a9e-95cf-54e0ee864f8a.png', '泰国普吉岛礁石', '22', '12', '2017-06-08', '0');
INSERT INTO `t_works` VALUES ('10', '泰国普吉岛街景', '/uploads/images/62f70d3a-51f1-4c33-a043-c02269e65aff.png', '泰国普吉岛街景', '23', '13', '2017-06-08', '0');
INSERT INTO `t_works` VALUES ('11', '泰国普吉岛夜景', '/uploads/images/7d42cc86-0d55-49ae-bb5e-26405210a9ca.png', '泰国普吉岛夜景', '24', '10', '2017-06-08', '0');
INSERT INTO `t_works` VALUES ('12', '泰国沙滩普吉岛海景', '/uploads/images/2b0a8740-19a4-4b18-9806-4aea714d719c.png', '泰国沙滩普吉岛海景', '25', '11', '2017-06-08', '0');
INSERT INTO `t_works` VALUES ('13', '111', null, '1', '20', '10', '2017-06-28', '1');
