-- ============================================================
-- 06_create_tables_ai.sql
-- AI中台模块表
-- ============================================================

-- ----------------------------
-- 知识库表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_knowledge_base (
    id BIGINT PRIMARY KEY,
    kb_name VARCHAR(256) NOT NULL,
    kb_type SMALLINT,
    description TEXT,
    doc_count INTEGER DEFAULT 0,
    vector_collection VARCHAR(128),
    embedding_model VARCHAR(128),
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_knowledge_base IS '知识库表';
COMMENT ON COLUMN ai_knowledge_base.id IS '知识库ID';
COMMENT ON COLUMN ai_knowledge_base.kb_name IS '知识库名称';
COMMENT ON COLUMN ai_knowledge_base.kb_type IS '知识库类型 1法规政策 2行业标准 3技术文档 4业务知识 5其他';
COMMENT ON COLUMN ai_knowledge_base.description IS '描述';
COMMENT ON COLUMN ai_knowledge_base.doc_count IS '文档数量';
COMMENT ON COLUMN ai_knowledge_base.vector_collection IS '向量集合名称';
COMMENT ON COLUMN ai_knowledge_base.embedding_model IS '嵌入模型';
COMMENT ON COLUMN ai_knowledge_base.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 知识库文档表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_knowledge_documents (
    id BIGINT PRIMARY KEY,
    kb_id BIGINT NOT NULL,
    doc_title VARCHAR(512) NOT NULL,
    doc_type VARCHAR(64),
    doc_content TEXT,
    doc_summary TEXT,
    file_url VARCHAR(512),
    chunk_count INTEGER DEFAULT 0,
    vector_ids TEXT,
    metadata TEXT,
    status SMALLINT DEFAULT 0,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_knowledge_documents IS '知识库文档表';
COMMENT ON COLUMN ai_knowledge_documents.id IS '文档ID';
COMMENT ON COLUMN ai_knowledge_documents.kb_id IS '知识库ID';
COMMENT ON COLUMN ai_knowledge_documents.doc_title IS '文档标题';
COMMENT ON COLUMN ai_knowledge_documents.doc_type IS '文档类型';
COMMENT ON COLUMN ai_knowledge_documents.doc_content IS '文档内容';
COMMENT ON COLUMN ai_knowledge_documents.doc_summary IS '文档摘要';
COMMENT ON COLUMN ai_knowledge_documents.file_url IS '文件URL';
COMMENT ON COLUMN ai_knowledge_documents.chunk_count IS '分块数量';
COMMENT ON COLUMN ai_knowledge_documents.vector_ids IS '向量ID列表';
COMMENT ON COLUMN ai_knowledge_documents.metadata IS '元数据JSON';
COMMENT ON COLUMN ai_knowledge_documents.status IS '状态 0待处理 1已入库 2处理失败';

CREATE INDEX idx_ai_knowledge_docs_kb_id ON ai_knowledge_documents(kb_id);

-- ----------------------------
-- AI模型表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_models (
    id BIGINT PRIMARY KEY,
    model_code VARCHAR(64) NOT NULL,
    model_name VARCHAR(256) NOT NULL,
    model_type SMALLINT,
    model_version VARCHAR(32),
    description TEXT,
    endpoint VARCHAR(512),
    api_key VARCHAR(256),
    config TEXT,
    metrics TEXT,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    opt_version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_models IS 'AI模型表';
COMMENT ON COLUMN ai_models.id IS '模型ID';
COMMENT ON COLUMN ai_models.model_code IS '模型编码';
COMMENT ON COLUMN ai_models.model_name IS '模型名称';
COMMENT ON COLUMN ai_models.model_type IS '模型类型 1大语言模型 2图像识别 3文本分类 4情感分析 5OCR 6向量模型 7其他';
COMMENT ON COLUMN ai_models.model_version IS '版本号';
COMMENT ON COLUMN ai_models.description IS '描述';
COMMENT ON COLUMN ai_models.endpoint IS 'API端点';
COMMENT ON COLUMN ai_models.api_key IS 'API密钥';
COMMENT ON COLUMN ai_models.config IS '配置信息JSON';
COMMENT ON COLUMN ai_models.metrics IS '性能指标JSON';
COMMENT ON COLUMN ai_models.status IS '状态 0禁用 1启用';

-- ----------------------------
-- AI智能体表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_agents (
    id BIGINT PRIMARY KEY,
    agent_code VARCHAR(64) NOT NULL,
    agent_name VARCHAR(256) NOT NULL,
    agent_type SMALLINT,
    description TEXT,
    flow_config TEXT,
    input_schema TEXT,
    output_schema TEXT,
    api_endpoint VARCHAR(512),
    status SMALLINT DEFAULT 0,
    version_no VARCHAR(32),
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_agents IS 'AI智能体表';
COMMENT ON COLUMN ai_agents.id IS '智能体ID';
COMMENT ON COLUMN ai_agents.agent_code IS '智能体编码';
COMMENT ON COLUMN ai_agents.agent_name IS '智能体名称';
COMMENT ON COLUMN ai_agents.agent_type IS '智能体类型 1鉴定助手 2舆情分析 3风险评估 4报告生成 5客服对话 6其他';
COMMENT ON COLUMN ai_agents.description IS '描述';
COMMENT ON COLUMN ai_agents.flow_config IS '流程配置JSON';
COMMENT ON COLUMN ai_agents.input_schema IS '输入Schema JSON';
COMMENT ON COLUMN ai_agents.output_schema IS '输出Schema JSON';
COMMENT ON COLUMN ai_agents.api_endpoint IS 'API端点';
COMMENT ON COLUMN ai_agents.status IS '状态 0停用 1运行中';
COMMENT ON COLUMN ai_agents.version_no IS '版本号';

-- ----------------------------
-- AI智能体调用记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_agent_calls (
    id BIGINT PRIMARY KEY,
    agent_id BIGINT NOT NULL,
    caller_id BIGINT,
    input_data TEXT,
    output_data TEXT,
    duration_ms BIGINT,
    token_count INTEGER,
    status SMALLINT DEFAULT 1,
    error_message TEXT,
    called_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_agent_calls IS 'AI智能体调用记录表';
COMMENT ON COLUMN ai_agent_calls.id IS '记录ID';
COMMENT ON COLUMN ai_agent_calls.agent_id IS '智能体ID';
COMMENT ON COLUMN ai_agent_calls.caller_id IS '调用者ID';
COMMENT ON COLUMN ai_agent_calls.input_data IS '输入数据';
COMMENT ON COLUMN ai_agent_calls.output_data IS '输出数据';
COMMENT ON COLUMN ai_agent_calls.duration_ms IS '耗时毫秒';
COMMENT ON COLUMN ai_agent_calls.token_count IS 'Token消耗';
COMMENT ON COLUMN ai_agent_calls.status IS '状态 0失败 1成功';
COMMENT ON COLUMN ai_agent_calls.error_message IS '错误信息';
COMMENT ON COLUMN ai_agent_calls.called_at IS '调用时间';

CREATE INDEX idx_ai_agent_calls_agent_id ON ai_agent_calls(agent_id);
CREATE INDEX idx_ai_agent_calls_called_at ON ai_agent_calls(called_at);

-- ----------------------------
-- 数字人表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_digital_humans (
    id BIGINT PRIMARY KEY,
    dh_name VARCHAR(128) NOT NULL,
    dh_code VARCHAR(64),
    avatar_url VARCHAR(512),
    voice_id VARCHAR(128),
    personality TEXT,
    knowledge_base_ids TEXT,
    model_id BIGINT,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_digital_humans IS '数字人表';
COMMENT ON COLUMN ai_digital_humans.id IS '数字人ID';
COMMENT ON COLUMN ai_digital_humans.dh_name IS '数字人名称';
COMMENT ON COLUMN ai_digital_humans.dh_code IS '数字人编码';
COMMENT ON COLUMN ai_digital_humans.avatar_url IS '形象URL';
COMMENT ON COLUMN ai_digital_humans.voice_id IS '语音ID';
COMMENT ON COLUMN ai_digital_humans.personality IS '性格描述';
COMMENT ON COLUMN ai_digital_humans.knowledge_base_ids IS '关联知识库ID列表';
COMMENT ON COLUMN ai_digital_humans.model_id IS '模型ID';
COMMENT ON COLUMN ai_digital_humans.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 对话记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_conversations (
    id BIGINT PRIMARY KEY,
    conversation_id VARCHAR(128) NOT NULL,
    user_id BIGINT,
    dh_id BIGINT,
    agent_id BIGINT,
    role SMALLINT,
    content TEXT,
    content_type SMALLINT,
    audio_url VARCHAR(512),
    image_url VARCHAR(512),
    intent VARCHAR(128),
    entities TEXT,
    tokens INTEGER,
    response_time_ms BIGINT,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_conversations IS '对话记录表';
COMMENT ON COLUMN ai_conversations.id IS '记录ID';
COMMENT ON COLUMN ai_conversations.conversation_id IS '会话ID';
COMMENT ON COLUMN ai_conversations.user_id IS '用户ID';
COMMENT ON COLUMN ai_conversations.dh_id IS '数字人ID';
COMMENT ON COLUMN ai_conversations.agent_id IS '智能体ID';
COMMENT ON COLUMN ai_conversations.role IS '角色 1用户 2AI';
COMMENT ON COLUMN ai_conversations.content IS '内容';
COMMENT ON COLUMN ai_conversations.content_type IS '内容类型 1文本 2图片 3音频 4视频';
COMMENT ON COLUMN ai_conversations.audio_url IS '音频URL';
COMMENT ON COLUMN ai_conversations.image_url IS '图片URL';
COMMENT ON COLUMN ai_conversations.intent IS '意图';
COMMENT ON COLUMN ai_conversations.entities IS '实体JSON';
COMMENT ON COLUMN ai_conversations.tokens IS 'Token消耗';
COMMENT ON COLUMN ai_conversations.response_time_ms IS '响应时间毫秒';

CREATE INDEX idx_ai_conversations_conversation_id ON ai_conversations(conversation_id);
CREATE INDEX idx_ai_conversations_user_id ON ai_conversations(user_id);

-- ----------------------------
-- 数据质量规则表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_quality_rules (
    id BIGINT PRIMARY KEY,
    rule_code VARCHAR(64) NOT NULL,
    rule_name VARCHAR(256) NOT NULL,
    rule_type SMALLINT,
    rule_expression TEXT,
    rule_description TEXT,
    target_table VARCHAR(128),
    target_field VARCHAR(128),
    severity SMALLINT DEFAULT 1,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_quality_rules IS '数据质量规则表';
COMMENT ON COLUMN ai_quality_rules.id IS '规则ID';
COMMENT ON COLUMN ai_quality_rules.rule_code IS '规则编码';
COMMENT ON COLUMN ai_quality_rules.rule_name IS '规则名称';
COMMENT ON COLUMN ai_quality_rules.rule_type IS '规则类型 1完整性 2一致性 3准确性 4及时性 5唯一性';
COMMENT ON COLUMN ai_quality_rules.rule_expression IS '规则表达式';
COMMENT ON COLUMN ai_quality_rules.rule_description IS '规则描述';
COMMENT ON COLUMN ai_quality_rules.target_table IS '目标表';
COMMENT ON COLUMN ai_quality_rules.target_field IS '目标字段';
COMMENT ON COLUMN ai_quality_rules.severity IS '严重程度 1提示 2警告 3严重';
COMMENT ON COLUMN ai_quality_rules.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 数据质量检查日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_quality_check_logs (
    id BIGINT PRIMARY KEY,
    check_target VARCHAR(128),
    target_id BIGINT,
    rule_id BIGINT,
    check_result SMALLINT,
    check_data TEXT,
    error_detail TEXT,
    checked_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_quality_check_logs IS '数据质量检查日志表';
COMMENT ON COLUMN ai_quality_check_logs.id IS '日志ID';
COMMENT ON COLUMN ai_quality_check_logs.check_target IS '检查目标';
COMMENT ON COLUMN ai_quality_check_logs.target_id IS '目标ID';
COMMENT ON COLUMN ai_quality_check_logs.rule_id IS '规则ID';
COMMENT ON COLUMN ai_quality_check_logs.check_result IS '检查结果 0通过 1不通过';
COMMENT ON COLUMN ai_quality_check_logs.check_data IS '检查数据';
COMMENT ON COLUMN ai_quality_check_logs.error_detail IS '错误详情';
COMMENT ON COLUMN ai_quality_check_logs.checked_at IS '检查时间';

CREATE INDEX idx_ai_quality_check_logs_rule_id ON ai_quality_check_logs(rule_id);

-- ----------------------------
-- 敏感关键词表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_sensitive_keywords (
    id BIGINT PRIMARY KEY,
    keyword VARCHAR(128) NOT NULL,
    category VARCHAR(64),
    level SMALLINT,
    description VARCHAR(512),
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_sensitive_keywords IS '敏感关键词表';
COMMENT ON COLUMN ai_sensitive_keywords.id IS '关键词ID';
COMMENT ON COLUMN ai_sensitive_keywords.keyword IS '关键词';
COMMENT ON COLUMN ai_sensitive_keywords.category IS '分类';
COMMENT ON COLUMN ai_sensitive_keywords.level IS '敏感级别 1低 2中 3高';
COMMENT ON COLUMN ai_sensitive_keywords.description IS '描述';
COMMENT ON COLUMN ai_sensitive_keywords.status IS '状态 0禁用 1启用';

CREATE INDEX idx_ai_sensitive_keywords_keyword ON ai_sensitive_keywords(keyword);

-- ----------------------------
-- 敏感内容拦截日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_interception_logs (
    id BIGINT PRIMARY KEY,
    target_type SMALLINT,
    target_content TEXT,
    target_url VARCHAR(1024),
    detection_type SMALLINT,
    risk_level SMALLINT,
    matched_items TEXT,
    action SMALLINT,
    user_id BIGINT,
    detected_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_interception_logs IS '敏感内容拦截日志表';
COMMENT ON COLUMN ai_interception_logs.id IS '日志ID';
COMMENT ON COLUMN ai_interception_logs.target_type IS '目标类型 1文本 2图片 3音频 4视频';
COMMENT ON COLUMN ai_interception_logs.target_content IS '目标内容';
COMMENT ON COLUMN ai_interception_logs.target_url IS '目标URL';
COMMENT ON COLUMN ai_interception_logs.detection_type IS '检测类型 1敏感词 2图像识别 3内容审核';
COMMENT ON COLUMN ai_interception_logs.risk_level IS '风险等级 1低 2中 3高';
COMMENT ON COLUMN ai_interception_logs.matched_items IS '匹配项JSON';
COMMENT ON COLUMN ai_interception_logs.action IS '处理动作 1放行 2拦截 3人工审核';
COMMENT ON COLUMN ai_interception_logs.user_id IS '用户ID';
COMMENT ON COLUMN ai_interception_logs.detected_at IS '检测时间';

CREATE INDEX idx_ai_interception_logs_user_id ON ai_interception_logs(user_id);
CREATE INDEX idx_ai_interception_logs_detected_at ON ai_interception_logs(detected_at);

-- ----------------------------
-- AI翻译任务表
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_translation_tasks (
    id BIGINT PRIMARY KEY,
    task_type SMALLINT,
    source_language VARCHAR(32),
    target_language VARCHAR(32),
    source_url VARCHAR(512),
    source_content TEXT,
    translated_content TEXT,
    translated_url VARCHAR(512),
    ocr_result TEXT,
    status SMALLINT DEFAULT 0,
    progress INTEGER DEFAULT 0,
    error_message TEXT,
    user_id BIGINT,
    completed_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE ai_translation_tasks IS 'AI翻译任务表';
COMMENT ON COLUMN ai_translation_tasks.id IS '任务ID';
COMMENT ON COLUMN ai_translation_tasks.task_type IS '任务类型 1文档翻译 2网页翻译 2图片翻译';
COMMENT ON COLUMN ai_translation_tasks.source_language IS '源语言';
COMMENT ON COLUMN ai_translation_tasks.target_language IS '目标语言';
COMMENT ON COLUMN ai_translation_tasks.source_url IS '源文件URL';
COMMENT ON COLUMN ai_translation_tasks.source_content IS '源内容';
COMMENT ON COLUMN ai_translation_tasks.translated_content IS '翻译内容';
COMMENT ON COLUMN ai_translation_tasks.translated_url IS '翻译结果URL';
COMMENT ON COLUMN ai_translation_tasks.ocr_result IS 'OCR识别结果';
COMMENT ON COLUMN ai_translation_tasks.status IS '状态 0待处理 1处理中 2已完成 3失败';
COMMENT ON COLUMN ai_translation_tasks.progress IS '进度百分比';
COMMENT ON COLUMN ai_translation_tasks.error_message IS '错误信息';
COMMENT ON COLUMN ai_translation_tasks.user_id IS '用户ID';
COMMENT ON COLUMN ai_translation_tasks.completed_at IS '完成时间';

CREATE INDEX idx_ai_translation_tasks_user_id ON ai_translation_tasks(user_id);
CREATE INDEX idx_ai_translation_tasks_status ON ai_translation_tasks(status);
