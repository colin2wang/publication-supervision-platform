-- ============================================================
-- 08_init_menu_data.sql
-- 初始化菜单数据
-- ============================================================

-- ----------------------------
-- 一级菜单（目录）
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1, 0, '智能辅助鉴定', 1, NULL, '/identification', NULL, 'identification', 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2, 0, '出版物流转监管', 1, NULL, '/circulation', NULL, 'circulation', 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3, 0, '智能舆情分析', 1, NULL, '/opinion', NULL, 'opinion', 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4, 0, 'AI中台', 1, NULL, '/ai-platform', NULL, 'ai', 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5, 0, '系统管理', 1, NULL, '/system', NULL, 'system', 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- 智能辅助鉴定 子菜单
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
-- 出版物管理
(101, 1, '出版物管理', 2, 'identification:publication:list', '/identification/publication', 'identification/publication/index', 'book', 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 鉴定任务
(102, 1, '鉴定任务', 2, 'identification:task:list', '/identification/task', 'identification/task/index', 'task', 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 鉴定报告
(103, 1, '鉴定报告', 2, 'identification:report:list', '/identification/report', 'identification/report/index', 'report', 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 违法样本库
(104, 1, '违法样本库', 2, 'identification:sample:list', '/identification/sample', 'identification/sample/index', 'database', 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 溯源分析
(105, 1, '溯源分析', 2, 'identification:traceability:list', '/identification/traceability', 'identification/traceability/index', 'trace', 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 执法对接
(106, 1, '执法对接', 2, 'identification:enforcement:list', '/identification/enforcement', 'identification/enforcement/index', 'law', 6, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 出版物管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1011, 101, '新增出版物', 3, 'identification:publication:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1012, 101, '编辑出版物', 3, 'identification:publication:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1013, 101, '删除出版物', 3, 'identification:publication:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1014, 101, '查询出版物', 3, 'identification:publication:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1015, 101, '导入出版物', 3, 'identification:publication:import', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 鉴定任务按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1021, 102, '新增任务', 3, 'identification:task:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1022, 102, '编辑任务', 3, 'identification:task:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1023, 102, '删除任务', 3, 'identification:task:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1024, 102, '查询任务', 3, 'identification:task:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1025, 102, '分配任务', 3, 'identification:task:assign', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1026, 102, '提交鉴定', 3, 'identification:task:submit', NULL, NULL, NULL, 6, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 鉴定报告按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1031, 103, '新增报告', 3, 'identification:report:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1032, 103, '编辑报告', 3, 'identification:report:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1033, 103, '删除报告', 3, 'identification:report:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1034, 103, '查询报告', 3, 'identification:report:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1035, 103, '导出报告', 3, 'identification:report:export', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 违法样本库按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1041, 104, '新增样本', 3, 'identification:sample:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1042, 104, '编辑样本', 3, 'identification:sample:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1043, 104, '删除样本', 3, 'identification:sample:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1044, 104, '查询样本', 3, 'identification:sample:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 溯源分析按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1051, 105, '新增溯源', 3, 'identification:traceability:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1052, 105, '查询溯源', 3, 'identification:traceability:query', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 执法对接按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(1061, 106, '新增对接', 3, 'identification:enforcement:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(1062, 106, '查询对接', 3, 'identification:enforcement:query', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- 出版物流转监管 子菜单
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
-- 商户管理
(201, 2, '商户管理', 2, 'circulation:merchant:list', '/circulation/merchant', 'circulation/merchant/index', 'shop', 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 包裹监管
(202, 2, '包裹监管', 2, 'circulation:package:list', '/circulation/package', 'circulation/package/index', 'package', 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 预警管理
(203, 2, '预警管理', 2, 'circulation:alert:list', '/circulation/alert', 'circulation/alert/index', 'warning', 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 黑白名单
(204, 2, '黑白名单', 2, 'circulation:blacklist:list', '/circulation/blacklist', 'circulation/blacklist/index', 'list', 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 统计分析
(205, 2, '统计分析', 2, 'circulation:statistics:list', '/circulation/statistics', 'circulation/statistics/index', 'chart', 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 商户管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(2011, 201, '新增商户', 3, 'circulation:merchant:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2012, 201, '编辑商户', 3, 'circulation:merchant:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2013, 201, '删除商户', 3, 'circulation:merchant:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2014, 201, '查询商户', 3, 'circulation:merchant:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2015, 201, '核验资质', 3, 'circulation:merchant:verify', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 包裹监管按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(2021, 202, '新增包裹', 3, 'circulation:package:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2022, 202, '查询包裹', 3, 'circulation:package:query', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2023, 202, '拦截包裹', 3, 'circulation:package:intercept', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 预警管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(2031, 203, '查询预警', 3, 'circulation:alert:query', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2032, 203, '处理预警', 3, 'circulation:alert:handle', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2033, 203, '归档预警', 3, 'circulation:alert:archive', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 黑白名单按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(2041, 204, '新增名单', 3, 'circulation:blacklist:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2042, 204, '编辑名单', 3, 'circulation:blacklist:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2043, 204, '删除名单', 3, 'circulation:blacklist:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2044, 204, '查询名单', 3, 'circulation:blacklist:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 统计分析按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(2051, 205, '查询统计', 3, 'circulation:statistics:query', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(2052, 205, '导出统计', 3, 'circulation:statistics:export', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- 智能舆情分析 子菜单
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
-- 采集任务
(301, 3, '采集任务', 2, 'opinion:task:list', '/opinion/task', 'opinion/task/index', 'download', 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 舆情数据
(302, 3, '舆情数据', 2, 'opinion:data:list', '/opinion/data', 'opinion/data/index', 'database', 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 舆情事件
(303, 3, '舆情事件', 2, 'opinion:event:list', '/opinion/event', 'opinion/event/index', 'event', 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 舆情报告
(304, 3, '舆情报告', 2, 'opinion:report:list', '/opinion/report', 'opinion/report/index', 'report', 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 舆情效能
(305, 3, '舆情效能', 2, 'opinion:effectiveness:list', '/opinion/effectiveness', 'opinion/effectiveness/index', 'chart', 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 采集任务按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(3011, 301, '新增采集任务', 3, 'opinion:task:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3012, 301, '编辑采集任务', 3, 'opinion:task:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3013, 301, '删除采集任务', 3, 'opinion:task:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3014, 301, '查询采集任务', 3, 'opinion:task:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3015, 301, '启停采集任务', 3, 'opinion:task:toggle', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 舆情数据按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(3021, 302, '查询舆情', 3, 'opinion:data:query', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3022, 302, '导出舆情', 3, 'opinion:data:export', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 舆情事件按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(3031, 303, '新增事件', 3, 'opinion:event:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3032, 303, '编辑事件', 3, 'opinion:event:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3033, 303, '删除事件', 3, 'opinion:event:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3034, 303, '查询事件', 3, 'opinion:event:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 舆情报告按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(3041, 304, '生成报告', 3, 'opinion:report:generate', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3042, 304, '查询报告', 3, 'opinion:report:query', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3043, 304, '审核报告', 3, 'opinion:report:review', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(3044, 304, '导出报告', 3, 'opinion:report:export', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 舆情效能按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(3051, 305, '查询效能', 3, 'opinion:effectiveness:query', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- AI中台 子菜单
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
-- 知识库管理
(401, 4, '知识库管理', 2, 'ai:knowledge:list', '/ai/knowledge', 'ai/knowledge/index', 'database', 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 模型管理
(402, 4, '模型管理', 2, 'ai:model:list', '/ai/model', 'ai/model/index', 'model', 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 智能体管理
(403, 4, '智能体管理', 2, 'ai:agent:list', '/ai/agent', 'ai/agent/index', 'robot', 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 数字人管理
(404, 4, '数字人管理', 2, 'ai:digital-human:list', '/ai/digital-human', 'ai/digital-human/index', 'avatar', 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 对话管理
(405, 4, '对话管理', 2, 'ai:conversation:list', '/ai/conversation', 'ai/conversation/index', 'chat', 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 数据质量
(406, 4, '数据质量', 2, 'ai:quality:list', '/ai/quality', 'ai/quality/index', 'check', 6, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 内容安全
(407, 4, '内容安全', 2, 'ai:security:list', '/ai/security', 'ai/security/index', 'shield', 7, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 翻译管理
(408, 4, '翻译管理', 2, 'ai:translation:list', '/ai/translation', 'ai/translation/index', 'translate', 8, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 知识库管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4011, 401, '新增知识库', 3, 'ai:knowledge:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4012, 401, '编辑知识库', 3, 'ai:knowledge:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4013, 401, '删除知识库', 3, 'ai:knowledge:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4014, 401, '查询知识库', 3, 'ai:knowledge:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4015, 401, '上传文档', 3, 'ai:knowledge:upload', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 模型管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4021, 402, '新增模型', 3, 'ai:model:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4022, 402, '编辑模型', 3, 'ai:model:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4023, 402, '删除模型', 3, 'ai:model:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4024, 402, '查询模型', 3, 'ai:model:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 智能体管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4031, 403, '新增智能体', 3, 'ai:agent:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4032, 403, '编辑智能体', 3, 'ai:agent:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4033, 403, '删除智能体', 3, 'ai:agent:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4034, 403, '查询智能体', 3, 'ai:agent:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 数字人管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4041, 404, '新增数字人', 3, 'ai:digital-human:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4042, 404, '编辑数字人', 3, 'ai:digital-human:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4043, 404, '删除数字人', 3, 'ai:digital-human:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4044, 404, '查询数字人', 3, 'ai:digital-human:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 数据质量按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4061, 406, '新增规则', 3, 'ai:quality:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4062, 406, '编辑规则', 3, 'ai:quality:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4063, 406, '删除规则', 3, 'ai:quality:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4064, 406, '查询规则', 3, 'ai:quality:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4065, 406, '执行检查', 3, 'ai:quality:execute', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 内容安全按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4071, 407, '新增关键词', 3, 'ai:security:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4072, 407, '编辑关键词', 3, 'ai:security:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4073, 407, '删除关键词', 3, 'ai:security:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4074, 407, '查询拦截日志', 3, 'ai:security:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 翻译管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(4081, 408, '新增翻译', 3, 'ai:translation:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(4082, 408, '查询翻译', 3, 'ai:translation:query', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- ----------------------------
-- 系统管理 子菜单
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
-- 用户管理
(501, 5, '用户管理', 2, 'system:user:list', '/system/user', 'system/user/index', 'user', 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 角色管理
(502, 5, '角色管理', 2, 'system:role:list', '/system/role', 'system/role/index', 'role', 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 菜单管理
(503, 5, '菜单管理', 2, 'system:menu:list', '/system/menu', 'system/menu/index', 'menu', 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 部门管理
(504, 5, '部门管理', 2, 'system:department:list', '/system/department', 'system/department/index', 'department', 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 字典管理
(505, 5, '字典管理', 2, 'system:dict:list', '/system/dict', 'system/dict/index', 'dict', 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 参数设置
(506, 5, '参数设置', 2, 'system:config:list', '/system/config', 'system/config/index', 'config', 6, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 通知管理
(507, 5, '通知管理', 2, 'system:notification:list', '/system/notification', 'system/notification/index', 'bell', 7, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 文件管理
(508, 5, '文件管理', 2, 'system:file:list', '/system/file', 'system/file/index', 'file', 8, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 日志管理
(509, 5, '日志管理', 2, 'system:log:list', '/system/log', 'system/log/index', 'log', 9, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
-- 定时任务
(510, 5, '定时任务', 2, 'system:task:list', '/system/task', 'system/task/index', 'clock', 10, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 用户管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5011, 501, '新增用户', 3, 'system:user:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5012, 501, '编辑用户', 3, 'system:user:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5013, 501, '删除用户', 3, 'system:user:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5014, 501, '查询用户', 3, 'system:user:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5015, 501, '重置密码', 3, 'system:user:reset-pwd', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5016, 501, '分配角色', 3, 'system:user:assign-role', NULL, NULL, NULL, 6, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 角色管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5021, 502, '新增角色', 3, 'system:role:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5022, 502, '编辑角色', 3, 'system:role:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5023, 502, '删除角色', 3, 'system:role:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5024, 502, '查询角色', 3, 'system:role:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5025, 502, '分配权限', 3, 'system:role:assign-permission', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 菜单管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5031, 503, '新增菜单', 3, 'system:menu:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5032, 503, '编辑菜单', 3, 'system:menu:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5033, 503, '删除菜单', 3, 'system:menu:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5034, 503, '查询菜单', 3, 'system:menu:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 部门管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5041, 504, '新增部门', 3, 'system:department:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5042, 504, '编辑部门', 3, 'system:department:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5043, 504, '删除部门', 3, 'system:department:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5044, 504, '查询部门', 3, 'system:department:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 字典管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5051, 505, '新增字典', 3, 'system:dict:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5052, 505, '编辑字典', 3, 'system:dict:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5053, 505, '删除字典', 3, 'system:dict:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5054, 505, '查询字典', 3, 'system:dict:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 日志管理按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5091, 509, '查询操作日志', 3, 'system:log:operation-query', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5092, 509, '查询登录日志', 3, 'system:log:login-query', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5093, 509, '清除日志', 3, 'system:log:clear', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP);

-- 定时任务按钮权限
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, permission_code, path, component, icon, sort, visible, status, created_by, created_at) VALUES
(5101, 510, '新增任务', 3, 'system:task:add', NULL, NULL, NULL, 1, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5102, 510, '编辑任务', 3, 'system:task:edit', NULL, NULL, NULL, 2, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5103, 510, '删除任务', 3, 'system:task:delete', NULL, NULL, NULL, 3, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5104, 510, '查询任务', 3, 'system:task:query', NULL, NULL, NULL, 4, TRUE, 1, 'admin', CURRENT_TIMESTAMP),
(5105, 510, '启停任务', 3, 'system:task:toggle', NULL, NULL, NULL, 5, TRUE, 1, 'admin', CURRENT_TIMESTAMP);
