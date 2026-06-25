-- ============================================================
-- 04_create_tables_circ.sql
-- 出版物流转监管模块表
-- ============================================================

-- ----------------------------
-- 商户表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_merchants (
    id BIGINT PRIMARY KEY,
    merchant_code VARCHAR(64) NOT NULL UNIQUE,
    merchant_name VARCHAR(256) NOT NULL,
    merchant_type SMALLINT,
    legal_person VARCHAR(64),
    contact_person VARCHAR(64),
    contact_phone VARCHAR(32),
    contact_email VARCHAR(128),
    address VARCHAR(512),
    business_scope TEXT,
    region_code VARCHAR(32),
    risk_score DECIMAL(5,4),
    risk_level SMALLINT,
    list_type SMALLINT DEFAULT 0,
    list_effective_at TIMESTAMP,
    list_expire_at TIMESTAMP,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_merchants IS '商户表';
COMMENT ON COLUMN circ_merchants.id IS '商户ID';
COMMENT ON COLUMN circ_merchants.merchant_code IS '商户编码';
COMMENT ON COLUMN circ_merchants.merchant_name IS '商户名称';
COMMENT ON COLUMN circ_merchants.merchant_type IS '商户类型 1出版社 2批发商 3零售商 4电商平台 5物流企业 6其他';
COMMENT ON COLUMN circ_merchants.legal_person IS '法人代表';
COMMENT ON COLUMN circ_merchants.contact_person IS '联系人';
COMMENT ON COLUMN circ_merchants.contact_phone IS '联系电话';
COMMENT ON COLUMN circ_merchants.contact_email IS '联系邮箱';
COMMENT ON COLUMN circ_merchants.address IS '地址';
COMMENT ON COLUMN circ_merchants.business_scope IS '经营范围';
COMMENT ON COLUMN circ_merchants.region_code IS '区域编码';
COMMENT ON COLUMN circ_merchants.risk_score IS '风险评分';
COMMENT ON COLUMN circ_merchants.risk_level IS '风险等级 0无 1低 2中 3高';
COMMENT ON COLUMN circ_merchants.list_type IS '名单类型 0不在名单 1黑名单 2白名单';
COMMENT ON COLUMN circ_merchants.list_effective_at IS '名单生效时间';
COMMENT ON COLUMN circ_merchants.list_expire_at IS '名单过期时间';
COMMENT ON COLUMN circ_merchants.status IS '状态 0禁用 1启用';

CREATE INDEX idx_circ_merchants_region_code ON circ_merchants(region_code);
CREATE INDEX idx_circ_merchants_risk_level ON circ_merchants(risk_level);
CREATE INDEX idx_circ_merchants_list_type ON circ_merchants(list_type);

-- ----------------------------
-- 商户资质表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_qualifications (
    id BIGINT PRIMARY KEY,
    merchant_id BIGINT NOT NULL,
    qualification_type SMALLINT,
    qualification_no VARCHAR(128),
    qualification_name VARCHAR(256),
    issue_authority VARCHAR(256),
    issue_date DATE,
    effective_date DATE,
    expire_date DATE,
    business_scope TEXT,
    scan_url VARCHAR(512),
    verify_status SMALLINT DEFAULT 0,
    verify_result VARCHAR(512),
    verified_at TIMESTAMP,
    verified_by BIGINT,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_qualifications IS '商户资质表';
COMMENT ON COLUMN circ_qualifications.id IS '资质ID';
COMMENT ON COLUMN circ_qualifications.merchant_id IS '商户ID';
COMMENT ON COLUMN circ_qualifications.qualification_type IS '资质类型 1营业执照 2出版物经营许可证 3其他';
COMMENT ON COLUMN circ_qualifications.qualification_no IS '资质编号';
COMMENT ON COLUMN circ_qualifications.qualification_name IS '资质名称';
COMMENT ON COLUMN circ_qualifications.issue_authority IS '发证机关';
COMMENT ON COLUMN circ_qualifications.issue_date IS '发证日期';
COMMENT ON COLUMN circ_qualifications.effective_date IS '生效日期';
COMMENT ON COLUMN circ_qualifications.expire_date IS '过期日期';
COMMENT ON COLUMN circ_qualifications.business_scope IS '经营范围';
COMMENT ON COLUMN circ_qualifications.scan_url IS '扫描件URL';
COMMENT ON COLUMN circ_qualifications.verify_status IS '核验状态 0未核验 1核验中 2通过 3不通过';
COMMENT ON COLUMN circ_qualifications.verify_result IS '核验结果';
COMMENT ON COLUMN circ_qualifications.verified_at IS '核验时间';
COMMENT ON COLUMN circ_qualifications.verified_by IS '核验人';

CREATE INDEX idx_circ_qualifications_merchant_id ON circ_qualifications(merchant_id);

-- ----------------------------
-- 包裹表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_packages (
    id BIGINT PRIMARY KEY,
    tracking_no VARCHAR(64) NOT NULL,
    express_company VARCHAR(128),
    merchant_id BIGINT,
    sender_name VARCHAR(64),
    sender_phone VARCHAR(32),
    sender_address VARCHAR(512),
    receiver_name VARCHAR(64),
    receiver_phone VARCHAR(32),
    receiver_address VARCHAR(512),
    package_items TEXT,
    package_weight DECIMAL(10,2),
    package_value DECIMAL(10,2),
    risk_score DECIMAL(5,4),
    risk_level SMALLINT,
    status SMALLINT DEFAULT 0,
    current_location VARCHAR(256),
    shipped_at TIMESTAMP,
    received_at TIMESTAMP,
    intercepted_at TIMESTAMP,
    intercept_reason VARCHAR(512),
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_packages IS '包裹表';
COMMENT ON COLUMN circ_packages.id IS '包裹ID';
COMMENT ON COLUMN circ_packages.tracking_no IS '快递单号';
COMMENT ON COLUMN circ_packages.express_company IS '快递公司';
COMMENT ON COLUMN circ_packages.merchant_id IS '商户ID';
COMMENT ON COLUMN circ_packages.sender_name IS '寄件人姓名';
COMMENT ON COLUMN circ_packages.sender_phone IS '寄件人电话';
COMMENT ON COLUMN circ_packages.sender_address IS '寄件地址';
COMMENT ON COLUMN circ_packages.receiver_name IS '收件人姓名';
COMMENT ON COLUMN circ_packages.receiver_phone IS '收件人电话';
COMMENT ON COLUMN circ_packages.receiver_address IS '收件地址';
COMMENT ON COLUMN circ_packages.package_items IS '包裹物品JSON';
COMMENT ON COLUMN circ_packages.package_weight IS '包裹重量(kg)';
COMMENT ON COLUMN circ_packages.package_value IS '包裹价值';
COMMENT ON COLUMN circ_packages.risk_score IS '风险评分';
COMMENT ON COLUMN circ_packages.risk_level IS '风险等级 0无 1低 2中 3高';
COMMENT ON COLUMN circ_packages.status IS '状态 0待揽收 1运输中 2已签收 3已拦截 4已退回';
COMMENT ON COLUMN circ_packages.current_location IS '当前位置';
COMMENT ON COLUMN circ_packages.shipped_at IS '发货时间';
COMMENT ON COLUMN circ_packages.received_at IS '签收时间';
COMMENT ON COLUMN circ_packages.intercepted_at IS '拦截时间';
COMMENT ON COLUMN circ_packages.intercept_reason IS '拦截原因';

CREATE INDEX idx_circ_packages_tracking_no ON circ_packages(tracking_no);
CREATE INDEX idx_circ_packages_merchant_id ON circ_packages(merchant_id);
CREATE INDEX idx_circ_packages_status ON circ_packages(status);
CREATE INDEX idx_circ_packages_risk_level ON circ_packages(risk_level);

-- ----------------------------
-- 物流跟踪日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_tracking_logs (
    id BIGINT PRIMARY KEY,
    package_id BIGINT NOT NULL,
    tracking_no VARCHAR(64),
    event_time TIMESTAMP,
    event_location VARCHAR(256),
    event_description VARCHAR(512),
    event_type SMALLINT,
    operator VARCHAR(128),
    node_verified SMALLINT DEFAULT 0,
    verify_result VARCHAR(256),
    raw_data TEXT,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_tracking_logs IS '物流跟踪日志表';
COMMENT ON COLUMN circ_tracking_logs.id IS '日志ID';
COMMENT ON COLUMN circ_tracking_logs.package_id IS '包裹ID';
COMMENT ON COLUMN circ_tracking_logs.tracking_no IS '快递单号';
COMMENT ON COLUMN circ_tracking_logs.event_time IS '事件时间';
COMMENT ON COLUMN circ_tracking_logs.event_location IS '事件地点';
COMMENT ON COLUMN circ_tracking_logs.event_description IS '事件描述';
COMMENT ON COLUMN circ_tracking_logs.event_type IS '事件类型 1揽收 2中转 3派送 4签收 5退回 6异常';
COMMENT ON COLUMN circ_tracking_logs.operator IS '操作人';
COMMENT ON COLUMN circ_tracking_logs.node_verified IS '节点是否已核验';
COMMENT ON COLUMN circ_tracking_logs.verify_result IS '核验结果';
COMMENT ON COLUMN circ_tracking_logs.raw_data IS '原始数据';

CREATE INDEX idx_circ_tracking_logs_package_id ON circ_tracking_logs(package_id);

-- ----------------------------
-- 预警表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_alerts (
    id BIGINT PRIMARY KEY,
    alert_code VARCHAR(64) NOT NULL UNIQUE,
    alert_level SMALLINT,
    alert_type VARCHAR(64),
    alert_source VARCHAR(128),
    title VARCHAR(256) NOT NULL,
    description TEXT,
    target_type SMALLINT,
    target_id BIGINT,
    trigger_data TEXT,
    status SMALLINT DEFAULT 1,
    handler_id BIGINT,
    handle_opinion TEXT,
    handle_result TEXT,
    triggered_at TIMESTAMP,
    verified_at TIMESTAMP,
    handled_at TIMESTAMP,
    archived_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_alerts IS '预警表';
COMMENT ON COLUMN circ_alerts.id IS '预警ID';
COMMENT ON COLUMN circ_alerts.alert_code IS '预警编号';
COMMENT ON COLUMN circ_alerts.alert_level IS '预警级别 1蓝 2黄 3橙 4红';
COMMENT ON COLUMN circ_alerts.alert_type IS '预警类型';
COMMENT ON COLUMN circ_alerts.alert_source IS '预警来源';
COMMENT ON COLUMN circ_alerts.title IS '预警标题';
COMMENT ON COLUMN circ_alerts.description IS '预警描述';
COMMENT ON COLUMN circ_alerts.target_type IS '关联目标类型 1商户 2包裹 3资质';
COMMENT ON COLUMN circ_alerts.target_id IS '关联目标ID';
COMMENT ON COLUMN circ_alerts.trigger_data IS '触发数据';
COMMENT ON COLUMN circ_alerts.status IS '状态 1待处理 2处理中 3已处理 4已归档';
COMMENT ON COLUMN circ_alerts.handler_id IS '处理人ID';
COMMENT ON COLUMN circ_alerts.handle_opinion IS '处理意见';
COMMENT ON COLUMN circ_alerts.handle_result IS '处理结果';
COMMENT ON COLUMN circ_alerts.triggered_at IS '触发时间';
COMMENT ON COLUMN circ_alerts.verified_at IS '核实时间';
COMMENT ON COLUMN circ_alerts.handled_at IS '处理时间';
COMMENT ON COLUMN circ_alerts.archived_at IS '归档时间';

CREATE INDEX idx_circ_alerts_status ON circ_alerts(status);
CREATE INDEX idx_circ_alerts_alert_level ON circ_alerts(alert_level);
CREATE INDEX idx_circ_alerts_triggered_at ON circ_alerts(triggered_at);

-- ----------------------------
-- 预警处理记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_alert_handles (
    id BIGINT PRIMARY KEY,
    alert_id BIGINT NOT NULL,
    handler_id BIGINT,
    handle_type SMALLINT,
    handle_action VARCHAR(128),
    handle_opinion TEXT,
    handle_result TEXT,
    attachments TEXT,
    handled_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_alert_handles IS '预警处理记录表';
COMMENT ON COLUMN circ_alert_handles.id IS '记录ID';
COMMENT ON COLUMN circ_alert_handles.alert_id IS '预警ID';
COMMENT ON COLUMN circ_alert_handles.handler_id IS '处理人ID';
COMMENT ON COLUMN circ_alert_handles.handle_type IS '处理类型 1核实 2处置 3上报';
COMMENT ON COLUMN circ_alert_handles.handle_action IS '处理动作';
COMMENT ON COLUMN circ_alert_handles.handle_opinion IS '处理意见';
COMMENT ON COLUMN circ_alert_handles.handle_result IS '处理结果';
COMMENT ON COLUMN circ_alert_handles.attachments IS '附件列表JSON';
COMMENT ON COLUMN circ_alert_handles.handled_at IS '处理时间';

CREATE INDEX idx_circ_alert_handles_alert_id ON circ_alert_handles(alert_id);

-- ----------------------------
-- 黑白名单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_blacklist_whitelist (
    id BIGINT PRIMARY KEY,
    list_type SMALLINT,
    target_type SMALLINT,
    target_id BIGINT,
    target_name VARCHAR(256),
    target_code VARCHAR(64),
    reason TEXT,
    evidence TEXT,
    effective_date DATE,
    expire_date DATE,
    status SMALLINT DEFAULT 1,
    applicant_id BIGINT,
    approver_id BIGINT,
    approved_at TIMESTAMP,
    appeal_status SMALLINT DEFAULT 0,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_blacklist_whitelist IS '黑白名单表';
COMMENT ON COLUMN circ_blacklist_whitelist.id IS '名单ID';
COMMENT ON COLUMN circ_blacklist_whitelist.list_type IS '名单类型 1黑名单 2白名单';
COMMENT ON COLUMN circ_blacklist_whitelist.target_type IS '目标类型 1商户 2个人 3IP 4其他';
COMMENT ON COLUMN circ_blacklist_whitelist.target_id IS '目标ID';
COMMENT ON COLUMN circ_blacklist_whitelist.target_name IS '目标名称';
COMMENT ON COLUMN circ_blacklist_whitelist.target_code IS '目标编码';
COMMENT ON COLUMN circ_blacklist_whitelist.reason IS '原因';
COMMENT ON COLUMN circ_blacklist_whitelist.evidence IS '证据';
COMMENT ON COLUMN circ_blacklist_whitelist.effective_date IS '生效日期';
COMMENT ON COLUMN circ_blacklist_whitelist.expire_date IS '过期日期';
COMMENT ON COLUMN circ_blacklist_whitelist.status IS '状态 0禁用 1启用';
COMMENT ON COLUMN circ_blacklist_whitelist.applicant_id IS '申请人ID';
COMMENT ON COLUMN circ_blacklist_whitelist.approver_id IS '审批人ID';
COMMENT ON COLUMN circ_blacklist_whitelist.approved_at IS '审批时间';
COMMENT ON COLUMN circ_blacklist_whitelist.appeal_status IS '申诉状态 0无 1申诉中 2已受理 3已撤销';

CREATE INDEX idx_circ_bw_list_type ON circ_blacklist_whitelist(list_type);
CREATE INDEX idx_circ_bw_target ON circ_blacklist_whitelist(target_type, target_id);

-- ----------------------------
-- 名单变更审计表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_list_changes_audit (
    id BIGINT PRIMARY KEY,
    list_id BIGINT NOT NULL,
    operation_type SMALLINT,
    operation_before TEXT,
    operation_after TEXT,
    operator_id BIGINT,
    operation_reason TEXT,
    operated_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_list_changes_audit IS '名单变更审计表';
COMMENT ON COLUMN circ_list_changes_audit.id IS '审计ID';
COMMENT ON COLUMN circ_list_changes_audit.list_id IS '名单ID';
COMMENT ON COLUMN circ_list_changes_audit.operation_type IS '操作类型 1新增 2修改 3删除 4启用 5禁用';
COMMENT ON COLUMN circ_list_changes_audit.operation_before IS '变更前数据';
COMMENT ON COLUMN circ_list_changes_audit.operation_after IS '变更后数据';
COMMENT ON COLUMN circ_list_changes_audit.operator_id IS '操作人ID';
COMMENT ON COLUMN circ_list_changes_audit.operation_reason IS '操作原因';
COMMENT ON COLUMN circ_list_changes_audit.operated_at IS '操作时间';

CREATE INDEX idx_circ_audit_list_id ON circ_list_changes_audit(list_id);

-- ----------------------------
-- 每日统计表
-- ----------------------------
CREATE TABLE IF NOT EXISTS circ_statistics_daily (
    id BIGINT PRIMARY KEY,
    stat_date DATE NOT NULL,
    region_code VARCHAR(32),
    merchant_count INTEGER DEFAULT 0,
    package_count INTEGER DEFAULT 0,
    intercepted_count INTEGER DEFAULT 0,
    alert_count INTEGER DEFAULT 0,
    alert_red_count INTEGER DEFAULT 0,
    alert_orange_count INTEGER DEFAULT 0,
    alert_yellow_count INTEGER DEFAULT 0,
    alert_blue_count INTEGER DEFAULT 0,
    blacklist_count INTEGER DEFAULT 0,
    whitelist_count INTEGER DEFAULT 0,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE circ_statistics_daily IS '每日统计表';
COMMENT ON COLUMN circ_statistics_daily.id IS '统计ID';
COMMENT ON COLUMN circ_statistics_daily.stat_date IS '统计日期';
COMMENT ON COLUMN circ_statistics_daily.region_code IS '区域编码';
COMMENT ON COLUMN circ_statistics_daily.merchant_count IS '商户数量';
COMMENT ON COLUMN circ_statistics_daily.package_count IS '包裹数量';
COMMENT ON COLUMN circ_statistics_daily.intercepted_count IS '拦截数量';
COMMENT ON COLUMN circ_statistics_daily.alert_count IS '预警总数';
COMMENT ON COLUMN circ_statistics_daily.alert_red_count IS '红色预警数';
COMMENT ON COLUMN circ_statistics_daily.alert_orange_count IS '橙色预警数';
COMMENT ON COLUMN circ_statistics_daily.alert_yellow_count IS '黄色预警数';
COMMENT ON COLUMN circ_statistics_daily.alert_blue_count IS '蓝色预警数';
COMMENT ON COLUMN circ_statistics_daily.blacklist_count IS '黑名单数量';
COMMENT ON COLUMN circ_statistics_daily.whitelist_count IS '白名单数量';

CREATE UNIQUE INDEX uk_circ_statistics_daily ON circ_statistics_daily(stat_date, region_code);
