package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.ai.AiAgent;
import com.pub.supervision.entity.ai.AiAgentCall;
import com.pub.supervision.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Tag(name = "智能体管理")
@RestController
@RequestMapping("/api/v1/ai/agents")
public class AgentController {
    private final AgentService agentService;
    public AgentController(AgentService agentService) { this.agentService = agentService; }

    @Operation(summary = "智能体列表")
    @GetMapping
    public R<PageResult<AiAgent>> list(@RequestParam(required = false) String agentName, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(agentService.page(agentName, status, pageNum, pageSize));
    }
    @Operation(summary = "智能体详情")
    @GetMapping("/{id}")
    public R<AiAgent> getById(@PathVariable Long id) { return R.ok(agentService.getById(id)); }
    @Operation(summary = "创建智能体")
    @PostMapping
    public R<Void> create(@RequestBody AiAgent agent) { agentService.create(agent); return R.ok(); }
    @Operation(summary = "更新智能体")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody AiAgent agent) { agent.setId(id); agentService.update(agent); return R.ok(); }
    @Operation(summary = "删除智能体")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { agentService.delete(id); return R.ok(); }
    @Operation(summary = "发布智能体")
    @PostMapping("/{id}/publish")
    public R<Void> publish(@PathVariable Long id) { agentService.publish(id); return R.ok(); }
    @Operation(summary = "调用智能体")
    @PostMapping("/{id}/invoke")
    public R<Map<String, Object>> invoke(@PathVariable Long id, @RequestBody Map<String, String> params) { return R.ok(agentService.invoke(id, params.get("input"))); }
    @Operation(summary = "调用记录")
    @GetMapping("/{id}/calls")
    public R<List<AiAgentCall>> calls(@PathVariable Long id, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) { return R.ok(agentService.getCalls(id, pageNum, pageSize)); }
}
