package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.ai.AiInterceptionLog;
import com.pub.supervision.entity.ai.AiSensitiveKeyword;
import com.pub.supervision.mapper.AiInterceptionLogMapper;
import com.pub.supervision.mapper.AiSensitiveKeywordMapper;
import com.pub.supervision.service.SensitiveService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SensitiveServiceImpl extends ServiceImpl<AiInterceptionLogMapper, AiInterceptionLog> implements SensitiveService {
    private final AiSensitiveKeywordMapper keywordMapper;
    public SensitiveServiceImpl(AiSensitiveKeywordMapper keywordMapper) { this.keywordMapper = keywordMapper; }

    @Override
    public Map<String, Object> textCheck(String text) {
        LambdaQueryWrapper<AiSensitiveKeyword> w = new LambdaQueryWrapper<>();
        w.eq(AiSensitiveKeyword::getStatus, 1);
        List<AiSensitiveKeyword> keywords = keywordMapper.selectList(w);
        List<String> matched = new ArrayList<>();
        for (AiSensitiveKeyword kw : keywords) {
            if (text.contains(kw.getKeyword())) {
                matched.add(kw.getKeyword());
            }
        }
        Map<String, Object> r = new HashMap<>();
        r.put("passed", matched.isEmpty());
        r.put("matchedKeywords", matched);
        AiInterceptionLog log = new AiInterceptionLog();
        log.setContent(text);
        log.setSource("text_check");
        log.setInterceptedCount(matched.size());
        log.setMatchKeywords(String.join(",", matched));
        save(log);
        return r;
    }

    @Override
    public Map<String, Object> imageCheck(String imageUrl) {
        Map<String, Object> r = new HashMap<>();
        r.put("passed", true);
        r.put("issues", List.of());
        return r;
    }

    @Override
    public PageResult<AiInterceptionLog> getLogs(String source, int pageNum, int pageSize) {
        LambdaQueryWrapper<AiInterceptionLog> w = new LambdaQueryWrapper<>();
        w.eq(source != null && !source.isEmpty(), AiInterceptionLog::getSource, source)
         .orderByDesc(AiInterceptionLog::getCreatedAt);
        Page<AiInterceptionLog> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
}
