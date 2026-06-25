package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.ai.AiQualityRule;
import com.pub.supervision.mapper.AiQualityRuleMapper;
import com.pub.supervision.service.QualityRuleService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QualityRuleServiceImpl extends ServiceImpl<AiQualityRuleMapper, AiQualityRule> implements QualityRuleService {
    @Override public PageResult<AiQualityRule> page(String ruleName, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<AiQualityRule> w = new LambdaQueryWrapper<>();
        w.like(ruleName != null && !ruleName.isEmpty(), AiQualityRule::getRuleName, ruleName)
         .eq(status != null, AiQualityRule::getStatus, status).orderByDesc(AiQualityRule::getCreatedAt);
        Page<AiQualityRule> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public void create(AiQualityRule rule) { save(rule); }
    @Override public void update(AiQualityRule rule) { updateById(rule); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public Map<String, Object> executeCheck(Long ruleId, String content) {
        Map<String, Object> r = new HashMap<>(); r.put("ruleId", ruleId); r.put("passed", true); r.put("violations", List.of()); return r;
    }
}
