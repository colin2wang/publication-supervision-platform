package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.ai.AiQualityRule;
import com.pub.supervision.service.QualityRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "质量检测")
@RestController
@RequestMapping("/api/v1/ai/quality")
public class QualityController {
    private final QualityRuleService ruleService;
    public QualityController(QualityRuleService ruleService) { this.ruleService = ruleService; }

    @Operation(summary = "规则列表")
    @GetMapping("/rules")
    public R<PageResult<AiQualityRule>> rules(@RequestParam(required = false) String ruleName, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(ruleService.page(ruleName, status, pageNum, pageSize));
    }
    @Operation(summary = "创建规则")
    @PostMapping("/rules")
    public R<Void> createRule(@RequestBody AiQualityRule rule) { ruleService.create(rule); return R.ok(); }
    @Operation(summary = "更新规则")
    @PutMapping("/rules/{id}")
    public R<Void> updateRule(@PathVariable Long id, @RequestBody AiQualityRule rule) { rule.setId(id); ruleService.update(rule); return R.ok(); }
    @Operation(summary = "删除规则")
    @DeleteMapping("/rules/{id}")
    public R<Void> deleteRule(@PathVariable Long id) { ruleService.delete(id); return R.ok(); }
    @Operation(summary = "执行检测")
    @PostMapping("/check")
    public R<Map<String, Object>> check(@RequestBody Map<String, Object> params) { return R.ok(ruleService.executeCheck((Long) params.get("ruleId"), (String) params.get("content"))); }
}
