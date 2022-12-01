/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : db_yunpin

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 30/11/2022 21:25:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(10) DEFAULT NULL COMMENT '账号',
  `a_password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
BEGIN;
INSERT INTO `tb_admin` VALUES (3, 'admin', 'admin');
INSERT INTO `tb_admin` VALUES (4, 'ltf', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for tb_collection
-- ----------------------------
DROP TABLE IF EXISTS `tb_collection`;
CREATE TABLE `tb_collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `studentId` bigint(20) NOT NULL COMMENT '学生Id',
  `jobId` bigint(20) NOT NULL COMMENT '岗位Id',
  `lable` varchar(10) NOT NULL COMMENT '标签',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tb_collection_ibfk_3` (`jobId`) USING BTREE,
  KEY `tb_collection_ibfk_1` (`studentId`) USING BTREE,
  CONSTRAINT `tb_collection_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `tb_student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_collection_ibfk_3` FOREIGN KEY (`jobId`) REFERENCES `tb_job` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_collection
-- ----------------------------
BEGIN;
INSERT INTO `tb_collection` VALUES (10, 16, 78, '已投递');
INSERT INTO `tb_collection` VALUES (11, 16, 61, '未投递');
INSERT INTO `tb_collection` VALUES (16, 16, 74, '已投递');
INSERT INTO `tb_collection` VALUES (18, 16, 35, '已投递');
INSERT INTO `tb_collection` VALUES (19, 16, 77, '已投递');
INSERT INTO `tb_collection` VALUES (21, 16, 57, '已投递');
INSERT INTO `tb_collection` VALUES (22, 21, 35, '已投递');
INSERT INTO `tb_collection` VALUES (23, 25, 35, '已投递');
COMMIT;

