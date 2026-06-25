-- ============================================================
-- 02_create_tables_sys.sql
-- 系统管理模块表
-- ============================================================

-- ----------------------------
-- 系统用户表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    real_name VARCHAR(64),
    gender SMALLINT DEFAULT 0,
    email VARCHAR(128),
    phone VARCHAR(32),
    avatar VARCHAR(512),
    department_id BIGINT,
    position VARCHAR(128),
    status SMALLINT DEFAULT 1,
    last_login_at TIMESTAMP,
    login_count INTEGER DEFAULT 0,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_user IS '系统用户表';
COMMENT ON COLUMN sys_user.id IS '用户ID';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.real_name IS '真实姓名';
COMMENT ON COLUMN sys_user.gender IS '性别 0未知 1男 2女';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.avatar IS '头像URL';
COMMENT ON COLUMN sys_user.department_id IS '部门ID';
COMMENT ON COLUMN sys_user.position IS '职位';
COMMENT ON COLUMN sys_user.status IS '状态 0禁用 1启用';
COMMENT ON COLUMN sys_user.last_login_at IS '最后登录时间';
COMMENT ON COLUMN sys_user.login_count IS '登录次数';
COMMENT ON COLUMN sys_user.created_by IS '创建人';
COMMENT ON COLUMN sys_user.created_at IS '创建时间';
COMMENT ON COLUMN sys_user.updated_by IS '更新人';
COMMENT ON COLUMN sys_user.updated_at IS '更新时间';
COMMENT ON COLUMN sys_user.deleted IS '是否删除';
COMMENT ON COLUMN sys_user.version IS '乐观锁版本号';

CREATE INDEX idx_sys_user_department_id ON sys_user(department_id);
CREATE INDEX idx_sys_user_status ON sys_user(status);

-- ----------------------------
-- 系统角色表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY,
    role_code VARCHAR(64) NOT NULL UNIQUE,
    role_name VARCHAR(64) NOT NULL,
    data_scope SMALLINT DEFAULT 1,
    sort INTEGER DEFAULT 0,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_role IS '系统角色表';
COMMENT ON COLUMN sys_role.id IS '角色ID';
COMMENT ON COLUMN sys_role.role_code IS '角色编码';
COMMENT ON COLUMN sys_role.role_name IS '角色名称';
COMMENT ON COLUMN sys_role.data_scope IS '数据范围 1全部 2自定义 3本部门 4本部门及以下 5仅本人';
COMMENT ON COLUMN sys_role.sort IS '排序';
COMMENT ON COLUMN sys_role.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 系统菜单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    menu_name VARCHAR(64) NOT NULL,
    menu_type SMALLINT DEFAULT 1,
    permission_code VARCHAR(128),
    path VARCHAR(256),
    component VARCHAR(256),
    icon VARCHAR(128),
    sort INTEGER DEFAULT 0,
    visible BOOLEAN DEFAULT TRUE,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_menu IS '系统菜单表';
COMMENT ON COLUMN sys_menu.id IS '菜单ID';
COMMENT ON COLUMN sys_menu.parent_id IS '父菜单ID 0表示根菜单';
COMMENT ON COLUMN sys_menu.menu_name IS '菜单名称';
COMMENT ON COLUMN sys_menu.menu_type IS '菜单类型 1目录 2菜单 3按钮';
COMMENT ON COLUMN sys_menu.permission_code IS '权限标识';
COMMENT ON COLUMN sys_menu.path IS '路由地址';
COMMENT ON COLUMN sys_menu.component IS '组件路径';
COMMENT ON COLUMN sys_menu.icon IS '图标';
COMMENT ON COLUMN sys_menu.sort IS '排序';
COMMENT ON COLUMN sys_menu.visible IS '是否可见';
COMMENT ON COLUMN sys_menu.status IS '状态 0禁用 1启用';

CREATE INDEX idx_sys_menu_parent_id ON sys_menu(parent_id);

