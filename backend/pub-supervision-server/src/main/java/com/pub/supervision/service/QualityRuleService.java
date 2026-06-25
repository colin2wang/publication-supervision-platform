package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiQualityRule;
import com.pub.supervision.common.result.PageResult;

import java.util.Map;

public interface QualityRuleService extends IService<AiQualityRule> {
    PageResult<AiQualityRule> page(String ruleName, Integer status, int pageNum, int pageSize);
    void create(AiQualityRule rule);
    void update(AiQualityRule rule);
    void delete(Long id);
    Map<String, Object> executeCheck(Long ruleId, String content);
}
