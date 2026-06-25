package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.ai.AiConversation;
import com.pub.supervision.entity.ai.AiDigitalHuman;
import com.pub.supervision.service.DigitalHumanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Tag(name = "数字人管理")
@RestController
@RequestMapping("/api/v1/ai/digital-humans")
public class DigitalHumanController {
    private final DigitalHumanService dhService;
    public DigitalHumanController(DigitalHumanService dhService) { this.dhService = dhService; }

    @Operation(summary = "数字人列表")
    @GetMapping
    public R<List<AiDigitalHuman>> list() { return R.ok(dhService.list()); }
    @Operation(summary = "数字人详情")
    @GetMapping("/{id}")
    public R<AiDigitalHuman> getById(@PathVariable Long id) { return R.ok(dhService.getById(id)); }
    @Operation(summary = "创建数字人")
    @PostMapping
    public R<Void> create(@RequestBody AiDigitalHuman human) { dhService.create(human); return R.ok(); }
    @Operation(summary = "对话")
    @PostMapping("/{id}/conversations")
    public R<Map<String, Object>> chat(@PathVariable Long id, @RequestBody Map<String, String> params) { return R.ok(dhService.chat(id, SecurityUtils.getCurrentUserId(), params.get("message"))); }
    @Operation(summary = "获取对话历史")
    @GetMapping("/{id}/conversations")
    public R<List<AiConversation>> history(@PathVariable Long id) { return R.ok(dhService.getHistory(id, SecurityUtils.getCurrentUserId())); }
}
