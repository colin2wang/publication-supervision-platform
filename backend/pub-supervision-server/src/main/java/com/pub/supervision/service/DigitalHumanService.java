package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiDigitalHuman;
import com.pub.supervision.entity.ai.AiConversation;
import java.util.List;
import java.util.Map;

public interface DigitalHumanService extends IService<AiDigitalHuman> {
    List<AiDigitalHuman> list();
    AiDigitalHuman getById(Long id);
    void create(AiDigitalHuman human);
    Map<String, Object> chat(Long humanId, Long userId, String message);
    List<AiConversation> getHistory(Long humanId, Long userId);
}
