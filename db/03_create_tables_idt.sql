-- ============================================================
-- 03_create_tables_idt.sql
-- 智能辅助鉴定模块表
-- ============================================================

-- ----------------------------
-- 出版物信息表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_publications (
    id BIGINT PRIMARY KEY,
    isbn VARCHAR(32),
    title VARCHAR(512) NOT NULL,
    author VARCHAR(256),
    publisher VARCHAR(256),
    publish_date DATE,
    edition VARCHAR(64),
    print_order VARCHAR(64),
    cip_number VARCHAR(64),
    category VARCHAR(128),
    language VARCHAR(32),
    page_count INTEGER,
    price DECIMAL(10,2),
    binding VARCHAR(32),
    size VARCHAR(64),
    description TEXT,
    cover_url VARCHAR(512),
    source SMALLINT DEFAULT 1,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_publications IS '出版物信息表';
COMMENT ON COLUMN idt_publications.id IS '出版物ID';
COMMENT ON COLUMN idt_publications.isbn IS 'ISBN编号';
COMMENT ON COLUMN idt_publications.title IS '书名';
COMMENT ON COLUMN idt_publications.author IS '作者';
COMMENT ON COLUMN idt_publications.publisher IS '出版社';
COMMENT ON COLUMN idt_publications.publish_date IS '出版日期';
COMMENT ON COLUMN idt_publications.edition IS '版次';
COMMENT ON COLUMN idt_publications.print_order IS '印次';
COMMENT ON COLUMN idt_publications.cip_number IS 'CIP编号';
COMMENT ON COLUMN idt_publications.category IS '分类';
COMMENT ON COLUMN idt_publications.language IS '语言';
COMMENT ON COLUMN idt_publications.page_count IS '页数';
COMMENT ON COLUMN idt_publications.price IS '定价';
COMMENT ON COLUMN idt_publications.binding IS '装帧';
COMMENT ON COLUMN idt_publications.size IS '开本';
COMMENT ON COLUMN idt_publications.description IS '描述';
COMMENT ON COLUMN idt_publications.cover_url IS '封面URL';
COMMENT ON COLUMN idt_publications.source IS '来源 1手动录入 2ISBN检索 3爬虫采集';
COMMENT ON COLUMN idt_publications.status IS '状态 0禁用 1启用';

CREATE INDEX idx_idt_publications_isbn ON idt_publications(isbn);
CREATE INDEX idx_idt_publications_title ON idt_publications(title);
CREATE INDEX idx_idt_publications_status ON idt_publications(status);

-- ----------------------------
-- 鉴定任务表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_identification_tasks (
    id BIGINT PRIMARY KEY,
    task_code VARCHAR(64) NOT NULL UNIQUE,
    task_name VARCHAR(256),
    task_type SMALLINT,
    publication_id BIGINT,
    isbn VARCHAR(32),
    title VARCHAR(512),
    publisher VARCHAR(256),
    source VARCHAR(128),
    priority SMALLINT DEFAULT 2,
    status SMALLINT DEFAULT 0,
    assignee_id BIGINT,
    reviewer_id BIGINT,
    assigned_at TIMESTAMP,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    reviewed_at TIMESTAMP,
    archived_at TIMESTAMP,
    deadline TIMESTAMP,
    description TEXT,
    remark TEXT,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_identification_tasks IS '鉴定任务表';
COMMENT ON COLUMN idt_identification_tasks.id IS '任务ID';
COMMENT ON COLUMN idt_identification_tasks.task_code IS '任务编号';
COMMENT ON COLUMN idt_identification_tasks.task_name IS '任务名称';
COMMENT ON COLUMN idt_identification_tasks.task_type IS '任务类型';
COMMENT ON COLUMN idt_identification_tasks.publication_id IS '关联出版物ID';
COMMENT ON COLUMN idt_identification_tasks.isbn IS 'ISBN';
COMMENT ON COLUMN idt_identification_tasks.title IS '书名';
COMMENT ON COLUMN idt_identification_tasks.publisher IS '出版社';
COMMENT ON COLUMN idt_identification_tasks.source IS '来源';
COMMENT ON COLUMN idt_identification_tasks.priority IS '优先级 1低 2中 3高 4紧急';
COMMENT ON COLUMN idt_identification_tasks.status IS '状态 0待分配 1待鉴定 2鉴定中 3待审核 4已完成 5已归档 6已驳回';
COMMENT ON COLUMN idt_identification_tasks.assignee_id IS '鉴定员ID';
COMMENT ON COLUMN idt_identification_tasks.reviewer_id IS '审核员ID';
COMMENT ON COLUMN idt_identification_tasks.assigned_at IS '分配时间';
COMMENT ON COLUMN idt_identification_tasks.started_at IS '开始时间';
COMMENT ON COLUMN idt_identification_tasks.completed_at IS '完成时间';
COMMENT ON COLUMN idt_identification_tasks.reviewed_at IS '审核时间';
COMMENT ON COLUMN idt_identification_tasks.archived_at IS '归档时间';
COMMENT ON COLUMN idt_identification_tasks.deadline IS '截止时间';

CREATE INDEX idx_idt_tasks_status ON idt_identification_tasks(status);
CREATE INDEX idx_idt_tasks_assignee_id ON idt_identification_tasks(assignee_id);
CREATE INDEX idx_idt_tasks_isbn ON idt_identification_tasks(isbn);

-- ----------------------------
-- 鉴定材料表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_identification_materials (
    id BIGINT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    material_type SMALLINT,
    file_name VARCHAR(256),
    file_url VARCHAR(512),
    file_size BIGINT,
    file_type VARCHAR(64),
    ocr_result TEXT,
    ocr_status SMALLINT DEFAULT 0,
    ocr_completed_at TIMESTAMP,
    sort INTEGER DEFAULT 0,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_identification_materials IS '鉴定材料表';
COMMENT ON COLUMN idt_identification_materials.id IS '材料ID';
COMMENT ON COLUMN idt_identification_materials.task_id IS '任务ID';
COMMENT ON COLUMN idt_identification_materials.material_type IS '材料类型 1封面 2版权页 3目录 4内页 5其他';
COMMENT ON COLUMN idt_identification_materials.file_name IS '文件名';
COMMENT ON COLUMN idt_identification_materials.file_url IS '文件URL';
COMMENT ON COLUMN idt_identification_materials.file_size IS '文件大小';
COMMENT ON COLUMN idt_identification_materials.file_type IS '文件类型';
COMMENT ON COLUMN idt_identification_materials.ocr_result IS 'OCR识别结果';
COMMENT ON COLUMN idt_identification_materials.ocr_status IS 'OCR状态 0未识别 1识别中 2已完成 3失败';
COMMENT ON COLUMN idt_identification_materials.ocr_completed_at IS 'OCR完成时间';
COMMENT ON COLUMN idt_identification_materials.sort IS '排序';

CREATE INDEX idx_idt_materials_task_id ON idt_identification_materials(task_id);

-- ----------------------------
-- 鉴定结果表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_identification_results (
    id BIGINT PRIMARY KEY,
    task_id BIGINT NOT NULL UNIQUE,
    conclusion SMALLINT,
    confidence DECIMAL(5,4),
    similarity_score DECIMAL(5,4),
    version_verified SMALLINT DEFAULT 0,
    content_compared SMALLINT DEFAULT 0,
    evidences TEXT,
    differences TEXT,
    report_url VARCHAR(512),
    report_generated_at TIMESTAMP,
    remark TEXT,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_identification_results IS '鉴定结果表';
COMMENT ON COLUMN idt_identification_results.id IS '结果ID';
COMMENT ON COLUMN idt_identification_results.task_id IS '任务ID';
COMMENT ON COLUMN idt_identification_results.conclusion IS '鉴定结论 1正版 2盗版 3疑似盗版 4无法判定';
COMMENT ON COLUMN idt_identification_results.confidence IS '置信度';
COMMENT ON COLUMN idt_identification_results.similarity_score IS '相似度评分';
COMMENT ON COLUMN idt_identification_results.version_verified IS '版本是否已验证';
COMMENT ON COLUMN idt_identification_results.content_compared IS '内容是否已比对';
COMMENT ON COLUMN idt_identification_results.evidences IS '证据列表JSON';
COMMENT ON COLUMN idt_identification_results.differences IS '差异点列表JSON';
COMMENT ON COLUMN idt_identification_results.report_url IS '鉴定报告URL';
COMMENT ON COLUMN idt_identification_results.report_generated_at IS '报告生成时间';

CREATE INDEX idx_idt_results_task_id ON idt_identification_results(task_id);

-- ----------------------------
-- 审核日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_review_logs (
    id BIGINT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    reviewer_id BIGINT,
    review_type SMALLINT,
    review_result SMALLINT,
    review_opinion TEXT,
    issues TEXT,
    reviewed_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_review_logs IS '审核日志表';
COMMENT ON COLUMN idt_review_logs.id IS '日志ID';
COMMENT ON COLUMN idt_review_logs.task_id IS '任务ID';
COMMENT ON COLUMN idt_review_logs.reviewer_id IS '审核员ID';
COMMENT ON COLUMN idt_review_logs.review_type IS '审核类型 1初审 2复审 3终审';
COMMENT ON COLUMN idt_review_logs.review_result IS '审核结果 1通过 2驳回 3需修改';
COMMENT ON COLUMN idt_review_logs.review_opinion IS '审核意见';
COMMENT ON COLUMN idt_review_logs.issues IS '问题列表JSON';
COMMENT ON COLUMN idt_review_logs.reviewed_at IS '审核时间';

CREATE INDEX idx_idt_review_logs_task_id ON idt_review_logs(task_id);

-- ----------------------------
-- 违法样本库表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_illegal_samples (
    id BIGINT PRIMARY KEY,
    sample_code VARCHAR(64) NOT NULL UNIQUE,
    sample_name VARCHAR(256),
    category VARCHAR(128),
    images TEXT,
    print_feature TEXT,
    paper_feature TEXT,
    binding_feature TEXT,
    watermark_feature TEXT,
    feature_vector TEXT,
    feature_vector_id VARCHAR(128),
    source VARCHAR(128),
    collected_at TIMESTAMP,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_illegal_samples IS '违法样本库表';
COMMENT ON COLUMN idt_illegal_samples.id IS '样本ID';
COMMENT ON COLUMN idt_illegal_samples.sample_code IS '样本编号';
COMMENT ON COLUMN idt_illegal_samples.sample_name IS '样本名称';
COMMENT ON COLUMN idt_illegal_samples.category IS '分类';
COMMENT ON COLUMN idt_illegal_samples.images IS '图片URL列表JSON';
COMMENT ON COLUMN idt_illegal_samples.print_feature IS '印刷特征';
COMMENT ON COLUMN idt_illegal_samples.paper_feature IS '纸张特征';
COMMENT ON COLUMN idt_illegal_samples.binding_feature IS '装订特征';
COMMENT ON COLUMN idt_illegal_samples.watermark_feature IS '水印特征';
COMMENT ON COLUMN idt_illegal_samples.feature_vector IS '特征向量文本';
COMMENT ON COLUMN idt_illegal_samples.feature_vector_id IS '特征向量ID(向量库)';
COMMENT ON COLUMN idt_illegal_samples.source IS '来源';
COMMENT ON COLUMN idt_illegal_samples.collected_at IS '采集时间';
COMMENT ON COLUMN idt_illegal_samples.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 溯源分析结果表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_traceability_results (
    id BIGINT PRIMARY KEY,
    sample_id BIGINT NOT NULL,
    suspected_source VARCHAR(256),
    confidence DECIMAL(5,4),
    circulation_path TEXT,
    related_cases TEXT,
    analysis_report TEXT,
    analyzed_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_traceability_results IS '溯源分析结果表';
COMMENT ON COLUMN idt_traceability_results.id IS '结果ID';
COMMENT ON COLUMN idt_traceability_results.sample_id IS '样本ID';
COMMENT ON COLUMN idt_traceability_results.suspected_source IS '疑似来源';
COMMENT ON COLUMN idt_traceability_results.confidence IS '置信度';
COMMENT ON COLUMN idt_traceability_results.circulation_path IS '流通路径';
COMMENT ON COLUMN idt_traceability_results.related_cases IS '关联案件';
COMMENT ON COLUMN idt_traceability_results.analysis_report IS '分析报告';
COMMENT ON COLUMN idt_traceability_results.analyzed_at IS '分析时间';

CREATE INDEX idx_idt_traceability_sample_id ON idt_traceability_results(sample_id);

-- ----------------------------
-- 工单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_work_orders (
    id BIGINT PRIMARY KEY,
    order_code VARCHAR(64),
    task_id BIGINT,
    process_instance_id VARCHAR(128),
    current_node VARCHAR(128),
    current_assignee BIGINT,
    status SMALLINT DEFAULT 1,
    priority SMALLINT,
    deadline TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_work_orders IS '工单表';
COMMENT ON COLUMN idt_work_orders.id IS '工单ID';
COMMENT ON COLUMN idt_work_orders.order_code IS '工单编号';
COMMENT ON COLUMN idt_work_orders.task_id IS '任务ID';
COMMENT ON COLUMN idt_work_orders.process_instance_id IS '流程实例ID';
COMMENT ON COLUMN idt_work_orders.current_node IS '当前节点';
COMMENT ON COLUMN idt_work_orders.current_assignee IS '当前处理人';
COMMENT ON COLUMN idt_work_orders.status IS '状态 1进行中 2已完成 3已关闭';
COMMENT ON COLUMN idt_work_orders.priority IS '优先级';
COMMENT ON COLUMN idt_work_orders.deadline IS '截止时间';

CREATE INDEX idx_idt_work_orders_task_id ON idt_work_orders(task_id);
CREATE INDEX idx_idt_work_orders_status ON idt_work_orders(status);

-- ----------------------------
-- 工单评论表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_work_order_comments (
    id BIGINT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT,
    content TEXT,
    mentions VARCHAR(512),
    attachments TEXT,
    parent_id BIGINT,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_work_order_comments IS '工单评论表';
COMMENT ON COLUMN idt_work_order_comments.id IS '评论ID';
COMMENT ON COLUMN idt_work_order_comments.order_id IS '工单ID';
COMMENT ON COLUMN idt_work_order_comments.user_id IS '用户ID';
COMMENT ON COLUMN idt_work_order_comments.content IS '评论内容';
COMMENT ON COLUMN idt_work_order_comments.mentions IS '@提及用户';
COMMENT ON COLUMN idt_work_order_comments.attachments IS '附件列表JSON';
COMMENT ON COLUMN idt_work_order_comments.parent_id IS '父评论ID';

CREATE INDEX idx_idt_comments_order_id ON idt_work_order_comments(order_id);

-- ----------------------------
-- 数据集表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_datasets (
    id BIGINT PRIMARY KEY,
    dataset_name VARCHAR(256) NOT NULL,
    dataset_type SMALLINT,
    version VARCHAR(32),
    description TEXT,
    sample_count INTEGER DEFAULT 0,
    quality_score DECIMAL(5,4),
    status SMALLINT DEFAULT 0,
    storage_path VARCHAR(512),
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_datasets IS '数据集表';
COMMENT ON COLUMN idt_datasets.id IS '数据集ID';
COMMENT ON COLUMN idt_datasets.dataset_name IS '数据集名称';
COMMENT ON COLUMN idt_datasets.dataset_type IS '数据集类型 1训练集 2验证集 3测试集';
COMMENT ON COLUMN idt_datasets.version IS '版本号';
COMMENT ON COLUMN idt_datasets.description IS '描述';
COMMENT ON COLUMN idt_datasets.sample_count IS '样本数量';
COMMENT ON COLUMN idt_datasets.quality_score IS '质量评分';
COMMENT ON COLUMN idt_datasets.status IS '状态 0草稿 1已发布 2已归档';
COMMENT ON COLUMN idt_datasets.storage_path IS '存储路径';

-- ----------------------------
-- 数据集明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_dataset_items (
    id BIGINT PRIMARY KEY,
    dataset_id BIGINT NOT NULL,
    sample_data TEXT,
    label VARCHAR(128),
    annotation TEXT,
    annotator_id BIGINT,
    annotated_at TIMESTAMP,
    review_status SMALLINT DEFAULT 0,
    reviewer_id BIGINT,
    reviewed_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_dataset_items IS '数据集明细表';
COMMENT ON COLUMN idt_dataset_items.id IS '明细ID';
COMMENT ON COLUMN idt_dataset_items.dataset_id IS '数据集ID';
COMMENT ON COLUMN idt_dataset_items.sample_data IS '样本数据';
COMMENT ON COLUMN idt_dataset_items.label IS '标签';
COMMENT ON COLUMN idt_dataset_items.annotation IS '标注信息';
COMMENT ON COLUMN idt_dataset_items.annotator_id IS '标注员ID';
COMMENT ON COLUMN idt_dataset_items.annotated_at IS '标注时间';
COMMENT ON COLUMN idt_dataset_items.review_status IS '审核状态 0待审核 1通过 2驳回';
COMMENT ON COLUMN idt_dataset_items.reviewer_id IS '审核员ID';

CREATE INDEX idx_idt_dataset_items_dataset_id ON idt_dataset_items(dataset_id);

-- ----------------------------
-- 执法对接表
-- ----------------------------
CREATE TABLE IF NOT EXISTS idt_enforcement_docking (
    id BIGINT PRIMARY KEY,
    docking_type SMALLINT,
    task_id BIGINT,
    case_code VARCHAR(64),
    request_data TEXT,
    response_data TEXT,
    status SMALLINT DEFAULT 0,
    error_message TEXT,
    sent_at TIMESTAMP,
    received_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE idt_enforcement_docking IS '执法对接表';
COMMENT ON COLUMN idt_enforcement_docking.id IS '对接ID';
COMMENT ON COLUMN idt_enforcement_docking.docking_type IS '对接类型 1案件移送 2协查函 3鉴定委托';
COMMENT ON COLUMN idt_enforcement_docking.task_id IS '任务ID';
COMMENT ON COLUMN idt_enforcement_docking.case_code IS '案件编号';
COMMENT ON COLUMN idt_enforcement_docking.request_data IS '请求数据';
COMMENT ON COLUMN idt_enforcement_docking.response_data IS '响应数据';
COMMENT ON COLUMN idt_enforcement_docking.status IS '状态 0待发送 1已发送 2已接收 3失败';
COMMENT ON COLUMN idt_enforcement_docking.error_message IS '错误信息';
COMMENT ON COLUMN idt_enforcement_docking.sent_at IS '发送时间';
COMMENT ON COLUMN idt_enforcement_docking.received_at IS '接收时间';

CREATE INDEX idx_idt_enforcement_task_id ON idt_enforcement_docking(task_id);
