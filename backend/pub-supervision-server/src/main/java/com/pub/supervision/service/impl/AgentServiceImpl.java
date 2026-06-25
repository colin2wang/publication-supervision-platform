package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.ai.AiAgent;
import com.pub.supervision.entity.ai.AiAgentCall;
import com.pub.supervision.mapper.AiAgentMapper;
import com.pub.supervision.mapper.AiAgentCallMapper;
import com.pub.supervision.service.AgentService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AgentServiceImpl extends ServiceImpl<AiAgentMapper, AiAgent> implements AgentService {
    private final AiAgentCallMapper callMapper;
    public AgentServiceImpl(AiAgentCallMapper callMapper) { this.callMapper = callMapper; }

    @Override public PageResult<AiAgent> page(String agentName, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<AiAgent> w = new LambdaQueryWrapper<>();
        w.like(agentName != null && !agentName.isEmpty(), AiAgent::getAgentName, agentName)
         .eq(status != null, AiAgent::getStatus, status).orderByDesc(AiAgent::getCreatedAt);
        Page<AiAgent> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public AiAgent getById(Long id) { AiAgent a = super.getById(id); if (a == null) throw new BusinessException(ResultCode.NOT_FOUND); return a; }
    @Override public void create(AiAgent agent) { agent.setCreatorId(SecurityUtils.getCurrentUserId()); agent.setStatus(0); save(agent); }
    @Override public void update(AiAgent agent) { updateById(agent); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void publish(Long id) { AiAgent a = getById(id); a.setStatus(1); updateById(a); }
    @Override public Map<String, Object> invoke(Long id, String input) {
        AiAgentCall call = new AiAgentCall(); call.setAgentId(id); call.setUserId(SecurityUtils.getCurrentUserId()); call.setInput(input); call.setOutput("Agent response for: " + input); call.setTokenCount(100); call.setCostTime(200L); call.setStatus(1); callMapper.insert(call);
        Map<String, Object> r = new HashMap<>(); r.put("callId", call.getId()); r.put("output", call.getOutput()); return r;
    }
    @Override public List<AiAgentCall> getCalls(Long agentId, int pageNum, int pageSize) {
        LambdaQueryWrapper<AiAgentCall> w = new LambdaQueryWrapper<>(); w.eq(AiAgentCall::getAgentId, agentId).orderByDesc(AiAgentCall::getCreatedAt);
        return callMapper.selectPage(new Page<>(pageNum, pageSize), w).getRecords();
    }
}
