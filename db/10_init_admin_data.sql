-- ============================================================
-- 10_init_admin_data.sql
-- 初始化管理员用户并分配角色
-- ============================================================

-- 创建超级管理员用户
-- 密码: admin123 (BCrypt加密)
INSERT INTO sys_user (id, username, password, real_name, gender, email, phone, status, login_count, created_by, created_at, deleted, version) VALUES
(1, 'admin', '$2a$10$VQEDJgFGfIYpKFKBPOBjKu4wO1rNtIvQ2tKQXvHrYnB5Yp4eB7WQa', '系统管理员', 1, 'admin@pub-supervision.com', '13800138000', 1, 0, 'admin', CURRENT_TIMESTAMP, FALSE, 0);

-- 分配管理员角色
INSERT INTO sys_user_role (user_id, role_id, created_at) VALUES
(1, 1, CURRENT_TIMESTAMP);

-- 创建默认部门
INSERT INTO sys_department (id, parent_id, dept_name, dept_code, leader, phone, email, sort, status, ancestors, created_by, created_at, deleted, version) VALUES
(1, 0, '平台管理部', 'DEPT001', '系统管理员', '13800138000', 'admin@pub-supervision.com', 1, 1, '0', 'admin', CURRENT_TIMESTAMP, FALSE, 0),
(2, 1, '鉴定中心', 'DEPT002', NULL, NULL, NULL, 1, 1, '0,1', 'admin', CURRENT_TIMESTAMP, FALSE, 0),
(3, 1, '监管中心', 'DEPT003', NULL, NULL, NULL, 2, 1, '0,1', 'admin', CURRENT_TIMESTAMP, FALSE, 0),
(4, 1, '舆情中心', 'DEPT004', NULL, NULL, NULL, 3, 1, '0,1', 'admin', CURRENT_TIMESTAMP, FALSE, 0),
(5, 1, 'AI技术中心', 'DEPT005', NULL, NULL, NULL, 4, 1, '0,1', 'admin', CURRENT_TIMESTAMP, FALSE, 0);

-- 更新管理员用户部门
UPDATE sys_user SET department_id = 1 WHERE id = 1;
