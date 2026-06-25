package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.ai.AiInterceptionLog;
import com.pub.supervision.service.SensitiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "敏感词管理")
@RestController
@RequestMapping("/api/v1/ai/sensitive")
public class SensitiveController {
    private final SensitiveService sensitiveService;
    public SensitiveController(SensitiveService sensitiveService) { this.sensitiveService = sensitiveService; }

    @Operation(summary = "文本检测")
    @PostMapping("/text")
    public R<Map<String, Object>> textCheck(@RequestBody Map<String, String> params) { return R.ok(sensitiveService.textCheck(params.get("text"))); }
    @Operation(summary = "图片检测")
    @PostMapping("/image")
    public R<Map<String, Object>> imageCheck(@RequestBody Map<String, String> params) { return R.ok(sensitiveService.imageCheck(params.get("imageUrl"))); }
    @Operation(summary = "检测日志")
    @GetMapping("/logs")
    public R<PageResult<AiInterceptionLog>> logs(@RequestParam(required = false) String source, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(sensitiveService.getLogs(source, pageNum, pageSize));
    }
}
