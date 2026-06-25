package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "舆情分析")
@RestController
@RequestMapping("/api/v1/opinion/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;
    public AnalysisController(AnalysisService analysisService) { this.analysisService = analysisService; }

    @Operation(summary = "情感趋势")
    @GetMapping("/sentiment-trend")
    public R<Map<String, Object>> sentimentTrend(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(analysisService.sentimentTrend(startDate, endDate)); }
    @Operation(summary = "传播分析")
    @GetMapping("/propagation")
    public R<Map<String, Object>> propagation(@RequestParam Long opinionId) { return R.ok(analysisService.propagation(opinionId)); }
    @Operation(summary = "关联分析")
    @GetMapping("/correlation")
    public R<Map<String, Object>> correlation(@RequestParam String keyword) { return R.ok(analysisService.correlation(keyword)); }
    @Operation(summary = "热点话题")
    @GetMapping("/hot-topics")
    public R<Map<String, Object>> hotTopics(@RequestParam(defaultValue = "10") int topN) { return R.ok(analysisService.hotTopics(topN)); }
    @Operation(summary = "预测")
    @PostMapping("/predict")
    public R<Map<String, Object>> predict(@RequestBody Map<String, String> params) { return R.ok(analysisService.predict(params.get("keyword"))); }
}
