-- ============================================================
-- 05_create_tables_opi.sql
-- 智能舆情分析模块表
-- ============================================================

-- ----------------------------
-- 采集任务表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_collection_tasks (
    id BIGINT PRIMARY KEY,
    task_code VARCHAR(64),
    task_name VARCHAR(256),
    task_type SMALLINT,
    keywords TEXT,
    topics TEXT,
    accounts TEXT,
    channels TEXT,
    regions TEXT,
    time_range_start TIMESTAMP,
    time_range_end TIMESTAMP,
    cron_expression VARCHAR(128),
    frequency INTEGER,
    notify_rules TEXT,
    status SMALLINT DEFAULT 0,
    last_run_at TIMESTAMP,
    next_run_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_collection_tasks IS '采集任务表';
COMMENT ON COLUMN opi_collection_tasks.id IS '任务ID';
COMMENT ON COLUMN opi_collection_tasks.task_code IS '任务编号';
COMMENT ON COLUMN opi_collection_tasks.task_name IS '任务名称';
COMMENT ON COLUMN opi_collection_tasks.task_type IS '任务类型 1关键词采集 2账号采集 3话题采集 4定向采集';
COMMENT ON COLUMN opi_collection_tasks.keywords IS '关键词列表JSON';
COMMENT ON COLUMN opi_collection_tasks.topics IS '话题列表JSON';
COMMENT ON COLUMN opi_collection_tasks.accounts IS '账号列表JSON';
COMMENT ON COLUMN opi_collection_tasks.channels IS '渠道列表JSON';
COMMENT ON COLUMN opi_collection_tasks.regions IS '区域列表JSON';
COMMENT ON COLUMN opi_collection_tasks.time_range_start IS '采集起始时间';
COMMENT ON COLUMN opi_collection_tasks.time_range_end IS '采集结束时间';
COMMENT ON COLUMN opi_collection_tasks.cron_expression IS 'cron表达式';
COMMENT ON COLUMN opi_collection_tasks.frequency IS '采集频率(分钟)';
COMMENT ON COLUMN opi_collection_tasks.notify_rules IS '通知规则JSON';
COMMENT ON COLUMN opi_collection_tasks.status IS '状态 0停止 1运行中';
COMMENT ON COLUMN opi_collection_tasks.last_run_at IS '上次运行时间';
COMMENT ON COLUMN opi_collection_tasks.next_run_at IS '下次运行时间';

-- ----------------------------
-- 采集渠道表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_collection_channels (
    id BIGINT PRIMARY KEY,
    channel_code VARCHAR(64),
    channel_name VARCHAR(128),
    channel_type SMALLINT,
    api_endpoint VARCHAR(512),
    api_key VARCHAR(256),
    config TEXT,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_collection_channels IS '采集渠道表';
COMMENT ON COLUMN opi_collection_channels.id IS '渠道ID';
COMMENT ON COLUMN opi_collection_channels.channel_code IS '渠道编码';
COMMENT ON COLUMN opi_collection_channels.channel_name IS '渠道名称';
COMMENT ON COLUMN opi_collection_channels.channel_type IS '渠道类型 1微博 2微信 3论坛 4新闻 5短视频 6其他';
COMMENT ON COLUMN opi_collection_channels.api_endpoint IS 'API端点';
COMMENT ON COLUMN opi_collection_channels.api_key IS 'API密钥';
COMMENT ON COLUMN opi_collection_channels.config IS '配置信息JSON';
COMMENT ON COLUMN opi_collection_channels.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 舆情数据表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_opinions (
    id BIGINT PRIMARY KEY,
    opinion_code VARCHAR(64) NOT NULL UNIQUE,
    task_id BIGINT,
    channel_id BIGINT,
    channel_type SMALLINT,
    title VARCHAR(512),
    content TEXT,
    summary TEXT,
    author VARCHAR(128),
    author_id VARCHAR(128),
    author_avatar VARCHAR(512),
    publish_time TIMESTAMP,
    collect_time TIMESTAMP,
    source_url VARCHAR(1024),
    region VARCHAR(128),
    sentiment SMALLINT,
    sentiment_score DECIMAL(5,4),
    entities TEXT,
    keywords TEXT,
    repost_count INTEGER DEFAULT 0,
    comment_count INTEGER DEFAULT 0,
    like_count INTEGER DEFAULT 0,
    heat_score DECIMAL(10,2),
    risk_level SMALLINT,
    simhash BIGINT,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_opinions IS '舆情数据表';
COMMENT ON COLUMN opi_opinions.id IS '舆情ID';
COMMENT ON COLUMN opi_opinions.opinion_code IS '舆情编号';
COMMENT ON COLUMN opi_opinions.task_id IS '采集任务ID';
COMMENT ON COLUMN opi_opinions.channel_id IS '渠道ID';
COMMENT ON COLUMN opi_opinions.channel_type IS '渠道类型';
COMMENT ON COLUMN opi_opinions.title IS '标题';
COMMENT ON COLUMN opi_opinions.content IS '内容';
COMMENT ON COLUMN opi_opinions.summary IS '摘要';
COMMENT ON COLUMN opi_opinions.author IS '作者';
COMMENT ON COLUMN opi_opinions.author_id IS '作者ID';
COMMENT ON COLUMN opi_opinions.author_avatar IS '作者头像';
COMMENT ON COLUMN opi_opinions.publish_time IS '发布时间';
COMMENT ON COLUMN opi_opinions.collect_time IS '采集时间';
COMMENT ON COLUMN opi_opinions.source_url IS '来源URL';
COMMENT ON COLUMN opi_opinions.region IS '区域';
COMMENT ON COLUMN opi_opinions.sentiment IS '情感倾向 1正面 0中性 -1负面';
COMMENT ON COLUMN opi_opinions.sentiment_score IS '情感得分';
COMMENT ON COLUMN opi_opinions.entities IS '实体列表JSON';
COMMENT ON COLUMN opi_opinions.keywords IS '关键词JSON';
COMMENT ON COLUMN opi_opinions.repost_count IS '转发数';
COMMENT ON COLUMN opi_opinions.comment_count IS '评论数';
COMMENT ON COLUMN opi_opinions.like_count IS '点赞数';
COMMENT ON COLUMN opi_opinions.heat_score IS '热度分';
COMMENT ON COLUMN opi_opinions.risk_level IS '风险等级 0无 1低 2中 3高';
COMMENT ON COLUMN opi_opinions.simhash IS 'SimHash值(用于去重)';

CREATE INDEX idx_opi_opinions_task_id ON opi_opinions(task_id);
CREATE INDEX idx_opi_opinions_channel_id ON opi_opinions(channel_id);
CREATE INDEX idx_opi_opinions_sentiment ON opi_opinions(sentiment);
CREATE INDEX idx_opi_opinions_risk_level ON opi_opinions(risk_level);
CREATE INDEX idx_opi_opinions_publish_time ON opi_opinions(publish_time);
CREATE INDEX idx_opi_opinions_heat_score ON opi_opinions(heat_score);

-- ----------------------------
-- 分析结果表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_analysis_results (
    id BIGINT PRIMARY KEY,
    analysis_type SMALLINT,
    target_type SMALLINT,
    target_id BIGINT,
    target_name VARCHAR(256),
    analysis_data TEXT,
    analysis_summary TEXT,
    analysis_conclusion TEXT,
    analyzed_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_analysis_results IS '分析结果表';
COMMENT ON COLUMN opi_analysis_results.id IS '分析ID';
COMMENT ON COLUMN opi_analysis_results.analysis_type IS '分析类型 1情感分析 2趋势分析 3传播分析 4话题分析 5人物分析';
COMMENT ON COLUMN opi_analysis_results.target_type IS '目标类型 1舆情 2事件 3话题';
COMMENT ON COLUMN opi_analysis_results.target_id IS '目标ID';
COMMENT ON COLUMN opi_analysis_results.target_name IS '目标名称';
COMMENT ON COLUMN opi_analysis_results.analysis_data IS '分析数据JSON';
COMMENT ON COLUMN opi_analysis_results.analysis_summary IS '分析摘要';
COMMENT ON COLUMN opi_analysis_results.analysis_conclusion IS '分析结论';
COMMENT ON COLUMN opi_analysis_results.analyzed_at IS '分析时间';

-- ----------------------------
-- 传播节点表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_propagation_nodes (
    id BIGINT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    node_type SMALLINT,
    node_id VARCHAR(128),
    node_name VARCHAR(256),
    node_avatar VARCHAR(512),
    follower_count INTEGER,
    influence_score DECIMAL(5,4),
    pagerank_score DECIMAL(10,6),
    layer INTEGER,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_propagation_nodes IS '传播节点表';
COMMENT ON COLUMN opi_propagation_nodes.id IS '节点ID';
COMMENT ON COLUMN opi_propagation_nodes.event_id IS '事件ID';
COMMENT ON COLUMN opi_propagation_nodes.node_type IS '节点类型 1用户 2媒体 3机构 4其他';
COMMENT ON COLUMN opi_propagation_nodes.node_id IS '节点标识';
COMMENT ON COLUMN opi_propagation_nodes.node_name IS '节点名称';
COMMENT ON COLUMN opi_propagation_nodes.node_avatar IS '节点头像';
COMMENT ON COLUMN opi_propagation_nodes.follower_count IS '粉丝数';
COMMENT ON COLUMN opi_propagation_nodes.influence_score IS '影响力评分';
COMMENT ON COLUMN opi_propagation_nodes.pagerank_score IS 'PageRank得分';
COMMENT ON COLUMN opi_propagation_nodes.layer IS '传播层级';

CREATE INDEX idx_opi_propagation_nodes_event_id ON opi_propagation_nodes(event_id);

-- ----------------------------
-- 传播边表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_propagation_edges (
    id BIGINT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    source_node_id VARCHAR(128),
    target_node_id VARCHAR(128),
    edge_type SMALLINT,
    weight DECIMAL(5,4),
    occurred_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_propagation_edges IS '传播边表';
COMMENT ON COLUMN opi_propagation_edges.id IS '边ID';
COMMENT ON COLUMN opi_propagation_edges.event_id IS '事件ID';
COMMENT ON COLUMN opi_propagation_edges.source_node_id IS '源节点ID';
COMMENT ON COLUMN opi_propagation_edges.target_node_id IS '目标节点ID';
COMMENT ON COLUMN opi_propagation_edges.edge_type IS '边类型 1转发 2评论 3引用 4推送';
COMMENT ON COLUMN opi_propagation_edges.weight IS '权重';
COMMENT ON COLUMN opi_propagation_edges.occurred_at IS '发生时间';

CREATE INDEX idx_opi_propagation_edges_event_id ON opi_propagation_edges(event_id);

-- ----------------------------
-- 舆情事件表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_events (
    id BIGINT PRIMARY KEY,
    event_code VARCHAR(64) NOT NULL UNIQUE,
    event_name VARCHAR(256),
    event_type VARCHAR(64),
    description TEXT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    heat_score DECIMAL(10,2),
    risk_level SMALLINT,
    sentiment_trend TEXT,
    opinion_count INTEGER DEFAULT 0,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_events IS '舆情事件表';
COMMENT ON COLUMN opi_events.id IS '事件ID';
COMMENT ON COLUMN opi_events.event_code IS '事件编号';
COMMENT ON COLUMN opi_events.event_name IS '事件名称';
COMMENT ON COLUMN opi_events.event_type IS '事件类型';
COMMENT ON COLUMN opi_events.description IS '事件描述';
COMMENT ON COLUMN opi_events.start_time IS '开始时间';
COMMENT ON COLUMN opi_events.end_time IS '结束时间';
COMMENT ON COLUMN opi_events.heat_score IS '热度分';
COMMENT ON COLUMN opi_events.risk_level IS '风险等级 0无 1低 2中 3高';
COMMENT ON COLUMN opi_events.sentiment_trend IS '情感趋势数据JSON';
COMMENT ON COLUMN opi_events.opinion_count IS '关联舆情数';
COMMENT ON COLUMN opi_events.status IS '状态 0草稿 1进行中 2已结束 3已归档';

CREATE INDEX idx_opi_events_risk_level ON opi_events(risk_level);
CREATE INDEX idx_opi_events_start_time ON opi_events(start_time);

-- ----------------------------
-- 舆情报告表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_reports (
    id BIGINT PRIMARY KEY,
    report_code VARCHAR(64) NOT NULL UNIQUE,
    report_name VARCHAR(256),
    report_type SMALLINT,
    template_id BIGINT,
    time_range_start TIMESTAMP,
    time_range_end TIMESTAMP,
    content TEXT,
    summary TEXT,
    charts TEXT,
    file_url VARCHAR(512),
    file_format VARCHAR(32),
    status SMALLINT DEFAULT 0,
    generated_by BIGINT,
    generated_at TIMESTAMP,
    reviewed_by BIGINT,
    reviewed_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_reports IS '舆情报告表';
COMMENT ON COLUMN opi_reports.id IS '报告ID';
COMMENT ON COLUMN opi_reports.report_code IS '报告编号';
COMMENT ON COLUMN opi_reports.report_name IS '报告名称';
COMMENT ON COLUMN opi_reports.report_type IS '报告类型 1日报 2周报 3月报 4专题报告 5简报';
COMMENT ON COLUMN opi_reports.template_id IS '模板ID';
COMMENT ON COLUMN opi_reports.time_range_start IS '统计起始时间';
COMMENT ON COLUMN opi_reports.time_range_end IS '统计结束时间';
COMMENT ON COLUMN opi_reports.content IS '报告内容HTML';
COMMENT ON COLUMN opi_reports.summary IS '报告摘要';
COMMENT ON COLUMN opi_reports.charts IS '图表配置JSON';
COMMENT ON COLUMN opi_reports.file_url IS '文件URL';
COMMENT ON COLUMN opi_reports.file_format IS '文件格式';
COMMENT ON COLUMN opi_reports.status IS '状态 0草稿 1已生成 2已审核 3已发布';
COMMENT ON COLUMN opi_reports.generated_by IS '生成人';
COMMENT ON COLUMN opi_reports.generated_at IS '生成时间';
COMMENT ON COLUMN opi_reports.reviewed_by IS '审核人';
COMMENT ON COLUMN opi_reports.reviewed_at IS '审核时间';

CREATE INDEX idx_opi_reports_status ON opi_reports(status);
CREATE INDEX idx_opi_reports_report_type ON opi_reports(report_type);

-- ----------------------------
-- 报告模板表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_report_templates (
    id BIGINT PRIMARY KEY,
    template_name VARCHAR(256) NOT NULL,
    template_type SMALLINT,
    description TEXT,
    structure TEXT,
    style_config TEXT,
    status SMALLINT DEFAULT 1,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_report_templates IS '报告模板表';
COMMENT ON COLUMN opi_report_templates.id IS '模板ID';
COMMENT ON COLUMN opi_report_templates.template_name IS '模板名称';
COMMENT ON COLUMN opi_report_templates.template_type IS '模板类型 1日报 2周报 3月报 4专题';
COMMENT ON COLUMN opi_report_templates.description IS '描述';
COMMENT ON COLUMN opi_report_templates.structure IS '模板结构JSON';
COMMENT ON COLUMN opi_report_templates.style_config IS '样式配置JSON';
COMMENT ON COLUMN opi_report_templates.status IS '状态 0禁用 1启用';

-- ----------------------------
-- 舆情效能统计表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_effectiveness_stats (
    id BIGINT PRIMARY KEY,
    stat_date DATE NOT NULL,
    stat_period SMALLINT,
    user_id BIGINT,
    department_id BIGINT,
    discovery_count INTEGER DEFAULT 0,
    discovery_rate DECIMAL(5,4),
    first_discovery_time INTEGER,
    handle_count INTEGER DEFAULT 0,
    handle_rate DECIMAL(5,4),
    timely_handle_rate DECIMAL(5,4),
    channel_coverage DECIMAL(5,4),
    region_coverage DECIMAL(5,4),
    alert_accuracy DECIMAL(5,4),
    report_pass_rate DECIMAL(5,4),
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_effectiveness_stats IS '舆情效能统计表';
COMMENT ON COLUMN opi_effectiveness_stats.id IS '统计ID';
COMMENT ON COLUMN opi_effectiveness_stats.stat_date IS '统计日期';
COMMENT ON COLUMN opi_effectiveness_stats.stat_period IS '统计周期 1日报 2周报 3月报';
COMMENT ON COLUMN opi_effectiveness_stats.user_id IS '用户ID';
COMMENT ON COLUMN opi_effectiveness_stats.department_id IS '部门ID';
COMMENT ON COLUMN opi_effectiveness_stats.discovery_count IS '发现数量';
COMMENT ON COLUMN opi_effectiveness_stats.discovery_rate IS '发现率';
COMMENT ON COLUMN opi_effectiveness_stats.first_discovery_time IS '首次发现时间(分钟)';
COMMENT ON COLUMN opi_effectiveness_stats.handle_count IS '处置数量';
COMMENT ON COLUMN opi_effectiveness_stats.handle_rate IS '处置率';
COMMENT ON COLUMN opi_effectiveness_stats.timely_handle_rate IS '及时处置率';
COMMENT ON COLUMN opi_effectiveness_stats.channel_coverage IS '渠道覆盖率';
COMMENT ON COLUMN opi_effectiveness_stats.region_coverage IS '区域覆盖率';
COMMENT ON COLUMN opi_effectiveness_stats.alert_accuracy IS '预警准确率';
COMMENT ON COLUMN opi_effectiveness_stats.report_pass_rate IS '报告通过率';

CREATE INDEX idx_opi_effectiveness_stat_date ON opi_effectiveness_stats(stat_date);
CREATE INDEX idx_opi_effectiveness_user_id ON opi_effectiveness_stats(user_id);

-- ----------------------------
-- 外部对接表
-- ----------------------------
CREATE TABLE IF NOT EXISTS opi_external_docking (
    id BIGINT PRIMARY KEY,
    docking_type SMALLINT,
    direction SMALLINT,
    target_system VARCHAR(128),
    interface_name VARCHAR(256),
    request_data TEXT,
    response_data TEXT,
    status SMALLINT DEFAULT 1,
    error_message TEXT,
    operated_at TIMESTAMP,
    created_by VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 0
);

COMMENT ON TABLE opi_external_docking IS '外部对接表';
COMMENT ON COLUMN opi_external_docking.id IS '对接ID';
COMMENT ON COLUMN opi_external_docking.docking_type IS '对接类型 1数据采集 2数据推送 3预警通知';
COMMENT ON COLUMN opi_external_docking.direction IS '方向 1请求 2响应';
COMMENT ON COLUMN opi_external_docking.target_system IS '目标系统';
COMMENT ON COLUMN opi_external_docking.interface_name IS '接口名称';
COMMENT ON COLUMN opi_external_docking.request_data IS '请求数据';
COMMENT ON COLUMN opi_external_docking.response_data IS '响应数据';
COMMENT ON COLUMN opi_external_docking.status IS '状态 0失败 1成功';
COMMENT ON COLUMN opi_external_docking.error_message IS '错误信息';
COMMENT ON COLUMN opi_external_docking.operated_at IS '操作时间';
