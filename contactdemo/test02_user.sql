/*
 Navicat Premium Data Transfer

 Source Server         : demo
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : test02_user

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 06/08/2022 22:17:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phoneNumber` varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL,
  `password` varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `studentID` varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = gb2312 COLLATE = gb2312_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '18259573398', '80F25A8D98B54D0D00ADF84351597CBE', '123567', 'hhhhhhh');
INSERT INTO `user` VALUES (2, '17346098723', '7AB6D090F85DF5C6A7412E68B1EBD4D5', 'Swift', '12345');
INSERT INTO `user` VALUES (3, '15805968792', '4C64FE67FC9DCDCFD4A4140498A0D905', 'Taylor', 'abcdefg');
INSERT INTO `user` VALUES (4, '18960345876', '0F1A3DE0DEF28E2C4D3134BAB4960B57', 'grthr', 'gzx1234');
INSERT INTO `user` VALUES (15, '18259573444', '4931105AC90C9C10B98ABE1D1CA0D4D5', '东方不败', 'ABCD');
INSERT INTO `user` VALUES (16, '18259573333', '1770332F09DFA278DF501ABB82A7B258', '令狐冲', '1234');
INSERT INTO `user` VALUES (17, '15805963452', 'CB14BDF6E8BD07F68C7FA14CB5DD8EA6', 'Taylor', '123456');
INSERT INTO `user` VALUES (19, '18960367876', 'CB14BDF6E8BD07F68C7FA14CB5DD8EA6', 'zeer', '123456');
INSERT INTO `user` VALUES (21, '15346049876', '7D0249C3EFEC5623AB84E375C70D12D6', 'hhhh', '1234567');
INSERT INTO `user` VALUES (26, '18259573399', 'C78DD4FD14891470ACC69CE28278DBC8', 'hhh', '21113103');

SET FOREIGN_KEY_CHECKS = 1;