-- ----------------------------
-- 用户角色关联表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_user_role (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE sys_user_role IS '用户角色关联表';
COMMENT ON COLUMN sys_user_role.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_role.role_id IS '角色ID';

CREATE UNIQUE INDEX uk_sys_user_role ON sys_user_role(user_id, role_id);

-- ----------------------------
-- 角色菜单关联表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id SERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE sys_role_menu IS '角色菜单关联表';
COMMENT ON COLUMN sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_menu.menu_id IS '菜单ID';

CREATE UNIQUE INDEX uk_sys_role_menu ON sys_role_menu(role_id, menu_id);

-- ----------------------------
-- 部门表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_department (
    id BIGINT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    dept_name VARCHAR(64) NOT NULL,
    dept_code VARCHAR(64),
    leader VARCHAR(64),
    phone VARCHAR(32),
    email VARCHAR(128),
    sort INTEGER DEFAULT 0,
    status SMALLINT DEFAULT 1,
    ancestors VARCHAR(512),
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_department IS '部门表';
COMMENT ON COLUMN sys_department.id IS '部门ID';
COMMENT ON COLUMN sys_department.parent_id IS '父部门ID 0表示根部门';
COMMENT ON COLUMN sys_department.dept_name IS '部门名称';
COMMENT ON COLUMN sys_department.dept_code IS '部门编码';
COMMENT ON COLUMN sys_department.leader IS '负责人';
COMMENT ON COLUMN sys_department.phone IS '联系电话';
COMMENT ON COLUMN sys_department.email IS '邮箱';
COMMENT ON COLUMN sys_department.sort IS '排序';
COMMENT ON COLUMN sys_department.status IS '状态 0禁用 1启用';
COMMENT ON COLUMN sys_department.ancestors IS '祖级列表 如0,1,2';

CREATE INDEX idx_sys_department_parent_id ON sys_department(parent_id);

-- ----------------------------
-- 字典类型表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_dict_type (
    id BIGINT PRIMARY KEY,
    dict_code VARCHAR(64) NOT NULL UNIQUE,
    dict_name VARCHAR(128) NOT NULL,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_dict_type IS '字典类型表';
COMMENT ON COLUMN sys_dict_type.id IS '字典类型ID';
COMMENT ON COLUMN sys_dict_type.dict_code IS '字典编码';
COMMENT ON COLUMN sys_dict_type.dict_name IS '字典名称';
COMMENT ON COLUMN sys_dict_type.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 字典数据表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_dict_data (
    id BIGINT PRIMARY KEY,
    dict_type_id BIGINT NOT NULL,
    dict_label VARCHAR(128) NOT NULL,
    dict_value VARCHAR(128) NOT NULL,
    dict_sort INTEGER DEFAULT 0,
    is_default SMALLINT DEFAULT 0,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_dict_data IS '字典数据表';
COMMENT ON COLUMN sys_dict_data.id IS '字典数据ID';
COMMENT ON COLUMN sys_dict_data.dict_type_id IS '字典类型ID';
COMMENT ON COLUMN sys_dict_data.dict_label IS '字典标签';
COMMENT ON COLUMN sys_dict_data.dict_value IS '字典值';
COMMENT ON COLUMN sys_dict_data.dict_sort IS '排序';
COMMENT ON COLUMN sys_dict_data.is_default IS '是否默认 0否 1是';
COMMENT ON COLUMN sys_dict_data.status IS '状态 0禁用 1启用';

CREATE INDEX idx_sys_dict_data_type_id ON sys_dict_data(dict_type_id);

-- ----------------------------
-- 操作日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_operation_logs (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    username VARCHAR(64),
    operation VARCHAR(256),
    method VARCHAR(256),
    request_url VARCHAR(512),
    request_method VARCHAR(32),
    request_params TEXT,
    response_result TEXT,
    ip VARCHAR(64),
    user_agent VARCHAR(512),
    operation_time TIMESTAMP,
    duration_ms BIGINT,
    status SMALLINT DEFAULT 1,
    error_msg TEXT,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_operation_logs IS '操作日志表';
COMMENT ON COLUMN sys_operation_logs.id IS '日志ID';
COMMENT ON COLUMN sys_operation_logs.user_id IS '用户ID';
COMMENT ON COLUMN sys_operation_logs.operation IS '操作描述';
COMMENT ON COLUMN sys_operation_logs.method IS '请求方法';
COMMENT ON COLUMN sys_operation_logs.request_url IS '请求URL';
COMMENT ON COLUMN sys_operation_logs.request_method IS 'HTTP请求方法';
COMMENT ON COLUMN sys_operation_logs.request_params IS '请求参数';
COMMENT ON COLUMN sys_operation_logs.response_result IS '返回结果';
COMMENT ON COLUMN sys_operation_logs.ip IS '操作IP';
COMMENT ON COLUMN sys_operation_logs.user_agent IS '用户代理';
COMMENT ON COLUMN sys_operation_logs.operation_time IS '操作时间';
COMMENT ON COLUMN sys_operation_logs.duration_ms IS '耗时毫秒';
COMMENT ON COLUMN sys_operation_logs.status IS '状态 0失败 1成功';
COMMENT ON COLUMN sys_operation_logs.error_msg IS '错误信息';

CREATE INDEX idx_sys_operation_logs_user_id ON sys_operation_logs(user_id);
CREATE INDEX idx_sys_operation_logs_operation_time ON sys_operation_logs(operation_time);

-- ----------------------------
-- 登录日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_login_logs (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    username VARCHAR(64),
    login_type SMALLINT,
    ip VARCHAR(64),
    login_location VARCHAR(256),
    browser VARCHAR(128),
    os VARCHAR(128),
    status SMALLINT DEFAULT 1,
    msg VARCHAR(512),
    login_time TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_login_logs IS '登录日志表';
COMMENT ON COLUMN sys_login_logs.id IS '日志ID';
COMMENT ON COLUMN sys_login_logs.user_id IS '用户ID';
COMMENT ON COLUMN sys_login_logs.login_type IS '登录类型 1账号密码 2手机验证码 3微信 4其他';
COMMENT ON COLUMN sys_login_logs.ip IS '登录IP';
COMMENT ON COLUMN sys_login_logs.login_location IS '登录地点';
COMMENT ON COLUMN sys_login_logs.browser IS '浏览器';
COMMENT ON COLUMN sys_login_logs.os IS '操作系统';
COMMENT ON COLUMN sys_login_logs.status IS '状态 0失败 1成功';
COMMENT ON COLUMN sys_login_logs.msg IS '提示消息';
COMMENT ON COLUMN sys_login_logs.login_time IS '登录时间';

CREATE INDEX idx_sys_login_logs_user_id ON sys_login_logs(user_id);
CREATE INDEX idx_sys_login_logs_login_time ON sys_login_logs(login_time);

-- ----------------------------
-- 文件上传表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_file_upload (
    id BIGINT PRIMARY KEY,
    file_name VARCHAR(256) NOT NULL,
    file_path VARCHAR(512) NOT NULL,
    file_url VARCHAR(512),
    file_size BIGINT,
    file_type VARCHAR(128),
    file_md5 VARCHAR(64),
    business_type VARCHAR(64),
    business_id BIGINT,
    uploaded_by BIGINT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_file_upload IS '文件上传表';
COMMENT ON COLUMN sys_file_upload.id IS '文件ID';
COMMENT ON COLUMN sys_file_upload.file_name IS '文件名';
COMMENT ON COLUMN sys_file_upload.file_path IS '文件路径';
COMMENT ON COLUMN sys_file_upload.file_url IS '文件URL';
COMMENT ON COLUMN sys_file_upload.file_size IS '文件大小(字节)';
COMMENT ON COLUMN sys_file_upload.file_type IS '文件类型';
COMMENT ON COLUMN sys_file_upload.file_md5 IS '文件MD5';
COMMENT ON COLUMN sys_file_upload.business_type IS '业务类型';
COMMENT ON COLUMN sys_file_upload.business_id IS '业务ID';
COMMENT ON COLUMN sys_file_upload.uploaded_by IS '上传人ID';

CREATE INDEX idx_sys_file_upload_business ON sys_file_upload(business_type, business_id);
CREATE INDEX idx_sys_file_upload_file_md5 ON sys_file_upload(file_md5);

-- ----------------------------
-- 通知消息表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_notifications (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    content TEXT,
    notification_type SMALLINT,
    business_type VARCHAR(64),
    business_id BIGINT,
    is_read SMALLINT DEFAULT 0,
    read_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_notifications IS '通知消息表';
COMMENT ON COLUMN sys_notifications.id IS '通知ID';
COMMENT ON COLUMN sys_notifications.user_id IS '接收用户ID';
COMMENT ON COLUMN sys_notifications.title IS '标题';
COMMENT ON COLUMN sys_notifications.content IS '内容';
COMMENT ON COLUMN sys_notifications.notification_type IS '通知类型';
COMMENT ON COLUMN sys_notifications.business_type IS '业务类型';
COMMENT ON COLUMN sys_notifications.business_id IS '业务ID';
COMMENT ON COLUMN sys_notifications.is_read IS '是否已读 0未读 1已读';
COMMENT ON COLUMN sys_notifications.read_at IS '阅读时间';

CREATE INDEX idx_sys_notifications_user_id ON sys_notifications(user_id);
CREATE INDEX idx_sys_notifications_is_read ON sys_notifications(is_read);

-- ----------------------------
-- 定时任务表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_scheduled_tasks (
    id BIGINT PRIMARY KEY,
    task_name VARCHAR(128) NOT NULL,
    task_group VARCHAR(64),
    cron_expression VARCHAR(128),
    invoke_target VARCHAR(512),
    description TEXT,
    status SMALLINT DEFAULT 1,
    last_run_at TIMESTAMP,
    next_run_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE sys_scheduled_tasks IS '定时任务表';
COMMENT ON COLUMN sys_scheduled_tasks.id IS '任务ID';
COMMENT ON COLUMN sys_scheduled_tasks.task_name IS '任务名称';
COMMENT ON COLUMN sys_scheduled_tasks.task_group IS '任务组名';
COMMENT ON COLUMN sys_scheduled_tasks.cron_expression IS 'cron表达式';
COMMENT ON COLUMN sys_scheduled_tasks.invoke_target IS '调用目标';
COMMENT ON COLUMN sys_scheduled_tasks.description IS '描述';
COMMENT ON COLUMN sys_scheduled_tasks.status IS '状态 0暂停 1运行';
COMMENT ON COLUMN sys_scheduled_tasks.last_run_at IS '上次运行时间';
COMMENT ON COLUMN sys_scheduled_tasks.next_run_at IS '下次运行时间';
