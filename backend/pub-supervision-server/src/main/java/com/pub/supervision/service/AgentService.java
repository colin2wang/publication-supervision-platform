package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiAgent;
import com.pub.supervision.entity.ai.AiAgentCall;
import com.pub.supervision.common.result.PageResult;
import java.util.List;
import java.util.Map;

public interface AgentService extends IService<AiAgent> {
    PageResult<AiAgent> page(String agentName, Integer status, int pageNum, int pageSize);
    AiAgent getById(Long id);
    void create(AiAgent agent);
    void update(AiAgent agent);
    void delete(Long id);
    void publish(Long id);
    Map<String, Object> invoke(Long id, String input);
    List<AiAgentCall> getCalls(Long agentId, int pageNum, int pageSize);
}
