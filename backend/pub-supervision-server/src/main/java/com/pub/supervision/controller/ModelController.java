package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.ai.AiModel;
import com.pub.supervision.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Tag(name = "模型管理")
@RestController
@RequestMapping("/api/v1/ai/models")
public class ModelController {
    private final ModelService modelService;
    public ModelController(ModelService modelService) { this.modelService = modelService; }

    @Operation(summary = "模型列表")
    @GetMapping
    public R<List<AiModel>> list(@RequestParam(required = false) String modelType) { return R.ok(modelService.list(modelType)); }
    @Operation(summary = "注册模型")
    @PostMapping
    public R<AiModel> register(@RequestBody AiModel model) { return R.ok(modelService.register(model)); }
    @Operation(summary = "测试模型")
    @PostMapping("/{id}/test")
    public R<Map<String, Object>> test(@PathVariable Long id, @RequestBody Map<String, String> params) { return R.ok(modelService.test(id, params.get("input"))); }
    @Operation(summary = "构建模型")
    @PostMapping("/{id}/build")
    public R<Void> build(@PathVariable Long id) { modelService.build(id); return R.ok(); }
}
