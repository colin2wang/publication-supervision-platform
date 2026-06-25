package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.ai.AiDigitalHuman;
import com.pub.supervision.entity.ai.AiConversation;
import com.pub.supervision.mapper.AiDigitalHumanMapper;
import com.pub.supervision.mapper.AiConversationMapper;
import com.pub.supervision.service.DigitalHumanService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DigitalHumanServiceImpl extends ServiceImpl<AiDigitalHumanMapper, AiDigitalHuman> implements DigitalHumanService {
    private final AiConversationMapper conversationMapper;
    public DigitalHumanServiceImpl(AiConversationMapper conversationMapper) { this.conversationMapper = conversationMapper; }

    @Override public List<AiDigitalHuman> list() { return baseMapper.selectList(null); }
    @Override public AiDigitalHuman getById(Long id) { return super.getById(id); }
    @Override public void create(AiDigitalHuman human) { save(human); }
    @Override public Map<String, Object> chat(Long humanId, Long userId, String message) {
        AiConversation userMsg = new AiConversation(); userMsg.setDigitalHumanId(humanId); userMsg.setUserId(userId); userMsg.setRole("user"); userMsg.setContent(message); conversationMapper.insert(userMsg);
        AiConversation aiMsg = new AiConversation(); aiMsg.setDigitalHumanId(humanId); aiMsg.setUserId(userId); aiMsg.setRole("assistant"); aiMsg.setContent("Digital human response to: " + message); conversationMapper.insert(aiMsg);
        Map<String, Object> r = new HashMap<>(); r.put("response", aiMsg.getContent()); return r;
    }
    @Override public List<AiConversation> getHistory(Long humanId, Long userId) {
        LambdaQueryWrapper<AiConversation> w = new LambdaQueryWrapper<>(); w.eq(AiConversation::getDigitalHumanId, humanId).eq(AiConversation::getUserId, userId).orderByAsc(AiConversation::getCreatedAt); return conversationMapper.selectList(w);
    }
}
