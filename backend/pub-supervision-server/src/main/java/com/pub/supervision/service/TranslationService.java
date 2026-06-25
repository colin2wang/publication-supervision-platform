package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiTranslationTask;
import java.util.Map;

public interface TranslationService extends IService<AiTranslationTask> {
    AiTranslationTask translateText(String text, String sourceLang, String targetLang);
    AiTranslationTask translateAttachment(Long fileId, String sourceLang, String targetLang);
    Map<String, Object> getStatus(Long taskId);
    String detectLanguage(String text);
}