-- ----------------------------
-- Table structure for tb_company
-- ----------------------------
DROP TABLE IF EXISTS `tb_company`;
CREATE TABLE `tb_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(20) DEFAULT NULL COMMENT '公司名称',
  `email` varchar(20) NOT NULL COMMENT '邮箱',
  `login_password` varchar(50) NOT NULL COMMENT '登录密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `tell_call` varchar(20) DEFAULT NULL COMMENT '电话',
  `portrait` varchar(255) DEFAULT NULL COMMENT '头像',
  `home_link` varchar(255) DEFAULT NULL COMMENT '首页链接',
  `infomation` varchar(255) DEFAULT NULL COMMENT '公司信息',
  `business_license` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `check_status` varchar(255) NOT NULL COMMENT '审核状态',
  `credit_code` varchar(50) DEFAULT NULL COMMENT '信用代码',
  `legal_person` varchar(255) DEFAULT NULL COMMENT '法人',
  `address` varchar(50) DEFAULT NULL COMMENT '公司地址',
  `nature` varchar(10) DEFAULT NULL COMMENT '性质',
  `scale` varchar(10) DEFAULT NULL COMMENT '规模',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_company
-- ----------------------------
BEGIN;
INSERT INTO `tb_company` VALUES (16, '深圳市腾讯计算机系统有限公司', '1746007300@qq.com', 'aaaa1111', '18078444412', '5578324', 'http://oss.lankejun.cn/62b7d704-f09d-45c6-b227-073fc378b496.png', 'https://www.tencent.com/zh-cn', '深圳市腾讯计算机系统有限公司成立于1998年11月 [1]  ，由马化腾、张志东、许晨晔、陈一丹、曾李青五位创始人共同创立。 ', 'http://oss.lankejun.cn/2c760cb1-ec44-43d9-9e4f-d30ffa34aa1b.png', '已通过', '9144030071526726XG', '马化腾aaaaaa', '深圳市南山必胜客', '私企', '500-1000');
INSERT INTO `tb_company` VALUES (17, '百度在线网络技术（北京）有限公司', '3161035568@qq.com', 'aaaa1111', '18077643234', '5543786', 'http://oss.lankejun.cn/ff899b9a-c622-458b-bb49-7480b418a0ac.png', 'https://www.baidu.com/', '百度拥有数万名研发工程师，这是中国乃至全球都顶尖的技术团队。这支队伍掌握着世界上最为先进的搜索引擎技术，使百度成为中国掌握世界尖端科学核心技术的中国高科技企业，使中国成为美国、俄罗斯、和韩国之外，全球仅有的4个拥有搜索引擎核心技术的国家之一。', 'http://oss.lankejun.cn/03754a26-e63e-4a5d-b21a-abe415a088de.png', '已通过', '9144030333526766XG', '李彦宏', '北京市海淀区中关村', '合资', '500-1000');
INSERT INTO `tb_company` VALUES (18, '阿里巴巴集团控股有限公司', '2729920479@qq.com', 'aaaa1111', '18078444898', '5564781', 'http://oss.lankejun.cn/1598513b-dcca-46c8-8ae6-da9396fbdc06.png', 'https://re.1688.com/', '阿里巴巴集团经营多项业务，另外也从关联公司的业务和服务中取得经营商业生态系统上的支援。业务和关联公司的业务包括：淘宝网、天猫、聚划算、全球速卖通、阿里巴巴国际交易市场、1688、阿里妈妈、阿里云、蚂蚁金服、菜鸟网络等。', 'http://oss.lankejun.cn/4527264d-49e9-4feb-aeae-02fb999835cf.png', '已通过', '8854030333523566XG', '马云', '杭州西湖灵隐寺', '国企', '50-150');
INSERT INTO `tb_company` VALUES (21, '禁用测试公司', 'jinyong@test.com', 'aaaa1111', '18078222234', NULL, 'http://oss.lankejun.cn/3ebfe345-7ba7-4f11-89cc-ce4d65ea68c7.png', NULL, NULL, 'http://oss.lankejun.cn/327db0c6-3160-4bfc-a1ea-58ebed48a724.png', '已退回', NULL, NULL, NULL, '事业单位', '少于50');
INSERT INTO `tb_company` VALUES (22, '演示公司', 'yanshi@test.com', 'aaaa1111', '18078444843', '6676846', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 'lankejun.cn', '这是一个测试公司', 'http://oss.lankejun.cn/5f5907e8-6631-4345-9b94-43b798e37071.png', '已禁用', '78999823434SSS', '蓝岸', '桂航', '私企', '50-150');
INSERT INTO `tb_company` VALUES (24, '阿里云', '2350938432@qq.com', 'aaaa1111', '13825166025', '13825166025', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/7-20100Q05624563.jpg', 'https://cn.aliyun.com/', 'ssss', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/7-20100Q05624563.jpg', '已通过', '好', '马云', '杭州', '国企', '1000以上');
INSERT INTO `tb_company` VALUES (25, NULL, '2644293073@qq.com', 'aaaa1111', NULL, NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', NULL, NULL, NULL, '未完善', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_company` VALUES (26, '微信组', '2644293053@qq.com', 'aaaa1111', '13501919680', '13501919680', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/aaafd32f237a4ffc97c338d1de413f34', 'https://wx.qq.com/', '微信（英语：WeChat）是中国腾讯公司于2011年1月21日推出的一款支持Android以及iOS等移动操作系统的即时通信软件，其面对智能手机用户。', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/c2cdae4adea945e5b6c2b4e1c34e5532', '待审核', '43543252', '张小龙', '深圳', '私企', '1000以上');
COMMIT;

-- ----------------------------
-- Table structure for tb_invitation
-- ----------------------------
DROP TABLE IF EXISTS `tb_invitation`;
CREATE TABLE `tb_invitation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resumeid` bigint(20) NOT NULL,
  `jobid` bigint(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tb_invitation_ibfk_2` (`jobid`) USING BTREE,
  KEY `tb_invitation_ibfk_1` (`resumeid`) USING BTREE,
  CONSTRAINT `tb_invitation_ibfk_1` FOREIGN KEY (`resumeid`) REFERENCES `tb_resume` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_invitation_ibfk_2` FOREIGN KEY (`jobid`) REFERENCES `tb_job` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_invitation
-- ----------------------------
BEGIN;
INSERT INTO `tb_invitation` VALUES (4, 46, 78, '已邀请');
INSERT INTO `tb_invitation` VALUES (7, 46, 12, '已邀请');
INSERT INTO `tb_invitation` VALUES (10, 46, 55, '已邀请');
INSERT INTO `tb_invitation` VALUES (11, 46, 60, '已邀请');
INSERT INTO `tb_invitation` VALUES (13, 46, 61, '已邀请');
INSERT INTO `tb_invitation` VALUES (14, 46, 63, '已邀请');
INSERT INTO `tb_invitation` VALUES (17, 48, 78, '已邀请');
INSERT INTO `tb_invitation` VALUES (18, 46, 70, '已邀请');
INSERT INTO `tb_invitation` VALUES (21, 56, 12, '已邀请');
INSERT INTO `tb_invitation` VALUES (22, 46, 72, '已邀请');
COMMIT;

-- ----------------------------
-- Table structure for tb_job
-- ----------------------------
DROP TABLE IF EXISTS `tb_job`;
CREATE TABLE `tb_job` (
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司id',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `qualifications` varchar(255) NOT NULL COMMENT '任职要求',
  `job_content` varchar(255) NOT NULL COMMENT '工作内容',
  `salary` int(11) DEFAULT NULL COMMENT '薪资待遇',
  `place` varchar(20) DEFAULT NULL COMMENT '工作地点',
  `education` varchar(255) DEFAULT NULL COMMENT '学历要求',
  `number` varchar(100) DEFAULT NULL COMMENT '招聘人数',
  `send_time` varchar(10) DEFAULT NULL COMMENT '发布时间',
  `recruitment_status` varchar(10) DEFAULT NULL COMMENT '招聘状态',
  `majors` varchar(255) DEFAULT NULL COMMENT '推荐专业',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `company_id` (`company_id`) USING BTREE,
  CONSTRAINT `tb_job_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_job
-- ----------------------------
BEGIN;
INSERT INTO `tb_job` VALUES (16, 12, '安全工程师', '必须具备的:\n专业不限，热爱互联网，对操作系统和网络安全有狂热的追求;熟悉漏洞挖掘、网络安全攻防技术，了解常见黑客攻击手法;掌握基本开发能力，熟练使用C/C++语言;\n对数据库、操作系统、网络原理有较好掌握。\n', '我们说，安全技术是一个工作，\n无论你是研究业界最新网络安全攻防技术，定制严密安全方案，研发稳固安全系统，\n还是你为腾讯旗下所有业务、产品和用户制定安全规范流程，指导业务部门进行安全设计、开发、测试和运营，—切，都将为腾讯产品与亿万用户保驾护航,\n因此，安全技术更是—份责任。\n', 12000, '深圳', '研究生', '10', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (18, 33, '产品经理', '我们正在寻找对产品有热情有追求的你，用伟大的产品和世界对话：\n• 我们希望你能始终保持一颗好奇心，持续思考事物的本质，果敢地做出判断，并且始终如一地关注细节。\n• 我们希望你对数据足够敏感，不断探寻数据背后的Why，发现数据中蕴含的巨大能量。\n• 坚持做对的事，始终把实现客户价值放在首位。\n• 面向本科及以上2022届应届毕业生，计算机相关专业、信息与通信工程、管理学、经济学、统计学、心理学、社会学、应用数学等专业优先。', '我们的业务包括核心商业、云计算、数字媒体及娱乐以及创新业务。除此之外， 我们的非并表关联方蚂蚁集团为我们平台上的消费者和商家提供支付和金融服务。围绕着我们的平台与业务，一个涵盖了消费者、商家、品牌、零售商、第三方服务提供商、战略合作伙伴及其他企业的数字经济体已经建立起来。', 3000, '上海', '专科', '5', '2021-05-23', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (17, 35, '音频算法工程师', '-通信、电子及相关专业\n\n-精通C/C++开发语言，会使用Matlab等至少一种仿真工具，熟悉Python\n\n-熟悉数字信号处理（DSP）理论，熟悉音频信号特性及通用处理理论\n\n-工作认真、踏实，学习及创新能力强；良好的沟通能力和团队合作精神\n\n-有高因子相关论文者优先', '-参与音频处理相关算法开发，包括语音增强、混响抑制、回声消除、波束形成等算法的开发工作\n\n-参与音频处理相关项目开发，包括算法设计，优化和性能验证\n\n-跟踪业界的最新技术\n\n', 15000, '北京', '研究生', '10', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 55, 'java开发工程师', '1.      全日制本科及以上学历，理工类专业，英文良好；    \n\n2.      熟悉java开发语言，熟悉UML及面向对象设计；    \n\n3.      熟悉Struts、Spring、Hibernate；了解EJB、HTML、CSS、Javascript；    \n\n4.      理解数据库原理，熟悉1个以上数据库系统；    \n\n5.      了解掌握Tomcat、jboss、webLogic等应用服务器配置应用；', '1.参与企业级物流综合平台（全新）的全流程各项工作。\n2.参与项目前/后端设计、开发及单元测试工作。\n3.参与项目后续实施及支持工作。\n4.完成项目相关的其他各类工作。', 8000, '广州', '本科', '100', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (17, 57, '机器学习/数据挖掘/自然语言处理工程师', '-具有以下一个或多个领域的理论背景和实践经验，包括机器学习/深度学习/强化学习/自然语言处理/推荐系统/信息检索等\n\n-深入理解机器学习常用组件（全连接神经网络，CNN等）的原理，有1年以上机器学习相关项目或研究经验\n\n-熟练掌握常用数据结构及算法\n\n-熟悉JAVA/C++等语言编程，有一定编程经验\n\n-熟练使用至少一门脚本语言，如python，linux shell等', '-研究数据挖掘或统计学习领域的前沿技术,并用于实际问题的解决和优化\n\n-大规模机器学习算法研究及并行化实现,为各种大规模机器学习应用研发核心技术\n\n-通过对数据的敏锐洞察,深入挖掘产品潜在价值和需求,进而提供更有价值的产品和服务,通过技术创新推动产品成长', 15000, '深圳', '研究生', '30', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 60, '移动端开发工程师', '必须具备的:\n计算机软件相关专业本科及以上学历;\n对于创新及解决具有挑战性的问题充满激情;\n热爱编程，基础扎实，理解算法和数据结构相关知识;至少掌握─种编程语言。\n可以加分的:\nC/C++/Java编程经验优先;\niOs、Android、Windows Phone开发经验。\n', '方寸之间，大有可为。\n无论是智能手机、还是平板电脑，有限的资源难不到才华横益的你。\n你将通过精简而不失高性能的设计，开发面向移动终端设备的互联网应用。在移动客户端开发团队，成为移动互联网发展的C位见证者。\n', 6800, '深圳', '本科', '11', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 61, '后台开发工程师', '必须具备的:扎实的编程能力;C/C++/Java开发语言;\nTCP/UDP网络协议及相关编程、进程间通讯编程;\n专业软件知识，包括算法、操作系统、软件工程、设计模式、数据结构、数据库系统、网络安全等。\n', '你是腾讯产品「背后」的英雄，\n服务后台的架构设计、开发、优化以及运营是你的绝学;\n你通过网络接入、业务运行逻辑、用户数据存储、业务数据挖掘等方向的优化，打造出更稳定、安全、高效和可靠的专业后台支撑体系，\n守护海量用户的笑容，\n深藏功与名。\n', 9000, '上海', '研究生', '40', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 63, '游戏引擎开发工程师', '必须具备的:\n计算机科学、计算机工程相关专业;\n扎实的算法、数据结构、操作系统等基础知识;热爱编程，至少掌握─种游戏开发常用的编程语言，对图形、物理、动画、Al等某一领域比较熟悉;熟悉Unreal、Unity等商业引擎;\n良好的数学和英文阅读功底;\n良好的沟通表达能力和团队合作意识;\n热爱游戏，对于创新和解决有挑战性的问题充满激情，有较强的学习能力、分析及解决问题能力;\n', '好的游戏，离不开优秀的游戏引擎，\n好的游戏引|擎，背后是一支顶尖的研发团队，\n他们不断探索、优化和打磨最前沿游戏引擎技术，钻研新技术的可能性，他们开发并优化游戏引擎，不断攻克渲染、动画、A等各类难题，\n他们为美术和策划—步步搭建起高效的内容制作工作流，\n他们等你来。\n', 9000, '深圳', '研究生', '30', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (17, 66, '运维开发工程师', '-具备扎实的编程基础，熟练掌握 Python 或 Golang 的一种，熟练掌握常见的数据结构和算法，熟悉面向对象设计原则，了解设计模式\n\n-熟悉单元测试的编写，有良好的编码风格\n\n-熟悉 HTTP 协议，有 Web 开发经验者优先\n\n-熟练掌握 git 的日常操作，有 GitHub、GitLab 或 Gerrit 使用经验者优先', '-参与运维自动化平台和工具的设计和研发工作，如配置管理、自动化部署、监控告警等，为小度智能屏的音视频业务提供支撑\n\n-参与运维自动化平台高可用架构的设计和构建，帮助运维工程师更有效地管理大规模集群，提升运维自动化水平，加速音视频业务的迭代\n\n-参与制定和改进部署流程，提高部署效率', 8000, '广州', '本科', '50', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (17, 68, '开发测试工程师', '-计算机相关专业，本科及以上学历\n-能熟练地应用以下一门或几门技术进行相关开发：C/C++/Java/object-c、Linux/Unix Shell、Perl/Python/PHP、JavaScript/Html/Ajax、MySql/Oracle及相关数据库技术等\n-具备快速的产品及业务学习能力，敏捷全面的逻辑思维能力\n-有责任心、敢于担当，工作积极主动，具备良好的团队合作精神，能融入多功能团队并与其他部门同事进行良好的沟通及合作\n', '-负责百度核心产品的测试工作\n\n-参与产品需求、系统设计和程序代码的评审工作并提出改进意见\n\n-评估项目质量风险并制定项目测试方案，设计并执行测试用例，跟踪定位产品软件中的缺陷或问题，保证项目质量和进度\n\n-参与互联网产品整个工程生产、发布过程中的技术创新，包括研发敏捷研发工具、线上监控系统、性能测试和监督工具等精确评估线上系统表现，以创新的工作模式提升产品的用户价值', 7000, '深圳', '本科', '70', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 70, '数据开发工程师', '你相信数据的力量。\n亿量级产品数据，无疑是你的梦想乐园。\n你可以在这片土壤上，开发数据评估模型及指标体系，用数据防范风险，你也可以通过数据建模，让抽象，变为具象，\n你甚至可以利用数据，引导核心产品的前进方向，在这里，让数据不止于数据。\n', '你相信数据的力量。\n亿量级产品数据，无疑是你的梦想乐园。\n你可以在这片土壤上，开发数据评估模型及指标体系，用数据防范风险，你也可以通过数据建模，让抽象，变为具象，\n你甚至可以利用数据，引导核心产品的前进方向，在这里，让数据不止于数据。\n', 10000, '深圳', '研究生', '10', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 72, '测试开发工程师', '必须具备的:\n计算机或相关专业本科及以上学历;\n—至两年的C/C++/Python或其他计算机语言的编程经验;了解操作系统底层原理;\n了解程序语言编译原理;了解动态语言工作原理;了解性能和安全等测试;了解自动化系统的能力;\n极具专研精神，主动积极的责任心、良好的团队合作精神。\n', '这是一个宏大、深邃而自由的技术世界，你将面对所有操作系统与虚拟机，\n扎实的底层基础原理是你的内功,卓越的自动化技术和工具是你的武器，在这里，你将一马当先，积极挑战新技术。\n深入钻研，如消息队列、事件冒泡、鼠标轨迹模拟、词法语法分析、进程注入、网络编程、综合工程、分布式计算等等，追求毫秒级无感式顺滑性能;\n大量机器与海量进程在你的指挥下井然有序，畅享算法与数据结构的美。你就是测试开发的先锋人。\n', 7000, '重庆', '本科', '56', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 73, 'python工程师', '1、计算机、信息、通信、数学相关专业本科及以上学历；\n2、熟悉面向对象编程；\n3、具有良好的沟通协调能力、团队协作精神；\n4、具备一定的抗压能力、应变能力和学习能力，有强烈的责任心、勤奋好学、热爱生活、热爱技术。', '1、参与数字机器人项目组件开发和维护；\n2、完成开发任务，完成软件的单元测试，并配合完成整个系统的集成测试；', 7000, '上海', '研究生', '50', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (18, 74, '算法工程师', '1、本科及以上学历，硕士博士优先，计算机、数学、电子工程、通信等相关专业；\n2、有极佳的工程实现能力，精通C/C++、Java、Python等至少一门编程语言；\n3、良好的数据敏感能力、较强的逻辑分析能力；\n4、热衷于数据结构和算法、在ACM大赛成绩优异者优先；\n5、有实际成果并发表在国际顶级会议、期刊者优先；\n6、熟悉常用机器学习算法，有linux下开发经验的，大规模数据处理经验优先。', '阿里巴巴广告/推荐/搜索引擎平台支持包括淘宝、天猫、菜鸟、优酷乃至海外电商在内整个集团的推荐与搜索的业务，也支撑集团的商业营销平台阿里妈妈，同时也负责阿里云的搜索和推荐产品。凭着十年的执着与幸运，这个三位一体的技术体系(AI·OS)构筑了世界上业务规模独一无二的大数据智能业务主战场，其技术集中度与灵活性也在业界也达到了空前的高度。', 9000, '杭州', '本科', '40', '2021-05-23', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (17, 75, '自动驾驶GNSS/INS融合定位算法工程师', '- 航天、测绘、卫星导航等相关专业的硕士或以上学历，博士优先\n\n-深入理解卡尔曼滤波、贝叶斯理论、估计理论、最优化等相关理论\n\n-深入了解模糊度固定理论、大气误差建模和高精度载波数据处理等\n\n-深入了解惯性解算、误差状态转移建模等，\n\n-熟悉C/C++程序开发，具有良好的编程习惯\n\n-具有良好的英文读写能力\n\n-具有良好的沟通能力，良好的团队合作精神，良好的科研精神\n\n-在相关领域会议或期刊发表论文者优先', '-设计开发高精度卫星导航定位算法,包括:RTK、PPP、PPP-RTK等\n\n-设计开发SINS解算和融合定位算法\n\n-分析解决无人车卫星定位/融合定位系统问题等\n\n-包括但不限于以下基础技术研发:惯导解算、组合滤波、GNSS融合等', 18000, '北京', '研究生', '80', '2021-05-22', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (18, 76, '安全工程师', '如果你熟识计算机网络，具备Linux环境下C/C++开发能力，至少掌握Perl/Python/Shell一种脚本语言；\n如果你熟知Web安全，了解当前流行的Web漏洞（XSS, SQL Injection, CSRF, etc），了解Java开发框架（Struts, Spring, iBATIS, Hibernate, etc）；', '从Web安全到底层系统安全，\n从客户端安全到无线产品安全，\n从安全规范制定到实时应急响应，\n从媒体安全到数字取证、数字确权，\n一切的努力都是为了更安全！\n从底层驱动到人机验证，\n从网络编程到数据加密，\n从大数据到风控服务，\n我们需要的是全栈的技术人！', 5000, '杭州', '本科', '10', '2021-05-23', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (18, 77, '研发工程师C/C++', '或许，你来自计算机专业，机械专业，甚至可能是学生物的，\n但是，你酷爱着计算机以及互联网技术，热衷于解决挑战性的问题。\n或许，你痴迷于数据结构和算法，热衷于ACM，常常为看到“accept”而兴奋的手足舞蹈；\n或许，你熟悉Unix/Linux/Win32环境下编程，并有相关开发经验，熟练使用调试工具，并熟悉某种脚本语言；\n或许，你熟悉网络编程和多线程编程，对TCP/IP，HTTP等网络协议有很深的理解；\n或许，你享受底层技术，在kernel的源代码中纵横驰骋；', '如果你对基础技术感兴趣，你可以参与基础软件的设计、开发和维护，如分布式文件系统、缓存系统、Key/Value存储系统、数据库、Linux操作系统等；\n如果你热衷于高性能分布式技术，你可以参与世界顶级规模的分布式服务端程序的系统设计，为阿里巴巴的产品提供强有力的后台支持，在海量的网络访问和数据处理中，设计并设施最强大的解决方案；\n如果你喜欢研究搜索技术，你可以参与搜索引擎各个功能模块的设计和实现，构建高可靠性、高可用性、高可扩展性的体系结构，满足日趋复杂的业务需求；', 4000, '北京', '专科', '60', '2021-05-23', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (16, 78, '数据库工程师', '1.计算机相关专业本科及以上，熟练掌握MySQL原理以及相关运维工具的使用,2年及以上相关经验；\n2.熟练掌握数据库优化方法，并对系统内核和硬件优化有一定的涉猎；\n3.对于Linux操作系统以及网络等有一定了解，并且能结合业务体系，快速定位问题；\n4.至少熟悉Python，Golang，Java，C等编程语言中的一种，有自动化运维系统建设经验的优先；\n5.有过互联网大规模MySQL集群运维经验的优先；\n6.熟悉Redis、MongoDB等NoSQL技术的优先；\n7.熟悉K8S、Docker等技术的优先。', '1.维护新浪网数据库集群稳定运行，持续发现和解决潜在问题；\n2.负责数据库集群的性能优化，探索新技术和新架构，以满足新浪移动高速业务发展需求；\n3.负责数据库的虚拟化平台以及自动化系统建设，实现自动化监控、高可用、可扩展架构；\n4.对数据库进行容量规划、架构设计，提高业务高可用性和容灾能力；\n5.负责数据库相关工作的整体规划，提供高价值决策建议。', 6500, '北京', '研究生', '71', '2021-05-22', '正在招聘', '软件工程,物联网工程');
INSERT INTO `tb_job` VALUES (21, 81, '前端开发工程师', '1、扎实的计算机基础知识，熟悉常用的数据结构、算法和设计模式，并能在日常研发中灵活使用；\n2、深入理解Web前端开发技术，包括HTML/CSS/JavaScript等；\n3、掌握至少一种主流前端框架（react vue angular等），有实际项目研发经验；\n4、熟悉网站性能优化，了解浏览器实现原理；', '1、负责字节核心级商业产品的前端研发工作，包括抖音电商相关的C端产品以及B端中后台系统的前端架构设计、开发与优化，覆盖Web应用、多端组件库、数据可视化等多个方向；\n2、参与团队前端工程化体系建设，逐步提升研发效率、研发质量，通过前端技术的不断产出驱动业务的发展；\n3、与产品经理、设计师、后端工程师一起，提升产品的用户体验，打造卓越的互联网产品；\n4、关注前端前沿技术发展，能够将新知识传递给团队，并且转化到潜在项目中。', 8000, '北京', '本科', '35', '2021-05-23', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (21, 82, '渗透测试工程师', '1、熟练掌握常见 web、系统安全漏洞原理、利用方式以及对解决方案有较深入理解；\n2、精通 web 攻击方法，能独立研究发现新的漏洞；\n3、熟悉白盒审计，能对python、go、php代码进行审计；\n4、具备一定的开发能力，能将重复的工作自动化；\n5、对工作有热情，耐心、责任心强，具备沟通能力和良好的团队意识。', '1、负责字节跳动各业务上线前安全测试（黑盒、白盒）及针对字节跳动产品的渗透测试工作；\n2、负责安全事件应急响应，快速定位、处置、溯源及复盘工作；\n3、跟踪安全漏洞、补丁信息，提出解决方案，和多部门协同进行处理；\n4、负责内部安全平台的策略设计与运营工作。', 7000, '武汉', '本科', '55', '2021-05-23', '正在招聘', '软件工程');
INSERT INTO `tb_job` VALUES (22, 83, '前端开发工程师', '1、计算机相关基础知识扎实，本科及以上学历，计算机相关专业；\n2、熟练掌握各种前端技术，包括HTML/CSS/JavaScript/Node.js 等；\n3、理解工程化思想，对构建和持续集成有一定认识，熟悉一种构建工具；\n4、理解组件化开发思想，有一定的设计能力，熟悉最少一种前端 MV* 框架；', '1、主要负责公司各个产品的增长服务；\n2、监控产品重要指标，分析理解重要指标波动原因；\n3、关注前沿技术研究，通过新技术服务团队和业务；\n4、完成跨部门紧密合作，推动用户产品的不断迭代优化。', 8000, '上海', '本科', '123', '2021-05-23', '停止招聘', '软件工程');
INSERT INTO `tb_job` VALUES (24, 84, 'Java初级开发', '1. Java 基础扎实，熟悉 IO、多线程、集合等基础框架，熟悉分布式、缓存、消息等机制；\n2. 五年以上使用 Java 进行开发的经验；精通AOP、MVC、Mybatis 等框架；', '1. 参与哈啰出行新业务系统设计、开发以及系统优化等工作，帮助业务快速成长。\n2. 协助业务方梳理需求，提供规划方案、架构设计方案， 能独立推进研发工作的开展。', 12000, '不限', '研究生', '50', '2022-11-30', '正在招聘', '计算机科学与技术,哲学,不限');
INSERT INTO `tb_job` VALUES (24, 85, 'C++', '精通C++', '写bug', 18000, '不限', '研究生', '20', '2022-11-30', '正在招聘', '不限,哲学,逻辑学,宗教学,伦理学');
INSERT INTO `tb_job` VALUES (26, 86, '嵌入式开发', '1、熟悉至少一种嵌入式平台的软件开发（如Power PC 、arm、DSP），熟悉常用的外设驱动开发（如CAN、IIC、FLASH）。\n2、具备一定的硬件基础知识，能够进行软件的嵌入式系统调试。\n3、熟练掌握C语言开发能力。', '1、根据项目需求，负责汽车电子类产品嵌入式软件开发工作。\n2、负责嵌入式软件架构设计、接口设计与调试、代码实现等相关工作。\n3、完成嵌入式软件相关文档的编写工作。', 20000, '不限', '研究生', '20', '2022-11-30', '正在招聘', '不限,哲学,计算机科学与技术,软件工程');
INSERT INTO `tb_job` VALUES (26, 87, '开发', '1、熟悉JAVA、Python、JS中的至少一种，掌握常见的数据结构、算法，了解软件工程、敏捷开发等知识，熟悉常用设计模式；', '有关键模块/组件的需求分析、方案设计、软件开发经验', 20000, '不限', '研究生', '4', '2022-11-30', '正在招聘', '不限');
COMMIT;

-- ----------------------------
-- Table structure for tb_job_resume
-- ----------------------------
DROP TABLE IF EXISTS `tb_job_resume`;
CREATE TABLE `tb_job_resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyid` bigint(20) NOT NULL COMMENT '公司id',
  `studentid` bigint(20) NOT NULL COMMENT '学生id',
  `jobid` bigint(20) NOT NULL COMMENT '岗位id',
  `resumeid` bigint(20) NOT NULL COMMENT '简历id',
  `send_status` varchar(10) NOT NULL COMMENT '投递状态',
  `send_time` varchar(10) NOT NULL COMMENT '投递时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tb_job_resume_ibfk_1` (`resumeid`) USING BTREE,
  KEY `tb_job_resume_ibfk_2` (`jobid`) USING BTREE,
  KEY `studentid` (`studentid`) USING BTREE,
  KEY `companyid` (`companyid`) USING BTREE,
  CONSTRAINT `tb_job_resume_ibfk_1` FOREIGN KEY (`resumeid`) REFERENCES `tb_resume` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_job_resume_ibfk_2` FOREIGN KEY (`jobid`) REFERENCES `tb_job` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_job_resume_ibfk_3` FOREIGN KEY (`studentid`) REFERENCES `tb_student` (`id`),
  CONSTRAINT `tb_job_resume_ibfk_4` FOREIGN KEY (`companyid`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_job_resume
-- ----------------------------
BEGIN;
INSERT INTO `tb_job_resume` VALUES (31, 16, 16, 78, 46, '已回复', '2022-05-13');
INSERT INTO `tb_job_resume` VALUES (33, 16, 16, 63, 46, '已回复', '2022-05-22');
INSERT INTO `tb_job_resume` VALUES (36, 17, 16, 35, 46, '未查阅', '2022-05-19');
INSERT INTO `tb_job_resume` VALUES (38, 18, 16, 77, 46, '已回复', '2022-05-17');
INSERT INTO `tb_job_resume` VALUES (39, 16, 17, 12, 49, '已回复', '2022-05-10');
INSERT INTO `tb_job_resume` VALUES (40, 17, 17, 35, 50, '未查阅', '2022-05-17');
INSERT INTO `tb_job_resume` VALUES (41, 16, 17, 60, 48, '人才库', '2022-11-19');
INSERT INTO `tb_job_resume` VALUES (42, 16, 17, 63, 49, '人才库', '2022-05-18');
INSERT INTO `tb_job_resume` VALUES (43, 17, 17, 66, 49, '未查阅', '2022-11-05');
INSERT INTO `tb_job_resume` VALUES (44, 17, 17, 57, 50, '未查阅', '2022-10-23');
INSERT INTO `tb_job_resume` VALUES (45, 18, 17, 77, 49, '已查阅', '2022-09-19');
INSERT INTO `tb_job_resume` VALUES (46, 18, 17, 74, 49, '人才库', '2021-05-23');
INSERT INTO `tb_job_resume` VALUES (47, 17, 18, 35, 52, '未查阅', '2021-05-23');
INSERT INTO `tb_job_resume` VALUES (48, 18, 18, 77, 53, '未查阅', '2021-05-15');
INSERT INTO `tb_job_resume` VALUES (49, 17, 18, 68, 52, '未查阅', '2021-05-09');
INSERT INTO `tb_job_resume` VALUES (50, 16, 18, 61, 51, '人才库', '2021-05-15');
INSERT INTO `tb_job_resume` VALUES (51, 18, 18, 33, 51, '未查阅', '2021-05-17');
INSERT INTO `tb_job_resume` VALUES (54, 16, 18, 63, 52, '人才库', '2021-05-14');
INSERT INTO `tb_job_resume` VALUES (55, 16, 18, 73, 52, '已查阅', '2021-05-12');
INSERT INTO `tb_job_resume` VALUES (56, 16, 18, 60, 51, '人才库', '2021-05-23');
INSERT INTO `tb_job_resume` VALUES (66, 16, 19, 72, 56, '已回复', '2021-05-27');
INSERT INTO `tb_job_resume` VALUES (73, 22, 16, 83, 46, '未查阅', '2021-07-26');
INSERT INTO `tb_job_resume` VALUES (80, 17, 19, 35, 56, '未查阅', '2022-11-30');
INSERT INTO `tb_job_resume` VALUES (81, 17, 19, 57, 56, '未查阅', '2022-11-29');
INSERT INTO `tb_job_resume` VALUES (82, 16, 19, 61, 56, '未查阅', '2022-11-28');
INSERT INTO `tb_job_resume` VALUES (93, 17, 25, 35, 86, '未查阅', '2022-11-29');
INSERT INTO `tb_job_resume` VALUES (94, 18, 25, 77, 86, '未查阅', '2022-11-30');
INSERT INTO `tb_job_resume` VALUES (95, 16, 25, 63, 86, '未查阅', '2022-11-30');
INSERT INTO `tb_job_resume` VALUES (96, 26, 25, 87, 86, '未查阅', '2022-11-30');
INSERT INTO `tb_job_resume` VALUES (97, 26, 25, 86, 86, '人才库', '2022-11-30');
COMMIT;

-- ----------------------------
-- Table structure for tb_resume
-- ----------------------------
DROP TABLE IF EXISTS `tb_resume`;
CREATE TABLE `tb_resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resume_name` varchar(20) NOT NULL COMMENT '简历名称',
  `intention` varchar(20) DEFAULT NULL COMMENT '求职意向',
  `studentId` bigint(20) NOT NULL COMMENT '学生id',
  `update_time` varchar(20) NOT NULL COMMENT '更新时间',
  `attachment` varchar(100) DEFAULT NULL COMMENT '附近链接',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `学生id` (`studentId`) USING BTREE,
  CONSTRAINT `tb_resume_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `tb_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_resume
-- ----------------------------
BEGIN;
INSERT INTO `tb_resume` VALUES (46, '蓝岸C/C++简历', '', 16, '2022-07-26', 'http://oss.lankejun.cn/aba913d1-62b3-421b-8113-ad90dbf89ac7.pdf');
INSERT INTO `tb_resume` VALUES (48, '张三java简历', '', 17, '2022-08-22', 'http://oss.lankejun.cn/3c618f33-d7a0-43ac-a10f-4486909fef7c.pdf');
INSERT INTO `tb_resume` VALUES (49, '张三C简历', '', 17, '2022-07-22', 'http://oss.lankejun.cn/c836d62e-f40e-4dcb-83eb-bf62e3068876.pdf');
INSERT INTO `tb_resume` VALUES (50, '张三python简历', '', 17, '2022-06-22', 'http://oss.lankejun.cn/6553e4dc-cce7-470b-8d4a-46081704c205.pdf');
INSERT INTO `tb_resume` VALUES (51, '李四Java.', '', 18, '2022-04-23', 'http://oss.lankejun.cn/cad82787-39ee-47c0-82e0-a52caad0748e.pdf');
INSERT INTO `tb_resume` VALUES (52, '李四python', '', 18, '2022-11-23', 'http://oss.lankejun.cn/7577ac14-7a10-46ca-8137-59ec9eb80325.pdf');
INSERT INTO `tb_resume` VALUES (53, '李四C/C++', '', 18, '2022-10-23', 'http://oss.lankejun.cn/13bd4fd2-ce81-4068-8bc5-631a60e1db76.pdf');
INSERT INTO `tb_resume` VALUES (56, '测试', '', 19, '2022-11-27', 'http://oss.lankejun.cn/3e177377-7ee5-4364-9cd2-92fd3ce04585.pdf');
INSERT INTO `tb_resume` VALUES (57, '前端', '', 19, '2022-11-27', 'http://oss.lankejun.cn/f9d06270-9362-43e9-b709-104cdc5d1982.pdf');
INSERT INTO `tb_resume` VALUES (58, 'Java', '', 19, '2022-06-27', 'http://oss.lankejun.cn/b05ac3d5-0019-40f4-880a-b072337a30d1.pdf');
INSERT INTO `tb_resume` VALUES (85, 'java', '', 21, '2022-11-30', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/cd2cc50e290d434eb28ad54a8afab011');
INSERT INTO `tb_resume` VALUES (86, 'c++', '', 25, '2022-11-30', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/c2af3567f95e4161bbc8afe7b4a04209');
INSERT INTO `tb_resume` VALUES (87, 'java', '', 25, '2022-11-30', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/e14a6994a9124e08907ab2dca0ded499');
COMMIT;

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_name` varchar(20) DEFAULT NULL COMMENT '学生姓名',
  `email` varchar(20) NOT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `login_password` varchar(50) NOT NULL COMMENT '登录密码',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `portrait` varchar(255) DEFAULT NULL COMMENT '头像',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `education` varchar(10) DEFAULT NULL COMMENT '学历',
  `lengthOfSchooling` varchar(10) DEFAULT NULL COMMENT '学制',
  `foreignLanguages` varchar(10) DEFAULT NULL COMMENT '外语',
  `college` varchar(50) DEFAULT NULL COMMENT '高校',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `name` (`student_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_student
-- ----------------------------
BEGIN;
INSERT INTO `tb_student` VALUES (16, '蓝岸', '1746007300@qq.com', '18075864481', 'aaaa1111', '男', 'http://oss.lankejun.cn/6362d867-6750-4fd5-8854-349ad8da81f5.png', '1998-01-18', '本科', '统招全日制', '英语四级', '广西民族师范学院', '软件工程');
INSERT INTO `tb_student` VALUES (17, '张三', '3161035568@qq.com', '18077883456', 'aaaa1111', '女', 'http://oss.lankejun.cn/c79268f9-e6bc-4bd6-98e6-a50efbf104f4.png', '2000-01-06', '本科', '非全日制', '英语六级', '华东师范大学', '物联网工程');
INSERT INTO `tb_student` VALUES (18, '李四', '2729920479@qq.com', '18744538976', 'aaaa1111', '男', 'http://oss.lankejun.cn/10071bc6-4764-40a9-83c9-5d8e97f88089.png', '1997-07-16', '专科', '非全日制', '其他', '中国政法大学', NULL);
INSERT INTO `tb_student` VALUES (19, '踏雪寻梅', '2017165879@qq.com', '18894740432', 'aaaa1111', '男', 'http://oss.lankejun.cn/80bfcaea-4a72-4a34-9869-142f1259ac80.png', '1998-04-28', '本科', '统招全日制', '其他', '桂林航天工业学院', '计算机科学与技术');
INSERT INTO `tb_student` VALUES (21, '小蔡', '2350938432@qq.com', '16429307311', 'aaaa1111', '男', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/github.jpg', '2022-11-15', '研究生', '统招全日制', '英语六级', '北京大学', '哲学');
INSERT INTO `tb_student` VALUES (25, '王五', '2644293063@qq.com', '13501903313', 'aaaa1111', '男', 'https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/694242ff9a104600adaafb26d9381038', '1982-03-10', '研究生', '统招全日制', '英语六级', '北京大学', '哲学');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
