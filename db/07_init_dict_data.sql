-- ============================================================
-- 07_init_dict_data.sql
-- 初始化字典类型和字典数据
-- ============================================================

-- ----------------------------
-- 字典类型
-- ----------------------------
INSERT INTO sys_dict_type (id, dict_code, dict_name, status, created_by, created_at) VALUES
(1, 'sys_status', '系统状态', 1, 'admin', CURRENT_TIMESTAMP),
(2, 'sys_gender', '性别', 1, 'admin', CURRENT_TIMESTAMP),
(3, 'idt_task_type', '鉴定任务类型', 1, 'admin', CURRENT_TIMESTAMP),
(4, 'idt_task_status', '鉴定任务状态', 1, 'admin', CURRENT_TIMESTAMP),
(5, 'idt_conclusion', '鉴定结论', 1, 'admin', CURRENT_TIMESTAMP),
(6, 'idt_material_type', '鉴定材料类型', 1, 'admin', CURRENT_TIMESTAMP),
(7, 'idt_priority', '优先级', 1, 'admin', CURRENT_TIMESTAMP),
(8, 'circ_alert_level', '预警级别', 1, 'admin', CURRENT_TIMESTAMP),
(9, 'circ_list_type', '名单类型', 1, 'admin', CURRENT_TIMESTAMP),
(10, 'circ_package_status', '包裹状态', 1, 'admin', CURRENT_TIMESTAMP),
(11, 'opi_sentiment', '情感倾向', 1, 'admin', CURRENT_TIMESTAMP),
(12, 'opi_report_type', '报告类型', 1, 'admin', CURRENT_TIMESTAMP),
(13, 'opi_channel_type', '渠道类型', 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- sys_status 系统状态
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(1, 1, '启用', '1', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(2, 1, '禁用', '0', 2, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- sys_gender 性别
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(10, 2, '未知', '0', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(11, 2, '男', '1', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(12, 2, '女', '2', 3, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- idt_task_type 鉴定任务类型
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(20, 3, 'ISBN鉴定', '1', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(21, 3, 'CIP鉴定', '2', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(22, 3, '封面鉴定', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(23, 3, '内容鉴定', '4', 4, 0, 1, 'admin', CURRENT_TIMESTAMP),
(24, 3, '印刷鉴定', '5', 5, 0, 1, 'admin', CURRENT_TIMESTAMP),
(25, 3, '纸张鉴定', '6', 6, 0, 1, 'admin', CURRENT_TIMESTAMP),
(26, 3, '装订鉴定', '7', 7, 0, 1, 'admin', CURRENT_TIMESTAMP),
(27, 3, '综合鉴定', '8', 8, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- idt_task_status 鉴定任务状态
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(30, 4, '待分配', '0', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(31, 4, '待鉴定', '1', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(32, 4, '鉴定中', '2', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(33, 4, '待审核', '3', 4, 0, 1, 'admin', CURRENT_TIMESTAMP),
(34, 4, '已完成', '4', 5, 0, 1, 'admin', CURRENT_TIMESTAMP),
(35, 4, '已归档', '5', 6, 0, 1, 'admin', CURRENT_TIMESTAMP),
(36, 4, '已驳回', '6', 7, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- idt_conclusion 鉴定结论
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(40, 5, '正版', '1', 1, 0, 1, 'admin', CURRENT_TIMESTAMP),
(41, 5, '盗版', '2', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(42, 5, '疑似盗版', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(43, 5, '无法判定', '4', 4, 1, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- idt_material_type 鉴定材料类型
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(50, 6, '封面', '1', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(51, 6, '版权页', '2', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(52, 6, '目录', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(53, 6, '内页', '4', 4, 0, 1, 'admin', CURRENT_TIMESTAMP),
(54, 6, '其他', '5', 5, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- idt_priority 优先级
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(60, 7, '低', '1', 1, 0, 1, 'admin', CURRENT_TIMESTAMP),
(61, 7, '中', '2', 2, 1, 1, 'admin', CURRENT_TIMESTAMP),
(62, 7, '高', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(63, 7, '紧急', '4', 4, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- circ_alert_level 预警级别
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(70, 8, '蓝色预警', '1', 1, 0, 1, 'admin', CURRENT_TIMESTAMP),
(71, 8, '黄色预警', '2', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(72, 8, '橙色预警', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(73, 8, '红色预警', '4', 4, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- circ_list_type 名单类型
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(80, 9, '不在名单', '0', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(81, 9, '黑名单', '1', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(82, 9, '白名单', '2', 3, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- circ_package_status 包裹状态
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(90, 10, '待揽收', '0', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(91, 10, '运输中', '1', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(92, 10, '已签收', '2', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(93, 10, '已拦截', '3', 4, 0, 1, 'admin', CURRENT_TIMESTAMP),
(94, 10, '已退回', '4', 5, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- opi_sentiment 情感倾向
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(100, 11, '正面', '1', 1, 0, 1, 'admin', CURRENT_TIMESTAMP),
(101, 11, '中性', '0', 2, 1, 1, 'admin', CURRENT_TIMESTAMP),
(102, 11, '负面', '-1', 3, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- opi_report_type 报告类型
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(110, 12, '日报', '1', 1, 1, 1, 'admin', CURRENT_TIMESTAMP),
(111, 12, '周报', '2', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(112, 12, '月报', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(113, 12, '专题报告', '4', 4, 0, 1, 'admin', CURRENT_TIMESTAMP),
(114, 12, '简报', '5', 5, 0, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- opi_channel_type 渠道类型
-- ----------------------------
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, dict_sort, is_default, status, created_by, created_at) VALUES
(120, 13, '微博', '1', 1, 0, 1, 'admin', CURRENT_TIMESTAMP),
(121, 13, '微信', '2', 2, 0, 1, 'admin', CURRENT_TIMESTAMP),
(122, 13, '论坛', '3', 3, 0, 1, 'admin', CURRENT_TIMESTAMP),
(123, 13, '新闻', '4', 4, 0, 1, 'admin', CURRENT_TIMESTAMP),
(124, 13, '短视频', '5', 5, 0, 1, 'admin', CURRENT_TIMESTAMP),
(125, 13, '其他', '6', 6, 1, 1, 'admin', CURRENT_TIMESTAMP);
