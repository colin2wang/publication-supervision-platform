package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.ai.AiTranslationTask;
import com.pub.supervision.mapper.AiTranslationTaskMapper;
import com.pub.supervision.service.TranslationService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TranslationServiceImpl extends ServiceImpl<AiTranslationTaskMapper, AiTranslationTask> implements TranslationService {
    @Override public AiTranslationTask translateText(String text, String sourceLang, String targetLang) {
        AiTranslationTask t = new AiTranslationTask(); t.setSourceLang(sourceLang); t.setTargetLang(targetLang); t.setSourceType(0); t.setSourceContent(text); t.setResultContent("Translated: " + text); t.setStatus(2); save(t); return t;
    }
    @Override public AiTranslationTask translateAttachment(Long fileId, String sourceLang, String targetLang) {
        AiTranslationTask t = new AiTranslationTask(); t.setSourceLang(sourceLang); t.setTargetLang(targetLang); t.setSourceType(1); t.setStatus(1); save(t); return t;
    }
    @Override public Map<String, Object> getStatus(Long taskId) {
        AiTranslationTask t = getById(taskId); Map<String, Object> r = new HashMap<>(); r.put("taskId", taskId); r.put("status", t.getStatus()); r.put("result", t.getResultContent()); return r;
    }
    @Override public String detectLanguage(String text) { return "zh"; }
}
