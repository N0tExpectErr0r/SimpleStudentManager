/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50544
 Source Host           : 127.0.0.1:3306
 Source Schema         : student_manager

 Target Server Type    : MySQL
 Target Server Version : 50544
 File Encoding         : 65001

 Date: 26/04/2018 19:52:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '大一(1)班', 1, 100003);
INSERT INTO `class` VALUES (2, '大一(2)班', 1, 100004);
INSERT INTO `class` VALUES (5, '大一(3)班', 1, 100005);
INSERT INTO `class` VALUES (6, '大二(1)班', 4, 100006);
INSERT INTO `class` VALUES (7, '大二(2)班', 4, 100007);
INSERT INTO `class` VALUES (8, '大二(3)班', 4, 100008);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `school_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, '大一', 1, 100001);
INSERT INTO `grade` VALUES (4, '大二', 1, 100002);

-- ----------------------------
-- Table structure for logstatus
-- ----------------------------
DROP TABLE IF EXISTS `logstatus`;
CREATE TABLE `logstatus`  (
  `current_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of logstatus
-- ----------------------------
INSERT INTO `logstatus` VALUES ('100000', 5);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '学生');
INSERT INTO `permission` VALUES (2, '科任老师');
INSERT INTO `permission` VALUES (3, '班主任');
INSERT INTO `permission` VALUES (4, '级长');
INSERT INTO `permission` VALUES (5, '校长');

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES (1, '广东供液大学', 100000);

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) NULL DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `index_num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (9, 99, 300000, 1, 1);
INSERT INTO `score` VALUES (10, 99, 300000, 2, 1);
INSERT INTO `score` VALUES (11, 99, 300000, 3, 1);
INSERT INTO `score` VALUES (13, 100, 300000, 1, 2);
INSERT INTO `score` VALUES (14, 87, 300000, 2, 2);
INSERT INTO `score` VALUES (15, 99, 300000, 3, 2);
INSERT INTO `score` VALUES (17, 98, 300001, 1, 1);
INSERT INTO `score` VALUES (18, 99, 300001, 2, 1);
INSERT INTO `score` VALUES (19, 77, 300001, 3, 1);
INSERT INTO `score` VALUES (21, 88, 300001, 1, 2);
INSERT INTO `score` VALUES (22, 77, 300001, 2, 2);
INSERT INTO `score` VALUES (23, 88, 300001, 3, 2);
INSERT INTO `score` VALUES (25, 78, 300002, 1, 1);
INSERT INTO `score` VALUES (26, 99, 300002, 2, 1);
INSERT INTO `score` VALUES (27, 100, 300002, 3, 1);
INSERT INTO `score` VALUES (29, 99, 300002, 1, 2);
INSERT INTO `score` VALUES (30, 88, 300002, 2, 2);
INSERT INTO `score` VALUES (31, 99, 300002, 3, 2);
INSERT INTO `score` VALUES (33, 99, 300003, 1, 1);
INSERT INTO `score` VALUES (34, 100, 300003, 2, 1);
INSERT INTO `score` VALUES (35, 56, 300003, 3, 1);
INSERT INTO `score` VALUES (37, 100, 300003, 1, 2);
INSERT INTO `score` VALUES (38, 88, 300003, 2, 2);
INSERT INTO `score` VALUES (39, 70, 300003, 3, 2);
INSERT INTO `score` VALUES (41, 100, 300004, 1, 1);
INSERT INTO `score` VALUES (42, 98, 300004, 2, 1);
INSERT INTO `score` VALUES (43, 70, 300004, 3, 1);
INSERT INTO `score` VALUES (45, 98, 300004, 1, 2);
INSERT INTO `score` VALUES (46, 100, 300004, 2, 2);
INSERT INTO `score` VALUES (47, 87, 300004, 3, 2);
INSERT INTO `score` VALUES (49, 88, 300005, 1, 1);
INSERT INTO `score` VALUES (50, 77, 300005, 2, 1);
INSERT INTO `score` VALUES (51, 99, 300005, 3, 1);
INSERT INTO `score` VALUES (53, 100, 300005, 1, 2);
INSERT INTO `score` VALUES (54, 56, 300005, 2, 2);
INSERT INTO `score` VALUES (55, 99, 300005, 3, 2);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 300010 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (300000, '梁文俊', '男', '15521031087', '871731342@qq.com', 1);
INSERT INTO `student` VALUES (300001, '韦星莉', '女', '15525645577', '84558466@qq.com', 1);
INSERT INTO `student` VALUES (300002, '肖运豪', '男', '15657897575', '878876414@qq.com', 2);
INSERT INTO `student` VALUES (300003, '杨丽佳', '女', '13726565644', '', 2);
INSERT INTO `student` VALUES (300004, '李沛衡', '男', '15687887898', '78787878@qq.com', 5);
INSERT INTO `student` VALUES (300005, '徐祎程', '女', '15484877894', '', 5);
INSERT INTO `student` VALUES (300006, '郭海', '男', '15556487878', '524548874@qq.com', 8);
INSERT INTO `student` VALUES (300007, '周堋昊', '男', '18787874547', '457874545@sina.com', 8);
INSERT INTO `student` VALUES (300008, '杨文昊', '男', '15678748778', '887848287@qq.com', 7);
INSERT INTO `student` VALUES (300009, '测试学生', '男', '15545648721', '548567745@sina.cn', 8);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (1, '语文');
INSERT INTO `subject` VALUES (2, '数学');
INSERT INTO `subject` VALUES (3, '英语');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100010 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (100000, '张三', '15510002000', '123@qq.com');
INSERT INTO `teacher` VALUES (100001, '李四', '15610002000', '1234@qq.com');
INSERT INTO `teacher` VALUES (100002, '王五', '15710002000', '12345@qq.com');
INSERT INTO `teacher` VALUES (100003, '三毛', '15810002000', '123456@qq.com');
INSERT INTO `teacher` VALUES (100004, '四毛', '15454845344', '8488785@qq.com');
INSERT INTO `teacher` VALUES (100005, '五毛', '15231245567', '87878977@qq.com');
INSERT INTO `teacher` VALUES (100006, '六毛', '11889845877', '454877898@qq.com');
INSERT INTO `teacher` VALUES (100007, '张四', '15451564564', '');
INSERT INTO `teacher` VALUES (100008, '张五', '15654887788', '58565454@qq.com');
INSERT INTO `teacher` VALUES (100009, '李五', '15456787897', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '100003', '61be2eaa6baab436389a8f2f6414c305', 3);
INSERT INTO `user` VALUES (2, '100004', '4b237335ff4e41e7178cdfc3af985545', 3);
INSERT INTO `user` VALUES (3, '100001', '5fbbee32f9c4f2535c013a5656e67aa2', 4);
INSERT INTO `user` VALUES (4, '100002', 'd1407000b00f17a341a4ada52947d288', 4);
INSERT INTO `user` VALUES (5, '100000', 'da314e3e620aa0947afc6bb9f6a4fff5', 5);
INSERT INTO `user` VALUES (6, '100005', '276e33b720d9379df2ca0150119866bf', 3);
INSERT INTO `user` VALUES (7, '100006', 'f318cd7f118ccbdd235589446e4bc253', 3);
INSERT INTO `user` VALUES (8, '100007', '0ed00445076ba0ff1c800c6750ab3b55', 3);
INSERT INTO `user` VALUES (9, '100008', '13e44ff3d5f1ea016faddc2944300c05', 3);
INSERT INTO `user` VALUES (11, '100009', 'e43f76d19cc612e9fbde225ae9044b45', 2);
INSERT INTO `user` VALUES (16, '300000', 'd94ceee34ffb1e0f8710c3c7804bca46', 1);
INSERT INTO `user` VALUES (17, '300001', '1224a719766f12b775bf223d717a50c3', 1);
INSERT INTO `user` VALUES (18, '300002', '590e1b8685be7e223505213c61aaeb3d', 1);
INSERT INTO `user` VALUES (19, '300003', '07b5fbacb520ce557859dabb4962d38b', 1);
INSERT INTO `user` VALUES (20, '300004', '5e1c767a4599b64d70e636eb74c7139a', 1);
INSERT INTO `user` VALUES (21, '300005', '9ef730b22c71ee4440476232894a2d93', 1);
INSERT INTO `user` VALUES (22, '300006', '1305657d41899b5fa613a0a632461a58', 1);
INSERT INTO `user` VALUES (23, '300007', 'b4b2b9a50fece46bb6265c5f9b567829', 1);
INSERT INTO `user` VALUES (24, '300008', '8a1fb725f7c425331f6703978ca40b75', 1);
INSERT INTO `user` VALUES (29, '300009', '2b23ed05d3c09c8dafe2f8f59e13c232', 1);

SET FOREIGN_KEY_CHECKS = 1;
