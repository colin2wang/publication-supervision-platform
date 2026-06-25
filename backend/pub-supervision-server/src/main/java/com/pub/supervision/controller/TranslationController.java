package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.ai.AiTranslationTask;
import com.pub.supervision.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "翻译服务")
@RestController
@RequestMapping("/api/v1/ai/translate")
public class TranslationController {
    private final TranslationService translationService;
    public TranslationController(TranslationService translationService) { this.translationService = translationService; }

    @Operation(summary = "文本翻译")
    @PostMapping("/text")
    public R<AiTranslationTask> translateText(@RequestBody Map<String, String> params) { return R.ok(translationService.translateText(params.get("text"), params.get("sourceLang"), params.get("targetLang"))); }
    @Operation(summary = "附件翻译")
    @PostMapping("/attachment")
    public R<AiTranslationTask> translateAttachment(@RequestBody Map<String, Object> params) { return R.ok(translationService.translateAttachment((Long) params.get("fileId"), (String) params.get("sourceLang"), (String) params.get("targetLang"))); }
    @Operation(summary = "翻译状态")
    @GetMapping("/tasks/{id}")
    public R<Map<String, Object>> getStatus(@PathVariable Long id) { return R.ok(translationService.getStatus(id)); }
}
